package bank;

import java.io.Serializable;
import java.util.HashMap;

public class AccountId implements Serializable {
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

	public static void main(String[] args) {
		HashMap<AccountId, Integer> hashset = new HashMap<AccountId, Integer>();
		AccountId x = new AccountId("b0.1");
		hashset.put(x, 9);
		AccountId y = new AccountId("b0.1");
		System.out.println(hashset.containsKey(y));
		System.out.println(hashset.get(y));
		System.out.println("X and Y are equal: " + x.equals(y));
	}
}
