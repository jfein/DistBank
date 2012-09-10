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

	public void addAccount(Account account) {
		this.branchAccounts.put(account.getAccountNumber(), account);
	}

	public int deposit(Integer accountId, Double amount, Integer serialNumber) {
		// check if this accountId exists
		if (branchAccounts.containsKey(accountId)) {
			return -1;
		}
		// TODO: check the serial number
		// Set account amount
		Account accountToDo = branchAccounts.get(accountId);
		accountToDo.setAccountBalance(accountToDo.getAccountBalance() + amount);
		branchAccounts.put(accountId, accountToDo);
		return 1;
	}

	private int withdraw(Integer accountId, Double amount, Integer serialNumber) {
		// check if this accountId exists
		if (branchAccounts.containsKey(accountId)) {
			return -1;
		}
		// TODO: check the serial number
		// Set account amount
		Account accountToDo = branchAccounts.get(accountId);
		accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
		branchAccounts.put(accountId, accountToDo);
		return 1;
	}

	public Double query(Integer accountId, Integer serialNumber) {
		// check if this accountId exists
		if (branchAccounts.containsKey(accountId)) {
			return -1.0;
		}
		// TODO: check the serial number
		// Set account amount
		Account accountToDo = branchAccounts.get(accountId);
		return accountToDo.getAccountBalance();
	}

	private int transfer(Integer srcAccountId, Integer destAccountId,
			Double amount, Integer serialNumber) {
		// check if this accountId exists
		if (branchAccounts.containsKey(srcAccountId)) {
			return -1;
		}

		// TODO: check the serial number

		// Update the source account amount
		Account accountToDo = branchAccounts.get(srcAccountId);
		accountToDo.setAccountBalance(accountToDo.getAccountBalance() - amount);
		branchAccounts.put(srcAccountId, accountToDo);

		// TODO: Send a deposit request to the destination
		return 1;
	}

}
