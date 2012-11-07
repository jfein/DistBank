package core.node;

import oracle.OracleClient;
import oracle.messages.SubscribeResponse;

import core.app.App;
import core.app.AppState;
import core.messages.Fail;
import core.messages.NotifyFailure;
import core.messages.NotifyRecovery;

public class Configurator extends App {

	public Configurator() {
		super(null);
	}

	@Override
	protected AppState newState() {
		return null;
	}

	/**
	 * Sends the oracle a subscription request for each node of interest
	 */
	public void initialize() {
		for (NodeId nodeOfInterest : NodeRuntime.getAppManager().getNodesOfInterest()) {
			System.out.println("Subscribing to node " + nodeOfInterest);
			SubscribeResponse resp = OracleClient.subscribe(nodeOfInterest);
			if (resp.isFailed()) {
				System.out.println("Configurator notified of failure");
				NodeRuntime.getAppManager().removeFailedNode(nodeOfInterest);
			}
		}
	}

	public void handleRequest(Fail m) {
		System.out.println("Configurator FAILING");
		System.exit(0);
	}

	public void handleRequest(NotifyFailure m) {
		System.out.println("Configurator notified of failure");
		NodeRuntime.getAppManager().removeFailedNode(m.getFailedNode());
	}

	public void handleRequest(NotifyRecovery m) {
		System.out.println("Configurator notified of recovery");
		NodeRuntime.getAppManager().addRecoveredNode(m.getRecoveredNode());
	}

}
