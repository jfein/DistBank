package core.messages;

/**
 * Message that is sent when a socket to a node's network interface is first
 * opened. Used to identify the node that opened the socket. This is the only
 * message that is not a request or response.
 */
public class InitConnMessage extends Message {

	private static final long serialVersionUID = 734007858801055921L;

	public InitConnMessage() {
		// Sends to network listener, so no receiving app
		super(null);
	}

}
