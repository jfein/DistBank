package bank.messages;

import core.app.AppId;
import bank.branch.AccountId;

public class TransferRequest extends BranchRequest {

	private static final long serialVersionUID = 1L;

	private AccountId destAccountId;
	private Double amount;

	public TransferRequest(AppId<?> myAppId, AccountId accountId, AccountId destAccount, Double amount, Integer serial) {
		super(myAppId, accountId, serial);
		this.destAccountId = destAccount;
		this.amount = amount;
	}

	public AccountId getDestAccountId() {
		return this.destAccountId;
	}

	public Double getAmount() {
		return this.amount;
	}
}
