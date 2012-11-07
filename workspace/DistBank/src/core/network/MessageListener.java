package core.network;

import core.app.App;
import core.messages.AppMessage;
import core.messages.Message;
import core.messages.Request;
import core.messages.Response;
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

				// Got a message meant for an app
				if (msgIn instanceof AppMessage) {
					App<?> app = NodeRuntime.getAppManager().getApp(((AppMessage) msgIn).getReceiverAppId());

					// Place the msgIn in the App's request buffer
					if (msgIn instanceof Request) {
						app.requestBuffer.put((Request) msgIn);
						System.out.println("\t\tMessage Listener put request on app "
								+ ((Request) msgIn).getReceiverAppId() + " buffer");
					}
					// Place the msgIn in the App's response buffer
					else if (msgIn instanceof Response) {
						app.responseBuffer.put((Response) msgIn);
						System.out.println("\t\tMessage Listener put response on app "
								+ ((Response) msgIn).getReceiverAppId() + " buffer");
					}
				}
				// Got a message for the system
				else {
					NodeRuntime.getConfigurator().buffer.put(msgIn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

		System.out.println("CONNECTION LISTENER TO NODE " + src + " SHUTTING DOWN");
	}
}
