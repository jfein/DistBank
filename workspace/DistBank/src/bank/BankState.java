package bank;

import core.distsys.State;

public class BankState implements State {

	private int amt;

	public BankState() {
		amt = 0;
	}

	public int getAmt() {
		System.out.println("Returning amt " + amt);
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
		System.out.println("Amt set to " + amt);
	}
}
