package bank.branch;

import core.node.NodeId;
import core.node.NodeRuntime;

public class BranchRunner {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for Node ID.");
			System.exit(-1);
		}

		NodeId id = new NodeId(Integer.parseInt(args[0]));

		System.out.println("BankServer Node " + id + " running.");

		(new NodeRuntime(id, BranchApp.class)).run();
	}
}
