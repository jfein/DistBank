package bank.main;

import bank.GuiId;
import core.distsys.NodeRuntime;
import bank.gui.BranchMain;

public class BankGuiRunner {

	public static void main(String[] args) {
		GuiId id = new GuiId(args[0]);
		new NodeRuntime(id, null);
		BranchMain.createAndShowGUI();
	}

}
