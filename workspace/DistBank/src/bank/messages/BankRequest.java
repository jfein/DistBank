package bank.messages;

import core.network.common.Message;

public abstract class  BankRequest  extends Message{

	private static final long serialVersionUID = 1L;
	private Integer srcAccountId;
	private Integer serialNumber;
	
	public BankRequest(Integer accountId, Integer serial) {
		this.srcAccountId = accountId;
		this.serialNumber = serial;
	}
	
	public Integer getSrcAccountId() {
		return this.srcAccountId;
	}
	
	public Integer getSerialNumber() {
		return this.serialNumber;
	}
}
