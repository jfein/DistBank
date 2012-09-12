package core.distsys;

public class NodeRuntime {

	private static NodeRuntime nodeRuntime = null;

	public static <T extends State> T getNodeRuntimeState() {
		return (T) nodeRuntime.state;
	}

	public static NodeId getNodeRuntimeId() {
		return nodeRuntime.id;
	}

	private NodeId id;
	private State state;

	public NodeRuntime(NodeId id, State state) {
		this.id = id;
		this.state = state;

		Topology.setTopology(id);

		NodeRuntime.nodeRuntime = this;
	}

}
