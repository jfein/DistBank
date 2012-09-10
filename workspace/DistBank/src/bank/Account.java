package bank;

import java.io.Serializable;

public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer accountNumber;
    private Double accountBalance;
    
    public Account(Integer accountNumber) {
    	this.accountNumber = accountNumber;
        this.accountBalance = 0.0;
    }

    public Integer getAccountNumber() {
    	return this.accountNumber;
    }
    
    public Double getAccountBalance() {
    	return this.accountBalance;
    }
    
    public void setAccountNumber(Integer accountNumber) {
    	this.accountNumber = accountNumber;
    }
    
    public void setAccountBalance(Double accountBalance) {
    	this.accountBalance = accountBalance;
    }
    
}
