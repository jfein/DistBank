package bank.messages;

import core.network.common.Message;

public class BankResponse extends Message {

	private static final long serialVersionUID = -3689706541111933234L;

	private double amt;

	public BankResponse(double amt) {
		this.amt = amt;
	}

	public double getAmt() {
		return amt;
	}
}
