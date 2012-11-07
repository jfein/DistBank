package core.app;

import java.util.concurrent.TimeUnit;

import core.messages.Request;
import core.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public static <T extends Response> T exec(Request req) {
		return exec(req, true);
	}

	/**
	 * Send a request to an app. Finds the primary to the app and routes the
	 * request there. On failure, will simply keep trying in hopes the
	 * Configurator will change the primary.
	 * 
	 * @param req
	 * @param waitForResponse
	 * @return
	 */
	public static <T extends Response> T exec(Request req, boolean waitForResponse) {
		T resp = null;

		while (resp == null) {
			System.out.println("CLIENT CALL FROM MY APP " + req.getSenderAppId() + " TO DEST APP "
					+ req.getReceiverAppId());

			// Map app to primary node
			NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(req.getReceiverAppId());
			System.out.println("CLIENT CALL ROUTED TO PRIMARY NODE #" + dest);

			// Send request and wait for response on the sending app's
			// responseBuffer
			resp = exec(dest, req, waitForResponse);
			if (!waitForResponse || resp != null)
				break;

			// Make the client wait before trying again
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}

		return resp;
	}

	/**
	 * Sends a request to a specific node. If waitForResponse, will block on the
	 * app's response buffer.
	 * 
	 * @param nodeDest
	 * @param req
	 * @param waitForResponse
	 * @return
	 */
	// TODO: should this be synchronous????
	public static <T extends Response> T exec(NodeId nodeDest, Request req, boolean waitForResponse) {
		T resp = null;

		// Send the request
		try {
			System.out.println("\tApp Client exec sending request...");
			NodeRuntime.getNetworkInterface().sendMessage(nodeDest, req);
			System.out.println("\tApp Client exec request sent!...");
		} catch (Exception e) {
			System.out.println("ERROR: App Client exec unable to send reques to node #" + nodeDest + "!!!!!!!");
			return resp;
		}

		// If can receive from the node, block waiting for a response
		if (waitForResponse && NodeRuntime.getNetworkInterface().canReceiveFrom(nodeDest)) {
			try {
				System.out.println("\tApp Client exec waiting for response...");

				while (resp == null) {
					// Check if the node we want a response from is alive!
					if (!NodeRuntime.getAppManager().isNodeAlive(nodeDest))
						throw new Exception();

					App<?> a = NodeRuntime.getAppManager().getApp(req.getSenderAppId());
					resp = (T) a.responseBuffer.poll(500, TimeUnit.MILLISECONDS);
				}

				System.out.println("\tApp Client exec got response from node " + resp.getSenderNodeId());

			} catch (Exception e) {
				System.out.println("ERROR: App client exec unable to get response from node #" + nodeDest + "!!!!!!");
			}
		}

		return resp;
	}
}
