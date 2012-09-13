package bank.messages;

import bank.AccountId;

public class DepositRequest extends BankRequest {

	private static final long serialVersionUID = 2192276582514840321L;

	private Double amount;

	public DepositRequest(AccountId accountId, Double amount, Integer serial) {
		super(accountId, serial);
		this.amount = amount;
	}

	public Double getAmount() {
		return this.amount;
	}

}
