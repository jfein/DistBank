package core.node;

import core.network.NetworkInterface;
import core.network.MessageHandler;

public class NodeRuntime implements Runnable {

	public static final String NODE_MAPPING_FILE = "server_node_mapping.txt";
	public static final String TOPOLOGY_FILE = "topology_file.txt";

	private static NodeId id;
	private static NodeState state;
	private static NetworkInterface networkInterface;
	private static MessageHandler messageHandler;
	private static SnapshotHandler snapshotHandler;

	public static <T extends NodeState> T getState() {
		return (T) state;
	}

	public static NodeId getId() {
		return id;
	}

	public static NetworkInterface getNetworkInterface() {
		return networkInterface;
	}

	public static <T extends MessageHandler> T getMessageHandler() {
		return (T) messageHandler;
	}

	public static SnapshotHandler getSnapshotHandler() {
		return snapshotHandler;
	}

	public NodeRuntime(NodeId id, NodeState state, MessageHandler handler,
			boolean canTakeSnapshot) {
		if (handler == null)
			NodeRuntime.messageHandler = new MessageHandler();
		else
			NodeRuntime.messageHandler = handler;
		NodeRuntime.id = id;
		NodeRuntime.state = state;
		NodeRuntime.snapshotHandler = new SnapshotHandler(canTakeSnapshot);
		NodeRuntime.networkInterface = new NetworkInterface(NODE_MAPPING_FILE,
				TOPOLOGY_FILE);
	}

	public NodeRuntime(NodeId id, NodeState state, MessageHandler handler) {
		this(id, state, handler, true);
	}

	public void run() {
		System.out.println("NodeRuntime starting node ID " + NodeRuntime.id
				+ " to listen on address "
				+ NodeRuntime.networkInterface.getNodeAddress(id));

		// Run network to take in new connections
		NodeRuntime.networkInterface.run();
	}

}
