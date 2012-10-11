package core.network.messages;

import java.util.List;
/**
 * DisplaySnapshotRequest
 * This message is sent from a branch server to a gui client
 * once a snapshot algorithm is finished. This message includes the 
 * local branch state inclusive of the branch state taken at the time
 * of initation of snapshot and all the transactions in progress that have
 * occured after the initation of snapshot but before the snapshot ended.
 */

import core.node.NodeState;

public class DisplaySnapshotRequest extends Request {

	private static final long serialVersionUID = 181104943999835599L;

	private NodeState nodeState;
	private List<Message> incomingMessages;

	public DisplaySnapshotRequest(NodeState nodeState,
			List<Message> incomingMessages) {
		this.nodeState = nodeState;
		this.incomingMessages = incomingMessages;
	}

	public <T extends NodeState> T getNodeState() {
		return (T) nodeState;
	}

	public List<Message> getIncomingMessages() {
		return incomingMessages;
	}

}
