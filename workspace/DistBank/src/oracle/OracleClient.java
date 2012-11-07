package oracle;


import oracle.messages.FailRequest;
import core.app.AppId;
import core.network.Client;
import core.network.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public class OracleClient extends Client {
//TODO what do we want to do when we don't want a response
		public static Response fail(AppId myAppId, NodeId dest) {
			FailRequest req = new FailRequest();
			return NodeRuntime.getAppManager().getApp(myAppId).sendRequestGetResponse(dest,req);
		}
		
		public static Response notifyRecovery(NodeId nodeId, NodeId destNodei) {
			
		}
}
