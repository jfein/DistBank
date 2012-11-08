package core.messages;

import core.app.AppId;

/**
 * An "ack" to a PingRequest.
 */
public class PingResponse extends Response {

	private static final long serialVersionUID = -8503773616489611646L;

	public PingResponse(AppId<?> receiverAppId) {
		super(receiverAppId);
	}

}
