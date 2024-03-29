package core.node;

import oracle.OracleClient;

import core.app.App;
import core.app.AppState;
import core.messages.Fail;
import core.messages.NotifyFailure;
import core.messages.NotifyFailureResponse;
import core.messages.NotifyRecovery;
import core.messages.NotifyRecoveryResponse;

/**
 * Configurator class that is on all nodes (besides the oracle node).
 * Responsible for handing all requests from the oracle to this node,
 * specifically to fail, notify fail, and notify recover. Makes appropriate
 * changes to the AppManager on the appropriate requests.
 * 
 * Has a function "initialize" that sends the oracle a subscription request for
 * each node of interest. If the response tells us that node is failed,
 * registers it as failed with the AppManager. "Nodes of interest" are defined
 * as any node connected to us in the topology, since if any of those fail we
 * must register it to know to close the sockets. Will subscribe to ourselves,
 * so that on startup we can know if we are technically still failed in the
 * overall system; this is to trigger ourselves as still failed and thus
 * removing ourselves from any primaries.
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
	 * response tells us that node is failed, we mark it as failed. Will
	 * subscribe to ourselves, so that on startup we can know if we are
	 * technically still failed in the overall system; this is to trigger
	 * ourselves as still failed and thus removing ourselves from any primaries.
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

	public NotifyFailureResponse handleRequest(NotifyFailure m) {
		System.out.println("Configurator notified of failure " + m.getFailedNode());
		NodeRuntime.getAppManager().removeFailedNode(m.getFailedNode());
		return new NotifyFailureResponse(m.getSenderAppId());
	}

	public NotifyRecoveryResponse handleRequest(NotifyRecovery m) {
		System.out.println("Configurator notified of recovery " + m.getRecoveredNode());
		NodeRuntime.getAppManager().addRecoveredNode(m.getRecoveredNode());
		return new NotifyRecoveryResponse(m.getSenderAppId());
	}

}
