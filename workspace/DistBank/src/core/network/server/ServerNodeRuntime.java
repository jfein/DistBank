package core.network.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import core.distsys.NodeRuntime;
import core.distsys.State;
import core.distsys.NodeId;
import core.distsys.Topology;

public class ServerNodeRuntime extends NodeRuntime {

	public ServerNodeRuntime(NodeId id, State state, ServerHandler handler)
			throws IOException {
		super(id, state);
		new Server(Topology.getAddress(id), handler).start();
	}

}
