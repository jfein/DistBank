package oracle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import core.node.ConfiguratorClient;
import core.node.NodeId;

public class OracleState {

	// All failed nodes
	private Set<NodeId> failedNodes;
	// Map who is subscribed to the key
	private HashMap<NodeId, LinkedList<NodeId>> nodeSubscriptions;

	public OracleState() {
		// store for each appid nodes of interest
		failedNodes = new HashSet<NodeId>();
		nodeSubscriptions = new HashMap<NodeId, LinkedList<NodeId>>();
	}

	public void registerNodeFailure(NodeId failedNodeId) {
		this.failedNodes.add(failedNodeId);
		LinkedList<NodeId> subscriptions = nodeSubscriptions.get(failedNodeId);
		for (NodeId subscriber : subscriptions) {
			ConfiguratorClient.notifyFailure(subscriber, failedNodeId);
		}
	}

	public void registerNodeRecovery(NodeId recoveredNodeId) {
		this.failedNodes.remove(recoveredNodeId);
		LinkedList<NodeId> subscriptions = nodeSubscriptions.get(recoveredNodeId);
		for (NodeId subscriber : subscriptions) {
			ConfiguratorClient.notifyRecovery(subscriber, recoveredNodeId);
		}
	}

	public void processSubscription(NodeId subscribingNode, NodeId nodeOfInterest) {
		LinkedList<NodeId> subscriptions = nodeSubscriptions.get(nodeOfInterest);
		if (subscriptions == null) {
			subscriptions = new LinkedList<NodeId>();
			nodeSubscriptions.put(nodeOfInterest, subscriptions);
		}
		subscriptions.add(subscribingNode);

		// If what you're subscribing too is already failed, send a
		// NotifyFailure message
		if (failedNodes.contains(nodeOfInterest))
			ConfiguratorClient.notifyFailure(subscribingNode, nodeOfInterest);
	}
}
