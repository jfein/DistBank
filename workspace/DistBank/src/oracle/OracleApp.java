package oracle;

import oracle.messages.SubscribeRequest;
import oracle.messages.SubscribeResponse;
import core.app.App;
import core.app.AppId;

public class OracleApp extends App<OracleGuiController> {

	public OracleApp(AppId appId) {
		super(appId);
	}

	@Override
	protected OracleGuiController newState() {
		return new OracleGuiController();
	}

	public SubscribeResponse handleRequest(SubscribeRequest req) {
		System.out.println("New subscription from " + req.getSenderNodeId());
		OracleState state = getState().getOracleState();
		boolean isFailed = state.processSubscription(req.getSenderNodeId(), req.getNodeOfInterest());
		return new SubscribeResponse(isFailed);
	}

}
