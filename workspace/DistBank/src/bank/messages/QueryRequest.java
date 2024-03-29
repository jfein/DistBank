package bank.messages;

import core.app.AppId;
import bank.branch.AccountId;

public class QueryRequest extends BranchRequest {

	private static final long serialVersionUID = 4050589149139383647L;

	public QueryRequest(AppId<?> senderAppId, AccountId accountId, Integer serial) {
		super(senderAppId, accountId, serial);
	}

}
