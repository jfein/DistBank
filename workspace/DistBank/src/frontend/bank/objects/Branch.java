package frontend.bank.objects;

import java.util.ArrayList;
import java.util.HashMap;

import bank.Account;

public class Branch {
	
     private Integer branchId;
     private HashMap<Integer,Account> branchAccounts;
    
     public Branch(Integer branchId) {
    	 this.branchId = branchId;
    	 this.branchAccounts = new HashMap<Integer, Account>();
     }
     
     private void addAccount(Account account) {
    	 this.branchAccounts.put(account.getAccountNumber(), account);
     }
     
     private int Deposit(Integer accountId, Double amount, Integer serialNumber) {
    	 //check if this accountId exists
    	 if (branchAccounts.containsKey(accountId)) {
    		 return -1;
    	 }
    	 //TODO: check the serial number
    	 // Set account amount
    	 Account accountToDo = branchAccounts.get(accountId);
    	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() + amount);
    	 branchAccounts.put(accountId, accountToDo);
    	 return 1; 
     }
     
     private int Withdraw(Integer accountId, Double amount, Integer serialNumber) {
    	 //check if this accountId exists
    	 if (branchAccounts.containsKey(accountId)) {
    		 return -1;
    	 }
    	 //TODO: check the serial number
    	 // Set account amount
    	 Account accountToDo = branchAccounts.get(accountId);
    	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
    	 branchAccounts.put(accountId, accountToDo);
    	 return 1; 
     }
     
     private Double Query(Integer accountId, Integer serialNumber) {
    	 //check if this accountId exists
    	 if (branchAccounts.containsKey(accountId)) {
    		 return -1.0;
    	 }
    	 //TODO: check the serial number
    	 // Set account amount
    	 Account accountToDo = branchAccounts.get(accountId);
    	 return accountToDo.getAccountBalance();
     }
     
     private int Transfer(Integer srcAccountId, Integer destAccountId, Double amount, Integer serialNumber) {
    	 //check if this accountId exists
    	 if (branchAccounts.containsKey(srcAccountId)) {
    		 return -1;
    	 }
    	
    	 //TODO: check the serial number
    	 
    	 // Update the source account amount
    	 Account accountToDo = branchAccounts.get(srcAccountId);
    	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
    	 branchAccounts.put(srcAccountId, accountToDo);
    	 
    	 //TODO: Send a deposit request to the destination
    	 return 1; 
     }

}
