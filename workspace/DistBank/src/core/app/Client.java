package core.app;

import java.util.concurrent.TimeUnit;

import core.messages.Request;
import core.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * Generic client class. Should be used whenever one app wants to send a request
 * to another app. Uses the sending app's response buffer as a serialization
 * point for an app sending request (although shouldnt happen since an app only
 * runs on one thread).
 * 
 * @author JFein
 * 
 */
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
			// Map app to primary node
			NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(req.getReceiverAppId());

			// Send request and wait for response on the sending app's
			// responseBuffer
			resp = exec(dest, req, waitForResponse);
			if (!waitForResponse || resp != null)
				break;

			// If we got here, we did not get a response (probably because the
			// remote node crashed).
			// Make the client wait before trying again
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

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
	public static <T extends Response> T exec(NodeId nodeDest, Request req, boolean waitForResponse) {
		T resp = null;

		App<?> a = NodeRuntime.getAppManager().getApp(req.getSenderAppId());

		synchronized (a.responseBuffer) {
			// Send the request
			try {
				NodeRuntime.getNetworkInterface().sendMessage(nodeDest, req);
			} catch (Exception e) {
				System.out.println("ERROR: App Client exec unable to send reques to node #" + nodeDest);
				return resp;
			}

			// If can receive from the node, block waiting for a response
			if (waitForResponse && NodeRuntime.getNetworkInterface().canReceiveFrom(nodeDest)) {
				try {
					while (resp == null) {
						// Check if the node we want a response from is alive!
						if (!NodeRuntime.getAppManager().isNodeAlive(nodeDest))
							throw new Exception();

						// Poll the response buffer for 500ms, then wake up to
						// check if the node we want a response from is still
						// alive
						resp = (T) a.responseBuffer.poll(500, TimeUnit.MILLISECONDS);
					}
				} catch (Exception e) {
					System.out.println("ERROR: App client exec unable to get response from node #" + nodeDest);
				}
			}
		}

		return resp;
	}
}
