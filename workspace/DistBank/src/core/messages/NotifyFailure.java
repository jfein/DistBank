package core.messages;

import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * Request from the oracle to a node's configurator to notify that some node has
 * failed. Will cause the configurator to register the failure, close any
 * sockets to the remote node, and send back a NotifyFailureResponse as
 * acknowledgment.
 */
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
