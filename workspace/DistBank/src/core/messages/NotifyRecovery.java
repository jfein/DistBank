package core.messages;

import core.node.NodeId;
import core.node.NodeRuntime;

public class NotifyRecovery extends Request {

	private static final long serialVersionUID = -3769673078317003531L;

	private NodeId recoveredNode;

	public NotifyRecovery(NodeId failedNode) {
		super(NodeRuntime.oracleAppId, null);
		this.recoveredNode = failedNode;
	}

	public NodeId getRecoveredNode() {
		return recoveredNode;
	}
}
