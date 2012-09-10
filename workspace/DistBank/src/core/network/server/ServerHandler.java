package core.network.server;

import core.network.common.Message;

public abstract class ServerHandler {

	public Message handle(Message m) {
		try {
			Class[] params = { m.getClass() };
			return (Message) this.getClass().getMethod("handle", params)
					.invoke(this, m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
