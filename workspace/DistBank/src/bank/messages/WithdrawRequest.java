package bank.messages;

import core.network.common.Message;

public class WithdrawRequest implements BankRequest {
	private Integer srcAccountId;
	private Double amount;
	
	public WithdrawRequest (Integer accountId, Double amount) {
		this.srcAccountId = srcAccountId;
		this.amount = amount;
	}
	@Override
	public Integer getSrcAccountId() {
		// TODO Auto-generated method stub
		return srcAccountId;
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
