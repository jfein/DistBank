package oracle.messages;

import core.messages.Response;

/**
 * Response to a SubscribeRequest. Oracle sends this back to a node that just
 * subscribed to a nodeOfInterest. If the nodeOfInterest is already failed, this
 * response will indicate so.
 */
public class SubscribeResponse extends Response {

	private static final long serialVersionUID = -2576993435099359642L;

	boolean isFailed;

	public SubscribeResponse(boolean isFailed) {
		super(null); // Null, since meant for configurator.
		this.isFailed = isFailed;
	}

	public boolean isFailed() {
		return isFailed;
	}

}
