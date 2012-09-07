package frontend.gui.main;

/**
 * Create main log in page.
 * Add an action listener to log in button
 * Store the text in user name, must be communicated to next pages.
 */
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;



public class BranchMain extends JPanel {
	
	private String mainContentIndex = "Main Content Panel";
	private String branchMainIndex = "Main Bank Branch Panel";
    private JPanel mainButtonPanel;
    private JPanel mainContentPanel;
    private static BranchMain currentBranchMain;
	private static final long serialVersionUID = 1L;


	/**
	 * Create the frame.
	 */
	public BranchMain() {
		super(new CardLayout());
		setBounds(0, 0, 500, 500);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		currentBranchMain = this;
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
        add(mainPanel, branchMainIndex);
		
        
        // North Panel
		JLabel northPanel = new JLabel(
				"Welcome to WHYDOIHAVETODOTHIS (WDIHTDT) Bank", JLabel.CENTER);
		northPanel.setPreferredSize(new Dimension(500, 100));
		mainPanel.add(northPanel, BorderLayout.NORTH);
	    
        // South Panel
		JLabel southPanel = new JLabel(
				"Vera Kutsenko, Jeremy Fein", JLabel.CENTER);
		southPanel.setPreferredSize(new Dimension(500, 100));
		mainPanel.add(southPanel, BorderLayout.SOUTH);
	    
		
		//Center Panel
		SpringLayout mainButtonPanelLayout = new SpringLayout();
	    mainButtonPanel = new JPanel();
		//Set layout of button panel to Spring layout
		mainButtonPanel.setLayout(mainButtonPanelLayout);
		//add button panel to main panel
		mainPanel.add(mainButtonPanel, BorderLayout.CENTER);
	    JLabel buttonChoiceLabel = new JLabel(" PLEASE CHOOSE YOUR ACTION ");
	    buttonChoiceLabel.setPreferredSize(new Dimension(250,30));
		
	    //Transfer Button
	    JButton transferButton = new JButton(" Transfer ");
		transferButton.setPreferredSize(new Dimension(150,30));
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doTransferPanel();
					}
				});
			}
		});
		
		//Deposit Button
		JButton depositButton = new JButton(" Deposit ");
		depositButton.setPreferredSize(new Dimension(150,30));
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doDepositPanel();
					}
				});
			}
		});
		
		//Withdraw Button
		JButton withdrawButton = new JButton(" Widthraw ");
		withdrawButton.setPreferredSize(new Dimension(150,30));
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doWithdrawPanel();
					}
				});
			}
		});
		
		//Check Balance Button
		JButton checkBalanceButton = new JButton(" Check Balance");
		checkBalanceButton.setPreferredSize(new Dimension(150,30));
		checkBalanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doCheckBalancePanel();
					}
				});
			}
		});
		mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, buttonChoiceLabel, -215,
				SpringLayout.SOUTH, mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, depositButton, -185,
				SpringLayout.SOUTH, mainButtonPanel);
		
		mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, withdrawButton, -155,
				SpringLayout.SOUTH, mainButtonPanel);
		
		mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, transferButton, -125,
				SpringLayout.SOUTH, mainButtonPanel);
		
		mainButtonPanelLayout.putConstraint(SpringLayout.SOUTH, checkBalanceButton, -95,
				SpringLayout.SOUTH, mainButtonPanel);
		
		//Put constraints on buttons so they are aligned the same distance from
		//the right side of the panel
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, checkBalanceButton, -175,
				SpringLayout.EAST,mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, withdrawButton, -175,
				SpringLayout.EAST,mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, depositButton, -175,
				SpringLayout.EAST,mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, transferButton, -175,
				SpringLayout.EAST,mainButtonPanel);
		mainButtonPanelLayout.putConstraint(SpringLayout.EAST, buttonChoiceLabel, -100,
				SpringLayout.EAST,mainButtonPanel);
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
	public void addBackButton(){
		JButton depositButton = new JButton(" Back To Main ");
		depositButton.setPreferredSize(new Dimension(150,30));
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						doBackButton();
						}
					});
				}
			});
		mainContentPanel.add(depositButton,BorderLayout.SOUTH);
	}
    public void updateBranchMainLayout() {
    	addBackButton();
    	CardLayout cL = (CardLayout) (this.getLayout());
    	cL.show(this, mainContentIndex);
    	this.updateUI();
    }
	public void doDepositPanel(){
		mainContentPanel.removeAll();
        DepositPanel dp = new DepositPanel();
        dp.setVisible(true);
        mainContentPanel.add(dp, BorderLayout.CENTER);
		updateBranchMainLayout();
	}
	public void doWithdrawPanel(){
		mainContentPanel.removeAll();
        WithdrawPanel dp = new WithdrawPanel();
        dp.setVisible(true);
        mainContentPanel.add(dp, BorderLayout.CENTER);
        updateBranchMainLayout();
	}
	public void doCheckBalancePanel(){
		mainContentPanel.removeAll();
        BalancePanel dp = new BalancePanel();
        dp.setVisible(true);
        mainContentPanel.add(dp, BorderLayout.CENTER);
        updateBranchMainLayout();
	}
	public void doTransferPanel(){
		mainContentPanel.removeAll();
        TransferPanel dp = new TransferPanel();
        dp.setVisible(true);
        mainContentPanel.add(dp, BorderLayout.CENTER);
        updateBranchMainLayout();
	}
	
	public void doBackButton(){
		// Remove the content
		mainContentPanel.removeAll();

    	// Show the branch home page
		CardLayout cL = (CardLayout) (this.getLayout());
		cL.show(this, branchMainIndex);
		this.updateUI();
	}
	
	public static void createAndShowGUI() {
		// Create and set up a window
		JFrame frame = new JFrame("Shouter!");
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("Creating GUI\n");
		// add contents to this window
		frame.getContentPane().add(new BranchMain());
		// Display the window
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	public static void main(String[] args){
		createAndShowGUI();
	}
}
