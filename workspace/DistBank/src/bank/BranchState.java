package bank;

import java.util.HashMap;

import bank.messages.BranchResponse;

import core.node.NodeRuntime;
import core.node.NodeState;

public class BranchState implements NodeState {

	private HashMap<AccountId, Account> branchAccounts;

	public BranchState() {
		this.branchAccounts = new HashMap<AccountId, Account>();
	}

	private Account getAccountCreateIfNotExist(AccountId accountId) {
		if (!branchAccounts.containsKey(accountId))
			branchAccounts.put(accountId, new Account(accountId));
		return branchAccounts.get(accountId);
	}

	public Double getBalance(AccountId accountId) {
		if (branchAccounts.containsKey(accountId))
			return branchAccounts.get(accountId).getAccountBalance();
		return 0.0;
	}

	public boolean deposit(AccountId accountId, double amount,
			Integer serialNumber) {
		Account accnt = getAccountCreateIfNotExist(accountId);

		// Do serial number check
		if (accnt.isUsedSerialNumber(serialNumber))
			return false;

		// Set account amount
		accnt.setAccountBalance(accnt.getAccountBalance() + amount);

		accnt.insertUsedSerialNumber(serialNumber);
		return true;
	}

	public boolean withdraw(AccountId accountId, double amount,
			Integer serialNumber) {
		Account accnt = getAccountCreateIfNotExist(accountId);

		// Do serial number check
		if (accnt.isUsedSerialNumber(serialNumber))
			return false;

		// Set account amount
		accnt.setAccountBalance(accnt.getAccountBalance() - amount);

		accnt.insertUsedSerialNumber(serialNumber);
		return true;
	}

	public boolean query(AccountId accountId, Integer serialNumber) {
		Account accnt = getAccountCreateIfNotExist(accountId);

		// Do serial number check
		if (accnt.isUsedSerialNumber(serialNumber))
			return false;

		accnt.insertUsedSerialNumber(serialNumber);
		return true;
	}

	public boolean transfer(AccountId srcAccountId, AccountId destAccountId,
			double amount, Integer serialNumber) {
		Account srcAccnt = getAccountCreateIfNotExist(srcAccountId);

		// Do serial number check
		if (srcAccnt.isUsedSerialNumber(serialNumber))
			return false;

		// Call deposit on local branch
		if (destAccountId.getBranchId().equals(srcAccountId.getBranchId())) {
			boolean success = this.deposit(destAccountId, amount, serialNumber);
			if (success)
				return withdraw(srcAccountId, amount, serialNumber);
		}
		// Call deposit on other branch
		else {
			BranchResponse resp = BranchClient.deposit(destAccountId, amount,
					serialNumber);
			// Deposit returned null (meaning a network error) but we cannot get
			// messages from dest branch so its OK
			if (resp == null
					&& !NodeRuntime.getNetworkInterface().canReceiveFrom(
							destAccountId.getBranchId()))
				return withdraw(srcAccountId, amount, serialNumber);
			// Response was succesful
			if (resp != null && resp.wasSuccessfull())
				return withdraw(srcAccountId, amount, serialNumber);
		}

		return false;
	}
}
