package bank;

import java.util.HashMap;

import core.distsys.State;

public class BankState implements State {

	private Integer branchId;
	private HashMap<Integer, Account> branchAccounts;

	public BankState(Integer branchId) {
		this.branchId = branchId;
		this.branchAccounts = new HashMap<Integer, Account>();
	}
	
	public void addNewAccount(Integer account) {
   	 this.branchAccounts.put(account, new Account(account));
    }

    public Double deposit(Integer accountId, Double amount, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() + amount);
   	 branchAccounts.put(accountId, accountToDo);
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double withdraw(Integer accountId, Double amount, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
   	 branchAccounts.put(accountId, accountToDo);
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double query(Integer accountId, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 return accountToDo.getAccountBalance();
    }
    
    public Double transfer(Integer srcAccountId, Integer destAccountId, Double amount, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (branchAccounts.containsKey(srcAccountId)) {
   		addNewAccount(srcAccountId);
   	 }
   	
   	 //TODO: check the serial number
   	 
   	 // Update the source account amount
   	 Account accountToDo = branchAccounts.get(srcAccountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
   	 branchAccounts.put(srcAccountId, accountToDo);
   	 
   	 //TODO: Send a deposit request to the destination
   	 return accountToDo.getAccountBalance(); 
    }

}
