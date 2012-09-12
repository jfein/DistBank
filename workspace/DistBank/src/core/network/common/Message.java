package core.network.common;

import java.io.Serializable;

import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = -6617634283053225009L;

	private NodeId senderId;

	public Message() {
		this.senderId = NodeRuntime.getNodeId();
	}

	public NodeId getSenderId() {
		return senderId;
	}

}
