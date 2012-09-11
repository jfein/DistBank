package bank;

import java.net.SocketAddress;

import core.network.client.Client;

import bank.messages.BankResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BankClient extends Client {

	public static double query(SocketAddress branch, Integer accountId, String serial) {
		//TODO do serial number
		QueryRequest req = new QueryRequest(accountId, serial);
		BankResponse resp = BankClient.exec(branch, req);
		return resp.getAmt();
	}

	public static double deposit(SocketAddress branch, Integer accountId, double amount, String serial) {
		DepositRequest req = new DepositRequest(accountId, amount, serial);
		System.out.println("Deposit request id: " + req.getSrcAccountId());
		BankResponse resp = BankClient.exec(branch, req);
		return resp.getAmt();
	}
	
	public static double withdraw(SocketAddress branch, Integer accountId, double amount, String serial) {
		WithdrawRequest req = new WithdrawRequest(accountId, amount, serial);
		BankResponse resp = BankClient.exec(branch, req);
		return resp.getAmt();
	}
	
	public static double transfer(SocketAddress branch, Integer srcAccountId, Integer destAccountId, double amount, String serial) {
		TransferRequest req = new TransferRequest(srcAccountId, destAccountId, amount, serial);
		BankResponse resp = BankClient.exec(branch, req);
		return resp.getAmt();
	}
}
