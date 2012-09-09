package abstraction.distsys;

import abstraction.network.common.NetworkInterface;
import abstraction.network.server.Server;

public abstract class Runtime {

	private static Runtime runtime;

	public static Runtime getRuntime() {
		return runtime;
	}

	private Server server;
	private State state;
	private NetworkInterface networkInterface;

	public Runtime(Server server, State state, NetworkInterface networkInterface) {
		this.server = server;
		this.state = state;
		this.networkInterface = networkInterface;

		Runtime.runtime = this;
	}

	public Server getServer() {
		return server;
	}

	public State getState() {
		return state;
	}

	public NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

}
