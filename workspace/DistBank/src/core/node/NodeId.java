package core.node;

import java.io.Serializable;

/**
 * ID to identify a node on the network. A node corresponds to a single JVM
 * running a single NodeRuntime. The ID is in the form of an integer. The node
 * then starts up multiple apps with different AppIds. It is important to
 * understand the difference between NodeIDs and AppIDs.
 */
public class NodeId implements Serializable {

	private static final long serialVersionUID = 1083289454276019163L;

	private Integer id;

	public NodeId(Integer nodeId) {
		this.id = nodeId;
	}

	public Integer getNodeId() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof NodeId)
			return id.hashCode() == ((NodeId) other).hashCode();
		return false;
	}

	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
