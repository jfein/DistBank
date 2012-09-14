package bank.gui;

/**
 * Create main log in page.
 * Add an action listener to log in button
 * Store the text in user name, must be communicated to next pages.
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import bank.AccountId;
import bank.BankClient;
import bank.messages.BankResponse;


public class BranchMain extends JPanel {
	
	private String mainContentIndex = "Main Content Panel";
	private String branchMainIndex = "Main Bank Branch Panel";
	
    private JPanel mainButtonPanel;
    private JPanel mainContentPanel;

	private static final long serialVersionUID = 1L;
	
	//All the text fields
	private JTextField serialNumberField;
	private JTextField srcAccountNumberField;
	private JTextField amountNumberField;
	private JTextField destAccountNumberField;
	
	//All the buttons
	private JButton withdrawButton;
	private JButton depositButton;
	private JButton transferButton;
	private JButton checkBalanceButton;
	
	private JLabel balanceLabel;
	
	private SpringLayout mainButtonPanelLayout;

	/**
	 * Create the frame.
	 */
	public BranchMain() {
		super(new CardLayout());
		setBounds(0, 0, 500, 500);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
        add(mainPanel, branchMainIndex);
		
        
        // North Panel
		JLabel northPanel = new JLabel(
				"Welcome to J&V Bank", JLabel.CENTER);
		northPanel.setPreferredSize(new Dimension(500, 100));
		mainPanel.add(northPanel, BorderLayout.NORTH);
	    
        // South Panel
		balanceLabel = new JLabel("Vera Kutsenko, Jeremy Fein", JLabel.CENTER);
		balanceLabel.setPreferredSize(new Dimension(500, 100));
		mainPanel.add(balanceLabel, BorderLayout.SOUTH); 
		
		//Center Panel
		mainButtonPanelLayout = new SpringLayout();
	    mainButtonPanel = new JPanel();
		mainButtonPanel.setLayout(mainButtonPanelLayout);
		mainPanel.add(mainButtonPanel, BorderLayout.CENTER);
	 
		//Deposit Button
		depositButton = createMenuButton("Deposit", 125);
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber() && isValidSerialNumber() && isValidAmount()) {
							doDepositPanel();
						}
					}
				});
			}
		});
		
		//Withdraw Button
		withdrawButton = createMenuButton("Withdraw", 150);
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber() && isValidSerialNumber() && isValidAmount()) {
							doWithdrawPanel();
						}
					}
				});
			}
		});
		
		//Transfer Button
	    transferButton = createMenuButton("Transfer", 175);
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber() && isValidDestinationAccountNumber() 
								&& isValidSerialNumber() && isValidAmount()) {
							doTransferPanel();
						}
					}
				});
			}
		});
		
		//Check Balance Button
		checkBalanceButton = createMenuButton("Check Balance", 200);
		checkBalanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (isValidSourceAccountNumber() && isValidSerialNumber()) {
							doCheckBalancePanel();
						}
					}
				});
			}
		});
	    
	    //Create Serial Number Field
	    this.serialNumberField = createTextFieldInMainPanel(withdrawButton, 0);
	    setLabelNextToField(new JLabel("Serial Number*:"), this.serialNumberField);
	    
	    //Create Source Account Number Field
	    this.srcAccountNumberField = createTextFieldInMainPanel(withdrawButton, 25);
	    setLabelNextToField(new JLabel("Source Account Number*:"), this.srcAccountNumberField);
	    
	    //Create Destination Account Number Field
	    this.destAccountNumberField = createTextFieldInMainPanel(withdrawButton, 50);
	    setLabelNextToField(new JLabel("Dest. Account Number:"), this.destAccountNumberField);
	    
	    //Create an amount field
	    this.amountNumberField = createTextFieldInMainPanel(withdrawButton, 75);
	    setLabelNextToField(new JLabel("Amount:"), this.amountNumberField);
	 
	    JLabel buttonChoiceLabel = new JLabel(" PLEASE CHOOSE YOUR ACTION ");
	    mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, buttonChoiceLabel, 0, SpringLayout.SOUTH, amountNumberField);
	    mainButtonPanelLayout.putConstraint(SpringLayout.EAST, buttonChoiceLabel, -96, SpringLayout.EAST, mainButtonPanel);
	    buttonChoiceLabel.setPreferredSize(new Dimension(250,30));

	    mainButtonPanel.add(withdrawButton);
	    mainButtonPanel.add(buttonChoiceLabel);
	    mainButtonPanel.add(transferButton);
	    mainButtonPanel.add(depositButton);
	    mainButtonPanel.add(checkBalanceButton);
	    
	    withdrawButton.setVisible(true);
	    checkBalanceButton.setVisible(true);
	    
	    
       //Create a main content panel that will hold other panels
	    mainContentPanel = new JPanel(new BorderLayout());
	    mainContentPanel.setPreferredSize(new Dimension(500,500));
	    add(mainContentPanel, mainContentIndex);
	}
	
	private void setLabelNextToField(JLabel label, JTextField textField) {
		 mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, textField);
		 mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, label, -5, SpringLayout.SOUTH, textField);
		 mainButtonPanelLayout.putConstraint(SpringLayout.EAST, label, -5, SpringLayout.WEST, textField);
		 mainButtonPanel.add(label);
	}
	
	private JTextField createTextFieldInMainPanel(JButton button, Integer northOffset){
		 JTextField textField = new JTextField();
		 mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, textField, northOffset, SpringLayout.NORTH, mainButtonPanel);
		 mainButtonPanelLayout.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, button);
		 mainButtonPanelLayout.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, button);
		 textField.setColumns(10);
		 mainButtonPanel.add(textField);
		 return textField;
	}
	
	public JButton createMenuButton (String name, Integer northOffset) {
		JButton button = new JButton(name);
	    mainButtonPanelLayout.putConstraint(SpringLayout.NORTH, button, northOffset, SpringLayout.NORTH, mainButtonPanel);
	    mainButtonPanelLayout.putConstraint(SpringLayout.EAST, button, -170, SpringLayout.EAST, mainButtonPanel);
		button.setPreferredSize(new Dimension(150,30));
		return button;
	}
    public void popUpErrorMessage(String message) {
    	JOptionPane.showMessageDialog(new JFrame(), message, "Error",
		        JOptionPane.ERROR_MESSAGE);
    }
    
    public boolean isStringNumeric(String s) {
    	Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
    	if(!doublePattern.matcher(s).matches()) {
    		popUpErrorMessage("Please enter a numeric value.");
    		return false;
    	}
    	return true;
    }
    
    public boolean isValidSerialNumber() {
    	if (this.serialNumberField.getText().equals("")){
    		popUpErrorMessage("Please fill out serial number.");
    		return false;
    	} 
    	return isStringNumeric(this.serialNumberField.getText());
    }
    
    public boolean isValidAccountNumber(String accountNumber) {
    	if(accountNumber.equals("")) {
    		popUpErrorMessage("Please fill out the required fields.");
    	}else if(isStringNumeric(accountNumber)) {
	    	String[] tokens = accountNumber.split("\\.");
	    	if (tokens[0].length() == 2 && tokens[1].length() == 5) {
	    			return true;
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
		} else if(!isStringNumeric(this.amountNumberField.getText())) {
			return false;
		} else {
			if(Double.parseDouble(this.amountNumberField.getText()) < 0.0) {
				popUpErrorMessage("Please enter a positive value for the amount.");
				return false;
			}
		} 
    	return true;
    }
    private void disableAllButtons () {
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
    
    private void enableAllButtons () {
    	this.withdrawButton.setEnabled(true);
		this.transferButton.setEnabled(true);
		this.depositButton.setEnabled(true);
		this.checkBalanceButton.setEnabled(true);
    }
    
	public void doDepositPanel(){
			disableAllButtons();
			AccountId accountId = new AccountId(this.srcAccountNumberField.getText());
			BankResponse response =  BankClient.deposit(accountId, Double.parseDouble(this.amountNumberField.getText()), Integer.parseInt(this.serialNumberField.getText()));
			if(checkResponse(response)){
				this.balanceLabel.setText("Your Account [" + this.srcAccountNumberField.getText() + "] Balance: " + String.valueOf(response.getAmt()));
			}
			clearAllTextFields();
			enableAllButtons();
	}
	
	public void doWithdrawPanel(){
		    disableAllButtons();
		    AccountId accountId = new AccountId(this.srcAccountNumberField.getText());
		    BankResponse response =  BankClient.withdraw(accountId, Double.parseDouble(this.amountNumberField.getText()), Integer.parseInt(this.serialNumberField.getText()));
			if(checkResponse(response)) {
				this.balanceLabel.setText("Your Account [" + this.srcAccountNumberField.getText() + "] Balance: " + String.valueOf(response.getAmt()));
			}
			clearAllTextFields();
	        enableAllButtons();
	}
	
	public void doCheckBalancePanel(){
		disableAllButtons();
		AccountId accountId = new AccountId(this.srcAccountNumberField.getText());
		BankResponse response = BankClient.query(accountId, Integer.parseInt(this.serialNumberField.getText()));
		if(checkResponse(response)){
			this.balanceLabel.setText("Your Account [" + this.srcAccountNumberField.getText() + "] Balance: " + String.valueOf(response.getAmt()));
		}
		clearAllTextFields();
		enableAllButtons();
		
	}
	public boolean checkResponse(BankResponse response) {
		if(response == null) { 
			popUpErrorMessage("Network Failure.");
			return false;
		} else if (!response.wasSuccessfull()) {
			popUpErrorMessage("Your request was unsuccessfull. Please try again. Check your serial number.");
			return false;
		} else {
			return true;
		}
	}
	public void doTransferPanel(){
		disableAllButtons();
		
			AccountId srcAccountId = new AccountId(this.srcAccountNumberField.getText());
				AccountId destAccountId = new AccountId(this.destAccountNumberField.getText());
				System.out.println("Is empty: " + this.amountNumberField.getText().equals(""));
				if (this.amountNumberField.getText().equals("")) {
					  JOptionPane.showMessageDialog(new JFrame(), "Please enter an amount.", "Error",
						        JOptionPane.ERROR_MESSAGE);
				} else {
					  // TODO: check for null! and check if successfull
					 BankResponse response =  BankClient.transfer(srcAccountId, destAccountId, Double.parseDouble(this.amountNumberField.getText()), Integer.parseInt(this.serialNumberField.getText()));
					 if (checkResponse(response)) {
						 this.balanceLabel.setText("Your Account [" + this.srcAccountNumberField.getText() + "] Balance: " + String.valueOf(response.getAmt()));
					 }
				}
		clearAllTextFields();
		enableAllButtons();
	}

	public static void createAndShowGUI(Integer branchID) {
		// Create and set up a window
		JFrame frame = new JFrame("J&V Bank ATM " + branchID);
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add contents to this window
		frame.getContentPane().add(new BranchMain());
		// Display the window
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//createAndShowGUI(1);
		String x = "11.122,,";
		Pattern doublePattern = Pattern.compile("-?\\d+(\\.\\d*)?");
		System.out.println("String " + x + " is numeric: " + doublePattern.matcher(x).matches());
		
	}
}
