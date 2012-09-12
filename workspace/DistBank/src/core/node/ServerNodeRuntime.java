package core.node;

import java.io.IOException;

import core.network.common.Topology;
import core.network.server.Server;
import core.network.server.ServerHandler;

public class ServerNodeRuntime extends NodeRuntime {

	public static void init(NodeId id, NodeState state, ServerHandler handler)
			throws IOException {
		NodeRuntime.init(id, state);
		new Server(Topology.getAddress(id), handler).start();
	}

}