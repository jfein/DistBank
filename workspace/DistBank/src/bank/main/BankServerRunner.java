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

		new ServerNodeRuntime(id, new BankState(2), new BankServerHandler());

		AccountId test = new AccountId("01.40");
		AccountId destTest = new AccountId("02.9");
		InetSocketAddress dest = new InetSocketAddress("localhost", 4000);

		System.out.println("Withdraw 54.89 from Acnt 111: "
				+ BankClient.withdraw(test, 54.89, 111));
		System.out.println("Deposit 100 to Acnt 1111:"
				+ BankClient.deposit(test, 100.00, 112));
		System.out.println("Query 1111: " + BankClient.query(test, 113));
		System.out.println("Transfer from 1111 to 9: "
				+ BankClient.transfer(test, destTest, 30, 114));

	}
}
