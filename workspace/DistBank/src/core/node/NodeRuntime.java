package core.node;

import core.network.common.Topology;

public class NodeRuntime {

	private static NodeId id;
	private static NodeState state;

	public static <T extends NodeState> T getNodeState() {
		return (T) state;
	}

	public static NodeId getNodeId() {
		return id;
	}

	public static void init(NodeId id, NodeState state) {
		NodeRuntime.id = id;
		NodeRuntime.state = state;

		Topology.setTopology();
	}

}
