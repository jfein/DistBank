package core.messages;

import core.app.AppId;

/**
 * A blank "ping" message sent to an app. Will cause the app to register the
 * request as an "updateHist", and trigger a SYNCH to all backups. Once that
 * happens, will send a PingResponse.
 */
public class PingRequest extends Request {

	private static final long serialVersionUID = 2571359217122746125L;

	public PingRequest(AppId<?> senderAppId, AppId<?> receiverAppId) {
		super(senderAppId, receiverAppId);
	}

}
