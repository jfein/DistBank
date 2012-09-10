package core.distsys;

import java.net.InetSocketAddress;


public abstract class NodeRuntime {

	private static NodeRuntime nodeRuntime = null;

	public static <T extends State> T getNodeRuntimeState() {
		return (T) nodeRuntime.state;
	}

	public static InetSocketAddress getNodeRuntimeMyAddress() {
		return nodeRuntime.myAddress;
	}

	private InetSocketAddress myAddress;
	private State state;

	public NodeRuntime(InetSocketAddress myAddress, State state) {
		this.myAddress = myAddress;
		this.state = state;

		Topology.setTopology(myAddress);

		NodeRuntime.nodeRuntime = this;
	}

}
