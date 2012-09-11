package frontend.gui.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import bank.AccountId;
import bank.BankClient;

public class WithdrawPanel extends JPanel {
	private JTextField amountField;
	private String accountNumber;
	private Integer userSerialNumber;
	private JLabel balanceLabel;
	public WithdrawPanel(String accountNumber, Integer userSerialNumber) {
		this.accountNumber = accountNumber;
		this.userSerialNumber = userSerialNumber;
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
		
		JButton btnWithdraw = new JButton("Withdraw");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnWithdraw, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnWithdraw, -146, SpringLayout.EAST, this);
		add(btnWithdraw);
		
		balanceLabel = new JLabel("");
		thisLayout.putConstraint(SpringLayout.NORTH, balanceLabel, 6, SpringLayout.SOUTH, btnWithdraw);
		thisLayout.putConstraint(SpringLayout.EAST, balanceLabel, -166, SpringLayout.EAST, this);
		add(balanceLabel);
	
		btnWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doWithdraw();
					}
				});
			}
		});
		
	}
	
	public void doWithdraw() {
		InetSocketAddress dest = new InetSocketAddress("localhost", 4000);
		AccountId accountId = Utils.stringToAccountId(this.accountNumber);
		double balance  = BankClient.withdraw(dest, accountId, Double.parseDouble(this.amountField.getText()), this.userSerialNumber);
		this.balanceLabel.setText("Your Account [" + this.accountNumber + "] Balance: " + String.valueOf(balance));
	}
}
