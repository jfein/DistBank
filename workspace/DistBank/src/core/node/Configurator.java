package core.node;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import oracle.messages.SubscribeRequest;

import core.app.AppId;
import core.messages.Fail;
import core.messages.Message;
import core.messages.NotifyFailure;
import core.messages.NotifyRecovery;

public class Configurator implements Runnable {

	// TODO: don't hardcode this!
	public static final AppId oracleAppId = new AppId(42);

	public final BlockingQueue<Message> buffer = new ArrayBlockingQueue<Message>(100, true);

	/**
	 * Sends the oracle a subscription request for each node of interest
	 */
	public void initialize() {
		for (NodeId node : NodeRuntime.getAppManager().getNodesOfInterest()) {
			SubscribeRequest req = new SubscribeRequest(oracleAppId, node);
			this.sendMessageToOracle(req);
		}
	}

	public synchronized void sendMessageToOracle(Message msg) {
		try {
			NodeId nodeDest = NodeRuntime.getAppManager().appToPrimaryNode(oracleAppId);
			NodeRuntime.getNetworkInterface().sendMessage(nodeDest, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configurator's main loop. Reads messages from the buffer and calls the
	 * appropriate "handle" function.
	 */
	public void run() {
		while (true) {
			try {
				Message msg = buffer.take();
				this.getClass().getMethod("handle", msg.getClass()).invoke(this, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void handle(Fail m) {
		System.out.println("Configurator FAILING");
		System.exit(0);
	}

	public void handle(NotifyFailure m) {
		System.out.println("Configurator notified of failure");
		NodeRuntime.getAppManager().removeFailedNode(m.getFailedNode());
	}

	public void handle(NotifyRecovery m) {
		System.out.println("Configurator notified of recovery");
		NodeRuntime.getAppManager().addRecoveredNode(m.getRecoveredNode());
	}
}
