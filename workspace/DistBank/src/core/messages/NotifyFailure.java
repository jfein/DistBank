package core.messages;

import core.node.NodeId;
import core.node.NodeRuntime;

public class NotifyFailure extends Request {

	private static final long serialVersionUID = 7436631534213464565L;

	private NodeId failedNode;

	public NotifyFailure(NodeId failedNode) {
		super(NodeRuntime.oracleAppId, null);
		this.failedNode = failedNode;
	}

	public NodeId getFailedNode() {
		return failedNode;
	}

}
