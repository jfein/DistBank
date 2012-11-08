package core.messages;

import core.app.AppId;
import core.app.AppState;

/**
 * A request that a primary app will send to a backup app (both will have the
 * same AppId, but will be on different nodes). Contains a serialized AppState,
 * so the backup can simply make the sent AppState its new AppState. Once this
 * happens, the backup will send a SynchResponse back to the primary. Generic
 * type is the type of AppState the request holds.
 */
public class SynchRequest<S extends AppState> extends Request {

	private static final long serialVersionUID = 7286050442184790323L;

	private S state;

	public SynchRequest(AppId<?> senderAppId, AppId<?> receiverAppId, S state) {
		super(senderAppId, receiverAppId);
		this.state = state;
	}

	public S getState() {
		return state;
	}

}
