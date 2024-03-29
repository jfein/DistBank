package core.node;

/**
 * Starts up a node with the given NodeID. With this, the AppManager can figure
 * out what apps to start on this node, its topology, and its server listening
 * IP address.
 */
public class NodeRunner {

	/**
	 * Run any node with the given node ID. Will automatically load all apps
	 * that should be on this node.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for Node ID.");
			System.exit(-1);
		}

		NodeId id = new NodeId(Integer.parseInt(args[0]));

		System.out.println("Node " + id + " running.");

		(new NodeRuntime(id)).run();
	}
}
