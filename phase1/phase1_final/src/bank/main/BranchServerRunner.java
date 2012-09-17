package bank.main;

import java.io.IOException;

import bank.BranchServerHandler;
import bank.BranchState;
import bank.BranchId;

import core.node.ServerNodeRuntime;

public class BranchServerRunner {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		BranchId id = new BranchId(Integer.parseInt(args[0]));
		ServerNodeRuntime.init(id, new BranchState(), new BranchServerHandler());

		System.out.println("BankServer Branch " + id + " running.");
	}
}
