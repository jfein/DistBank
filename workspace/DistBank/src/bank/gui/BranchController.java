package bank.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
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

import core.network.messages.Message;
import core.node.NodeRuntime;
import core.node.NodeState;

import bank.Account;
import bank.AccountId;
import bank.BranchClient;
import bank.BranchState;
import bank.messages.BranchResponse;

public class BranchController extends NodeState implements Runnable {

	private static final long serialVersionUID = -3098432013575721538L;

	private BranchView branchView;

	@Override
	public void run() {
		this.branchView = new BranchView();

		branchView.addDepositListener(new DepositListener());
		branchView.addWithdrawListener(new WithdrawListener());
		branchView.addQueryListener(new QueryListener());
		branchView.addTransferListener(new TransferListener());
		branchView.addTakeSnapShotListener(new SnapShotListener());

		branchView.setVisible(true);
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
			branchView
					.popUpErrorMessage("Please fill out the required fields.");
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
				branchView
						.popUpErrorMessage("Please enter a positive value for the amount.");
				return false;
			}
		}
		return true;
	}

	class DepositListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			if (isValidAccountNumber(branchView.getUISrcAccount())
					&& isValidSerialNumber()
					&& isValidAmount(branchView.getUIAmount())) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(
						branchView.getUISrcAccount());
				BranchResponse response = BranchClient.deposit(accountId,
						Double.parseDouble(branchView.getUIAmount()),
						Integer.parseInt(branchView.getUISerial()));
				if (checkResponse(response)) {
					branchView.setBalanceLabel("Your Account ["
							+ branchView.getUISrcAccount() + "] Balance: "
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
			if (isValidAccountNumber(branchView.getUISrcAccount())
					&& isValidSerialNumber()
					&& isValidAmount(branchView.getUIAmount())) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(
						branchView.getUISrcAccount());
				BranchResponse response = BranchClient.withdraw(accountId,
						Double.parseDouble(branchView.getUIAmount()),
						Integer.parseInt(branchView.getUISerial()));
				if (checkResponse(response)) {
					branchView.setBalanceLabel("Your Account ["
							+ branchView.getUISrcAccount() + "] Balance: "
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
					&& isValidAccountNumber(branchView.getUIDestAccount())
					&& isValidSerialNumber()
					&& isValidAmount(branchView.getUIAmount())) {
				// TODO Auto-generated method stub
				branchView.enableAllButtons(false);
				AccountId srcAccountId = new AccountId(
						branchView.getUISrcAccount());
				AccountId destAccountId = new AccountId(
						branchView.getUIDestAccount());
				if (branchView.getUIAmount().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter an amount.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					BranchResponse response = BranchClient.transfer(
							srcAccountId, destAccountId,
							Double.parseDouble(branchView.getUIAmount()),
							Integer.parseInt(branchView.getUISerial()));
					if (checkResponse(response)) {
						branchView.setBalanceLabel("Your Account ["
								+ branchView.getUISrcAccount() + "] Balance: "
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

			if (isValidAccountNumber(branchView.getUISrcAccount())
					&& isValidSerialNumber()) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(
						branchView.getUISrcAccount());
				BranchResponse response = BranchClient.query(accountId,
						Integer.parseInt(branchView.getUISerial()));
				if (checkResponse(response)) {
					branchView.setBalanceLabel("Your Account ["
							+ branchView.getUISrcAccount() + "] Balance: "
							+ String.valueOf(response.getAmt()));
				}
				branchView.clearAllTextFields();
				branchView.enableAllButtons(true);
			}
		}
	}

	class SnapShotListener implements ActionListener {
		// TODO CHANGE THIS

		// AccountId accountId = new
		// AccountId(this.srcAccountNumberField.getText());
		// BranchResponse response =
		// BranchClient.takeSnapshot(accountId,Integer.parseInt(this.serialNumberField.getText()));
		// clearAllTextFields();
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: make this better
			NodeRuntime.getSnapshotHandler().broadcastSnapshotMessage();

			
		}

	}
	
	public void displaySnapshot(BranchState branchState, List<Message> messages) {
		JPanel leftPanel = branchView.getClearSnapShotPanel();
		JLabel title = new JLabel("Accounts");
		JLabel secondTitle = new JLabel("Transactions In Progress");
		JScrollPane scrollPanel = new JScrollPane(title);
		JScrollPane transactionScrollPane = new JScrollPane(secondTitle);
		scrollPanel.setPreferredSize(new Dimension(
				GuiSpecs.GUI_SNAPSHOT_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT/2 - 50));
		transactionScrollPane.setPreferredSize(new Dimension(
				GuiSpecs.GUI_SNAPSHOT_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT/2 - 50));

		JButton clear = new JButton("Clear");
		clear.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH,
				30));
	
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("Accounts On This Branch");
		for (Map.Entry<AccountId, Account> entry : branchState.getAccounts().entrySet()) {
			AccountId key = entry.getKey();
			Account value = entry.getValue();
			listModel.addElement(key + " : " + value.getAccountBalance());
		}
		
		JList accounts = new JList(listModel);
		scrollPanel.getViewport().add(accounts);
		
		DefaultListModel transactionsListModel = new DefaultListModel();
		transactionsListModel.addElement("Transactions In Progress");
		for (Message msg : messages) {
			listModel.addElement("Source: " + msg.getSenderId() + " did " + msg.getClass());
		}
		JList transactions = new JList(transactionsListModel);
		transactionScrollPane.getViewport().add(transactions);
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						branchView.resetScrollPanel();
					}

				});
			}
		});

		leftPanel.add(clear, BorderLayout.SOUTH);
		leftPanel.add(transactionScrollPane, BorderLayout.NORTH);
		leftPanel.add(scrollPanel, BorderLayout.CENTER);
		branchView.clearAllTextFields();
		leftPanel.revalidate();
	}

	public boolean checkResponse(BranchResponse response) {
		if (response == null) {
			branchView.popUpErrorMessage("Network Failure.");
			return false;
		} else if (!response.wasSuccessfull()) {
			branchView
					.popUpErrorMessage("Your request was unsuccessfull. Please try again. Check your serial number.");
			branchView.setBalanceLabel("Your Account ["
					+ branchView.getUISrcAccount() + "] Balance: "
					+ String.valueOf(response.getAmt()));
			return false;
		} else {
			return true;
		}
	}

}
