package core.network;

import core.app.App;
import core.messages.Message;
import core.messages.Request;
import core.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * A thread that runs continuously on a node listening for incoming messages
 * from one specific remote node. When it gets a new message, retrieves the app
 * running on this node that the message is meant for. Then, if request, puts
 * the message on that app's request buffer. If its a response, puts it on the
 * app's response buffer.
 */
public class MessageListener implements Runnable {

	private NodeId src;

	public MessageListener(NodeId src) {
		this.src = src;
	}

	/**
	 * Blocks listening for a Message on the inbound connection from src. When
	 * gets a new msgIn, puts it on the appropriate buffer (request or response)
	 * for the appropriate app.
	 */
	@Override
	public void run() {
		System.out.println("\t\tMESSAGE LISTENER FOR REMOTE NODE " + src + " STARTING");

		while (true) {
			try {
				// Get an incoming message from the src node
				Message msgIn = NodeRuntime.getNetworkInterface().getMessage(src);

				// Load the app to send the message to
				App<?> app = NodeRuntime.getAppManager().getApp(msgIn.getReceiverAppId());

				// Place the msgIn in the App's request buffer
				if (msgIn instanceof Request) {
					app.requestBuffer.put((Request) msgIn);
				}
				// Place the msgIn in the App's response buffer
				else if (msgIn instanceof Response) {
					app.responseBuffer.put((Response) msgIn);
				}
			} catch (Exception e) {
				break;
			}
		}

		System.out.println("\t\tMESSAGE LISTENER FOR REMOTE NODE " + src + " SHUTTING DOWN");
	}
}
