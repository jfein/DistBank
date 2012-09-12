package bank.main;

import java.io.IOException;
import java.net.InetSocketAddress;

import bank.AccountId;
import bank.BankClient;
import bank.BankServerHandler;
import bank.BankState;
import bank.BranchId;

import core.network.server.ServerNodeRuntime;

public class BankServerRunner {

	public static void main(String[] args) throws IOException {	
		BranchId id = new BranchId(args[0]);		
		new ServerNodeRuntime(id, new BankState(1), new BankServerHandler());
	}
}
