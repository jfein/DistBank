package core.network;

import core.network.messages.Message;
import core.network.messages.Request;
import core.network.messages.Response;
import core.node.NodeRuntime;

public class MessageHandler {

	public void handleMessage(Message msgIn) {
		// Got a request; process requests synchronously
		if (msgIn instanceof Request)
			handleRequest((Request) msgIn);

		// Got a response
		if (msgIn instanceof Response)
			handleResponse((Response) msgIn);
	}

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

	public void handleResponse(Response m) {
		try {
			// Send the incoming response to the client
			Client.responseQueue.put((Response) m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
