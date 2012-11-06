package bank.gui;

import core.app.App;
import core.app.AppId;

public class BranchGuiApp extends App<BranchController> {

	public BranchGuiApp(AppId appId) {
		super(appId);
	}

	@Override
	protected BranchController newState() {
		BranchController controller = new BranchController(getAppId());
		new Thread(controller).start();
		return controller;
	}

	// Handle requests below

}
