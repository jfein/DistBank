package test;

import core.network.common.Message;

public class Response extends Message {

	private int x;

	public Response(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
}
