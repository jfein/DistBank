package bank.messages;

import core.network.common.Message;

public class BankResponse implements Message {

	private static final long serialVersionUID = -3689706541111933234L;

	private int amt;

	public BankResponse(int amt) {
		this.amt = amt;
	}

	public int getAmt() {
		return amt;
	}

}
