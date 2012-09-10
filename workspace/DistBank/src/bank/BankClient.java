package bank;

import java.net.SocketAddress;

import core.network.client.Client;

import bank.messages.BankResponse;
import bank.messages.ChangeRequest;
import bank.messages.QueryRequest;

public class BankClient extends Client {

	public static int query(SocketAddress a) {
		QueryRequest req = new QueryRequest();
		BankResponse resp = BankClient.exec(a, req);
		return resp.getAmt();
	}

	public static int change(SocketAddress a, int amt) {
		ChangeRequest req = new ChangeRequest(amt);
		BankResponse resp = BankClient.exec(a, req);
		return resp.getAmt();
	}

}
