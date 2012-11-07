package core.messages;

import core.app.AppId;

public abstract class Response extends Message {

	private static final long serialVersionUID = -5624023138287704166L;

	public Response(AppId<?> receiverAppId) {
		super(receiverAppId);
	}

}
