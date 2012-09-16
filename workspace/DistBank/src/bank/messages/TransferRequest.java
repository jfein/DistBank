package bank.messages;

import bank.AccountId;

public class TransferRequest extends BranchRequest {

	private static final long serialVersionUID = 1L;
	private AccountId destAccountId;
	private Double amount;
	
	public TransferRequest(AccountId accountId, AccountId destAccount, Double amount, Integer serial) {
		super(accountId, serial);
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
