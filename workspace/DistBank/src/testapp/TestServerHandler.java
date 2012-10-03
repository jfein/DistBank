package testapp;

import core.network.RequestHandler;
import core.node.NodeRuntime;

public class TestServerHandler extends RequestHandler {

	public Response handle(SetRequest req) {
		TestState state = NodeRuntime.getNodeState();
		state.setX(req.getX());
		return new Response(state.getX());
	}

	public Response handle(GetRequest req) {
		TestState state = NodeRuntime.getNodeState();
		return new Response(state.getX());
	}

}
