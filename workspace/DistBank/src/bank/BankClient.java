package bank;

import core.network.client.Client;

import bank.messages.BankResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BankClient extends Client {

	public static double query(AccountId accountId, Integer serial) {
		QueryRequest req = new QueryRequest(accountId, serial);
		BankResponse resp = BankClient.exec(accountId.getBranchId(), req);
		return (resp != null) ? resp.getAmt() : 0;
	}

	public static double deposit(AccountId accountId, double amount, Integer serial) {
		DepositRequest req = new DepositRequest(accountId, amount, serial);
		System.out.println("Deposit request id: " + req.getSrcAccountId());
		BankResponse resp = BankClient.exec(accountId.getBranchId(), req);
		return (resp != null) ? resp.getAmt() : 0;
	}

	public static double withdraw(AccountId accountId, double amount, Integer serial) {
		System.out.println("Withdraw request id: " + accountId);
		WithdrawRequest req = new WithdrawRequest(accountId, amount, serial);
		BankResponse resp = BankClient.exec(accountId.getBranchId(), req);
		return (resp != null) ? resp.getAmt() : 0;
	}

	public static double transfer(AccountId srcAccountId, AccountId destAccountId, double amount, Integer serial) {
		TransferRequest req = new TransferRequest(srcAccountId, destAccountId, amount, serial);
		BankResponse resp = BankClient.exec(srcAccountId.getBranchId(), req);
		return (resp != null) ? resp.getAmt() : 0;
	}
}
