package bank.messages;

import core.network.common.Message;

public class TransferRequest implements BankRequest {
	private Integer srcAccountId;
	private Integer destAccountId;
	private Double amount;
	
	public TransferRequest(Integer accountId, Integer destAccount, Double amount) {
		this.srcAccountId = accountId;
		this.destAccountId = destAccount;
		this.amount = amount;
	}
	@Override
	public Integer getSrcAccountId() {
		// TODO Auto-generated method stub
		return this.srcAccountId;
	}

	@Override
	public Integer getSerialNumber() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer getDestAccountId() {
		return this.destAccountId;
	}
	
	public Double getAmount() {
		return this.amount;
	}
}
