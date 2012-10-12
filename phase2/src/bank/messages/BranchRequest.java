package bank.messages;

import bank.AccountId;
import core.network.messages.Request;

public abstract class BranchRequest extends Request {

	private static final long serialVersionUID = 4221497401551041572L;

	private AccountId srcAccountId;
	private Integer serialNumber;

	public BranchRequest(AccountId accountId, Integer serial) {
		this.srcAccountId = accountId;
		this.serialNumber = serial;
	}

	public AccountId getSrcAccountId() {
		return this.srcAccountId;
	}

	public Integer getSerialNumber() {

		return this.serialNumber;
	}
}
