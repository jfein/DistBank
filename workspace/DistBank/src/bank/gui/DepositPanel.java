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

public class DepositPanel extends JPanel{
	private JTextField amountField;
	private JLabel balanceLabel;
	private String accountNumber;
	private Integer userSerialNumber;
	
	public DepositPanel(String accountNumber, Integer userSerialNumber){
		
		this.userSerialNumber = userSerialNumber;
		this.accountNumber = accountNumber;
		
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
		
		amountField = new JTextField();
		thisLayout.putConstraint(SpringLayout.SOUTH, amountField, -250, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, amountField, -134, SpringLayout.EAST, this);
		add(amountField);
		amountField.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAmount, 6, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAmount, -27, SpringLayout.WEST, amountField);
		add(lblAmount);
		
		JButton btnDeposit = new JButton("Deposit");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnDeposit, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnDeposit, -146, SpringLayout.EAST, this);
		add(btnDeposit);
		
		balanceLabel = new JLabel("");
		thisLayout.putConstraint(SpringLayout.NORTH, balanceLabel, 29, SpringLayout.SOUTH, btnDeposit);
		thisLayout.putConstraint(SpringLayout.EAST, balanceLabel, -179, SpringLayout.EAST, this);
		add(balanceLabel);
	
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doDeposit();
					}
				});
			}
		});
	}
	
	public void doDeposit() {
		AccountId accountId = new AccountId(this.accountNumber);
		if (this.amountField.getText().equals("")) {
			  JOptionPane.showMessageDialog(new JFrame(), "Please enter an amount.", "Error",
				        JOptionPane.ERROR_MESSAGE);
		} else {
		double balance  = BankClient.deposit(accountId, Double.parseDouble(this.amountField.getText()), this.userSerialNumber);
		this.balanceLabel.setText("Your Account [" + this.accountNumber + "] Balance: " + String.valueOf(balance));
		}
	}
}
