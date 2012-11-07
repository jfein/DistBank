package oracle;

import oracle.messages.SubscribeRequest;
import oracle.messages.SubscribeResponse;
import core.app.AppId;
import core.app.Client;
import core.node.NodeId;

public class OracleClient extends Client {

	// TODO: don't hardcode this!
	public static final AppId oracleAppId = new AppId(42);

	public static SubscribeResponse subscribe(NodeId nodeOfInterest) {
		SubscribeRequest req = new SubscribeRequest(oracleAppId, nodeOfInterest);
		return OracleClient.exec(req);
	}

}
