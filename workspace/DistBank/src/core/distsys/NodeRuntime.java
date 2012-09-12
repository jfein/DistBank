package core.distsys;

public class NodeRuntime {

	private static NodeId id;
	private static State state;

	public static <T extends State> T getNodeRuntimeState() {
		return (T) state;
	}

	public static NodeId getNodeRuntimeId() {
		return id;
	}

	public static void init(NodeId id, State state) {
		NodeRuntime.id = id;
		NodeRuntime.state = state;

		Topology.setTopology(id);
	}

}
