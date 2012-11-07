package core.messages;

import java.io.Serializable;

import core.app.AppId;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = -6617634283053225009L;

	private NodeId senderNodeId;
	private AppId receiverAppId;

	public Message(AppId receiverAppId) {
		this.senderNodeId = NodeRuntime.getId();
		this.receiverAppId = receiverAppId;
	}

	public NodeId getSenderNodeId() {
		return senderNodeId;
	}

	public AppId getReceiverAppId() {
		return receiverAppId;
	}

}
