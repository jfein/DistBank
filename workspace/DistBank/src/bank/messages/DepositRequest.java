package bank.messages;

public class DepositRequest extends BankRequest {

	private static final long serialVersionUID = 1L;
	private Integer srcAccountId;
	private Double amount;
	
	public DepositRequest(Integer accountId, Double amount, Integer serial) {
		super(accountId, serial);
		this.amount = amount;
	}
	@Override
	public Integer getSrcAccountId() {
		return this.srcAccountId;
	}

	@Override
	public Integer getSerialNumber() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Double getAmount() {
		return this.amount;
	}

}
