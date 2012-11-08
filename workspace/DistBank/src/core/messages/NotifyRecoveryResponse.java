package core.messages;

import core.app.AppId;

/**
 * An "ack" to a NotifyRecovery request.
 */
public class NotifyRecoveryResponse extends Response {

	private static final long serialVersionUID = 3035137540122471407L;

	public NotifyRecoveryResponse(AppId<?> receiverAppId) {
		super(receiverAppId);
	}

}
