package bank;

import java.net.InetSocketAddress;
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

    public Double deposit(Integer accountId, double amount, String serialNumber) {
   	 //check if this accountId exists
    System.out.println("Deposit Account : " + accountId);
   	 if (!branchAccounts.containsKey(accountId)) {
   		 System.out.println("Account doesn't exist. \n");
   		addNewAccount(accountId);
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() + amount);
   	 branchAccounts.put(accountId, accountToDo);
   	 System.out.println("Serial ID of this transaction: " +serialNumber);
   	 System.out.println("Account " + accountId + " balance: " + accountToDo.getAccountBalance());
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double withdraw(Integer accountId, double amount, String serialNumber) {
   	 //check if this accountId exists
     System.out.println("Account : " + accountId);
   	 if (!branchAccounts.containsKey(accountId)) {
        System.out.println("Account doesn't exist. \n");
   		addNewAccount(accountId);
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
   	 branchAccounts.put(accountId, accountToDo);
	 System.out.println("Serial ID of this transaction: " +serialNumber);
   	 System.out.println("Account " + accountId + " balance: " + accountToDo.getAccountBalance());
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double query(Integer accountId, String serialNumber) {
   	 //check if this accountId exists
   	 if (!branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 return accountToDo.getAccountBalance();
    }
    
    public Double transfer(Integer srcAccountId, Integer destAccountId, double amount, String serialNumber) {
   	 //check if this accountId exists
   	 if (!branchAccounts.containsKey(srcAccountId)) {
   		addNewAccount(srcAccountId);
   	 }
   	
   	 //TODO: check the serial number
   	 
   	 // Update the source account amount
   	 double newBalance =  withdraw(srcAccountId, amount, serialNumber);
   	 //TODO: Send a deposit request to the destination
   	 InetSocketAddress dest = new InetSocketAddress("localhost", 4002);
   	 BankClient.deposit(dest, destAccountId, amount, serialNumber);
   	 return newBalance;
    }

}
