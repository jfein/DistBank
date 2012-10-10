package core.network;

import core.network.messages.Message;
import core.network.messages.Request;
import core.network.messages.Response;
import core.network.messages.SnapshotMessage;
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
		//Got a snapshot message
		if (msgIn instanceof SnapshotMessage)
			NodeRuntime.getSnapshotHandler().handleSnapshotMessage((SnapshotMessage) msgIn);
		
		// Got a request; process requests synchronously
		if (msgIn instanceof Request)
			handleRequest((Request) msgIn);

		// Got a response
		if (msgIn instanceof Response)
			handleResponse((Response) msgIn);
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
			// Process the request to get a response
			Response resp = (Response) this.getClass()
					.getMethod("handleRequest", m.getClass()).invoke(this, m);
			// Send the response
			if (resp != null)
				NodeRuntime.getNetworkInterface().sendMessage(m.getSenderId(),
						resp);
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			// Send the incoming response to the client
			Client.responseQueue.put((Response) m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
