package bank.network.messages;

import abstraction.network.common.Message;

public class ChangeRequest implements Message {

	private static final long serialVersionUID = -1365786274945693277L;

	private int newAmt;

	public ChangeRequest(int newAmt) {
		this.newAmt = newAmt;
	}

	public int getNewAmt() {
		return newAmt;
	}

}
