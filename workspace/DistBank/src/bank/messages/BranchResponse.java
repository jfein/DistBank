package bank.messages;

import core.app.AppId;
import core.messages.Response;

public class BranchResponse extends Response {

	private static final long serialVersionUID = -3689706541111933234L;

	private double amt;
	private boolean wasSuccessfull;

	public BranchResponse(AppId receiverAppId, double amt, boolean wasSuccessfull) {
		super(receiverAppId);
		this.amt = amt;
		this.wasSuccessfull = wasSuccessfull;
	}

	public double getAmt() {
		return amt;
	}

	public boolean wasSuccessfull() {
		return wasSuccessfull;
	}
}
