package core.network.common;

import java.io.Serializable;
import java.net.SocketAddress;

import core.distsys.NodeRuntime;

public abstract class Message implements Serializable {

	private static final long serialVersionUID = -6617634283053225009L;

	private SocketAddress returnAddress;

	public Message() {
		this.returnAddress = NodeRuntime.getNodeRuntimeMyAddress();
	}

	public SocketAddress getReturnAddress() {
		return returnAddress;
	}
}
