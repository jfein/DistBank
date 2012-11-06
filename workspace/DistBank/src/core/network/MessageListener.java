package core.network;

import core.app.App;
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

	/**
	 * Blocks listening for a Message on the inbound connection from src.
	 * 
	 * When gets a new msgIn, puts it on the appropriate buffer
	 */
	@Override
	public void run() {
		while (true) {
			try {
				// Get an incoming message from the src node
				Message msgIn = NodeRuntime.getNetworkInterface().getMessage(src);

				System.out.println("\t\tMessage Listener new message from " + src);

				// Place the msgIn in the right App's request buffer
				if (msgIn instanceof Request) {
					App<?> app = NodeRuntime.getAppManager().getApp(((Request) msgIn).getReceiverAppId());
					app.requestBuffer.put((Request) msgIn);
					System.out.println("\t\tMessage Listener put request on app "
							+ ((Request) msgIn).getReceiverAppId() + " buffer");
				}
				// Place the msgIn in the client response buffer
				else if (msgIn instanceof Response) {
					Client.responseBuffer.put((Response) msgIn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

		System.out.println("CONNECTION LISTENER TO NODE " + src + " SHUTTING DOWN");
	}
}
