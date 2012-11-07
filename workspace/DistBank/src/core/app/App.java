package core.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.messages.Request;
import core.messages.Response;
import core.messages.SynchRequest;
import core.messages.SynchResponse;
import core.node.NodeId;
import core.node.NodeRuntime;

public abstract class App<S extends AppState> implements Runnable {

	public final BlockingQueue<Request> requestBuffer = new ArrayBlockingQueue<Request>(100, true);
	public final BlockingQueue<Response> responseBuffer = new ArrayBlockingQueue<Response>(1, true);

	private AppId<? extends App<S>> appId;
	private S appState;

	public App(AppId<? extends App<S>> appId) {
		this.appId = appId;
		this.appState = newState();
	}

	protected abstract S newState();

	public AppId<? extends App<S>> getAppId() {
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
		System.out.println("\tApp " + appId + " got new request from node " + req.getSenderNodeId());
		Response resp = (Response) this.getClass().getMethod("handleRequest", req.getClass()).invoke(this, req);
		System.out.println("\tApp " + appId + " handled request");

		// Send state to all backups (if this request is not a SynchRequest)
		if (!(req instanceof SynchRequest)) {
			boolean success = true;
			do {
				for (NodeId backup : NodeRuntime.getAppManager().appToBackupNodes(appId)) {
					System.out.println("Attemtping to synch to backup node " + backup);
					SynchRequest<S> synchReq = new SynchRequest<S>(appId, appId, appState);
					SynchResponse synchResp = Client.exec(backup, synchReq, true);
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
		if (resp != null) {
			System.out.println("\tApp " + appId + " sending response to node " + req.getSenderNodeId());
			NodeRuntime.getNetworkInterface().sendMessage(req.getSenderNodeId(), resp);
			System.out.println("\tApp " + appId + " sent response");
		}
	}

	public SynchResponse handleRequest(SynchRequest<S> m) {
		System.out.println("GOT SYNCH REQUEST FROM NODE " + m.getSenderNodeId() + " AND APP " + appId);
		setState(m.getState());
		return new SynchResponse(appId);
	}

}
