package core.network.messages;

import bank.BranchState;
import core.node.NodeRuntime;

public class SnapshotHandler {

	private boolean isInSnapshotState = false;
    private BranchState copyOfBranchState;	

	public SnapshotHandler() {
		
	}
	
	public synchronized void handleSnapshotMessage(SnapshotMessage sm) {
		
		// if snapshot protocol has not been initiated
		if (this.isInSnapshotState) { //TODO: Syncrhonize
			//initiate snapshot state if not initated
			this.isInSnapshotState = true;
			//start record any incoming requests
			//make copy of branch state if this is initiation
			// start waiting to receive snapshot requests from other branches
		} else {
		// else if snapshot has already been initiated
			//check this src account id off if it's on incoming chanel of the branch state
			//if all the channels in are done ( messages have been received), then we turn off snapshot state
		}
	
	}
}
