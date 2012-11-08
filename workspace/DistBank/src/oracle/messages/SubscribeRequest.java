package oracle.messages;

import oracle.OracleApp;
import core.app.AppId;
import core.messages.Request;
import core.node.NodeId;

/**
 * Request that configurator sends to an oracle when a node starts up. Contains
 * a nodeOfInterest node id. When oracle gets this request, will mark the sender
 * as subscribed to nodeOfInterest, so the sender will receive any failure or
 * recovery information about the nodeOfInterest. Oracle will send back a
 * SubscribeResponse. This response holds whether or not the nodeOfInterest is
 * already failed, so the sending node will have an up to date system view on
 * startup.
 */
public class SubscribeRequest extends Request {

	private static final long serialVersionUID = -1812124901168541643L;

	private NodeId nodeOfInterest;

	public SubscribeRequest(AppId<OracleApp> receiverAppId, NodeId nodeOfInterest) {
		super(null, receiverAppId); // sender app id null since is configurator
		this.nodeOfInterest = nodeOfInterest;
	}

	public NodeId getNodeOfInterest() {
		return nodeOfInterest;
	}

}
