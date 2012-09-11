package bank.messages;

import core.network.common.Message;

public abstract class BankRequest extends Message {

	private static final long serialVersionUID = 4221497401551041572L;

	private Integer srcAccountId;
	private String serialNumber;

	public BankRequest(Integer accountId, String serial) {
		this.srcAccountId = accountId;
		this.serialNumber = serial;
	}

	public Integer getSrcAccountId() {
		return this.srcAccountId;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}
}
