package bank.main;

import java.io.IOException;

import bank.BranchGuiRequestHandler;
import bank.GuiId;
import core.node.NodeRuntime;
import bank.gui.BranchController;

public class OracleGuiRunner {


	public static void main(String[] args) throws IOException {
		

		OracleController oracleController = new OracleController();

		

		oracleController.run();

	}

}
