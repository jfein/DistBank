package abstraction.network.client;

import java.net.SocketAddress;

import abstraction.network.common.Message;
import abstraction.network.common.NetworkInterface;

public abstract class Client {

	protected static Message exec(SocketAddress a, Message m) {
		return NetworkInterface.sendRequestGetResponse(a, m);
	}

}
