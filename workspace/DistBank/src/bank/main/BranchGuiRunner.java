package bank.main;

import java.io.IOException;

import bank.BranchGuiRequestHandler;
import bank.GuiId;
import core.node.NodeRuntime;
import bank.gui.BranchController;

public class BranchGuiRunner {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		GuiId id = new GuiId(Integer.parseInt(args[0]));

		BranchController branchController = new BranchController();

		new Thread(new NodeRuntime(id, branchController,
				new BranchGuiRequestHandler(), false)).start();

		branchController.run();

		System.out.println("BankGui ATM " + id + " running.");
	}
}
