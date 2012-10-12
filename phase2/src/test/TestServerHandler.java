package test;

import core.network.MessageHandler;
import core.node.NodeRuntime;

public class TestServerHandler extends MessageHandler {

	public Response handle(SetRequest req) {
		TestState state = NodeRuntime.getState();
		state.setX(req.getX());
		return new Response(state.getX());
	}

	public Response handle(GetRequest req) {
		TestState state = NodeRuntime.getState();
		return new Response(state.getX());
	}

}
