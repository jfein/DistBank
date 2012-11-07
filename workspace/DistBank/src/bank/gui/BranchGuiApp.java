package bank.gui;

import core.app.App;
import core.app.AppId;

public class BranchGuiApp extends App<BranchGuiController> {

	public BranchGuiApp(AppId appId) {
		super(appId);
	}

	@Override
	protected BranchGuiController newState() {
		BranchGuiController controller = new BranchGuiController(getAppId());
		controller.run();
		return controller;
	}

	// Handle requests below

}
