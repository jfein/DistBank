package testapp;

import core.app.AppId;
import core.app.Client;

public class TestClient extends Client {

	public static int getX(AppId<?> myAppId, AppId<TestApp> remoteAppId) {
		GetRequest req = new GetRequest(myAppId, remoteAppId);
		Response resp = TestClient.exec(req);
		return resp.getX();
	}

	public static void setX(AppId<TestApp> id, int x) {
		SetRequest req = new SetRequest(x);
		TestClient.exec(req);
	}

}
