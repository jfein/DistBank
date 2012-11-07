package core.messages;

import core.app.AppId;

public abstract class Request extends AppMessage {

	private static final long serialVersionUID = 1L;

	private AppId senderAppId;

	public Request(AppId senderAppId, AppId receiverAppId) {
		super(receiverAppId);
		this.senderAppId = senderAppId;
	}

	public AppId getSenderAppId() {
		return senderAppId;
	}

}
