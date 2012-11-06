package core.app;

public class App<S extends AppState> {

	private S appState;

	public App(S appState) {
		this.appState = appState;
	}

	public S getState() {
		return appState;
	}

	public void setState(AppState newAppState) {
		this.appState = (S) newAppState;
		System.out.println("NEW STATE:");
		System.out.println(this.getState());
	}

}
