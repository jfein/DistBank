package bank.messages;

import core.network.messages.Response;

public class BranchResponse extends Response {

	private static final long serialVersionUID = -3689706541111933234L;

	private double amt;
	private boolean wasSuccessfull;

	public BranchResponse(double amt, boolean wasSuccessfull) {
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
