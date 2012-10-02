package bank.messages;

import bank.AccountId;

public class SnapshotRequest extends BranchRequest {

	private static final long serialVersionUID = 1L;
	

	public SnapshotRequest(AccountId accountId, Integer serial) {
		super(accountId, serial);
		// TODO Auto-generated constructor stub
	}

}
