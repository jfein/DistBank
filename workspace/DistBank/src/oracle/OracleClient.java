package oracle;

import oracle.messages.SubscribeRequest;
import oracle.messages.SubscribeResponse;
import core.app.Client;
import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * Client class for any Configurator to send a SubscribeRequest to the oracle.
 */
public class OracleClient extends Client {

	public static Boolean subscribe(NodeId nodeOfInterest) {
		SubscribeRequest req = new SubscribeRequest(NodeRuntime.oracleAppId, nodeOfInterest);
		SubscribeResponse resp = OracleClient.exec(req);
		if (resp == null)
			return null;
		return resp.isFailed();
	}

}
