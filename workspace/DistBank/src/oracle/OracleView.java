package oracle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import bank.gui.GuiSpecs;

public class OracleView extends JFrame {
	
	/**
	 * 
	 */
	
	private JButton failButton;
	private JButton notifyFailButton;
	private JButton recoverButton;
	private JButton notifyRecoverButton;
	
	private JTextField nodeIdField;
	
	private JPanel buttonsPanel;
	private SpringLayout buttonsPanelLayout;
	private JPanel mainPanel;
	
	private static final long serialVersionUID = 1L;
	public OracleView() {
		this.setPreferredSize(new Dimension(GuiSpecs.GUI_FRAME_WIDTH/2,GuiSpecs.GUI_FRAME_HEIGHT/2));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		buttonsPanel = new JPanel();
		buttonsPanelLayout = new SpringLayout();
		buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanel.setPreferredSize(new Dimension(GuiSpecs.GUI_BRANCH_PANEL_WIDTH/2, 100));
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		JLabel nodeId = new JLabel("Node Id");
		this.buttonsPanelLayout.putConstraint(SpringLayout.NORTH, nodeId, 5, SpringLayout.NORTH, buttonsPanel);
		this.buttonsPanelLayout.putConstraint(SpringLayout.EAST, nodeId,-150, SpringLayout.EAST, buttonsPanel);
		
		nodeIdField = new JTextField("");
		this.buttonsPanelLayout.putConstraint(SpringLayout.NORTH, nodeIdField, 25, SpringLayout.NORTH, buttonsPanel);
		this.buttonsPanelLayout.putConstraint(SpringLayout.EAST, nodeIdField,-100, SpringLayout.EAST, buttonsPanel);
		nodeIdField.setPreferredSize(new Dimension(150, 30));
		
		failButton = createButton("Fail", 0);
		notifyFailButton = createButton("Notify of Failure", GuiSpecs.MENU_BUTTON_OFFSET);
		recoverButton = createButton("Recover", 2* GuiSpecs.MENU_BUTTON_OFFSET);
		notifyRecoverButton = createButton("Notify of Recovery", 3*GuiSpecs.MENU_BUTTON_OFFSET);
		
		buttonsPanel.add(nodeId);
		buttonsPanel.add(this.nodeIdField);
		buttonsPanel.add(this.failButton);
		buttonsPanel.add(this.notifyFailButton);
		buttonsPanel.add(this.recoverButton);
		buttonsPanel.add(this.notifyRecoverButton);
		
		this.setContentPane(mainPanel);
		this.pack();
		
	}
	
	public void addFailListener(ActionListener fal) {
		this.failButton.addActionListener(fal);
	}
	
	public void addNotifyFailListener(ActionListener nfal){
		this.notifyFailButton.addActionListener(nfal);
	}
	
	public void addRecoverListener(ActionListener ral) {
		this.recoverButton.addActionListener(ral);
	}
	public void addNotifyRecoverListener(ActionListener nral) {
		this.notifyRecoverButton.addActionListener(nral);
	}
	
	public Integer getNodeId() {
		if(this.nodeIdField.getText().equals(""))
			return -1;
		else {
			return Integer.parseInt(this.nodeIdField.getText());
		}
	}
	
	public JButton createButton(String name, Integer northOffset) {
		JButton button = new JButton(name);
		this.buttonsPanelLayout.putConstraint(SpringLayout.NORTH, button,
				GuiSpecs.ORACLE_MENU_BUTTON_START_N + northOffset, SpringLayout.NORTH,
				buttonsPanel);
		this.buttonsPanelLayout.putConstraint(SpringLayout.EAST, button,
				-100, SpringLayout.EAST, buttonsPanel);
		button.setPreferredSize(new Dimension(150, 30));
		return button;
	}
	
	

}
