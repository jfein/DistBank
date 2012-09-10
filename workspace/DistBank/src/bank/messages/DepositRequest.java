package bank.messages;

import core.network.common.Message;

public class DepositRequest implements BankRequest {
	private Integer srcAccountId;
	
	public DepositRequest(Integer accountId) {
		this.srcAccountId = accountId;
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

}
