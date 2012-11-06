package bank.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import bank.BranchClient;
import bank.branch.AccountId;
import bank.messages.BranchResponse;

import core.node.NodeRuntime;

public class BranchView extends JFrame {
	private String branchMainIndex = "Main Bank Branch Panel";

	private final String greetingTextLabel = "<html><FONT COLOR = RED SIZE = 18> J&V Bank </FONT></html>";

	private JPanel mainButtonPanel;

	private static final long serialVersionUID = 1L;

	// All the text fields
	private JTextField serialNumberField;
	private JTextField srcAccountNumberField;
	private JTextField amountNumberField;
	private JTextField destAccountNumberField;

	// All the buttons
	private JButton withdrawButton;
	private JButton depositButton;
	private JButton transferButton;
	private JButton checkBalanceButton;
	private JButton takeSnapshotButton;

	private JLabel balanceLabel;

	private JPanel snapshotPanel;
	private JPanel mainPanel;
	private JSplitPane splitFramePane;

	private SpringLayout mainButtonPanelLayout;

	public BranchView() {

		this.setPreferredSize(new Dimension(GuiSpecs.GUI_FRAME_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT));
		this.setResizable(false);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		snapshotPanel = new JPanel();
		snapshotPanel.setBackground(Color.pink);
		snapshotPanel.setLayout(new BorderLayout());
		snapshotPanel.setMaximumSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT));

		splitFramePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				snapshotPanel, mainPanel);
		splitFramePane.setMaximumSize(new Dimension(
				GuiSpecs.GUI_BRANCH_PANEL_WIDTH, GuiSpecs.GUI_FRAME_HEIGHT));
		splitFramePane
				.setDividerLocation(170 + splitFramePane.getInsets().left);

		// North Panel
		JLabel northPanel = new JLabel("Welcome to J&V Bank ATM #"
				+ NodeRuntime.getId(), JLabel.CENTER);
		northPanel.setPreferredSize(new Dimension(500, 100));
		mainPanel.add(northPanel, BorderLayout.NORTH);

		// Snapshot Panel
		JLabel welcomeMessage = new JLabel(greetingTextLabel, JLabel.CENTER);
		snapshotPanel.add(welcomeMessage, BorderLayout.CENTER);

		// South Panel
		balanceLabel = new JLabel("Vera Kutsenko, Jeremy Fein", JLabel.CENTER);
		balanceLabel.setPreferredSize(new Dimension(
				GuiSpecs.GUI_BRANCH_PANEL_WIDTH, 100));
		mainPanel.add(balanceLabel, BorderLayout.SOUTH);

		// Center Panel
		mainButtonPanelLayout = new SpringLayout();
		mainButtonPanel = new JPanel();
		mainButtonPanel.setPreferredSize(new Dimension(
				GuiSpecs.GUI_BRANCH_PANEL_WIDTH, 280));
		mainButtonPanel.setLayout(mainButtonPanelLayout);
		mainPanel.add(mainButtonPanel, BorderLayout.CENTER);

		depositButton = createMenuButton("Deposit", 0);
		withdrawButton = createMenuButton("Withdraw",
				GuiSpecs.MENU_BUTTON_OFFSET);
		transferButton = createMenuButton("Transfer",
				2 * GuiSpecs.MENU_BUTTON_OFFSET);
		checkBalanceButton = createMenuButton("Check Balance",
				3 * GuiSpecs.MENU_BUTTON_OFFSET);
		takeSnapshotButton = createMenuButton("Take Snapshot",
				4 * GuiSpecs.MENU_BUTTON_OFFSET);

		mainButtonPanel.add(this.transferButton);
		mainButtonPanel.add(this.depositButton);
		mainButtonPanel.add(this.checkBalanceButton);
		mainButtonPanel.add(this.takeSnapshotButton);
		mainButtonPanel.add(this.withdrawButton);

		// Create Serial Number Field
		this.serialNumberField = createTextFieldInMainPanel(withdrawButton, 0);
		setLabelNextToField(new JLabel("Serial Number*:"),
				this.serialNumberField);

		// Create Source Account Number Field
		this.srcAccountNumberField = createTextFieldInMainPanel(withdrawButton,
				GuiSpecs.TEXT_FIELD_OFFSET);
		setLabelNextToField(new JLabel("Source Account Number*:"),
				this.srcAccountNumberField);

		// Create Destination Account Number Field
		this.destAccountNumberField = createTextFieldInMainPanel(
				withdrawButton, GuiSpecs.TEXT_FIELD_OFFSET * 2);
		setLabelNextToField(new JLabel("Dest. Account Number:"),
				this.destAccountNumberField);

		// Create an amount field
		this.amountNumberField = createTextFieldInMainPanel(withdrawButton,
				GuiSpecs.TEXT_FIELD_OFFSET * 3);
		setLabelNextToField(new JLabel("Amount:"), this.amountNumberField);

		JLabel buttonChoiceLabel = new JLabel("* indicated a REQUIRED field.");
		mainButtonPanelLayout.putConstraint(SpringLayout.NORTH,
				buttonChoiceLabel, 0, SpringLayout.SOUTH, amountNumberField);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST,
				buttonChoiceLabel, -96, SpringLayout.EAST, mainButtonPanel);
		buttonChoiceLabel.setPreferredSize(new Dimension(250, 30));

		mainButtonPanel.add(buttonChoiceLabel);
		withdrawButton.setVisible(true);
		checkBalanceButton.setVisible(true);
		this.setContentPane(splitFramePane);
		this.pack();
	}

	public void addTransferListener(ActionListener tal) {
		this.transferButton.addActionListener(tal);
	}

	public void addDepositListener(ActionListener dal) {
		this.depositButton.addActionListener(dal);
	}

	public void addWithdrawListener(ActionListener wal) {
		this.withdrawButton.addActionListener(wal);
	}

	public void addQueryListener(ActionListener qal) {
		this.checkBalanceButton.addActionListener(qal);
	}

	public void addTakeSnapShotListener(ActionListener sal) {
		this.takeSnapshotButton.addActionListener(sal);
	}

	public String getUISrcAccount() {
		return this.srcAccountNumberField.getText();
	}

	public String getUIAmount() {
		return this.amountNumberField.getText();
	}

	public String getUISerial() {
		return this.serialNumberField.getText();
	}

	public String getUIDestAccount() {
		return this.destAccountNumberField.getText();
	}

	public void setBalanceLabel(String label) {
		this.balanceLabel.setText(label);
	}

	public void enableAllButtons(boolean enable) {
		this.withdrawButton.setEnabled(enable);
		this.transferButton.setEnabled(enable);
		this.depositButton.setEnabled(enable);
		this.checkBalanceButton.setEnabled(enable);
	}

	public void clearAllTextFields() {
		this.amountNumberField.setText("");
		this.srcAccountNumberField.setText("");
		this.destAccountNumberField.setText("");
		this.serialNumberField.setText("");
	}

	private void setLabelNextToField(JLabel label, JTextField textField) {
		this.mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, label, 5,
				SpringLayout.NORTH, textField);
		this.mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, label, -5,
				SpringLayout.SOUTH, textField);
		this.mainButtonPanelLayout.putConstraint(SpringLayout.EAST, label, -5,
				SpringLayout.WEST, textField);
		this.mainButtonPanel.add(label);
	}

	private JTextField createTextFieldInMainPanel(JButton button,
			Integer northOffset) {
		JTextField textField = new JTextField();
		this.mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, textField,
				northOffset, SpringLayout.NORTH, mainButtonPanel);
		this.mainButtonPanelLayout.putConstraint(SpringLayout.WEST, textField,
				0, SpringLayout.WEST, button);
		this.mainButtonPanelLayout.putConstraint(SpringLayout.EAST, textField,
				0, SpringLayout.EAST, button);
		textField.setColumns(10);
		this.mainButtonPanel.add(textField);
		return textField;
	}

	public JButton createMenuButton(String name, Integer northOffset) {
		JButton button = new JButton(name);
		this.mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, button,
				GuiSpecs.MENU_BUTTON_START_N + northOffset, SpringLayout.NORTH,
				mainButtonPanel);
		this.mainButtonPanelLayout.putConstraint(SpringLayout.EAST, button,
				-170, SpringLayout.EAST, mainButtonPanel);
		button.setPreferredSize(new Dimension(150, 30));
		return button;
	}

	public void popUpErrorMessage(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public JPanel getClearSnapShotPanel() {
		this.snapshotPanel.removeAll();
		return this.snapshotPanel;
	}

	public void resetScrollPanel() {
		this.snapshotPanel.removeAll();
		JLabel welcomeMessage = new JLabel(greetingTextLabel, JLabel.CENTER);
		snapshotPanel.add(welcomeMessage, BorderLayout.CENTER);
		snapshotPanel.revalidate();
	}
	
	public JButton getDepositButton() {
		return this.depositButton;
	}
	
	public JButton getSnapshotButton() {
		return this.takeSnapshotButton;
	}

}
