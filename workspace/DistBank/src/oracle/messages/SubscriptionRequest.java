package oracle.messages;

import java.util.LinkedList;

import core.app.AppId;
import core.network.messages.Message;
import core.network.messages.Request;
import core.node.NodeId;

public class SubscriptionRequest extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1812124901168541643L;
	private LinkedList<NodeId> subscriptionList;

	public SubscriptionRequest(LinkedList<NodeId> sList) {
		subscriptionList = sList;
	}

	public LinkedList<NodeId> getSubscriptionList() {
		return subscriptionList;
	}
}
