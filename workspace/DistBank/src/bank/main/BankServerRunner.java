package bank.main;

import java.io.IOException;

import bank.BankServerHandler;
import bank.BankState;
import bank.BranchId;

import core.node.ServerNodeRuntime;

public class BankServerRunner {

	public static void main(String[] args) throws IOException {
		BranchId id = new BranchId(args[0]);
		ServerNodeRuntime.init(id, new BankState(id), new BankServerHandler());
	}
}
