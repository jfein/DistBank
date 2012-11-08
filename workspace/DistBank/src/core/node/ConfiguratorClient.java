package core.node;

import core.app.Client;
import core.messages.Fail;
import core.messages.NotifyFailure;
import core.messages.NotifyRecovery;

/**
 * Client for the oracle to use to send messages to a node's Configurator.
 */
public class ConfiguratorClient extends Client {

	public static void fail(NodeId nodeToFail) {
		ConfiguratorClient.exec(nodeToFail, new Fail(), false);
	}

	public static void notifyFailure(NodeId dest, NodeId failedNode) {
		ConfiguratorClient.exec(dest, new NotifyFailure(failedNode), false);
	}

	public static void notifyRecovery(NodeId dest, NodeId recoveredNode) {
		ConfiguratorClient.exec(dest, new NotifyRecovery(recoveredNode), false);
	}

}
