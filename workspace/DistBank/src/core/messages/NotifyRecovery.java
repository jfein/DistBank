package core.messages;

import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * Request from the oracle to a node's configurator to notify that some node that
 * was previously failed has now recovered. Will cause the configurator to
 * register the recovery, which brings the node back up as a backup for any apps
 * on it. If the node receiving this request is the primary to an app that the
 * recovered node is a backup for, will cause the configurator to "ping" that
 * primary app which will trigger the primary app to synchronize the backup.
 * Once all pings happen, this NotifyRecovery request is complete, and the
 * configurator sends back a NotifyRecoveryResponse.
 */
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
