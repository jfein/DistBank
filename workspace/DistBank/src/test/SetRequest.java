package test;

import core.network.common.Message;

public class SetRequest extends Message {

	private static final long serialVersionUID = -8944465011824972200L;

	private int x;

	public SetRequest(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
}
