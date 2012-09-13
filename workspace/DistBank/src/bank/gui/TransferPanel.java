package bank.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import bank.AccountId;
import bank.BankClient;

public class TransferPanel extends JPanel {
	private JTextField amountField;
	private JTextField destAcntNumberField;
	private String srcAccountNumber;
	private Integer userSerialNumber;
	private JLabel balanceLabel;
	public TransferPanel(String srcAccountNumber, Integer userSerialNumber) {
		this.userSerialNumber = userSerialNumber;
		this.srcAccountNumber = srcAccountNumber;
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
	    destAcntNumberField = new JTextField();

		add(destAcntNumberField);
		destAcntNumberField.setColumns(10);

		
		amountField = new JTextField();
		thisLayout.putConstraint(SpringLayout.SOUTH, destAcntNumberField, -17, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, destAcntNumberField, 0, SpringLayout.EAST, amountField);
		thisLayout.putConstraint(SpringLayout.SOUTH, amountField, -250, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, amountField, -134, SpringLayout.EAST, this);
		add(amountField);
		amountField.setColumns(10);
		
		JLabel lblAccountNumber = new JLabel("Destination Account Number:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAccountNumber, 6, SpringLayout.NORTH, destAcntNumberField);
		thisLayout.putConstraint(SpringLayout.SOUTH, lblAccountNumber, -23, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAccountNumber, -160, SpringLayout.EAST, amountField);
		add(lblAccountNumber);

		JLabel lblAmount = new JLabel("Amount:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAmount, 6, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAmount, -27, SpringLayout.WEST, amountField);
		add(lblAmount);
		
		JButton btnTransfer = new JButton("Transfer");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnTransfer, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnTransfer, -146, SpringLayout.EAST, this);
		add(btnTransfer);
		
		balanceLabel = new JLabel("");
		thisLayout.putConstraint(SpringLayout.NORTH, balanceLabel, 6, SpringLayout.SOUTH, btnTransfer);
		thisLayout.putConstraint(SpringLayout.EAST, balanceLabel, -10, SpringLayout.EAST, btnTransfer);
		add(balanceLabel);
		

		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doTransfer();
					}
				});
			}
		});
	}
	
	public void doTransfer() {
		AccountId srcAccountId = new AccountId(this.srcAccountNumber);
		if (this.destAcntNumberField.getText().equals("")){
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a destination account.", "Error",
			        JOptionPane.ERROR_MESSAGE);
		} else {
			AccountId destAccountId = new AccountId(this.destAcntNumberField.getText());
			System.out.println("Is empty: " + this.amountField.getText().equals(""));
			if (this.amountField.getText().equals("")) {
				  JOptionPane.showMessageDialog(new JFrame(), "Please enter an amount.", "Error",
					        JOptionPane.ERROR_MESSAGE);
			} else {
				  // TODO: check for null! and check if successfull
				  double balance = BankClient.transfer(srcAccountId, destAccountId, Double.parseDouble(this.amountField.getText()), this.userSerialNumber).getAmt();
				  this.balanceLabel.setText("Your Account [" + this.srcAccountNumber + "] Balance: " + String.valueOf(balance));
			}
		}
	}
	
}
