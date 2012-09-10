package bank.messages;

import core.network.common.Message;

public class BankResponse implements Message {

	private static final long serialVersionUID = -3689706541111933234L;

	private int amt;

	public BankResponse(Double double1) {
		this.amt = double1;
	}

	public int getAmt() {
		return amt;
	}

}
