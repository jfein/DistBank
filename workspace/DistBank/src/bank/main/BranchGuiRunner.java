package bank.main;

import java.io.IOException;

import bank.GuiId;
import core.node.NodeRuntime;
import bank.gui.BranchController;
import bank.gui.BranchMain;
import bank.gui.BranchView;

public class BranchGuiRunner {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		GuiId id = new GuiId(Integer.parseInt(args[0]));

		new Thread(new NodeRuntime(id, null, null)).start();

		BranchView branchView = new BranchView();
		BranchController branchController = new BranchController(branchView);
		branchView.setVisible(true);

		System.out.println("BankGui ATM " + id + " running.");
	}
}
