package testapp;

import java.io.IOException;

import bank.BranchGuiRequestHandler;
import bank.GuiId;
import bank.gui.BranchController;
import bank.gui.BranchView;
import core.node.NodeRuntime;

public class GuiTestRunner {
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		GuiId id = new GuiId(Integer.parseInt(args[0]));

		BranchController branchController = new BranchController();

		new Thread(new NodeRuntime(id, branchController,
				new BranchGuiRequestHandler(), false)).start();

		System.out.println("BankGui ATM " + id + " running.");

		branchController.run();
		
		//TODO call methods through branch controller
		NodeRuntime.getSnapshotHandler().broadcastSnapshotMessage();
		BranchView bW = branchController.getBranchView();
		
		bW.getSnapshotButton().doClick();
	}
}
