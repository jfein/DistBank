package bank.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import core.app.AppId;
import core.app.AppState;
import core.messages.Message;

import bank.branch.Account;
import bank.branch.AccountId;
import bank.branch.BranchClient;
import bank.branch.BranchState;
import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchGuiController extends AppState {

	private static final long serialVersionUID = -3098432013575721538L;

	private AppId myAppId;
	private BranchGuiView branchView;

	public BranchGuiController(AppId myAppId) {
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
			branchView.popUpErrorMessage("Network Failure.");
			return false;
		} else if (!response.wasSuccessfull()) {
			branchView.popUpErrorMessage("Your request was unsuccessfull. Please try again. Check your serial number.");
			branchView.setBalanceLabel("Your Account [" + branchView.getUISrcAccount() + "] Balance: "
					+ String.valueOf(response.getAmt()));
			return false;
		} else {
			return true;
		}
	}

}
