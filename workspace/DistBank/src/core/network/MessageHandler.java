package core.network;

import java.util.List;

import core.app.App;
import core.app.AppId;
import core.network.messages.Message;
import core.network.messages.Request;
import core.network.messages.Response;
import core.network.messages.SynchRequest;
import core.network.messages.SynchResponse;
import core.node.NodeId;
import core.node.NodeRuntime;

public class MessageHandler {

	/**
	 * Handles a message. Acts as a serialization point for messages coming into
	 * this node.
	 * 
	 * Message can either be a request or a response. Requests are routed to the
	 * handleRequest message, which is synchronized Responses are routed to the
	 * responseQueue in the static Client class.
	 * 
	 * @param msgIn
	 */
	public void handleMessage(Message msgIn) {
		// Got a synch request
		if (msgIn instanceof SynchRequest)
			handleSynchRequest((SynchRequest) msgIn);

		// Got a request; process requests synchronously
		else if (msgIn instanceof Request)
			handleRequest((Request) msgIn);

		// Got a response
		else if (msgIn instanceof Response)
			handleResponse((Response) msgIn);
	}

	public void handleSynchRequest(SynchRequest m) {
		AppId appId = m.getReceiverAppId();
		App app = NodeRuntime.getAppManager().getApp(appId);

		System.out.println("GOT SYNCH REQUEST FROM NODE " + m.getSenderNodeId()
				+ " AND APP " + appId);

		synchronized (app) {
			app.setState(m.getState());
		}

		try {
			NodeRuntime.getNetworkInterface().sendMessage(m.getSenderNodeId(),
					new SynchResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("SENT SYNCH RESPONSE TO " + m.getSenderNodeId());
	}

	/**
	 * Handles a request in a synchronized fashion. Invokes the appropriate
	 * message to handle this specific type of request and get a response.
	 * 
	 * Sends the response back to the sender of the request
	 * 
	 * @param m
	 */
	public synchronized void handleRequest(Request m) {
		try {
			AppId appId = m.getReceiverAppId();
			App app = NodeRuntime.getAppManager().getApp(appId);

			// Process the request to get a response
			Response resp = null;
			synchronized (app) {
				resp = (Response) app.getClass()
						.getMethod("handleRequest", m.getClass())
						.invoke(app, m);
			}

			// Send state to all backups
			boolean success = false;
			while (!success) {
				List<NodeId> backups = NodeRuntime.getAppManager()
						.appToBackupNodes(appId);
				for (NodeId backup : backups) {
					System.out.println("Attemtping to synch to backup node "
							+ backup);
					SynchRequest req = new SynchRequest(appId, app.getState());
					SynchResponse resp2 = Client.exec(backup, req);
					if (resp2 == null) {
						System.out.println("FAILED Synching backup " + backup);
						success = false;
						break;
					} else {
						System.out.println("Synched backup " + backup);
						success = true;
					}
				}
			}

			// Send the response
			if (resp != null)
				NodeRuntime.getNetworkInterface().sendMessage(
						m.getSenderNodeId(), resp);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("WARNING: RECEIVED UNSUPPOSRTED REQUEST: "
					+ m.getClass().getCanonicalName());
		}
	}

	/**
	 * Handles responses by giving them to the Client class. Only one thread can
	 * ever invoke a Client operation at once, so the queue should always have
	 * room and this should never block
	 * 
	 * @param m
	 */
	public void handleResponse(Response m) {
		System.out.println("\tGOT RESPONSE FROM NODE " + m.getSenderNodeId()
				+ " OF TYPE " + m.getClass().getCanonicalName());
		try {
			// Send the incoming response to the client
			Client.responseQueue.put((Response) m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
