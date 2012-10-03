package core.network;

import core.network.messages.Message;
import core.network.messages.Request;
import core.network.messages.Response;
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

				// Process the message synchronously, so only one message can be
				// processed at once
				MessageListener.processMessage(msgIn);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

		System.out.println("CONNECTION LISTENER TO NODE " + src
				+ " SHUTTING DOWN");
	}

	public synchronized static void processMessage(Message msgIn)
			throws InterruptedException {
		// Got a request, handle it
		if (msgIn instanceof Request)
			NodeRuntime.getRequestHandler().handle((Request) msgIn);

		// Got a response, send it to Client
		if (msgIn instanceof Response)
			Client.responseQueue.put((Response) msgIn);
	}
}
