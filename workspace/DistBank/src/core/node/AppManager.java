package core.node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.app.App;
import core.app.AppId;

/**
 * Class to hold mappings from AppIDs to NodeIDs.
 * 
 * Also holds this runtime's specific apps
 * 
 * @param <A>
 */
public class AppManager<A extends App<?>> {

	private Map<AppId, LinkedList<NodeId>> appToNodes;
	private Map<NodeId, LinkedList<AppId>> originalNodeToApps;
	private Map<AppId, A> myApps;

	public AppManager(Class<A> appClass) throws Exception {
		parseAppFile();
		createApps(appClass);
	}

	/**
	 * Parses the APPS_FILE in NodeRuntime. Puts the App to Node mapping into
	 * the map appToNodes.
	 */
	private void parseAppFile() {
		appToNodes = new HashMap<AppId, LinkedList<NodeId>>();
		originalNodeToApps = new HashMap<NodeId, LinkedList<AppId>>();

		try {
			BufferedReader read1 = new BufferedReader(new FileReader(NodeRuntime.APPS_FILE));

			while (read1.ready()) {
				String t;
				try {
					t = read1.readLine();

					String parts[] = t.split(" ");

					AppId appId = new AppId(Integer.parseInt(parts[0]));

					LinkedList<NodeId> nodeIds = new LinkedList<NodeId>();
					for (int i = 1; i < parts.length; i++) {
						// Add nodeID to app mapping
						NodeId nodeId = new NodeId(Integer.parseInt(parts[i]));
						nodeIds.add(nodeId);

						// Add appID to app mapping
						LinkedList<AppId> apps = originalNodeToApps.get(nodeId);
						if (apps == null) {
							apps = new LinkedList<AppId>();
							originalNodeToApps.put(nodeId, apps);
						}
						apps.add(appId);
					}

					appToNodes.put(appId, nodeIds);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Creates an App for each AppID on th is node. Stores the app in the map
	 * myApps.
	 * 
	 * @param appClass
	 * @throws Exception
	 */
	private void createApps(Class<A> appClass) throws Exception {
		myApps = new HashMap<AppId, A>();
		for (Map.Entry<AppId, LinkedList<NodeId>> entry : appToNodes.entrySet()) {
			AppId appId = entry.getKey();
			LinkedList<NodeId> nodes = entry.getValue();
			for (NodeId node : nodes) {
				if (node.equals(NodeRuntime.getId())) {
					// Create app
					A app = appClass.getConstructor(AppId.class).newInstance(appId);
					System.out.println("\tStarting app " + appId);
					myApps.put(appId, app);
				}
			}
		}
	}

	/**
	 * Starts all apps in the myApps structure.
	 */
	public void startApps() {
		for (A app : myApps.values()) {
			new Thread(app).start();
		}
	}

	/**
	 * Returns the current primary NodeID for a given AppID.
	 * 
	 * @param appId
	 * @return
	 */
	public NodeId appToPrimaryNode(AppId appId) {
		synchronized (appToNodes) {
			System.out.println("Looking for primary for app " + appId);
			LinkedList<NodeId> lst = appToNodes.get(appId);
			if (lst == null)
				return null;
			return lst.peek();
		}
	}

	/**
	 * Returns the current backup NodeIDs for a given AppID.
	 * 
	 * @param appId
	 * @return
	 */
	public List<NodeId> appToBackupNodes(AppId appId) {
		synchronized (appToNodes) {
			LinkedList<NodeId> nodes = new LinkedList<NodeId>(appToNodes.get(appId));
			nodes.pop();
			return nodes;
		}
	}

	/**
	 * Removes a failed nodeID from appToNodes mapping
	 * 
	 * @param nodeId
	 */
	public void removeFailedNode(NodeId nodeId) {
		synchronized (appToNodes) {
			for (LinkedList<NodeId> nodes : appToNodes.values())
				nodes.remove(nodeId);
		}
	}

	/**
	 * Adds a recovered nodeID to appToNodes mapping
	 * 
	 * @param nodeId
	 */
	public void addRecoveredNode(NodeId nodeId) {
		synchronized (appToNodes) {
			LinkedList<AppId> apps = originalNodeToApps.get(nodeId);
			if (apps != null) {
				for (AppId appId : apps) {
					appToNodes.get(appId).add(nodeId);
				}
			}
		}
	}

	/**
	 * Get all nodes this node is interested in, meaning we run an app also
	 * running on that node
	 * 
	 * @return
	 */
	public Set<NodeId> getNodesOfInterest() {
		if (false) {
			synchronized (myApps) {
				Set<NodeId> nodes = new HashSet<NodeId>();
				for (Map.Entry<NodeId, LinkedList<AppId>> entry : originalNodeToApps.entrySet()) {
					for (AppId app : myApps.keySet()) {
						if (entry.getValue().contains(app))
							nodes.add(entry.getKey());
					}
				}
				nodes.remove(NodeRuntime.getId());
				return nodes;
			}
		}

		// TODO: don't do this
		Set<NodeId> nodes = new HashSet<NodeId>();
		nodes.addAll(NodeRuntime.getNetworkInterface().whoNeighbors());
		nodes.addAll(NodeRuntime.getNetworkInterface().whoNeighborsIn());
		return nodes;
	}

	/**
	 * Returns a specific app running on this system.
	 * 
	 * @param appId
	 * @return
	 */
	public A getApp(AppId appId) {
		System.out.println("App Manager looking for app " + appId);
		return myApps.get(appId);
	}
}
