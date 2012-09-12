package bank;

import java.net.InetSocketAddress;
import java.util.HashMap;

import core.distsys.State;

public class BankState implements State {

	private Integer branchId;
	private HashMap<AccountId, Account> branchAccounts;

	public BankState(Integer branchId) {
		this.branchId = branchId;
		this.branchAccounts = new HashMap<AccountId, Account>();
	}
	
	public void addNewAccount(AccountId account) {
   	 this.branchAccounts.put(account, new Account(account));
    }

    public Double deposit(AccountId accountId, double amount, Integer serialNumber) {
   	 //check if this accountId exists
    System.out.println("Deposit Account : " + accountId.getAccountNumber());
   	 if (!branchAccounts.containsKey(accountId)) {
   		 System.out.println("Account doesn't exist. \n");
   		addNewAccount(accountId);
   	 }
   	 System.out.println("Size of branch accounts: " + this.branchAccounts.size());
   	 if(branchAccounts.get(accountId).isUsedSerialNumber(serialNumber)) {
   		 return 0.0;
   	 }
   	 System.out.println("after checking used serial");
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 System.out.println("account to change: " + accountToDo.getAccountNumber());
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() + amount);
   	 branchAccounts.put(accountId, accountToDo);
	 //Add the serial number as used
   	 branchAccounts.get(accountId).insertUsedSerialNumber(serialNumber);
   	 System.out.println("Serial ID of this transaction: " +serialNumber);
   	 System.out.println("Account " + accountId + " balance: " + accountToDo.getAccountBalance());
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double withdraw(AccountId accountId, double amount, Integer serialNumber) {
   	 //check if this accountId exists
     System.out.println("Account : " + accountId);
   	 if (!branchAccounts.containsKey(accountId)) {
        System.out.println("Account doesn't exist. \n");
   		addNewAccount(accountId);
   	 }
	 if(branchAccounts.get(accountId).isUsedSerialNumber(serialNumber)) {
   		 return 0.0;
   	 }
   	 //TODO: check the serial number
   	 // Set account amount
   	 Account accountToDo = branchAccounts.get(accountId);
   	 accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
   	 branchAccounts.put(accountId, accountToDo);
	 //Add the serial number as used
   	 branchAccounts.get(accountId).insertUsedSerialNumber(serialNumber);
	 System.out.println("Serial ID of this transaction: " +serialNumber);
   	 System.out.println("Account " + accountId + " balance: " + accountToDo.getAccountBalance());
   	 return accountToDo.getAccountBalance(); 
    }
    
    public Double query(AccountId accountId, Integer serialNumber) {
   	 //check if this accountId exists
   	 if (!branchAccounts.containsKey(accountId)) {
   		addNewAccount(accountId);
   	 }
	 if(branchAccounts.get(accountId).isUsedSerialNumber(serialNumber)) {
   		 return 0.0;
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
   		 return 0.0;
   	 }
   	
   	 //TODO: check the serial number
   	 
   	 // Update the source account amount
   	 double newBalance =  withdraw(srcAccountId, amount, serialNumber);
   	 //TODO: Send a deposit request to the destination
   	 InetSocketAddress dest = new InetSocketAddress("localhost", 4002);
   	 BankClient.deposit(destAccountId, amount, serialNumber);
   	 
   	 //Add the serial number as used
   	 branchAccounts.get(srcAccountId).insertUsedSerialNumber(serialNumber);
   	 return newBalance;
    }

}
