package bank;

import core.network.Client;

import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchClient extends Client {

	public static BranchResponse query(AccountId accountId, Integer serial) {
		QueryRequest req = new QueryRequest(accountId, serial);
		return BranchClient.exec(accountId.getBranchId(), req);
	}

	public static BranchResponse deposit(AccountId accountId, double amount,
			Integer serial) {
		DepositRequest req = new DepositRequest(accountId, amount, serial);
		return BranchClient.exec(accountId.getBranchId(), req);
	}

	public static BranchResponse withdraw(AccountId accountId, double amount,
			Integer serial) {
		WithdrawRequest req = new WithdrawRequest(accountId, amount, serial);
		return BranchClient.exec(accountId.getBranchId(), req);
	}

	public static BranchResponse transfer(AccountId srcAccountId,
			AccountId destAccountId, double amount, Integer serial) {
		TransferRequest req = new TransferRequest(srcAccountId, destAccountId,
				amount, serial);
		return BranchClient.exec(srcAccountId.getBranchId(), req);
	}

}
