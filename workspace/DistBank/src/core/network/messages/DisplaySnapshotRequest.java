package core.network.messages;

import java.util.List;

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
