package oracle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import core.node.ConfiguratorClient;
import core.node.NodeId;

public class OracleState {

	// All failed nodes
	private final Set<NodeId> failedNodes = new HashSet<NodeId>();
	// Map list of who is subscribed to the key node
	private final HashMap<NodeId, LinkedList<NodeId>> nodeSubscriptions = new HashMap<NodeId, LinkedList<NodeId>>();

	/**
	 * Modifies the oracle state to mark a node as failed. Will notify all nodes
	 * subscribed to the failed node of the failure.
	 * 
	 * @param failedNodeId
	 */
	public void registerNodeFailure(NodeId failedNodeId) {
		failedNodes.add(failedNodeId);
		LinkedList<NodeId> subscriptions = nodeSubscriptions.get(failedNodeId);
		for (NodeId subscriber : subscriptions) {
			ConfiguratorClient.notifyFailure(subscriber, failedNodeId);
		}
	}

	/**
	 * Modifies the oracle state to mark a node as recovered. Will notify all
	 * nodes subscribed to the recovered node of the recovery.
	 * 
	 * @param recoveredNodeId
	 */
	public void registerNodeRecovery(NodeId recoveredNodeId) {
		failedNodes.remove(recoveredNodeId);
		LinkedList<NodeId> subscriptions = nodeSubscriptions.get(recoveredNodeId);
		for (NodeId subscriber : subscriptions) {
			ConfiguratorClient.notifyRecovery(subscriber, recoveredNodeId);
		}
	}

	/**
	 * Registers a node to subscribe to some other node. Returns whether or not
	 * the nodeOfInterest is already failed.
	 * 
	 * @param subscribingNode
	 * @param nodeOfInterest
	 */
	public boolean processSubscription(NodeId subscribingNode, NodeId nodeOfInterest) {
		LinkedList<NodeId> subscriptions = nodeSubscriptions.get(nodeOfInterest);
		if (subscriptions == null) {
			subscriptions = new LinkedList<NodeId>();
			nodeSubscriptions.put(nodeOfInterest, subscriptions);
		}
		subscriptions.add(subscribingNode);

		return failedNodes.contains(nodeOfInterest);
	}
}
