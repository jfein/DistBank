package core.node;

import java.util.LinkedList;
import java.util.List;

import core.network.messages.DisplaySnapshotRequest;
import core.network.messages.Message;
import core.network.messages.SnapshotMessage;

public class SnapshotHandler {

	private boolean takingSnapshot;

	private List<NodeId> unpluggedChannelIns;
	private NodeState copyNodeState;
	private List<Message> incomingMessages;

	public SnapshotHandler() {
		takingSnapshot = false;
		unpluggedChannelIns = new LinkedList<NodeId>();
		incomingMessages = new LinkedList<Message>();
		copyNodeState = null;
	}

	/**
	 * startTakingSnapshot: Initiates the snapshot algorithm by initializing
	 * variables that will be storing or recording state of this branch. Makes a
	 * copy of the current branch state. Initiates a list of incoming channels
	 * from which we will be recording any incoming transactions. Initiates an
	 * empty list that will store recorded messages
	 */
	public void enterSnapshotMode() {
		System.out.println("\tcalled start snapshot");

		// Not currently taking a snapshot, so record snapshot
		if (!takingSnapshot) {
			incomingMessages.clear();
			copyNodeState = null;
			if (NodeRuntime.getState() != null)
				copyNodeState = NodeRuntime.getState().copy();
		}

		unpluggedChannelIns.addAll(NodeRuntime.getNetworkInterface()
				.whoNeighborsIn());

		takingSnapshot = true;

		// Broadcast a take snapshot message to plug all of our channel outs
		broadcastSnapshotMessage();

		System.out.println("\tcompleted start snapshot");
	}

	public void leaveSnapshotMode() {
		System.out.println("\tcalled end snapshot");

		takingSnapshot = false;

		if (copyNodeState != null) {
			broadcastDisplaySnapshotRequest();
		}

		System.out.println("\tcompleted end snapshot");
	}

	/**
	 * broadcastSnapshotMessage: Broadcast a SnapshotMessage to all outgoing
	 * channels.
	 */
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

	/**
	 * broadcastDisplaySnapshotDisplay: Broadcast a DisplaySnapshotRequest to
	 * all outgoing channels. It will only be proccessed by GUI nodes who can
	 * handle the DisplaySnapshotRequests.
	 */
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

	/**
	 * processMessage: -If the current node is not in snapshot state, then
	 * initiate snapshot -Then change the state of this node to snapshot state.
	 * -Broadcast the snapshot messages to outgoing nodes in topology. -Then
	 * remove it from the channels into this branch that we are waiting on which
	 * will stop recording any transactions received from this branch. If
	 * current node is not enabled, we pass on the snapshot message by
	 * broadcasting, but we do not record any state. This is used for GUI nodes.
	 * 
	 * @param msgIn
	 */
	public synchronized void processMessage(Message msgIn) {
		// Snapshot message
		System.out.println("Message received: " + msgIn);
		if (msgIn instanceof SnapshotMessage) {

			// Not a plug on the channelIn, so enter a new snapshot mode
			if (!unpluggedChannelIns.contains(msgIn.getSenderId())) {
				System.out.println("GOT TAKE NEW SNAPSHOT MESSAGE FROM "
						+ msgIn.getSenderId());
				enterSnapshotMode();
			}

			// Plug the channel in that sent the message
			System.out.println("PLUGGING A CHANNEL IN FROM "
					+ msgIn.getSenderId());
			unpluggedChannelIns.remove(msgIn.getSenderId());

			// finished taking snapshot
			if (unpluggedChannelIns.isEmpty()) {
				leaveSnapshotMode();
				System.out.println("---------------------------------\n\n");
			}
		}

		// Regular message
		// record if in snapshot mode and the channel in isn't plugged
		else if (takingSnapshot
				&& unpluggedChannelIns.contains(msgIn.getSenderId())) {
			System.out.println("RECORDING MESSAGE");
			incomingMessages.add(msgIn);
		}
	}

	public void initiateSnapshot() {
		processMessage(new SnapshotMessage());
	}
}
