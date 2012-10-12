package bank.messages;

import bank.AccountId;

public class WithdrawRequest extends BranchRequest {

	private static final long serialVersionUID = 1L;
	private Double amount;
	
	public WithdrawRequest (AccountId accountId, Double amount, Integer serial) {
		super(accountId, serial);
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}

}
