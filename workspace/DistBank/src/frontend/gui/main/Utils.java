package frontend.gui.main;

import bank.AccountId;

public class Utils {

	public static AccountId stringToAccountId(String accountId){
		String[] tokens = accountId.split("\\.");
		return new AccountId(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[0]));
	}
	
	public static void main(String[] args){
		String accnt = "11.1111";
		AccountId accntId = stringToAccountId(accnt);
		System.out.println("Branch: " + accntId.getBranchNumber() + "\n Account: " + accntId.getAccountNumber());
	}
}
