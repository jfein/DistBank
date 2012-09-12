package core.network.server;

import java.io.IOException;

import core.distsys.NodeRuntime;
import core.distsys.State;
import core.distsys.NodeId;
import core.distsys.Topology;

public class ServerNodeRuntime extends NodeRuntime {

	public static void init(NodeId id, State state, ServerHandler handler)
			throws IOException {
		NodeRuntime.init(id, state);
		new Server(Topology.getAddress(id), handler).start();
		System.out.println("In node id: " + id);
	}

}
