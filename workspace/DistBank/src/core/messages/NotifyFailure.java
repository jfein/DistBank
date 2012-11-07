package core.messages;

import core.node.NodeId;

public class NotifyFailure extends Request {

	private static final long serialVersionUID = 7436631534213464565L;

	private NodeId failedNode;

	public NotifyFailure(NodeId failedNode) {
		super(null, null);
		this.failedNode = failedNode;
	}

	public NodeId getFailedNode() {
		return failedNode;
	}

}
