package frontend.gui.main;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

public class WithdrawPanel extends JPanel {
	private JTextField amountField;
	public WithdrawPanel() {
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
		JTextField accountNumberField = new JTextField("Withdraw");
		
		
		add(accountNumberField);
		accountNumberField.setColumns(10);
		
		amountField = new JTextField();
		thisLayout.putConstraint(SpringLayout.SOUTH, accountNumberField, -17, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, accountNumberField, 0, SpringLayout.EAST, amountField);
		thisLayout.putConstraint(SpringLayout.SOUTH, amountField, -250, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, amountField, -134, SpringLayout.EAST, this);
		add(amountField);
		amountField.setColumns(10);
		
		JLabel lblAccountNumber = new JLabel("Account Number:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAccountNumber, 6, SpringLayout.NORTH, accountNumberField);
		thisLayout.putConstraint(SpringLayout.SOUTH, lblAccountNumber, -23, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAccountNumber, -160, SpringLayout.EAST, amountField);
		add(lblAccountNumber);
		
		JLabel lblAmount = new JLabel("Amount:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAmount, 6, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAmount, -27, SpringLayout.WEST, amountField);
		add(lblAmount);
		
		JButton btnWithdraw = new JButton("Withdraw");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnWithdraw, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnWithdraw, -146, SpringLayout.EAST, this);
		add(btnWithdraw);
	}
}
