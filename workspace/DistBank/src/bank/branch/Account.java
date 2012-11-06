package bank.branch;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Account implements Serializable {

	private static final long serialVersionUID = -8124849113344519323L;

	private AccountId accountId;
	private Double accountBalance;
	private Set<Integer> usedSerialNumbers;

	public Account(AccountId accountId) {
		this.accountId = accountId;
		this.accountBalance = 0.0;
		this.usedSerialNumbers = new HashSet<Integer>();
	}

	public boolean isUsedSerialNumber(Integer serialNumber) {
		return usedSerialNumbers.contains(serialNumber);
	}

	public void insertUsedSerialNumber(Integer serialNumber) {
		this.usedSerialNumbers.add(serialNumber);
	}

	public AccountId getAccountId() {
		return this.accountId;
	}

	public Double getAccountBalance() {
		return this.accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

}
