package core.messages;

import core.app.AppId;

/**
 * A response that a backup will sent back to a primary after finishing handling
 * a SynchRequest.
 */
public class SynchResponse extends Response {

	private static final long serialVersionUID = -3922420857205997712L;

	public SynchResponse(AppId<?> receiverAppId) {
		super(receiverAppId);
	}
}
