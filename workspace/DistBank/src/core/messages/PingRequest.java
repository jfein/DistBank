package core.messages;

import core.app.AppId;

/**
 * Ping request is a simple NOP that will trigger an app to synchronize to its
 * backups.
 */
public class PingRequest extends Request {

	private static final long serialVersionUID = 2571359217122746125L;

	public PingRequest(AppId<?> senderAppId, AppId<?> receiverAppId) {
		super(senderAppId, receiverAppId);
	}

}
