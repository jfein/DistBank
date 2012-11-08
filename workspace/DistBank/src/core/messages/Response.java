package core.messages;

import core.app.AppId;

/**
 * An abstract class that represents a response that can be sent from one app to
 * another. All responses are associated with an original request. After an app
 * handles a request and synchronizes, will then create a Response to send to
 * the app that sent the request.
 */
public abstract class Response extends Message {

	private static final long serialVersionUID = -5624023138287704166L;

	public Response(AppId<?> receiverAppId) {
		super(receiverAppId);
	}

}
