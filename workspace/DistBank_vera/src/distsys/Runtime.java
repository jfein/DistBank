package distsys;

import java.net.InetSocketAddress;

import network.client.Client;
import network.common.NetworkInterface;

public class Runtime {

	private State state;
	private NetworkInterface networkInterface;

	public Runtime(int port) {
		state = new State();
		networkInterface = new NetworkInterface("localhost", port);
	}

	public State getState() {
		return state;
	}

	public NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	// ////
	// Static runtime code
	// ////

	private static Runtime runtime;

	public static void main(String[] args) {
		System.out.println("Starting on port " + args[0]);

		runtime = new Runtime(Integer.parseInt(args[0]));

		if (Integer.parseInt(args[0]) == 4001) {
			System.out.println("Doing stuff");
			Client.setX(new InetSocketAddress("localhost", 4000), 10);
			System.out.println("Other X: "
					+ Client.getX(new InetSocketAddress("localhost", 4000)));
		}
	}

	public static Runtime getRuntime() {
		return runtime;
	}

}
