package bank;

import java.util.List;

import bank.gui.BranchController;
import core.network.MessageHandler;
import core.network.messages.Message;
import core.network.messages.DisplaySnapshotRequest;
import core.node.NodeRuntime;

public class BranchGuiRequestHandler extends MessageHandler {

	public void handleRequest(DisplaySnapshotRequest snapshot) {
		BranchState snapshotNodeState = snapshot.getNodeState();
		List<Message> snapshotIncomingMessages = snapshot.getIncomingMessages();
		BranchController controller = NodeRuntime.getState();
		
		//Display snapshot information on GUI for this branch
		controller.displaySnapshot(snapshotNodeState, snapshotIncomingMessages);
		System.out.println(snapshotNodeState); //TODO remove
	}

}
