package bank.main;

import bank.GuiId;
import core.node.NodeRuntime;
import bank.gui.BranchMain;

public class BankGuiRunner {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		NodeRuntime.init(new GuiId(Integer.parseInt(args[0])), null);
		BranchMain.createAndShowGUI();
	}

}
