package test;

import core.distsys.NodeId;
import core.network.client.Client;

public class TestClient extends Client {

	public static int getX(NodeId id) {
		GetRequest req = new GetRequest();
		Response resp = TestClient.exec(id, req);
		return resp.getX();
	}

	public static void setX(NodeId id, int x) {
		SetRequest req = new SetRequest(x);
		TestClient.exec(id, req);
	}

}
