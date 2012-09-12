package bank;

import java.io.Serializable;

public class AccountId implements Serializable{
	private Integer accountNumber;
	private BranchId branchId;

	public AccountId(String accountId) {
		String[] tokens = accountId.split("\\.");
		this.accountNumber = Integer.parseInt(tokens[1]);
		this.branchId = new BranchId(tokens[0]);
	}

	public AccountId(Integer accountNumber, String branchId) {
		this.accountNumber = accountNumber;
		this.branchId = new BranchId(branchId);
	}

	public Integer getAccountNumber() {
		return this.accountNumber;
	}

	public BranchId getBranchId() {
		return this.branchId;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AccountId) {
			AccountId other = (AccountId) o;
			if (other.accountNumber == this.accountNumber
					&& other.branchId.equals(this.branchId))
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (this.branchId.getNodeId() + "." + this.accountNumber).hashCode();
	}
}
