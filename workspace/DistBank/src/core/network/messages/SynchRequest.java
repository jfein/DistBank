package core.network.messages;

import core.app.AppId;
import core.app.AppState;

public class SynchRequest extends Request {

	private static final long serialVersionUID = 7286050442184790323L;

	private AppState state;

	public SynchRequest(AppId receiverAppId, AppState state) {
		super(receiverAppId);
		this.state = state;
	}

	public AppState getState() {
		return state;
	}

}
