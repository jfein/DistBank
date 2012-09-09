package bank.distsys;

import abstraction.distsys.State;

public class BankState implements State {

	private int amt;

	public BankState() {
		amt = 0;
	}

	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
		System.out.println("Amt set to " + amt);
	}
}
