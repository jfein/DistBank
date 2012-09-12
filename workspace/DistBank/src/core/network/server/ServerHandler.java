package core.network.server;

import core.network.common.Message;

public abstract class ServerHandler {

	public synchronized Message handle(Message m) {
		try {
			return (Message) this.getClass().getMethod("handle", m.getClass())
					.invoke(this, m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
