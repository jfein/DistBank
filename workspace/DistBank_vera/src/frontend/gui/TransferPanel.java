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

public class TransferPanel extends JPanel {
	
	private JTextField amountField;
	private JTextField destAcntNumberField;
	private JTextField srcAcntNumberField;
	private JButton btnTransfer;
	
	public TransferPanel() {
		SpringLayout thisLayout = new SpringLayout();
		setLayout(thisLayout);
	    destAcntNumberField = new JTextField("Dest Account");

		add(destAcntNumberField);
		destAcntNumberField.setColumns(10);
		
		srcAcntNumberField = new JTextField("Src Account");
		srcAcntNumberField.setColumns(10);
		thisLayout.putConstraint(SpringLayout.SOUTH, srcAcntNumberField, -20, SpringLayout.NORTH, destAcntNumberField);
		thisLayout.putConstraint(SpringLayout.EAST, srcAcntNumberField, 0, SpringLayout.EAST, destAcntNumberField);
		add(srcAcntNumberField);
		
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
		
		JLabel lblSrcAccountNumber = new JLabel("Source Account Number:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblSrcAccountNumber, 6, SpringLayout.NORTH, srcAcntNumberField);
		thisLayout.putConstraint(SpringLayout.SOUTH, lblSrcAccountNumber, -70, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblSrcAccountNumber, -160, SpringLayout.EAST, amountField);
		add(lblSrcAccountNumber);
		
		JLabel lblAmount = new JLabel("Amount:");
		thisLayout.putConstraint(SpringLayout.NORTH, lblAmount, 6, SpringLayout.NORTH, amountField);
		thisLayout.putConstraint(SpringLayout.EAST, lblAmount, -27, SpringLayout.WEST, amountField);
		add(lblAmount);
		
		btnTransfer = new JButton("Transfer");
		thisLayout.putConstraint(SpringLayout.SOUTH, btnTransfer, -200, SpringLayout.SOUTH, this);
		thisLayout.putConstraint(SpringLayout.EAST, btnTransfer, -146, SpringLayout.EAST, this);
		add(btnTransfer);
	}
}
