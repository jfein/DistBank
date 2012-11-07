package core.app;

import core.messages.Request;
import core.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public static <T extends Response> T exec(Request req) {
		return exec(req, true);
	}

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
		}

		return resp;
	}

	public static <T extends Response> T exec(NodeId nodeDest, Request req, boolean waitForResponse) {
		T resp = null;

		try {
			// Send the request
			System.out.println("\tApp Client exec sending request...");
			NodeRuntime.getNetworkInterface().sendMessage(nodeDest, req);
			System.out.println("\tApp Client exec request sent!...");

			// If can receive from the node, block waiting for a response
			if (waitForResponse && NodeRuntime.getNetworkInterface().canReceiveFrom(nodeDest)) {
				System.out.println("\tApp Client exec waiting for response...");
				resp = (T) NodeRuntime.getAppManager().getApp(req.getSenderAppId()).responseBuffer.take();
				System.out.println("\tApp Client exec got response from node " + resp.getSenderNodeId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}

}
