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

	protected static synchronized <T extends Response> T exec(NodeId dest,
			Request req) {
		T resp = null;

		try {
			// Send the request
			NodeRuntime.getNetworkInterface().sendMessage(dest, req);
			// Block waiting for a response on the responseQueue
			resp = (T) responseQueue.take();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resp;
	}
}
