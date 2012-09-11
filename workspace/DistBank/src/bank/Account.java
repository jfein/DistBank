package bank;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private AccountId accountId;
    private Double accountBalance;
    private ArrayList<Integer> usedSerialNumbers;
    
    public Account(AccountId accountId) {
    	this.accountId = accountId;
        this.accountBalance = 0.0;
    }

    public boolean isUsedSerialNumber(Integer serialNumber) {
    	return usedSerialNumbers.contains(serialNumber);
    }
    public AccountId getAccountNumber() {
    	return this.accountId;
    }
    
    public void insertUsedSerialNumber(Integer serialNumber) {
    	this.usedSerialNumbers.add(serialNumber);
    }
    
    public Double getAccountBalance() {
    	return this.accountBalance;
    }
    
    public void setAccountNumber(AccountId accountId) {
    	this.accountId = accountId;
    }
    
    public void setAccountBalance(Double accountBalance) {
    	this.accountBalance = accountBalance;
    }
    
}
