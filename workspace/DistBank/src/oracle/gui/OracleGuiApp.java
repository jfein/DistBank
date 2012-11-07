package oracle.gui;

import oracle.OracleState;
import oracle.messages.SubscriptionRequest;
import core.app.App;
import core.app.AppId;

	public class OracleGuiApp extends App<OracleController> {

		public OracleGuiApp(AppId appId) {
			super(appId);
		}

		@Override
		protected OracleController newState() {
			OracleController controller = new OracleController(getAppId());
			new Thread(controller).start();
			return controller;
		}

	
		 //Only request we need here is like a request to notify the objects we are interested in watching on startup/recovery
		public void handleRequest(SubscriptionRequest req) {
			OracleState state = getState().getOracleState();
			state.processSubscription(req.getSenderNodeId(), req.getSubscriptionList());
		}
	}


