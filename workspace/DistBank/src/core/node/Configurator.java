package core.node;

import oracle.OracleClient;

import core.app.App;
import core.app.AppState;
import core.messages.Fail;
import core.messages.NotifyFailure;
import core.messages.NotifyRecovery;

/**
 * Configurator class that is on all nodes (besides the oracle node).
 * Responsible for handing all requests from the oracle to this node,
 * specifically to fail, notify fail, and notify recover. Makes appropriate
 * changes to the AppManager on the appropriate requests.
 * 
 * The configurator has an appID of null.
 */
public class Configurator extends App {

	public Configurator() {
		super(null);
	}

	@Override
	protected AppState newState() {
		return null;
	}

	/**
	 * Sends the oracle a subscription request for each node of interest. If the
	 * response tells us that node is failed, we mark it as failed.
	 */
	public void initialize() {
		for (NodeId nodeOfInterest : NodeRuntime.getAppManager().getNodesOfInterest()) {
			System.out.println("Configurator Subscribing to node " + nodeOfInterest);
			boolean isAlreadyFailed = OracleClient.subscribe(nodeOfInterest);
			if (isAlreadyFailed) {
				System.out.println("Configurator notified of failure of " + nodeOfInterest);
				NodeRuntime.getAppManager().removeFailedNode(nodeOfInterest);
			}
		}
	}

	public void handleRequest(Fail m) {
		System.out.println("Configurator FAILING");
		System.exit(0);
	}

	public void handleRequest(NotifyFailure m) {
		System.out.println("Configurator notified of failure " + m.getFailedNode());
		NodeRuntime.getAppManager().removeFailedNode(m.getFailedNode());
	}

	public void handleRequest(NotifyRecovery m) {
		System.out.println("Configurator notified of recovery " + m.getRecoveredNode());
		NodeRuntime.getAppManager().addRecoveredNode(m.getRecoveredNode());

	}

}
