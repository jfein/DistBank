package core.network.messages;

import core.app.AppId;

public abstract class Request extends Message {

	private static final long serialVersionUID = 1L;

	private AppId receiverAppId;

	public Request(AppId receiverAppId) {
		this.receiverAppId = receiverAppId;
	}

	public AppId getReceiverAppId() {
		return receiverAppId;
	}

}
