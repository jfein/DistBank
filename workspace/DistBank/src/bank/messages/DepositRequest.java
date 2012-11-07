package bank.messages;

import core.app.AppId;
import bank.branch.AccountId;

public class DepositRequest extends BranchRequest {

	private static final long serialVersionUID = 2192276582514840321L;

	private Double amount;

	public DepositRequest(AppId<?> senderAppId, AccountId accountId, Double amount, Integer serial) {
		super(senderAppId, accountId, serial);
		this.amount = amount;
	}

	public Double getAmount() {
		return this.amount;
	}

}
