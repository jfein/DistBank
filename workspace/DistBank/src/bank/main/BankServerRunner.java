package bank.main;

import java.io.IOException;
import java.net.InetSocketAddress;

import bank.AccountId;
import bank.BankClient;
import bank.BankServerHandler;
import bank.BankState;

import core.network.server.ServerNodeRuntime;

public class BankServerRunner {

	public static void main(String[] args) throws IOException {
		String host = "localhost";
		int port = Integer.parseInt(args[0]);
		InetSocketAddress myAddress = new InetSocketAddress(host, port);

		System.out.println("Starting on port " + port);
		if (port == 4002) {
			new ServerNodeRuntime(myAddress, new BankState(2),
					new BankServerHandler());
		} else {
			new ServerNodeRuntime(myAddress, new BankState(1),
					new BankServerHandler());
		}

		if (port == 4001) {
			AccountId test = new AccountId(1,40);
			AccountId destTest = new AccountId(2, 9);
			InetSocketAddress dest = new InetSocketAddress("localhost", 4000);

			System.out.println("Withdraw 54.89 from Acnt 111: " + BankClient.withdraw(dest, test, 54.89, 111));
			System.out.println("Deposit 100 to Acnt 1111:" + BankClient.deposit(dest, test, 100.00, 112));
			System.out.println("Query 1111: " + BankClient.query(dest, test, 113));
			System.out.println("Transfer from 1111 to 9: " + BankClient.transfer(dest, test, destTest, 30, 114));
			

		}
	}
}
