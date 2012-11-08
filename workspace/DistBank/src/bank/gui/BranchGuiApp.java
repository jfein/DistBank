package bank.gui;

import core.app.App;
import core.app.AppId;

/**
 * Extends core.app.App. This is the main GUI app that runs on a node. Simply
 * starts up a BranchGuiController. Since its a unique app, is able to use a
 * client to send requests and receive responses on its response buffer. Has no
 * new request handlers.
 */
public class BranchGuiApp extends App<BranchGuiController> {

	public BranchGuiApp(AppId<BranchGuiApp> appId) {
		super(appId);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected BranchGuiController newState() {
		return new BranchGuiController((AppId<BranchGuiApp>) getAppId());
	}

}
