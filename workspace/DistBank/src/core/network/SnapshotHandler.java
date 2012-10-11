package core.network;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import core.node.NodeId;
import core.node.NodeRuntime;
import core.node.NodeState;
import core.network.messages.DisplaySnapshotRequest;
import core.network.messages.Message;
import core.network.messages.SnapshotMessage;

public class SnapshotHandler {

	private boolean enabled;
	private boolean isInSnapshotMode;

	private Set<NodeId> waitingOn;
	private NodeState copyNodeState;
	private List<Message> incomingMessages;

	public SnapshotHandler(boolean enabled) {
		this.enabled = enabled;
		isInSnapshotMode = false;
	}

	public void enterSnapshotMode() {
		System.out.println("\tcalled start snapshot");

		incomingMessages = new LinkedList<Message>();
		copyNodeState = NodeRuntime.getState().copy();
		waitingOn = new HashSet<NodeId>(NodeRuntime.getNetworkInterface()
				.whoNeighborsIn());
		isInSnapshotMode = true;

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

	public void leaveSnapshotMode() {
		System.out.println("\tcalled finish snapshot");

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

		// End snapshot state
		isInSnapshotMode = false;

		System.out.println("\tcompleted finish snapshot");
	}

	public synchronized void processMessage(Message msgIn) {
		// Snapshot message
		if (msgIn instanceof SnapshotMessage) {
			// node in snapshot mode
			if (!isInSnapshotMode) {
				System.out.println("GOT TAKE SNAPSHOT MESSAGE FOR FIRST TIME");
				// enabled, so go to snapshot mode
				if (enabled)
					enterSnapshotMode();

				broadcastSnapshotMessage();
			}

			// enabled, so plug the channel
			if (enabled) {
				System.out.println("GOT TAKE SNAPSHOT TO PLUG A CHANNEL IN");

				// no longer waiting for snapshot msg from that channelIn
				waitingOn.remove(msgIn.getSenderId());

				// finished with snapshot
				if (waitingOn.isEmpty())
					leaveSnapshotMode();
			}
		}
		// Other message
		else {
			// enabled in snapshot mode and is a message we need to record
			if (enabled && isInSnapshotMode
					&& waitingOn.contains(msgIn.getSenderId())) {
				System.out.println("RECORDING MESSAGE");
				incomingMessages.add(msgIn);
			}
		}
	}
}
