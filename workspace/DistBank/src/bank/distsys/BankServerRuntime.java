package bank.distsys;

import java.net.InetSocketAddress;

import bank.network.client.BankClient;
import bank.network.server.BankServer;

import abstraction.distsys.Runtime;
import abstraction.network.common.NetworkInterface;

public class BankServerRuntime extends Runtime {

	public BankServerRuntime(String host, int port) {
		super(new BankServer(), new BankState(), new NetworkInterface(
				new InetSocketAddress(host, port)));
	}

	public static void main(String[] args) {
		System.out.println("Starting on port " + args[0]);

		new BankServerRuntime("localhost", Integer.parseInt(args[0]));

		if (Integer.parseInt(args[0]) == 4001) {
			System.out.println("Doing stuff");
			BankClient.change(new InetSocketAddress("localhost", 4000), 10);
			System.out.println("Other X: "
					+ BankClient
							.query(new InetSocketAddress("localhost", 4000)));
		}
	}

}
