package core.node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import core.network.messages.DisplaySnapshotRequest;
import core.network.messages.Message;
import core.network.messages.SnapshotMessage;

public class SnapshotHandler {

	private boolean enabled;
	private boolean takingSnapshot;

	private Set<NodeId> waitingOn;
	private NodeState copyNodeState;
	private List<Message> incomingMessages;

	public SnapshotHandler(boolean enabled) {
		this.enabled = enabled;
		takingSnapshot = false;
	}

	public void startTakingSnapshot() {
		System.out.println("\tcalled start snapshot");

		incomingMessages = new LinkedList<Message>();
		copyNodeState = NodeRuntime.getState().copy();
		waitingOn = new HashSet<NodeId>(NodeRuntime.getNetworkInterface()
				.whoNeighborsIn());

		System.out.println("\tcompleted start snapshot");
	}

	public void broadcastSnapshotMessage() {
		System.out.println("\tcalled broadcast snapshot msg");

		// Aggregate the snapshot information into a message
		SnapshotMessage msg = new SnapshotMessage();

		// Blast this message to all outgoing channels
		for (NodeId neighbor : NodeRuntime.getNetworkInterface().whoNeighbors()) {
			try {
				NodeRuntime.getNetworkInterface().sendMessage(neighbor, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("\tcompleted broadcast snapshot msg");
	}

	public void broadcastDisplaySnapshotRequest() {
		System.out.println("\tcalled broadcast display snapshot req");

		// Aggregate the snapshot information into a message
		DisplaySnapshotRequest msg = new DisplaySnapshotRequest(copyNodeState,
				incomingMessages);

		// Blast this message to all outgoing channels
		for (NodeId neighbor : NodeRuntime.getNetworkInterface().whoNeighbors()) {
			try {
				NodeRuntime.getNetworkInterface().sendMessage(neighbor, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("\tcompleted broadcast display snapshot req");
	}

	public synchronized void processMessage(Message msgIn) {
		// Snapshot message
		if (msgIn instanceof SnapshotMessage) {
			// node in snapshot mode
			if (!takingSnapshot) {
				System.out.println("GOT TAKE SNAPSHOT MESSAGE FOR FIRST TIME");

				// enabled, so go to snapshot mode
				if (enabled) {
					startTakingSnapshot();
					takingSnapshot = true;
				}

				// broadcast snapshot message regardless if enabled
				// to plug all your channel outs
				broadcastSnapshotMessage();
			}

			// enabled, so plug the channel
			if (enabled) {
				System.out.println("GOT TAKE SNAPSHOT TO PLUG A CHANNEL IN");

				// no longer waiting for snapshot msg from that channelIn
				waitingOn.remove(msgIn.getSenderId());

				// finished taking snapshot
				if (waitingOn.isEmpty()) {
					broadcastDisplaySnapshotRequest();
					takingSnapshot = false;
				}
			}
		}

		// Regular message, record if enabled and in snapshot mode and the
		// channel in isn't plugged
		else if (enabled && takingSnapshot
				&& waitingOn.contains(msgIn.getSenderId())) {
			System.out.println("RECORDING MESSAGE");
			incomingMessages.add(msgIn);
		}
	}
}
