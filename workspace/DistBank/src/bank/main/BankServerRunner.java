package bank.main;

import java.io.IOException;

import bank.BankServerHandler;
import bank.BankState;
import bank.BranchId;

import core.node.ServerNodeRuntime;

public class BankServerRunner {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		BranchId id = new BranchId(Integer.parseInt(args[0]));
		ServerNodeRuntime.init(id, new BankState(), new BankServerHandler());
	}
}
