package core.messages;

import core.node.NodeId;

public class NotifyRecovery extends Request {

	private static final long serialVersionUID = -3769673078317003531L;

	private NodeId recoveredNode;

	public NotifyRecovery(NodeId failedNode) {
		super(null, null);
		this.recoveredNode = failedNode;
	}

	public NodeId getRecoveredNode() {
		return recoveredNode;
	}
}
