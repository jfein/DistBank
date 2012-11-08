package core.messages;

import core.app.AppId;

public class PingResponse extends Response {

	private static final long serialVersionUID = -8503773616489611646L;

	public PingResponse(AppId<?> receiverAppId) {
		super(receiverAppId);
	}

}
