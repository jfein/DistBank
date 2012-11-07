package core.messages;

import core.app.AppId;

public class SynchResponse extends Response {

	private static final long serialVersionUID = -3922420857205997712L;

	public SynchResponse(AppId<?> receiverAppId) {
		super(receiverAppId);
	}
}
