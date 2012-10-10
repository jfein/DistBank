package core.network.messages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import bank.BranchState;
import core.node.NodeId;
import core.node.NodeRuntime;
import core.node.NodeState;

public class SnapshotHandler {

	private boolean isInSnapshotState = false;
    private NodeState copyNodeState;	
    private ArrayList<Request> incomingTransactions = new ArrayList<Request>();
    private Set<NodeId> waitingOn = new HashSet<NodeId>();

	public SnapshotHandler() {
		
	}
	
	public void addIncomingRequest(Request r) {
		this.incomingTransactions.add(r);
	}
	
	public synchronized void handleSnapshotMessage(SnapshotMessage sm) {
		
		// if snapshot protocol has not been initiated
		if (this.isInSnapshotState) { //TODO: Syncrhonize
			//initiate snapshot state if not initated
			this.isInSnapshotState = true;
			//start record any incoming requests
			this.copyNodeState = NodeRuntime.getState();
			//make copy of branch state if this is initiation
			// start waiting to receive snapshot requests from other branches
		} else {
		// else if snapshot has already been initiated
			if(waitingOn.contains(sm.getSenderId())) {
				waitingOn.remove(sm.getSenderId());
			}
			//check this src account id off if it's on incoming chanel of the branch state
			//if all the channels in are done ( messages have been received), then we turn off snapshot state
			if(waitingOn.isEmpty())
				this.isInSnapshotState = false;
		}
	
	}
}
