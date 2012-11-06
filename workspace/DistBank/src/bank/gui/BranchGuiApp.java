package bank.gui;

import core.app.App;

public class BranchGuiApp extends App<BranchController> {

	public BranchGuiApp() {
		super(null);
		new Thread(new BranchController()).start();
	}

	// Handle requests below

}
