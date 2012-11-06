package core.network;

import core.network.messages.Request;
import core.network.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public static <T extends Response> T exec(Request req) {
		System.out.println("CLIENT CALL FROM " + req.getSenderAppId() + " TO " + req.getReceiverAppId());
		NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(req.getReceiverAppId());

		return NodeRuntime.getAppManager().getApp(req.getSenderAppId()).sendRequestGetResponse(dest, req);
	}

}
