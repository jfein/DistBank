package testapp;

import core.app.AppId;
import core.messages.Request;

public class GetRequest extends Request {

	public GetRequest(AppId<?> senderAppId, AppId<TestApp> receiverAppId) {
		super(senderAppId, receiverAppId);
	}

	private static final long serialVersionUID = -1509507339425180933L;

}
