package bank.branch;

import java.io.Serializable;

import core.app.AppId;

public class AccountId implements Serializable {

	private static final long serialVersionUID = 4110648994886366615L;

	private AppId<BranchApp> branchAppId;
	private Integer accountNumber;

	public AccountId(String accountId) {
		String[] tokens = accountId.split("\\.");
		this.branchAppId = new AppId<BranchApp>(Integer.parseInt(tokens[0]), BranchApp.class);
		this.accountNumber = Integer.parseInt(tokens[1]);
	}

	public AppId<BranchApp> getBranchAppId() {
		return this.branchAppId;
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
		return this.branchAppId.getAppId() + "." + this.accountNumber;
	}

}
