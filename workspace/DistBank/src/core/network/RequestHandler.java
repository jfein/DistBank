package core.network;

import core.network.messages.Request;
import core.network.messages.Response;
import core.node.NodeRuntime;

public class RequestHandler {

	public synchronized void handle(Request m) {
		try {
			// Process the request to get a response
			Response resp = (Response) this.getClass()
					.getMethod("handle", m.getClass()).invoke(this, m);
			// Send the response
			if (resp != null) {
				NodeRuntime.getNetworkInterface().sendMessage(m.getSenderId(),
						resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
