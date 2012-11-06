package core.node;

import core.app.App;
import core.network.NetworkInterface;
import core.network.MessageHandler;

public class NodeRuntime implements Runnable {

	public static final String NODES_FILE = "nodes.txt";
	public static final String TOPOLOGY_FILE = "topology_file.txt";
	public static final String APPS_FILE = "apps.txt";

	private static NodeId id;
	private static NetworkInterface networkInterface;
	private static MessageHandler messageHandler;
	private static AppManager appManager;

	public static NodeId getId() {
		return id;
	}

	public static NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	public static MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public static AppManager getAppManager() {
		return appManager;
	}

	public <A extends App<?>> NodeRuntime(NodeId id, Class<A> appClass)
			throws Exception {
		NodeRuntime.id = id;
		NodeRuntime.messageHandler = new MessageHandler();
		NodeRuntime.networkInterface = new NetworkInterface();
		NodeRuntime.appManager = new AppManager(appClass);
	}

	public void run() {
		System.out.println("NodeRuntime starting node ID " + NodeRuntime.id
				+ " to listen on address "
				+ NodeRuntime.networkInterface.getNodeAddress(id));

		// Run network to take in new connections
		NodeRuntime.networkInterface.run();
	}

}
