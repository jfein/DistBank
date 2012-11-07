package core.node;

import core.messages.Fail;
import core.messages.NotifyFailure;
import core.messages.NotifyRecovery;

public class ConfiguratorClient {

	public synchronized static void fail(NodeId nodeToFail) {
		try {
			NodeRuntime.getNetworkInterface().sendMessage(nodeToFail, new Fail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void notifyFailure(NodeId dest, NodeId failedNode) {
		try {
			NodeRuntime.getNetworkInterface().sendMessage(dest, new NotifyFailure(failedNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void notifyRecovery(NodeId dest, NodeId recoveredNode) {
		try {
			NodeRuntime.getNetworkInterface().sendMessage(dest, new NotifyRecovery(recoveredNode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
