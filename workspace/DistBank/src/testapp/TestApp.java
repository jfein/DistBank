package testapp;

import core.app.App;
import core.app.AppId;

public class TestApp extends App<TestState> {

	public TestApp(AppId<TestApp> appId) {
		super(appId);
	}

	@Override
	protected TestState newState() {
		return new TestState();
	}

	public Response handle(SetRequest req) {
		TestState state = getState();
		state.setX(req.getX());
		return new Response(state.getX());
	}

	public Response handle(GetRequest req) {
		TestState state = getState();
		return new Response(state.getX());
	}

}
