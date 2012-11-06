package core.network;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.app.AppId;
import core.network.messages.Request;
import core.network.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public static BlockingQueue<Response> responseQueue = new ArrayBlockingQueue<Response>(
			1);

	public static synchronized <T extends Response> T exec(AppId appDest,
			Request req) {
		System.out.println("APP DEST: " + appDest);
		NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(appDest);

		return exec(dest, req);
	}

	/**
	 * Sends a request and returns a response. Synchronized statically, so only
	 * one request can be in flight and we can only be waiting on one response
	 * ever. Waits for the response to appear in the static responseQueue
	 * 
	 * @param dest
	 * @param req
	 * @return
	 */
	public static synchronized <T extends Response> T exec(NodeId nodeDest,
			Request req) {
		T resp = null;

		try {
			// Send the request
			NodeRuntime.getNetworkInterface().sendMessage(nodeDest, req);
			// Block waiting for a response on the responseQueue if we can
			// receive a response
			if (NodeRuntime.getNetworkInterface().canReceiveFrom(nodeDest)) {
				System.out.println("Client exec waiting for response...");
				resp = (T) responseQueue.take();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}
}
