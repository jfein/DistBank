package bank;

import core.network.client.Client;

import bank.messages.BankResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BankClient extends Client {

	public static BankResponse query(AccountId accountId, Integer serial) {
		QueryRequest req = new QueryRequest(accountId, serial);
		return BankClient.exec(accountId.getBranchId(), req);
	}

	public static BankResponse deposit(AccountId accountId, double amount,
			Integer serial) {
		DepositRequest req = new DepositRequest(accountId, amount, serial);
		return BankClient.exec(accountId.getBranchId(), req);
	}

	public static BankResponse withdraw(AccountId accountId, double amount,
			Integer serial) {
		WithdrawRequest req = new WithdrawRequest(accountId, amount, serial);
		return BankClient.exec(accountId.getBranchId(), req);
	}

	public static BankResponse transfer(AccountId srcAccountId,
			AccountId destAccountId, double amount, Integer serial) {
		TransferRequest req = new TransferRequest(srcAccountId, destAccountId,
				amount, serial);
		return BankClient.exec(srcAccountId.getBranchId(), req);
	}
}
