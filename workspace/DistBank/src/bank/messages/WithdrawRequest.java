package bank.messages;

import core.network.common.Message;

public class WithdrawRequest implements BankRequest {
	private Integer srcAccountId;
	
	public WithdrawRequest (Integer accountId) {
		this.srcAccountId = srcAccountId;
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

}
