package bank.messages;

public class TransferRequest extends BankRequest {

	private static final long serialVersionUID = 1L;
	private Integer destAccountId;
	private Double amount;
	
	public TransferRequest(Integer accountId, Integer destAccount, Double amount, String serial) {
		super(accountId, serial);
		this.destAccountId = destAccount;
		this.amount = amount;
	}
	
	public Integer getDestAccountId() {
		return this.destAccountId;
	}
	
	public Double getAmount() {
		return this.amount;
	}
}
