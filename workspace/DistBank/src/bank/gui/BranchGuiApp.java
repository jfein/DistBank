package bank.gui;

import core.app.App;
import core.app.AppId;

public class BranchGuiApp extends App<BranchGuiController> {

	public BranchGuiApp(AppId appId) {
		super(appId);
	}

	@Override
	protected BranchGuiController newState() {
		return new BranchGuiController(getAppId());
	}

	// Handle requests below

}
