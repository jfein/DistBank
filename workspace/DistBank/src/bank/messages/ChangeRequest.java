package bank.messages;

import core.network.common.Message;

public class ChangeRequest extends Message {

	private static final long serialVersionUID = -1365786274945693277L;

	private int newAmt;

	public ChangeRequest(int newAmt) {
		this.newAmt = newAmt;
	}

	public int getNewAmt() {
		return newAmt;
	}

}
