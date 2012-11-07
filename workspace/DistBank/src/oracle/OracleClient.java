package oracle;

import oracle.messages.SubscribeRequest;
import oracle.messages.SubscribeResponse;
import core.app.Client;
import core.node.NodeId;
import core.node.NodeRuntime;

public class OracleClient extends Client {

	public static SubscribeResponse subscribe(NodeId nodeOfInterest) {
		SubscribeRequest req = new SubscribeRequest(NodeRuntime.oracleAppId, nodeOfInterest);
		return OracleClient.exec(req);
	}

}
