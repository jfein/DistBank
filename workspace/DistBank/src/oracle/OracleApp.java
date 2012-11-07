package oracle;

import oracle.messages.SubscribeRequest;
import core.app.App;
import core.app.AppId;

public class OracleApp extends App<OracleGuiController> {

	public OracleApp(AppId appId) {
		super(appId);
	}

	@Override
	protected OracleGuiController newState() {
		OracleGuiController controller = new OracleGuiController(getAppId());
		controller.run();
		return controller;
	}

	public void handleRequest(SubscribeRequest req) {
		System.out.println("New subscription from " + req.getSenderNodeId());
		OracleState state = getState().getOracleState();
		state.processSubscription(req.getSenderNodeId(), req.getNodeOfInterest());
	}
}
