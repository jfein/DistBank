package bank;

public class AccountId {
	private Integer accountNumber;
	private Integer branchNumber;
	
	public AccountId(Integer accountNumber, Integer branchNumber) {
		this.accountNumber = accountNumber;
		this.branchNumber = branchNumber;
	}
	
	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setBranchNumber(Integer branchNumber) {
		this.branchNumber = branchNumber;
	}
	
	public Integer getAccountNumber() {
		return this.accountNumber;
	}
	
	public Integer getBranchNumber() {
		return this.branchNumber;
	}
	
	public boolean equals(Object o){
		if (o instanceof AccountId) {
			AccountId other = (AccountId) o;
			if (other.accountNumber == this.accountNumber && other.branchNumber == this.branchNumber) {
				return true;
			}
			return false;
		}
		return false;
	}
}
