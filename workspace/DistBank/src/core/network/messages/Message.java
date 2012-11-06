package core.network.messages;

import java.io.Serializable;

import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = -6617634283053225009L;

	private NodeId senderNodeId;

	public Message() {
		this.senderNodeId = NodeRuntime.getId();
	}

	public NodeId getSenderNodeId() {
		return senderNodeId;
	}

}
