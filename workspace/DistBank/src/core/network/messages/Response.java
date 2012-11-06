package core.network.messages;

import core.app.AppId;

public abstract class Response extends AppMessage {

	private static final long serialVersionUID = -5624023138287704166L;

	public Response(AppId receiverAppId) {
		super(receiverAppId);
	}

}
