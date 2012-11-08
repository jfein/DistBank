package core.messages;

import core.app.AppId;

/**
 * An abstract class that represents a request that can be sent from on app to
 * another. All requests have a senderAppId so the receiving node knows what app
 * to send a response to. Also has a receiverAppId, just like all messages. When
 * a node receives an incoming request, will use the receiverAppId to put the
 * request on a specific app's request buffer. That app will know to send a
 * response to this sending app's response buffer using the request's
 * senderAppId.
 */
public abstract class Request extends Message {

	private static final long serialVersionUID = 1L;

	private AppId<?> senderAppId;

	public Request(AppId<?> senderAppId, AppId<?> receiverAppId) {
		super(receiverAppId);
		this.senderAppId = senderAppId;
	}

	public AppId<?> getSenderAppId() {
		return senderAppId;
	}

}
