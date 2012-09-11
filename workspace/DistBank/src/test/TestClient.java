package test;

import java.net.SocketAddress;

import core.network.client.Client;

public class TestClient extends Client {

	public static int getX(SocketAddress dest) {
		GetRequest req = new GetRequest();
		Response resp = TestClient.exec(dest, req);
		return resp.getX();
	}

	public static void setX(SocketAddress dest, int x) {
		SetRequest req = new SetRequest(x);
		TestClient.exec(dest, req);
	}

}
