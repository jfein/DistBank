package bank.gui;

/**
 * Create main log in page.
 * Add an action listener to log in button
 * Store the text in user name, must be communicated to next pages.
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PanelUI;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import core.node.NodeRuntime;

import bank.AccountId;
import bank.BranchClient;
import bank.messages.BranchResponse;

public class BranchMain extends JPanel {

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

	private SpringLayout mainButtonPanelLayout;

	/**
	 * Create the frame.
	 */
	public BranchMain() {

		this.setPreferredSize(new Dimension(GuiSpecs.GUI_FRAME_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel snapshotPanel = new JPanel();
		snapshotPanel.setBackground(Color.pink);
		snapshotPanel.setLayout(new BorderLayout());
		snapshotPanel.setMaximumSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT));

		JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				snapshotPanel, mainPanel);
		add(mainPane, branchMainIndex);
		mainPane.setMaximumSize(new Dimension(GuiSpecs.GUI_BRANCH_PANEL_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT));
		mainPane.setDividerLocation(170 + mainPane.getInsets().left);

		// North Panel
		JLabel northPanel = new JLabel("Welcome to J&V Bank ATM #"
				+ NodeRuntime.getNodeId(), JLabel.CENTER);
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

		// Deposit Button
		depositButton = createMenuButton("Deposit", 0);
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber()
								&& isValidSerialNumber() && isValidAmount()) {
							doDepositPanel();
						}
					}
				});
			}
		});

		// Withdraw Button
		withdrawButton = createMenuButton("Withdraw",
				GuiSpecs.MENU_BUTTON_OFFSET);
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber()
								&& isValidSerialNumber() && isValidAmount()) {
							doWithdrawPanel();
						}
					}
				});
			}
		});

		// Transfer Button
		transferButton = createMenuButton("Transfer",
				2 * GuiSpecs.MENU_BUTTON_OFFSET);
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber()
								&& isValidDestinationAccountNumber()
								&& isValidSerialNumber() && isValidAmount()) {
							doTransferPanel();
						}
					}
				});
			}
		});

		// Check Balance Button
		checkBalanceButton = createMenuButton("Check Balance",
				3 * GuiSpecs.MENU_BUTTON_OFFSET);
		checkBalanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber()
								&& isValidSerialNumber()) {
							doCheckBalancePanel();
						}
					}
				});
			}
		});

		// Take Snapshot Button
		takeSnapshotButton = createMenuButton("Take Snapshot", 100);
		takeSnapshotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber()
								&& isValidSerialNumber()) {
							doTakeSnapshot();
						}
					}

				});
			}
		});

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

		mainButtonPanel.add(this.withdrawButton);
		mainButtonPanel.add(buttonChoiceLabel);
		mainButtonPanel.add(this.transferButton);
		mainButtonPanel.add(this.depositButton);
		mainButtonPanel.add(this.checkBalanceButton);
		mainButtonPanel.add(this.takeSnapshotButton);
		withdrawButton.setVisible(true);
		checkBalanceButton.setVisible(true);
	}

	private void setLabelNextToField(JLabel label, JTextField textField) {
		mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, label, 5,
				SpringLayout.NORTH, textField);
		mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, label, -5,
				SpringLayout.SOUTH, textField);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, label, -5,
				SpringLayout.WEST, textField);
		mainButtonPanel.add(label);
	}

	private JTextField createTextFieldInMainPanel(JButton button,
			Integer northOffset) {
		JTextField textField = new JTextField();
		mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, textField,
				northOffset, SpringLayout.NORTH, mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.WEST, textField, 0,
				SpringLayout.WEST, button);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, textField, 0,
				SpringLayout.EAST, button);
		textField.setColumns(10);
		mainButtonPanel.add(textField);
		return textField;
	}

	public JButton createMenuButton(String name, Integer northOffset) {
		JButton button = new JButton(name);
		mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, button,
				GuiSpecs.MENU_BUTTON_START_N + northOffset, SpringLayout.NORTH,
				mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, button, -170,
				SpringLayout.EAST, mainButtonPanel);
		button.setPreferredSize(new Dimension(150, 30));
		return button;
	}

	public void popUpErrorMessage(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public boolean isStringNumeric(String s) {
		Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
		if (!doublePattern.matcher(s).matches()) {
			popUpErrorMessage("Please enter a numeric value.");
			return false;
		}
		return true;
	}

	public boolean isValidSerialNumber() {
		if (this.serialNumberField.getText().equals("")) {
			popUpErrorMessage("Please fill out serial number.");
			return false;
		}
		return isStringNumeric(this.serialNumberField.getText());
	}

	public boolean isValidAccountNumber(String accountNumber) {
		if (accountNumber.equals("")) {
			popUpErrorMessage("Please fill out the required fields.");
		} else if (isStringNumeric(accountNumber)) {
			if (accountNumber.length() == 8) {
				String[] tokens = accountNumber.split("\\.");
				if (tokens[0].length() == 2 && tokens[1].length() == 5) {
					return true;
				}

			}
			popUpErrorMessage("Please ensure that account format is: bb.aaaa in numeric format. Ex:00.11111");
		}
		return false;
	}

	public boolean isValidDestinationAccountNumber() {
		return isValidAccountNumber(this.destAccountNumberField.getText());
	}

	public boolean isValidSourceAccountNumber() {
		return isValidAccountNumber(this.srcAccountNumberField.getText());
	}

	public boolean isValidAmount() {
		if (this.amountNumberField.getText().equals("")) {
			popUpErrorMessage("Please enter an amount.");
			return false;
		} else if (!isStringNumeric(this.amountNumberField.getText())) {
			return false;
		} else {
			if (Double.parseDouble(this.amountNumberField.getText()) < 0.0) {
				popUpErrorMessage("Please enter a positive value for the amount.");
				return false;
			}
		}
		return true;
	}

	private void disableAllButtons() {
		this.withdrawButton.setEnabled(false);
		this.transferButton.setEnabled(false);
		this.depositButton.setEnabled(false);
		this.checkBalanceButton.setEnabled(false);
	}

	private void clearAllTextFields() {
		this.amountNumberField.setText("");
		this.srcAccountNumberField.setText("");
		this.destAccountNumberField.setText("");
		this.serialNumberField.setText("");
	}

	private void enableAllButtons() {
		this.withdrawButton.setEnabled(true);
		this.transferButton.setEnabled(true);
		this.depositButton.setEnabled(true);
		this.checkBalanceButton.setEnabled(true);
	}

	public JPanel getClearSnapShotPanel() {
		JPanel snapShotPanel =  (JPanel) ((JSplitPane) this.getComponent(0)).getLeftComponent();
		snapShotPanel.removeAll();
		return snapShotPanel;
	}

	public void resetScrollPanel() {
		JPanel scrollPanel = getClearSnapShotPanel();
		JLabel welcomeMessage = new JLabel(greetingTextLabel, JLabel.CENTER);
		scrollPanel.add(welcomeMessage, BorderLayout.CENTER);
		scrollPanel.revalidate();
	}

	public void doTakeSnapshot() {
		// TODO CHANGE THIS
		JPanel leftPanel = getClearSnapShotPanel();
		JScrollPane scrollPanel = new JScrollPane();
		scrollPanel.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH,
				GuiSpecs.GUI_FRAME_HEIGHT - 100));

		JButton clear = new JButton("Clear");
		clear.setPreferredSize(new Dimension(GuiSpecs.GUI_SNAPSHOT_WIDTH, 30));

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						resetScrollPanel();
					}

				});
			}
		});

		leftPanel.add(clear, BorderLayout.SOUTH);
		leftPanel.add(scrollPanel, BorderLayout.CENTER);
		clearAllTextFields();
		leftPanel.revalidate();

		// AccountId accountId = new
		// AccountId(this.srcAccountNumberField.getText());
		// BranchResponse response =
		// BranchClient.takeSnapshot(accountId,Integer.parseInt(this.serialNumberField.getText()));
		// clearAllTextFields();

	}

	public void doDepositPanel() {
		disableAllButtons();
		AccountId accountId = new AccountId(
				this.srcAccountNumberField.getText());
		BranchResponse response = BranchClient.deposit(accountId,
				Double.parseDouble(this.amountNumberField.getText()),
				Integer.parseInt(this.serialNumberField.getText()));
		if (checkResponse(response)) {
			this.balanceLabel.setText("Your Account ["
					+ this.srcAccountNumberField.getText() + "] Balance: "
					+ String.valueOf(response.getAmt()));
		}
		clearAllTextFields();
		enableAllButtons();
	}

	public void doWithdrawPanel() {
		disableAllButtons();
		AccountId accountId = new AccountId(
				this.srcAccountNumberField.getText());
		BranchResponse response = BranchClient.withdraw(accountId,
				Double.parseDouble(this.amountNumberField.getText()),
				Integer.parseInt(this.serialNumberField.getText()));
		if (checkResponse(response)) {
			this.balanceLabel.setText("Your Account ["
					+ this.srcAccountNumberField.getText() + "] Balance: "
					+ String.valueOf(response.getAmt()));
		}
		clearAllTextFields();
		enableAllButtons();
	}

	public void doCheckBalancePanel() {
		disableAllButtons();
		AccountId accountId = new AccountId(
				this.srcAccountNumberField.getText());
		BranchResponse response = BranchClient.query(accountId,
				Integer.parseInt(this.serialNumberField.getText()));
		if (checkResponse(response)) {
			this.balanceLabel.setText("Your Account ["
					+ this.srcAccountNumberField.getText() + "] Balance: "
					+ String.valueOf(response.getAmt()));
		}
		clearAllTextFields();
		enableAllButtons();

	}

	public boolean checkResponse(BranchResponse response) {
		if (response == null) {
			popUpErrorMessage("Network Failure.");
			return false;
		} else if (!response.wasSuccessfull()) {
			popUpErrorMessage("Your request was unsuccessfull. Please try again. Check your serial number.");
			this.balanceLabel.setText("Your Account ["
					+ this.srcAccountNumberField.getText() + "] Balance: "
					+ String.valueOf(response.getAmt()));
			return false;
		} else {
			return true;
		}
	}

	public void doTransferPanel() {
		disableAllButtons();
		AccountId srcAccountId = new AccountId(
				this.srcAccountNumberField.getText());
		AccountId destAccountId = new AccountId(
				this.destAccountNumberField.getText());
		if (this.amountNumberField.getText().equals("")) {
			JOptionPane.showMessageDialog(new JFrame(),
					"Please enter an amount.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			BranchResponse response = BranchClient.transfer(srcAccountId,
					destAccountId,
					Double.parseDouble(this.amountNumberField.getText()),
					Integer.parseInt(this.serialNumberField.getText()));
			if (checkResponse(response)) {
				this.balanceLabel.setText("Your Account ["
						+ this.srcAccountNumberField.getText() + "] Balance: "
						+ String.valueOf(response.getAmt()));
			}
		}
		clearAllTextFields();
		enableAllButtons();
	}

	public static void createAndShowGUI(Integer branchID) {
		// Create and set up a window
		JFrame frame = new JFrame("J&V Bank ATM " + branchID);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BranchMain());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

}
