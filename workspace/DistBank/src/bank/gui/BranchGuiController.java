package bank.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import core.app.AppId;
import core.app.AppState;

import bank.branch.AccountId;
import bank.branch.BranchClient;
import bank.messages.BranchResponse;

public class BranchGuiController extends AppState {

	private static final long serialVersionUID = -3098432013575721538L;

	private AppId<BranchGuiApp> myAppId;
	private BranchGuiView branchView;

	public BranchGuiController(AppId<BranchGuiApp> myAppId) {
		this.myAppId = myAppId;
		this.branchView = new BranchGuiView(myAppId);

		branchView.addDepositListener(new DepositListener());
		branchView.addWithdrawListener(new WithdrawListener());
		branchView.addQueryListener(new QueryListener());
		branchView.addTransferListener(new TransferListener());

		branchView.setVisible(true);
	}

	public BranchGuiView getBranchView() {
		return this.branchView;
	}

	public boolean isStringNumeric(String s) {
		Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
		if (!doublePattern.matcher(s).matches()) {
			branchView.popUpErrorMessage("Please enter a numeric value.");
			return false;
		}
		return true;
	}

	public boolean isValidSerialNumber() {
		if (branchView.getUISerial().equals("")) {
			branchView.popUpErrorMessage("Please fill out serial number.");
			return false;
		}
		return isStringNumeric(branchView.getUISerial());
	}

	public boolean isValidAccountNumber(String accountNumber) {
		if (accountNumber.equals("")) {
			branchView.popUpErrorMessage("Please fill out the required fields.");
		} else if (isStringNumeric(accountNumber)) {
			if (accountNumber.length() == 8) {
				String[] tokens = accountNumber.split("\\.");
				if (tokens[0].length() == 2 && tokens[1].length() == 5) {
					return true;
				}

			}
			branchView
					.popUpErrorMessage("Please ensure that account format is: bb.aaaa in numeric format. Ex:00.11111");
		}
		return false;
	}

	public boolean isValidAmount(String amount) {
		if (amount.equals("")) {
			branchView.popUpErrorMessage("Please enter an amount.");
			return false;
		} else if (!isStringNumeric(amount)) {
			return false;
		} else {
			if (Double.parseDouble(amount) < 0.0) {
				branchView.popUpErrorMessage("Please enter a positive value for the amount.");
				return false;
			}
		}
		return true;
	}

	class DepositListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			if (isValidAccountNumber(branchView.getUISrcAccount()) && isValidSerialNumber()
					&& isValidAmount(branchView.getUIAmount())) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(branchView.getUISrcAccount());
				BranchResponse response = BranchClient.deposit(myAppId, accountId,
						Double.parseDouble(branchView.getUIAmount()), Integer.parseInt(branchView.getUISerial()));
				if (checkResponse(response)) {
					branchView.setBalanceLabel("Your Account [" + branchView.getUISrcAccount() + "] Balance: "
							+ String.valueOf(response.getAmt()));
				}
				branchView.clearAllTextFields();
				branchView.enableAllButtons(true);
			}
		}
	}

	class WithdrawListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isValidAccountNumber(branchView.getUISrcAccount()) && isValidSerialNumber()
					&& isValidAmount(branchView.getUIAmount())) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(branchView.getUISrcAccount());
				BranchResponse response = BranchClient.withdraw(myAppId, accountId,
						Double.parseDouble(branchView.getUIAmount()), Integer.parseInt(branchView.getUISerial()));
				if (checkResponse(response)) {
					branchView.setBalanceLabel("Your Account [" + branchView.getUISrcAccount() + "] Balance: "
							+ String.valueOf(response.getAmt()));
				}
				branchView.clearAllTextFields();
				branchView.enableAllButtons(true);
			}
		}
	}

	class TransferListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (isValidAccountNumber(branchView.getUISrcAccount())
					&& isValidAccountNumber(branchView.getUIDestAccount()) && isValidSerialNumber()
					&& isValidAmount(branchView.getUIAmount())) {
				// TODO Auto-generated method stub
				branchView.enableAllButtons(false);
				AccountId srcAccountId = new AccountId(branchView.getUISrcAccount());
				AccountId destAccountId = new AccountId(branchView.getUIDestAccount());
				if (branchView.getUIAmount().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter an amount.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					BranchResponse response = BranchClient.transfer(myAppId, srcAccountId, destAccountId,
							Double.parseDouble(branchView.getUIAmount()), Integer.parseInt(branchView.getUISerial()));
					if (checkResponse(response)) {
						branchView.setBalanceLabel("Your Account [" + branchView.getUISrcAccount() + "] Balance: "
								+ String.valueOf(response.getAmt()));
					}
				}
				branchView.clearAllTextFields();
				branchView.enableAllButtons(true);
			}
		}
	}

	class QueryListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (isValidAccountNumber(branchView.getUISrcAccount()) && isValidSerialNumber()) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(branchView.getUISrcAccount());
				BranchResponse response = BranchClient.query(myAppId, accountId,
						Integer.parseInt(branchView.getUISerial()));
				if (checkResponse(response)) {
					branchView.setBalanceLabel("Your Account [" + branchView.getUISrcAccount() + "] Balance: "
							+ String.valueOf(response.getAmt()));
				}
				branchView.clearAllTextFields();
				branchView.enableAllButtons(true);
			}
		}
	}

	public boolean checkResponse(BranchResponse response) {
		if (response == null) {
			branchView.popUpErrorMessage("Unknown failure of some sorts...");
			return false;
		}
		return true;
	}

}
