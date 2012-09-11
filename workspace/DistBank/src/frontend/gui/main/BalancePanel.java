package frontend.gui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import bank.AccountId;
import bank.BankClient;

public class BalancePanel extends JPanel{
	private JTextField balanceField;
	private String accountNumber;
	private Integer userSerialNumber;
	
	public BalancePanel(String accountNumber, Integer userSerialNumber) {
		this.accountNumber = accountNumber;
		this.userSerialNumber = userSerialNumber;
		
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
		
		JButton btnCheckBalance = new JButton("Check Balance");
		thisLayout.putConstraint(SpringLayout.EAST, btnCheckBalance, -175, SpringLayout.EAST, this);
		add(btnCheckBalance);
		
		
		
	    balanceField = new JTextField("");
		thisLayout.putConstraint(SpringLayout.NORTH, balanceField, 200, SpringLayout.NORTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, balanceField, -175, SpringLayout.EAST, this);
		thisLayout.putConstraint(SpringLayout.NORTH, btnCheckBalance, 11, SpringLayout.SOUTH, balanceField);
		add(balanceField);
		balanceField.setColumns(10);
		
		
		
		JLabel lblAccountNumber = new JLabel("Balance For Account " + this.accountNumber);
		thisLayout.putConstraint(SpringLayout.SOUTH, lblAccountNumber, -10, SpringLayout.NORTH, balanceField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAccountNumber, -175, SpringLayout.EAST, this);
		add(lblAccountNumber);
		
		btnCheckBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doCheckBalance();
					}
				});
			}
		});
	
		
	}
	
	public void doCheckBalance(){
		//TODO replace the inetsocket
		InetSocketAddress dest = new InetSocketAddress("localhost", 4000);
		AccountId accountId = Utils.stringToAccountId(this.accountNumber);
		double balance  = BankClient.query(dest, accountId, this.userSerialNumber);
		this.balanceField.setText(String.valueOf(balance));
	}
}
