package test;

public class Response extends core.messages.Response {

	private static final long serialVersionUID = -2396947497686527656L;

	private int x;

	public Response(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
}
