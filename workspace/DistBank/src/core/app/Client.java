package core.app;

import core.messages.Request;
import core.messages.Response;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class Client {

	public static <T extends Response> T exec(Request req) {
		T resp = null;

		System.out.println("CLIENT CALL FROM " + req.getSenderAppId() + " TO " + req.getReceiverAppId());

		while (resp == null) {
			System.out.println("CLIENT CALL FROM " + req.getSenderAppId() + " TO " + req.getReceiverAppId());
			// Map app to primary node
			NodeId dest = NodeRuntime.getAppManager().appToPrimaryNode(req.getReceiverAppId());
			System.out.println("CLIENT CALL ROUTED TO PRIMARY NODE #" + dest);
			// Send request through currently running app
			resp = NodeRuntime.getAppManager().getApp(req.getSenderAppId()).sendRequestGetResponse(dest, req);
			if (resp != null)
				break;
		}

		return resp;
	}
}
