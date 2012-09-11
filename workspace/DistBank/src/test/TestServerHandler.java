package test;

import core.distsys.NodeRuntime;
import core.network.server.ServerHandler;

public class TestServerHandler extends ServerHandler {

	public Response handle(SetRequest req) {
		TestState state = NodeRuntime.getNodeRuntimeState();
		state.setX(req.getX());
		return new Response(state.getX());
	}

	public Response handle(GetRequest req) {
		TestState state = NodeRuntime.getNodeRuntimeState();
		return new Response(state.getX());
	}

}