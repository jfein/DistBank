package core.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.network.Client;
import core.network.messages.Request;
import core.network.messages.Response;
import core.network.messages.SynchRequest;
import core.network.messages.SynchResponse;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class App<S extends AppState> implements Runnable {

	public final BlockingQueue<Request> requestBuffer = new ArrayBlockingQueue<Request>(100, true);

	private AppId appId;
	private S appState;

	public App(AppId appId) {
		this.appId = appId;
		this.appState = newState();
	}

	protected abstract S newState();

	public AppId getAppId() {
		return appId;
	}

	public S getState() {
		return appState;
	}

	public void setState(S newAppState) {
		this.appState = newAppState;
		System.out.println("NEW STATE:");
		System.out.println(this.appState);
	}

	/**
	 * An App's main loop.
	 * 
	 * Reads requests from the request buffer and calls handleRequest for each.s
	 */
	public void run() {
		while (true) {
			try {
				Request req = requestBuffer.take();
				handleRequest(req);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void handleRequest(Request req) throws Exception {
		// Process the request to get a response (including synch requests)
		System.out.println("App " + appId + " got new request from node " + req.getSenderNodeId() + " for "
				+ req.getClass().getCanonicalName());
		Response resp = (Response) this.getClass().getMethod("handleRequest", req.getClass()).invoke(this, req);

		System.out.println("App " + appId + " made response");

		// Send state to all backups (if this request is not a SynchRequest)
		if (!(req instanceof SynchRequest)) {
			boolean success = true;
			do {
				for (NodeId backup : NodeRuntime.getAppManager().appToBackupNodes(appId)) {
					System.out.println("Attemtping to synch to backup node " + backup);
					SynchRequest<S> synchReq = new SynchRequest<S>(appId, appState);
					SynchResponse synchResp = Client.exec(backup, synchReq);
					if (synchResp == null) {
						System.out.println("FAILED Synching backup " + backup);
						success = false;
						break;
					} else {
						System.out.println("Synched backup " + backup);
						success = true;
					}
				}
			} while (!success);
		}

		// Send the response
		if (resp != null)
			NodeRuntime.getNetworkInterface().sendMessage(req.getSenderNodeId(), resp);
	}

	public SynchResponse handleRequest(SynchRequest<S> m) {
		System.out.println("GOT SYNCH REQUEST FROM NODE " + m.getSenderNodeId() + " AND APP " + appId);
		setState(m.getState());
		return new SynchResponse();
	}

}
