package core.node;

import oracle.messages.FailRequest;
import bank.branch.BranchState;
import core.app.App;
import core.app.AppId;
import core.app.AppState;
import core.network.messages.Response;

public class SystemApp extends App<AppState> {
	public SystemApp(AppId appId) {
		super(appId);
	}

	@Override
	protected AppState newState() {
		// TODO Auto-generated method stub
		return null;
	}

	public void handleRequest(FailRequest req) {
		//if we receive this request,
		// we want to do sys.exit
	}
	
	/*public Response handleRequest(NotifyFailureRequest req) {
		NodeRuntime.getAppManager().removeFailedNode(reg.getSenderId());
	}
	
	public Response handleRequest(NotifyRecoveryRequest req) {
		
	}*/
	
}
