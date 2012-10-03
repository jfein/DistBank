package core.network;

import core.network.messages.Message;
import core.node.NodeId;
import core.node.NodeRuntime;

public class MessageListener implements Runnable {

	private NodeId src;

	public MessageListener(NodeId src) {
		this.src = src;
	}

	@Override
	public void run() {
		while (true) {
			try {

				// Get an incoming message from the src node
				Message msgIn = NodeRuntime.getNetworkInterface().getMessage(
						src);

				// Handle the message
				NodeRuntime.getMessageHandler().handleMessage(msgIn);

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

		System.out.println("CONNECTION LISTENER TO NODE " + src
				+ " SHUTTING DOWN");
	}

}
