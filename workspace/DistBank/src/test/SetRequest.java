package test;

import core.network.common.Message;

public class SetRequest extends Message {

	private int x;

	public SetRequest(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
}
