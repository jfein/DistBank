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
import core.network.messages.Message;

import bank.branch.Account;
import bank.branch.AccountId;
import bank.branch.BranchClient;
import bank.branch.BranchState;
import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchController extends AppState implements Runnable {

	private static final long serialVersionUID = -3098432013575721538L;

	public static BranchController controller = null;

	private AppId myAppId;
	private BranchView branchView;

	public BranchController(AppId myAppId) {
		this.myAppId = myAppId;
		this.branchView = new BranchView();

		branchView.addDepositListener(new DepositListener());
		branchView.addWithdrawListener(new WithdrawListener());
		branchView.addQueryListener(new QueryListener());
		branchView.addTransferListener(new TransferListener());
		branchView.addTakeSnapShotListener(new SnapShotListener());

		BranchController.controller = this;
	}

	@Override
	public void run() {
		branchView.setVisible(true);
	}

	public BranchView getBranchView() {
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

	class SnapShotListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			branchView.resetScrollPanel();

		}

	}

	public void displaySnapshot(BranchState branchState, List<Message> messages) {

		JPanel leftPanel = branchView.getClearSnapShotPanel();

		// Create two scroll panes that will store the list of accounts
		// transactions in progress.
		JScrollPane scrollPanel = new JScrollPane(new JLabel("Accounts"));
		JScrollPane transactionScrollPane = new JScrollPane(new JLabel("Transactions In Progress"));
		scrollPanel.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH, GuiSpecs.GUI_FRAME_HEIGHT / 2 - 50));
		transactionScrollPane.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT / 2 - 50));

		// Get list of accounts on this branch and add to pane
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("Accounts On This Branch");
		for (Account account : branchState.getAccounts().values()) {
			// If account balance is non-zero
			if (!(account.getAccountBalance().equals(0.0)))
				listModel.addElement(account.getAccountId() + " : " + account.getAccountBalance());
		}
		JList accounts = new JList(listModel);
		scrollPanel.getViewport().add(accounts);

		// Get list of transactions and add to pane
		DefaultListModel transactionsListModel = new DefaultListModel();
		transactionsListModel.addElement("Transactions In Progress");
		for (Message msg : messages) {
			String transaction = "";

			if (msg instanceof DepositRequest) {
				DepositRequest dReq = (DepositRequest) msg;
				transaction += " Deposit " + dReq.getAmount() + " from " + dReq.getSenderNodeId() + "."
						+ dReq.getSrcAccountId().getAccountNumber();
			} else if (msg instanceof WithdrawRequest) {
				WithdrawRequest wReq = (WithdrawRequest) msg;
				transaction += " Withdraw " + wReq.getAmount() + " from " + wReq.getSenderNodeId() + "."
						+ wReq.getSrcAccountId().getAccountNumber();
			} else if (msg instanceof TransferRequest) {
				TransferRequest tReq = (TransferRequest) msg;
				transaction += " Transfer " + tReq.getAmount() + " from " + tReq.getSenderNodeId() + "."
						+ tReq.getSrcAccountId().getAccountNumber() + " to " + tReq.getDestAccountId().getBranchAppId()
						+ "." + tReq.getDestAccountId().getAccountNumber();
			} else if (msg instanceof QueryRequest) {
				QueryRequest qReq = (QueryRequest) msg;
				transaction += " Query account " + qReq.getSenderNodeId() + "."
						+ qReq.getSrcAccountId().getAccountNumber();
			} else if (msg instanceof BranchResponse) {
				BranchResponse resp = (BranchResponse) msg;
				transaction += "Branch response from " + resp.getSenderNodeId() + " was " + resp.wasSuccessfull();
			}

			transactionsListModel.addElement(transaction);
		}
		JList transactions = new JList(transactionsListModel);
		transactionScrollPane.getViewport().add(transactions);

		// Create a clear button
		JButton clear = new JButton("Clear");
		clear.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH, 30));
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
			branchView.popUpErrorMessage("Your request was unsuccessfull. Please try again. Check your serial number.");
			branchView.setBalanceLabel("Your Account [" + branchView.getUISrcAccount() + "] Balance: "
					+ String.valueOf(response.getAmt()));
			return false;
		} else {
			return true;
		}
	}

}
