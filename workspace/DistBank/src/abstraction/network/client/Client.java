package abstraction.network.client;

import java.net.SocketAddress;

import abstraction.distsys.Runtime;
import abstraction.network.common.Message;

public abstract class Client {

	protected static Message exec(SocketAddress a, Message m) {
		return Runtime.getRuntime().getNetworkInterface()
				.sendRequestGetResponse(a, m);
	}

}
