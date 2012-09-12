package test;

import core.network.common.Message;

public class Response extends Message {

	private static final long serialVersionUID = -2396947497686527656L;

	private int x;

	public Response(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
}
