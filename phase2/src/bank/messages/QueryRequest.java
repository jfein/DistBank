package bank.messages;

import bank.AccountId;

public class QueryRequest extends BranchRequest {

	private static final long serialVersionUID = 4050589149139383647L;

	public QueryRequest(AccountId accountId, Integer serial) {
		super(accountId, serial);
	}
	
}
