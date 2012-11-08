package core.node;

import oracle.OracleApp;
import core.app.App;
import core.app.AppId;
import core.network.NetworkInterface;

/**
 * The static runtime for a JVM node on the network. Holds static variables to
 * be accessed any time from any thread on the node - specifically, the node ID,
 * the NetworkInterface, the AppManager, the configurator, and the oracle's
 * AppId. When run, will start a NetworkInterface thread to take in incoming
 * sockets, a Configurator app thread that will be able to interact with the
 * oracle, and finally one thread for each app on the system.
 * 
 * @author JFein
 * 
 */
public class NodeRuntime implements Runnable {

	public static final String NODES_FILE = "nodes.txt";
	public static final String TOPOLOGY_FILE = "topology_file.txt";
	public static final String APPS_FILE = "apps.txt";

	private static NodeId id;
	private static NetworkInterface networkInterface;
	private static AppManager appManager;
	private static Configurator configurator;

	public static AppId<OracleApp> oracleAppId;

	public static NodeId getId() {
		return id;
	}

	public static NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	public static AppManager getAppManager() {
		return appManager;
	}

	public static Configurator getConfigurator() {
		return configurator;
	}

	public <A extends App<?>> NodeRuntime(NodeId id) throws Exception {
		NodeRuntime.id = id;
		NodeRuntime.networkInterface = new NetworkInterface();
		NodeRuntime.appManager = new AppManager();
		// Create configurator if we are not the oracle
		if (!appManager.isMyApp(oracleAppId))
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
