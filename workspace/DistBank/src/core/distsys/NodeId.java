package core.distsys;

public class NodeId {

	private String id;

	public NodeId(String nodeId) {
		this.id = nodeId;
	}

	public String getNodeId() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof NodeId)
			return id.equals(((NodeId) other).id);
		return false;
	}

	@Override
	public String toString() {
		return id;
	}
}
