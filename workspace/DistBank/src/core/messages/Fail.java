package core.messages;

public class Fail extends Request {

	private static final long serialVersionUID = -5720738233390812581L;

	public Fail() {
		// Doesn't expect response, so can make senderAppId null
		super(null, null);
	}
}
