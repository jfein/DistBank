package bank.messages;

import core.network.common.Message;

public class DepositRequest implements BankRequest {
	private Integer srcAccountId;
	private Double amount;
	
	public DepositRequest(Integer accountId, Double amount) {
		this.srcAccountId = accountId;
		this.amount = amount;
	}
	@Override
	public Integer getSrcAccountId() {
		return this.srcAccountId;
	}

	@Override
	public Integer getSerialNumber() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Double getAmount() {
		return this.amount;
	}

}
