package bank.messages;

import core.app.AppId;
import bank.branch.AccountId;

public class WithdrawRequest extends BranchRequest {

	private static final long serialVersionUID = 1L;
	
	private Double amount;

	public WithdrawRequest(AppId myAppId, AccountId accountId, Double amount, Integer serial) {
		super(myAppId, accountId, serial);
		this.amount = amount;
	}

	public Double getAmount() {
		return this.amount;
	}

}
