package bank.messages;

import core.network.common.Message;

public class QueryRequest extends Message implements BankRequest {

	private static final long serialVersionUID = 4050589149139383647L;
	
	private Integer accountId;

	public QueryRequest(){}

	@Override
	public Integer getSrcAccountId() {
		// TODO Auto-generated method stub
		return accountId;
	}

	@Override
	public Integer getSerialNumber() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
