package core.network.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import core.distsys.NodeRuntime;
import core.distsys.State;

public class ServerNodeRuntime extends NodeRuntime {

	public ServerNodeRuntime(InetSocketAddress myAddress, State state, ServerHandler handler) throws IOException {
		super(myAddress, state);
		new Server(myAddress, handler).start();
	}

}
