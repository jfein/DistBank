package core.node;

import java.io.Serializable;

public class NodeId implements Serializable {

	private static final long serialVersionUID = 1083289454276019163L;

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
			return id.hashCode() == ((NodeId) other).hashCode();
		return false;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	
}
