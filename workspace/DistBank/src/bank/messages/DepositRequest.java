package bank.messages;

public class DepositRequest extends BankRequest {

	private static final long serialVersionUID = -4127927538689030026L;

	private Double amount;

	public DepositRequest(Integer accountId, Double amount, String serial) {
		super(accountId, serial);
		this.amount = amount;
	}

	public Double getAmount() {
		return this.amount;
	}

}
