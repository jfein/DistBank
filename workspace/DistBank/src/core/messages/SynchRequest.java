package core.messages;

import core.app.AppId;
import core.app.AppState;

public class SynchRequest<S extends AppState> extends Request {

	private static final long serialVersionUID = 7286050442184790323L;

	private S state;

	public SynchRequest(AppId senderAppId, AppId receiverAppId, S state) {
		super(senderAppId, receiverAppId);
		this.state = state;
	}

	public S getState() {
		return state;
	}

}
