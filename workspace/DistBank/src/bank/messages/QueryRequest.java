package bank.messages;

public class QueryRequest extends BankRequest {

	private static final long serialVersionUID = 4050589149139383647L;

	public QueryRequest(Integer accountId, Integer serial) {
		super(accountId, serial);
	}
	
}
