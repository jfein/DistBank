package network.client;

import java.net.SocketAddress;

import network.common.Request;
import network.common.Response;
import distsys.Runtime;

public class Client {

	public static void setX(SocketAddress a, int x) {
		Request req = new Request("setX");
		req.fields.put("x", x);
		Runtime.getRuntime().getNetworkInterface()
				.sendRequestGetResponse(a, req);
	}

	public static int getX(SocketAddress a) {
		Request req = new Request("getX");
		Response resp = Runtime.getRuntime().getNetworkInterface()
				.sendRequestGetResponse(a, req);
		return (Integer) resp.fields.get("x");
	}

}
