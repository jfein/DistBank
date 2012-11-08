package core.messages;

import core.app.AppId;

public class NotifyFailureResponse extends Response {

	private static final long serialVersionUID = -8109181926381227534L;

	public NotifyFailureResponse(AppId<?> receiverAppId) {
		super(receiverAppId);
	}

}
