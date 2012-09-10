package bank.messages;

import core.network.common.Message;

public class TransferRequest implements BankRequest {
	private Integer srcAccountId;

	public TransferRequest(Integer accountId) {
		this.srcAccountId = accountId;
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
	
}
