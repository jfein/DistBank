package frontend.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class DepositPanel extends JPanel{
	JTextField amountField;
	JTextField accountNumberField;
	JButton btnDeposit;
	public DepositPanel(){
		
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
		
		//Account Number field
		accountNumberField = new JTextField();
		add(accountNumberField);
		accountNumberField.setColumns(15);
		
		//Amount to deposit field
		amountField = new JTextField();
		thisLayout.putConstraint(SpringLayout.SOUTH, accountNumberField, -17, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, accountNumberField, 0, SpringLayout.EAST, amountField);
		thisLayout.putConstraint(SpringLayout.SOUTH, amountField, -250, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, amountField, -134, SpringLayout.EAST, this);
		add(amountField);
		amountField.setColumns(10);
		
		//Label for account number
		JLabel lblAccountNumber = new JLabel("Account Number:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAccountNumber, 6, SpringLayout.NORTH, accountNumberField);
		thisLayout.putConstraint(SpringLayout.SOUTH, lblAccountNumber, -23, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAccountNumber, -160, SpringLayout.EAST, amountField);
		add(lblAccountNumber);
		
		//Label for amount
		JLabel lblAmount = new JLabel("Amount:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAmount, 6, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAmount, -27, SpringLayout.WEST, amountField);
		add(lblAmount);
		
		btnDeposit = new JButton("Deposit");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnDeposit, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnDeposit, -146, SpringLayout.EAST, this);
		add(btnDeposit);
	}
}
