package core.node;

import oracle.OracleApp;
import core.app.App;
import core.network.NetworkInterface;

public class NodeRuntime implements Runnable {

	public static final String NODES_FILE = "nodes.txt";
	public static final String TOPOLOGY_FILE = "topology_file.txt";
	public static final String APPS_FILE = "apps.txt";

	private static NodeId id;
	private static NetworkInterface networkInterface;
	private static AppManager<? extends App<?>> appManager;
	private static Configurator configurator;

	public static NodeId getId() {
		return id;
	}

	public static NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	public static AppManager<?> getAppManager() {
		return appManager;
	}

	public static Configurator getConfigurator() {
		return configurator;
	}

	public <A extends App<?>> NodeRuntime(NodeId id, Class<A> appClass) throws Exception {
		NodeRuntime.id = id;
		NodeRuntime.networkInterface = new NetworkInterface();
		NodeRuntime.appManager = new AppManager<A>(appClass);
		// Create configurator if we are not the oracle
		if (!appClass.equals(OracleApp.class))
			NodeRuntime.configurator = new Configurator();
	}

	public void run() {
		System.out.println("NodeRuntime starting node ID " + NodeRuntime.id + " to listen on address "
				+ NodeRuntime.networkInterface.getNodeAddress(id));

		// Run network to take in new connections
		new Thread(networkInterface).start();

		// Start configurator
		if (configurator != null) {
			new Thread(configurator).start();
			configurator.initialize();
		}

		// Start message handling threads
		appManager.startApps();

	}

}
