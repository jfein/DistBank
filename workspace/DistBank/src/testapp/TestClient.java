package testapp;

import core.network.Client;
import core.node.NodeId;

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
