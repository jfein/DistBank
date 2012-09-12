package bank.main;

import bank.GuiId;
import core.node.NodeRuntime;
import bank.gui.BranchMain;

public class BankGuiRunner {

	public static void main(String[] args) {
		NodeRuntime.init(new GuiId(args[0]), null);
		BranchMain.createAndShowGUI();
	}

}
