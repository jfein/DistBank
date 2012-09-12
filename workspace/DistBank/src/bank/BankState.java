package bank;

import java.util.HashMap;

import core.node.NodeState;

public class BankState implements NodeState {

	private HashMap<AccountId, Account> branchAccounts;

	public BankState() {
		this.branchAccounts = new HashMap<AccountId, Account>();
	}
	
	public void addNewAccount(AccountId account) {
   	 this.branchAccounts.put(account, new Account(account));
    }

    public Double deposit(AccountId accountId, double amount, Integer serialNumber) {
   	 if (!branchAccounts.containsKey(accountId)) {
   		 System.out.println("Account doesn't exist. \n");
   		addNewAccount(accountId);
   	 }
   	 if(branchAccounts.get(accountId).isUsedSerialNumber(serialNumber)) {
   		 return branchAccounts.get(accountId).getAccountBalance();
   	 }
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() + amount);
   	 branchAccounts.put(accountId, accountToDo);
	 //Add the serial number as used
   	 branchAccounts.get(accountId).insertUsedSerialNumber(serialNumber);
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double withdraw(AccountId accountId, double amount, Integer serialNumber) {
    	
   	 //check if this accountId exists
   	 if (!branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
	 if(branchAccounts.get(accountId).isUsedSerialNumber(serialNumber)) {
		 return branchAccounts.get(accountId).getAccountBalance();
   	 }

   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
   	 branchAccounts.put(accountId, accountToDo);
   	 
	 //Add the serial number as used
   	 branchAccounts.get(accountId).insertUsedSerialNumber(serialNumber);
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double query(AccountId accountId, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (!branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
	 if(branchAccounts.get(accountId).isUsedSerialNumber(serialNumber)) {
		 return branchAccounts.get(accountId).getAccountBalance();
   	 }
	 //Add the serial number as used
   	 branchAccounts.get(accountId).insertUsedSerialNumber(serialNumber);
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 return accountToDo.getAccountBalance();
    }
    
    public Double transfer(AccountId srcAccountId, AccountId destAccountId, double amount, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (!branchAccounts.containsKey(srcAccountId)) {
   		addNewAccount(srcAccountId);
   	 }
	 if(branchAccounts.get(srcAccountId).isUsedSerialNumber(serialNumber)) {
		 return branchAccounts.get(srcAccountId).getAccountBalance();
   	 }
   	 // Update the source account amount
   	 double newBalance =  withdraw(srcAccountId, amount, serialNumber);
   	 BankClient.deposit(destAccountId, amount, serialNumber);  	 
   	 //Add the serial number as used
   	 branchAccounts.get(srcAccountId).insertUsedSerialNumber(serialNumber);
   	 return newBalance;
    }

}
