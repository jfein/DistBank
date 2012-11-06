package core.node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.app.App;
import core.app.AppId;

/**
 * Class to hold mappings from AppIDs to NodeIDs.
 * 
 * Also holds this runtime's specific apps
 * 
 * @param <A>
 */
public class AppManager {

	private Map<AppId, LinkedList<NodeId>> appToNodes;
	private Map<AppId, App<?>> myApps;

	public <A extends App<?>> AppManager(Class<A> appClass) {
		appToNodes = new HashMap<AppId, LinkedList<NodeId>>();
		myApps = new HashMap<AppId, App<?>>();

		try {
			BufferedReader read1 = new BufferedReader(new FileReader(
					NodeRuntime.APPS_FILE));

			while (read1.ready()) {
				String t = read1.readLine();
				String parts[] = t.split(" ");

				AppId appId = new AppId(Integer.parseInt(parts[0]));

				LinkedList<NodeId> nodeIds = new LinkedList<NodeId>();
				for (int i = 1; i < parts.length; i++) {
					// Add app to node mapping
					NodeId nodeId = new NodeId(Integer.parseInt(parts[i]));
					nodeIds.add(nodeId);

					// Our node, make an app!
					if (nodeId.equals(NodeRuntime.getId())) {
						// TODO: Make this better, no guarantee that App will
						// have a default constructor
						A app = appClass.newInstance();
						myApps.put(appId, app);

						System.out.println("\tStarting app " + appId);
					}
				}

				appToNodes.put(appId, nodeIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NodeId appToPrimaryNode(AppId appId) {
		System.out.println("Looking for primary for app " + appId);
		LinkedList<NodeId> lst = appToNodes.get(appId);
		if (lst == null)
			return null;
		return lst.peek();
	}

	public List<NodeId> appToBackupNodes(AppId appId) {
		LinkedList<NodeId> nodes = new LinkedList<NodeId>(appToNodes.get(appId));
		nodes.pop();
		return nodes;
	}

	public App getApp(AppId appId) {
		return myApps.get(appId);
	}

}
