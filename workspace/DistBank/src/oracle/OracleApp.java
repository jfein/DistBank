package oracle;

import oracle.messages.SubscribeRequest;
import oracle.messages.SubscribeResponse;
import core.app.App;
import core.app.AppId;

/**
 * An app representing an oracle. Can only take in 1 kind of request - to
 * register a new subscription. Its app state contains the GUI controller which
 * contains the overall OracleState with the failed node information.
 */
public class OracleApp extends App<OracleGuiController> {

	public OracleApp(AppId<OracleApp> appId) {
		super(appId);
	}

	@Override
	protected OracleGuiController newState() {
		return new OracleGuiController();
	}

	public SubscribeResponse handleRequest(SubscribeRequest req) {
		System.out.println("New subscription from " + req.getSenderNodeId() + ", interested in "
				+ req.getNodeOfInterest());
		OracleState state = getState().getOracleState();
		boolean isFailed = state.processSubscription(req.getSenderNodeId(), req.getNodeOfInterest());
		return new SubscribeResponse(isFailed);
	}

}
