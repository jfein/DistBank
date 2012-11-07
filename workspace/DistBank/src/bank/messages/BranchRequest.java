package bank.messages;

import bank.branch.AccountId;
import core.app.AppId;
import core.messages.Request;

public abstract class BranchRequest extends Request {

	private static final long serialVersionUID = 4221497401551041572L;

	private AccountId srcAccountId;
	private Integer serialNumber;

	public BranchRequest(AppId<?> senderAppId, AccountId accountId, Integer serial) {
		super(senderAppId, accountId.getBranchAppId());
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
