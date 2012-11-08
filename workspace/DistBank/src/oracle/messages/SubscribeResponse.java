package oracle.messages;

import core.messages.Response;

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
