package oracle.messages;

import core.app.AppId;
import core.messages.Request;
import core.node.NodeId;

public class SubscribeRequest extends Request {

	private static final long serialVersionUID = -1812124901168541643L;

	private NodeId nodeOfInterest;

	public SubscribeRequest(AppId receiverAppId, NodeId nodeOfInterest) {
		super(null, receiverAppId);
		this.nodeOfInterest = nodeOfInterest;
	}

	public NodeId getNodeOfInterest() {
		return nodeOfInterest;
	}

}
