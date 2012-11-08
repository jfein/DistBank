package core.app;

import java.util.concurrent.TimeUnit;

import core.messages.Request;
import core.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * Generic client class. Should be used whenever one app wants to send a request
 * to another app. Uses the sending app's response buffer as a serialization
 * point for an app sending request, so an app can only have one request in
 * flight at a time.
 * 
 * Contains one method for an app to send a request to another app and block
 * waiting for a response. If the response fails, the request is resent with an
 * updated primary (updated by the configurator by the oracle). If no node is
 * known to be running a primary of the app, then the request fails.
 * 
 * The other method simply sends a request to a specific node (not an app) and
 * can either block on a response or not. If block waiting for a response, uses
 * the sending app's repsonse buffer. If block waiting for a response and
 * returns NULL, then it can be assumed the request failed.
 * 
 * @author JFein
 * 
 */
public abstract class Client {

	/**
	 * Send a request to an app and wait for a response. Finds the primary to
	 * the app and routes the request there. If we receive a NULL response, then
	 * we know the request was not processed and we must try again with a
	 * (hopefully) update primary.
	 * 
	 * @param req
	 * @param waitForResponse
	 * @return
	 */
	public static <T extends Response> T exec(Request req) {
		T resp = null;

		while (resp == null) {
			// Map app to primary node
			NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(req.getReceiverAppId());

			// Couldn't find a primary - fail whole transaction
			if (dest == null)
				break;

			// Send request and wait for response on the sending app's
			// responseBuffer
			resp = exec(dest, req, true);
			if (resp != null)
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
	 * sending app's response buffer. Will unblock periodically to check if the
	 * specified node is still alive. If not alive, then will return NULL for
	 * the response. Thus, if waitForResponse is True and this method returns
	 * NULL, then the client request did not complete and the client should try
	 * again.
	 * 
	 * Synchronizes call to this function based on an app's response buffer, so
	 * an app can only make one request at a time.
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
