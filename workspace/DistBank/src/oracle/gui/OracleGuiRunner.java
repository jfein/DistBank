package oracle.gui;

import java.io.IOException;

import bank.gui.BranchGuiApp;
import core.node.NodeId;
import core.node.NodeRuntime;



public class OracleGuiRunner {


	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		NodeId id = new NodeId(Integer.parseInt(args[0]));

		System.out.println("BankGUI Node " + id + " running.");

		(new NodeRuntime(id, OracleGuiApp.class)).run();
	}

}
