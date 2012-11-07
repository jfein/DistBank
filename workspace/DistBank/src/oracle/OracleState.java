package oracle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import core.app.AppId;
import core.app.AppState;
import core.node.NodeId;

public class OracleState extends AppState{
//Keep track of failed nodes so we know if a node starts up from recovery or if its starting up for the firs time?
	private Set<NodeId> failedNodes;
	private HashMap<NodeId, LinkedList<NodeId>> nodeToSubscriptionList;
	private AppId myAppId;
	
	public OracleState(AppId appId) {
		//store for each appid nodes of interest
		failedNodes = new HashSet<NodeId>();
		nodeToSubscriptionList = new HashMap<NodeId, LinkedList<NodeId>>();
		myAppId = appId;
	}
	
	public void addFailedNode(NodeId nodeId) {
		this.failedNodes.add(nodeId);
	}
	
	public Set<NodeId> getFailedNodes() {
		return failedNodes;
	}
	
	public void processSubscription(NodeId nodeId, LinkedList<NodeId> subscriptionList) {
		this.nodeToSubscriptionList.put(nodeId, subscriptionList);
		if(failedNodes.contains(nodeId)) {//TODO
			failedNodes.remove(nodeId);
		}
	}
}
