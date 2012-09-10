package bank.main;

import java.io.IOException;
import java.net.InetSocketAddress;

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

		new ServerNodeRuntime(myAddress, new BankState(), new BankServerHandler());

		if (port == 4001) {
			InetSocketAddress dest = new InetSocketAddress("localhost", 4000);
			System.out.println("Doing stuff");
			BankClient.change(dest, 15);
			System.out.println("Other X: " + BankClient.query(dest));
		}
	}
}
