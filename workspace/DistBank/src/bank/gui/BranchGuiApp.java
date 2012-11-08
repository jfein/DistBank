package bank.gui;

import core.app.App;
import core.app.AppId;

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
