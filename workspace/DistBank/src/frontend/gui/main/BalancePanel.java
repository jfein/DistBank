package frontend.gui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class BalancePanel extends JPanel{
	private JTextField accountNumberField;
	public BalancePanel() {
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
		
		JButton btnWithdraw = new JButton("Withdraw");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnWithdraw, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnWithdraw, -146, SpringLayout.EAST, this);
		add(btnWithdraw);
		JTextField accountNumberField = new JTextField("Account Number");
		
		thisLayout.putConstraint(SpringLayout.SOUTH, accountNumberField, -23, SpringLayout.NORTH, btnWithdraw);
		thisLayout.putConstraint(SpringLayout.EAST, accountNumberField, 10, SpringLayout.EAST, btnWithdraw);
		add(accountNumberField);
		accountNumberField.setColumns(10);
		
		
		
		JLabel lblAccountNumber = new JLabel("Account Number:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAccountNumber, 6, SpringLayout.NORTH, accountNumberField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAccountNumber, -150 , SpringLayout.EAST, btnWithdraw);
		add(lblAccountNumber);
	
		
	}
}
