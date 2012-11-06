package core.network;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.app.AppId;
import core.network.messages.Request;
import core.network.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public final static BlockingQueue<Response> responseBuffer = new ArrayBlockingQueue<Response>(1, true);

	public static <T extends Response> T exec(AppId appDest, Request req) {
		System.out.println("APP DEST: " + appDest);
		NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(appDest);

		return exec(dest, req);
	}

	/**
	 * Sends a request and returns a response. Synchronized statically, so only
	 * one request can be in flight and we can only be waiting on one response
	 * ever. Waits for the response to appear in the message handler's
	 * responseQueue
	 * 
	 * @param dest
	 * @param req
	 * @return
	 */
	public static synchronized <T extends Response> T exec(NodeId nodeDest, Request req) {
		T resp = null;

		try {
			// Send the request
			System.out.println("\tClient exec sending request...");
			NodeRuntime.getNetworkInterface().sendMessage(nodeDest, req);
			System.out.println("\tClient exec request sent!...");

			// If can receive from the node, block waiting for a response
			if (NodeRuntime.getNetworkInterface().canReceiveFrom(nodeDest)) {
				System.out.println("\tClient exec waiting for response...");
				resp = (T) responseBuffer.take();
				System.out.println("\tClient exec got response from node " + resp.getSenderNodeId() + " of type "
						+ resp.getClass().getCanonicalName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}
}
