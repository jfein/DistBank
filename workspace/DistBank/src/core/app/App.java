package core.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import core.messages.PingRequest;
import core.messages.Request;
import core.messages.Response;
import core.messages.SynchRequest;
import core.messages.SynchResponse;
import core.node.NodeId;
import core.node.NodeRuntime;

/**
 * An App represents an individual program running on a node runtime. It is its
 * own thread with a unique appID, and infinitely listens for requests on its
 * requestBuffer. Could either be a primary or a backup.
 * 
 * Type of the App is the state this app holds. This state is what is replicated
 * to backups when a request happens.
 * 
 * The AppID is unique in the sense that a specific reachable app has a unique
 * ID, and all primaries and backups across different nodes will have the same
 * appID.
 * 
 * An app should simply define the constructor, a way to generate a new state
 * (for new app creation), and then any handleRequest methods for each request
 * this app can take in.
 */
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

	/**
	 * Main request handling thread. Does a reflection to call the appropriate
	 * handleRequest function on this app to product a response. If the request
	 * itself wasn't a synch request, then it synchronizes the state to all
	 * backups.
	 * 
	 * If an app is getting a request that is not a synch request, it assumes
	 * the role of the new primary and attempts to synch all other live nodes
	 * for this app (as recorded by app manager).
	 * 
	 * Once done synchronizing, sends the response back to the source.
	 * 
	 * @param req
	 * @throws Exception
	 */
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
					System.out.println("\tAttemtping to synch to backup node " + backup);
					SynchRequest<S> synchReq = new SynchRequest<S>(appId, appId, appState);
					SynchResponse synchResp = Client.exec(backup, synchReq, true);
					if (synchResp == null) {
						System.out.println("\tFAILED Synching backup, will try to synch all again " + backup);
						success = false;
						break;
					} else {
						System.out.println("\tSynched backup " + backup);
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

	/**
	 * Common handler across all apps to handle a synch request. Simply sets
	 * this apps state to the synch request state.
	 * 
	 * @param m
	 * @return
	 */
	public SynchResponse handleRequest(SynchRequest<S> m) {
		System.out.println("GOT SYNCH REQUEST FROM NODE " + m.getSenderNodeId() + " AND APP " + appId);
		setState(m.getState());
		System.out.println("NEW STATE:\n" + appState);
		return new SynchResponse(appId);
	}

	/**
	 * Common handler across all apps to handle a ping. Does nothing, but will
	 * force a synchronization to occur.
	 * 
	 * @param m
	 */
	public void handleRequest(PingRequest m) {
		System.out.println("App " + getAppId() + " pinged!");
	}

}
