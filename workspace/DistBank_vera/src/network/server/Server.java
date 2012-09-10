package network.server;

import network.common.Request;
import network.common.Response;
import distsys.Runtime;

public class Server {

	public static Response setX(Request req) {
		int newX = (Integer) req.fields.get("x");
		Runtime.getRuntime().getState().setX(newX);
		return new Response(0);
	}

	public static Response getX(Request req) {
		int x = Runtime.getRuntime().getState().getX();
		Response resp = new Response(0);
		resp.fields.put("x", x);
		return resp;
	}

}
