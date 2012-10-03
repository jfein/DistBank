package bank.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import bank.AccountId;
import bank.BranchClient;
import bank.messages.BranchResponse;

public class BranchController {
	
	private BranchView branchView;
	
	public BranchController(BranchView view) {
		this.branchView = view;
		
		branchView.addDepositListener(new DepositListener());
		branchView.addWithdrawListener(new WithdrawListener());
		branchView.addQueryListener(new QueryListener());
		branchView.addTransferListener(new TransferListener());
		branchView.addTakeSnapShotListener(new SnapShotListener());
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
			branchView.popUpErrorMessage("Please ensure that account format is: bb.aaaa in numeric format. Ex:00.11111");
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
			
			if(isValidAccountNumber(branchView.getUISrcAccount()) && 
					isValidSerialNumber() && isValidAmount(branchView.getUIAmount())){
			    branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(branchView.getUISrcAccount());
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
			if (isValidAccountNumber(branchView.getUISrcAccount()) && 
					isValidSerialNumber() && isValidAmount(branchView.getUIAmount())) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(branchView.getUISrcAccount());
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
    
    class TransferListener implements ActionListener  {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(isValidAccountNumber(branchView.getUISrcAccount()) &&
					isValidAccountNumber(branchView.getUIDestAccount()) &&
					isValidSerialNumber() && isValidAmount(branchView.getUIAmount())) {
				// TODO Auto-generated method stub
				branchView.enableAllButtons(false);
				AccountId srcAccountId = new AccountId(branchView.getUISrcAccount());
				AccountId destAccountId = new AccountId(branchView.getUIDestAccount());
				if (branchView.getUIAmount().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter an amount.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					BranchResponse response = BranchClient.transfer(srcAccountId,
							destAccountId,
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
			
			if(isValidAccountNumber(branchView.getUISrcAccount())
								&& isValidSerialNumber()) {
				branchView.enableAllButtons(false);
				AccountId accountId = new AccountId(branchView.getUISrcAccount());
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
			if(isValidAccountNumber(branchView.getUISrcAccount())
								&& isValidSerialNumber()) {
				JPanel leftPanel = branchView.getClearSnapShotPanel();
				JScrollPane scrollPanel = new JScrollPane();
				scrollPanel.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH,
						GuiSpecs.GUI_FRAME_HEIGHT - 100));
	
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
				leftPanel.add(scrollPanel, BorderLayout.CENTER);
				branchView.clearAllTextFields();
				leftPanel.revalidate();
			}
			
		}
	}
    
	
	public boolean checkResponse(BranchResponse response) {
		if (response == null) {
			branchView.popUpErrorMessage("Network Failure.");
			return false;
		} else if (!response.wasSuccessfull()) {
			branchView.popUpErrorMessage("Your request was unsuccessfull. Please try again. Check your serial number.");
			branchView.setBalanceLabel("Your Account ["
					+ branchView.getUISrcAccount() + "] Balance: "
					+ String.valueOf(response.getAmt()));
			return false;
		} else {
			return true;
		}
	}

}
