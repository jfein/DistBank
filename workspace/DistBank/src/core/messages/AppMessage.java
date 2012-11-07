package core.messages;

import core.app.AppId;

public abstract class AppMessage extends Message {

	private static final long serialVersionUID = -5116316637681312366L;

	private AppId receiverAppId;

	public AppMessage(AppId receiverAppId) {
		this.receiverAppId = receiverAppId;
	}

	public AppId getReceiverAppId() {
		return receiverAppId;
	}

}
