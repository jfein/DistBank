package bank;

import java.net.SocketAddress;

import core.network.client.Client;

import bank.messages.BankResponse;
import bank.messages.QueryRequest;

public class BankClient extends Client {

	public static double query(SocketAddress a) {
		QueryRequest req = new QueryRequest();
		BankResponse resp = BankClient.exec(a, req);
		return resp.getAmt();
	}

}
