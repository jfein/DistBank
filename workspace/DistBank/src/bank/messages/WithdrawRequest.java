package bank.messages;

public class WithdrawRequest extends BankRequest {

	private static final long serialVersionUID = 1L;
	private Double amount;
	
	public WithdrawRequest (Integer accountId, Double amount, String serial) {
		super(accountId, serial);
		this.amount = amount;
	}
	
	public Double getAmount() {
		return this.amount;
	}

}
