package bank;

import java.io.Serializable;

public class AccountId implements Serializable {

	private static final long serialVersionUID = 4110648994886366615L;

	private BranchId branchId;
	private Integer accountNumber;

	public AccountId(String accountId) {
		String[] tokens = accountId.split("\\.");
		this.branchId = new BranchId(Integer.parseInt(tokens[0]));
		this.accountNumber = Integer.parseInt(tokens[1]);
	}

	public BranchId getBranchId() {
		return this.branchId;
	}

	public Integer getAccountNumber() {
		return this.accountNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof AccountId)
			return this.hashCode() == ((AccountId) o).hashCode();
		return false;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		return this.branchId.getNodeId() + "." + this.accountNumber;
	}

}
