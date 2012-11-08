package bank.branch;

import java.util.HashMap;
import java.util.Map;

import core.app.AppId;
import core.app.AppState;

/**
 * BranchState holds all the main methods for changing the state of an account
 * which changes the state of the branch, hence the name BranchState. It
 * contains methods like deposit, withdraw, query, and transfer. Each method
 * will get the account or create a new account with the balance of 0.0 if it
 * does not exist. It will then proceed by checking if the serial number that
 * came with this request has been used in previous requests with this account.
 * If it has not, then we will do the appropriate change (withdraw, deposit,
 * transfer, check the balance), and add the serial number to the list of used
 * serial numbers, then return.
 */
public class BranchState extends AppState {

	private static final long serialVersionUID = -1275375970531871241L;

	private HashMap<AccountId, Account> branchAccounts;
	private AppId<BranchApp> myBranchAppId;

	public BranchState(AppId<BranchApp> myBranchAppId) {
		this.branchAccounts = new HashMap<AccountId, Account>();
		this.myBranchAppId = myBranchAppId;
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

	public boolean deposit(AccountId accountId, double amount, Integer serialNumber) {
		Account accnt = getAccountCreateIfNotExist(accountId);

		// Do serial number check
		if (accnt.isUsedSerialNumber(serialNumber))
			return false;

		// Set account amount
		accnt.setAccountBalance(accnt.getAccountBalance() + amount);

		accnt.insertUsedSerialNumber(serialNumber);
		return true;
	}

	public boolean withdraw(AccountId accountId, double amount, Integer serialNumber) {
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

	public boolean transfer(AccountId srcAccountId, AccountId destAccountId, double amount, Integer serialNumber) {
		Account srcAccnt = getAccountCreateIfNotExist(srcAccountId);

		// Do serial number check
		if (srcAccnt.isUsedSerialNumber(serialNumber))
			return false;

		// Withdraw (marks this serial number as used)
		withdraw(srcAccountId, amount, serialNumber);

		// Call deposit on local branch app
		if (destAccountId.getBranchAppId().equals(srcAccountId.getBranchAppId()))
			this.deposit(destAccountId, amount, serialNumber);
		// Call deposit on remote branch app
		else
			BranchClient.deposit(myBranchAppId, destAccountId, amount, serialNumber);

		return true;
	}

	public HashMap<AccountId, Account> getAccounts() {
		return this.branchAccounts;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Branch ID: " + myBranchAppId + "\n");

		for (Map.Entry<AccountId, Account> entry : branchAccounts.entrySet()) {
			AccountId key = entry.getKey();
			Account value = entry.getValue();
			sb.append("  Account " + key + ": $" + value.getAccountBalance() + "\n");
		}

		return sb.toString();
	}

}
