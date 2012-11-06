package bank.branch;

import core.app.AppId;
import core.network.Client;

import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchClient extends Client {

	public static BranchResponse query(AppId myAppId, AccountId accountId, Integer serial) {
		QueryRequest req = new QueryRequest(myAppId, accountId, serial);
		return BranchClient.exec(req);
	}

	public static BranchResponse deposit(AppId myAppId, AccountId accountId, double amount, Integer serial) {
		DepositRequest req = new DepositRequest(myAppId, accountId, amount, serial);
		return BranchClient.exec(req);
	}

	public static BranchResponse withdraw(AppId myAppId, AccountId accountId, double amount, Integer serial) {
		WithdrawRequest req = new WithdrawRequest(myAppId, accountId, amount, serial);
		return BranchClient.exec(req);
	}

	public static BranchResponse transfer(AppId myAppId, AccountId srcAccountId, AccountId destAccountId,
			double amount, Integer serial) {
		TransferRequest req = new TransferRequest(myAppId, srcAccountId, destAccountId, amount, serial);
		return BranchClient.exec(req);
	}

}
