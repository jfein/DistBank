package core.messages;

import java.io.Serializable;

import core.app.AppId;
import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * Generic message for the system. All messages record the sender's node ID and
 * the receipient app ID (so the receiver will know what app to route the
 * message to).
 */
public abstract class Message implements Serializable {

	private static final long serialVersionUID = -6617634283053225009L;

	private NodeId senderNodeId;
	private AppId<?> receiverAppId;

	public Message(AppId<?> receiverAppId) {
		this.senderNodeId = NodeRuntime.getId();
		this.receiverAppId = receiverAppId;
	}

	public NodeId getSenderNodeId() {
		return senderNodeId;
	}

	public AppId<?> getReceiverAppId() {
		return receiverAppId;
	}

}
