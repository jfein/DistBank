package core.messages;

public class InitConnMessage extends Message {

	private static final long serialVersionUID = 734007858801055921L;

	public InitConnMessage() {
		// Sends to network listener, to no receiving app
		super(null);
	}

}
