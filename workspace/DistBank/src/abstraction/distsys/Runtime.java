package abstraction.distsys;

import java.net.InetSocketAddress;

import abstraction.network.common.NetworkInterface;
import abstraction.network.common.Topology;
import abstraction.network.server.Server;

public abstract class Runtime {

	private static Runtime runtime;

	public static Runtime getRuntime() {
		return runtime;
	}

	private Server server;
	private State state;

	public Runtime(InetSocketAddress address, Server server, State state) {
		Topology.setMyChannels(address);

		this.server = server;
		this.state = state;

		Runtime.runtime = this;

		// Start receiving requests
		new NetworkInterface(address).start();
	}

	public Server getServer() {
		return server;
	}

	public State getState() {
		return state;
	}

}
