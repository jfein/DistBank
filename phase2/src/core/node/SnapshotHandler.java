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
	 * copy of the current branch state if we are not already taking a snapshot.
	 * Add all channelIns to our unplugged list to wait for them (can contain
	 * duplicates). Initiates an empty list that will store recorded messages if
	 * its not currently in snapshot mode.
	 */
	public void enterSnapshotMode() {
		System.out.println("\tcalled start snapshot");

		// Not currently taking a snapshot, so record state
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
	}

	/**
	 * broadcastDisplaySnapshotDisplay: Broadcast a DisplaySnapshotRequest to
	 * all outgoing channels. It will only be proccessed by GUI nodes who can
	 * handle the DisplaySnapshotRequests.
	 */
	public void broadcastDisplaySnapshotRequest() {
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
	}

	/**
	 * Upon receipt of a snapshot message:
	 * 
	 * - If we are not in snapshot more OR we are and the message is from a
	 * plugged channel In, then we re-enter snapshot mode. This means we wait
	 * for more "plugs" from our channel Ins, and if we are entering for first
	 * time we record our state. Thus, if two snapshots are initiated, we will
	 * get a snapshot message from a plugged channel, and will then wait for
	 * more channels to be plugged possible plugged twice. When entering
	 * snapshot mode, we then broadcat our "take snapshot message", our "plug",
	 * on our channel out.
	 * 
	 * - Then, regardless of previous step, we remove the channel In from the
	 * unplugged list. This essentially plugs the channel the message was sent
	 * from.
	 * 
	 * - If all channels are plugged, we exit snapshot mode. We then broadcast a
	 * "DisplaySnapshotMessage" to all of our channel outs. Since we only have
	 * one GUI, and since only GUIs can process these messages, the GUI will
	 * receive the request and display snapshot info.
	 * 
	 * @param msgIn
	 */
	public synchronized void processMessage(Message msgIn) {
		// Snapshot message
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
			System.out.println("RECORDING MESSAGE: "
					+ msgIn.getClass().getName());
			incomingMessages.add(msgIn);
		}
	}

	public void initiateSnapshot() {
		processMessage(new SnapshotMessage());
	}
}
