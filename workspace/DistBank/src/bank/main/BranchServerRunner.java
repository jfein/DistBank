package bank.main;

import java.io.IOException;

import bank.BranchRequestHandler;
import bank.BranchState;
import bank.BranchId;

import core.node.NodeRuntime;

public class BranchServerRunner {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		BranchId id = new BranchId(Integer.parseInt(args[0]));
		
		System.out.println("BankServer Branch " + id + " running.");
		
		(new NodeRuntime(id, new BranchState(), new BranchRequestHandler())).run();
	}
}
