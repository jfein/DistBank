package core.node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.app.App;
import core.app.AppId;
import core.app.Client;
import core.messages.PingRequest;
import oracle.OracleApp;

/**
 * Class to hold mappings from AppIDs to NodeIDs. Parses the APP_FILE "apps.txt"
 * to figure out what apps run on what nodes, including which apps to start on
 * this node. This app to node mapping is modified whenever the configurator is
 * notified of a failure or recovery.
 * 
 * Also holds this runtime's specific apps, creates all apps, and starts all app
 * threads. As mentioned above, creates apps based on the APP_FILE.
 */
public class AppManager {

	private Map<AppId<?>, LinkedList<NodeId>> appToNodes;
	private Map<NodeId, LinkedList<AppId<?>>> originalNodeToApps;
	private Map<AppId<?>, App<?>> myApps;

	public AppManager() throws Exception {
		parseAppFile();
		createApps();
	}

	/**
	 * Parses the APPS_FILE in NodeRuntime. Puts the App to Node mapping into
	 * the map appToNodes and originalNodeToApps.
	 */
	private void parseAppFile() {
		appToNodes = new HashMap<AppId<?>, LinkedList<NodeId>>();
		originalNodeToApps = new HashMap<NodeId, LinkedList<AppId<?>>>();

		try {
			BufferedReader read1 = new BufferedReader(new FileReader(NodeRuntime.APPS_FILE));

			read1.readLine();
			while (read1.ready()) {
				String t;
				try {
					t = read1.readLine();

					String parts[] = t.split(" ");

					// TODO: cleanup holding the class object in the appID?
					AppId<?> appId = new AppId(Integer.parseInt(parts[0]), Class.forName(parts[1]));

					// Found the oracle, set its global app ID
					if (parts[1].equals("oracle.OracleApp"))
						NodeRuntime.oracleAppId = (AppId<OracleApp>) appId;

					LinkedList<NodeId> nodeIds = new LinkedList<NodeId>();
					for (int i = 2; i < parts.length; i++) {
						// Add nodeID to app mapping
						NodeId nodeId = new NodeId(Integer.parseInt(parts[i]));
						nodeIds.add(nodeId);

						// Add appID to app mapping
						LinkedList<AppId<?>> apps = originalNodeToApps.get(nodeId);
						if (apps == null) {
							apps = new LinkedList<AppId<?>>();
							originalNodeToApps.put(nodeId, apps);
						}
						apps.add(appId);
					}

					appToNodes.put(appId, nodeIds);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Creates an App for each AppID on this node. Stores the app in the map
	 * myApps. Uses the Class object stored in the appID (parsed from original
	 * APPS_FILE) to instantiate the app.
	 * 
	 * @param appClass
	 * @throws Exception
	 */
	private void createApps() throws Exception {
		myApps = new HashMap<AppId<?>, App<?>>();
		for (Map.Entry<AppId<?>, LinkedList<NodeId>> entry : appToNodes.entrySet()) {
			AppId<?> appId = entry.getKey();
			LinkedList<NodeId> nodes = entry.getValue();
			for (NodeId node : nodes) {
				if (node.equals(NodeRuntime.getId())) {
					// Create app
					App<?> app = appId.getAppClass().getConstructor(AppId.class).newInstance(appId);
					System.out.println("Starting a " + appId.getAppClass().getCanonicalName() + " with App ID #"
							+ appId);
					myApps.put(appId, app);
				}
			}
		}
	}

	/**
	 * Starts all apps in the myApps structure.
	 */
	public void startApps() {
		for (App<?> app : myApps.values()) {
			new Thread(app).start();
		}
	}

	/**
	 * Returns true if the nodeId is a primary or backup to any app (so is
	 * currently alive)
	 * 
	 * @param nodeId
	 * @return
	 */
	public boolean isNodeAlive(NodeId nodeId) {
		synchronized (appToNodes) {
			for (LinkedList<NodeId> nodes : appToNodes.values()) {
				if (nodes.contains(nodeId))
					return true;
			}
			return false;
		}
	}

	/**
	 * Returns true if appID is a primary or a backup on this node.
	 * 
	 * @param appId
	 * @return
	 */
	public boolean isMyApp(AppId<?> appId) {
		return myApps.containsKey(appId);
	}

	/**
	 * Returns the current primary NodeID for a given AppID.
	 * 
	 * @param appId
	 * @return
	 */
	public NodeId appToPrimaryNode(AppId<?> appId) {
		synchronized (appToNodes) {
			LinkedList<NodeId> lst = appToNodes.get(appId);
			if (lst == null)
				return null;
			return lst.peek();
		}
	}

	/**
	 * Returns the current backup NodeIDs for a given AppID.
	 * 
	 * Assumes that you are the current primary and returns all other nodes
	 * mapped to that appID as the backup list.
	 * 
	 * @param appId
	 * @return
	 */
	public List<NodeId> appToBackupNodes(AppId<?> appId) {
		synchronized (appToNodes) {
			LinkedList<NodeId> origNodes = appToNodes.get(appId);
			if (origNodes == null)
				return new LinkedList<NodeId>();
			LinkedList<NodeId> nodes = new LinkedList<NodeId>(origNodes);
			nodes.remove(NodeRuntime.getId());
			return nodes;
		}
	}

	/**
	 * Removes a failed nodeID from appToNodes mapping. Also removes the failed
	 * node from the network interface, which will close all sockets to that
	 * node.
	 * 
	 * @param nodeId
	 */
	public void removeFailedNode(NodeId nodeId) {
		synchronized (appToNodes) {
			for (LinkedList<NodeId> nodes : appToNodes.values())
				nodes.remove(nodeId);
		}

		// Close any channels on this node
		NodeRuntime.getNetworkInterface().removeFailedNode(nodeId);
	}

	/**
	 * Adds a recovered nodeID to appToNodes mapping. If we are the primary to
	 * an app that is on the node, then we ping that app (on this current node)
	 * to force a synch with the newly recovered backup.
	 * 
	 * @param nodeId
	 */
	public void addRecoveredNode(NodeId nodeId) {
		List<AppId<?>> appsToPing = new LinkedList<AppId<?>>();
		synchronized (appToNodes) {
			LinkedList<AppId<?>> apps = originalNodeToApps.get(nodeId);
			if (apps != null) {
				for (AppId<?> appId : apps) {
					appToNodes.get(appId).add(nodeId);
					// Check if we are primary to this app. If so, this
					// recovered nodeID is our backup and we should synch it.
					if (appToPrimaryNode(appId).equals(NodeRuntime.getId()))
						appsToPing.add(appId);
				}
			}
		}
		for (AppId<?> appId : appsToPing)
			Client.exec(new PingRequest(null, appId));
	}

	/**
	 * Get all nodes this node is interested in. We are interested in all of our
	 * neighbors, since when a node fails we must be notified to close the
	 * sockets.
	 * 
	 * @return
	 */
	public Set<NodeId> getNodesOfInterest() {
		Set<NodeId> nodes = new HashSet<NodeId>();
		nodes.addAll(NodeRuntime.getNetworkInterface().whoNeighbors());
		nodes.addAll(NodeRuntime.getNetworkInterface().whoNeighborsIn());
		// Subscribe to yourself, so that if you recover you will be notified
		// you are still failed and remove yourself as any primary
		nodes.add(NodeRuntime.getId());

		return nodes;
	}

	/**
	 * Returns a specific app running on this system.
	 * 
	 * @param appId
	 * @return
	 */
	public App<?> getApp(AppId<?> appId) {
		if (appId == null)
			return NodeRuntime.getConfigurator();
		return myApps.get(appId);
	}
}
