package oracle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import core.node.NodeId;
import core.node.NodeRuntime;

import bank.gui.GuiSpecs;

public class OracleGuiView extends JFrame {

	private JButton failButton;
	private JButton notifyFailButton;
	private JButton recoverButton;
	private JButton notifyRecoverButton;

	private JPanel buttonsPanel;
	private SpringLayout buttonsPanelLayout;
	private JPanel mainPanel;

	private JComboBox nodeList;

	private static final long serialVersionUID = 1L;

	public OracleGuiView() {
		this.setPreferredSize(new Dimension(GuiSpecs.GUI_FRAME_WIDTH / 2, GuiSpecs.GUI_FRAME_HEIGHT / 2));

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		buttonsPanel = new JPanel();
		buttonsPanelLayout = new SpringLayout();
		buttonsPanel.setLayout(buttonsPanelLayout);
		buttonsPanel.setPreferredSize(new Dimension(GuiSpecs.GUI_BRANCH_PANEL_WIDTH / 2, 100));
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);

		JLabel nodeId = new JLabel("Node Id");
		this.buttonsPanelLayout.putConstraint(SpringLayout.NORTH, nodeId, 5, SpringLayout.NORTH, buttonsPanel);
		this.buttonsPanelLayout.putConstraint(SpringLayout.EAST, nodeId, -150, SpringLayout.EAST, buttonsPanel);
		System.out.println(parseNodeIds().size());

		nodeList = new JComboBox(parseNodeIds().toArray());
		nodeList.setPreferredSize(new Dimension(150, 30));
		this.buttonsPanelLayout.putConstraint(SpringLayout.NORTH, nodeList, 25, SpringLayout.NORTH, buttonsPanel);
		this.buttonsPanelLayout.putConstraint(SpringLayout.EAST, nodeList, -100, SpringLayout.EAST, buttonsPanel);

		failButton = createButton("Fail", 0);
		notifyFailButton = createButton("Notify of Failure", GuiSpecs.MENU_BUTTON_OFFSET);
		recoverButton = createButton("Recover", 2 * GuiSpecs.MENU_BUTTON_OFFSET);
		notifyRecoverButton = createButton("Notify of Recovery", 3 * GuiSpecs.MENU_BUTTON_OFFSET);

		buttonsPanel.add(nodeId);
		buttonsPanel.add(nodeList);
		buttonsPanel.add(this.failButton);
		buttonsPanel.add(this.notifyFailButton);
		buttonsPanel.add(this.recoverButton);
		buttonsPanel.add(this.notifyRecoverButton);

		this.setContentPane(mainPanel);
		this.pack();

	}

	public String[] convertToArray(Set<NodeId> nodes) {
		ArrayList<String> listOfNodes = new ArrayList<String>();
		for (NodeId id : nodes) {
			listOfNodes.add(id.toString());
		}
		return listOfNodes.toArray(new String[listOfNodes.size()]);
	}

	public void addFailListener(ActionListener fal) {
		this.failButton.addActionListener(fal);
	}

	public void addNotifyFailListener(ActionListener nfal) {
		this.notifyFailButton.addActionListener(nfal);
	}

	public void addRecoverListener(ActionListener ral) {
		this.recoverButton.addActionListener(ral);
	}

	public void addNotifyRecoverListener(ActionListener nral) {
		this.notifyRecoverButton.addActionListener(nral);
	}

	public NodeId getNodeId() {
		String NodeId = (String) this.nodeList.getSelectedItem();
		return new NodeId(Integer.parseInt(NodeId));
	}

	public JButton createButton(String name, Integer northOffset) {
		JButton button = new JButton(name);
		this.buttonsPanelLayout.putConstraint(SpringLayout.NORTH, button, GuiSpecs.ORACLE_MENU_BUTTON_START_N
				+ northOffset, SpringLayout.NORTH, buttonsPanel);
		this.buttonsPanelLayout.putConstraint(SpringLayout.EAST, button, -100, SpringLayout.EAST, buttonsPanel);
		button.setPreferredSize(new Dimension(150, 30));
		return button;
	}

	public Set<String> parseNodeIds() {
		Set<String> listOfNodes = new HashSet<String>();
		try {
			BufferedReader read1 = new BufferedReader(new FileReader(NodeRuntime.APPS_FILE));

			read1.readLine();
			while (read1.ready()) {
				String t;
				try {
					t = read1.readLine();
					String parts[] = t.split(" ");
					if (!(parts[1].equals("oracle.OracleApp") || parts[1].contains("gui"))) {
						for (int i = 2; i < parts.length; i++) {
							// Add nodeID to app mapping
							String nodeId = Integer.toString((Integer.parseInt(parts[i])));
							listOfNodes.add(nodeId);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return listOfNodes;
	}

}
