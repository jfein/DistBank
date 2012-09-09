package bank.network.client;

import java.net.SocketAddress;

import abstraction.network.client.Client;
import bank.network.messages.BankResponse;
import bank.network.messages.ChangeRequest;
import bank.network.messages.QueryRequest;

public class BankClient extends Client {

	public static int query(SocketAddress a) {
		QueryRequest req = new QueryRequest();
		BankResponse resp = (BankResponse) BankClient.exec(a, req);
		return resp.getAmt();
	}

	public static int change(SocketAddress a, int amt) {
		ChangeRequest req = new ChangeRequest(amt);
		BankResponse resp = (BankResponse) BankClient.exec(a, req);
		return resp.getAmt();
	}

}
