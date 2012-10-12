package core.network;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.network.messages.Request;
import core.network.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public static BlockingQueue<Response> responseQueue = new ArrayBlockingQueue<Response>(
			1);

	/**
	 * Sends a request and returns a response. Synchronized statically, so only
	 * one request can be in flight and we can only be waiting on one response
	 * ever. Waits for the response to appear in the static responseQueue
	 * 
	 * @param dest
	 * @param req
	 * @return
	 */
	protected static synchronized <T extends Response> T exec(NodeId dest,
			Request req) {
		T resp = null;

		try {
			// Send the request
			NodeRuntime.getNetworkInterface().sendMessage(dest, req);
			// Block waiting for a response on the responseQueue if we can
			// receive a response
			if (NodeRuntime.getNetworkInterface().canReceiveFrom(dest))
				resp = (T) responseQueue.take();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}

}
