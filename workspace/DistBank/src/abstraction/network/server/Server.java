package abstraction.network.server;

import abstraction.network.common.Message;

public abstract class Server {

	public Message serve(Message m) {
		try {
			Class[] params = { m.getClass() };
			return (Message) this.getClass().getMethod("serve", params)
					.invoke(null, m);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
