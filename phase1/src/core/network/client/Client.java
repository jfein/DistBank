package core.network.client;

import java.net.Socket;

import core.network.common.Message;
import core.network.common.NetworkInterface;
import core.node.NodeId;

public abstract class Client {

	protected static synchronized <T extends Message> T exec(NodeId dest,
			Message msgOut) {
		T msgIn = null;

		try {
			Socket conn = NetworkInterface.sendMessageNewSocket(dest, msgOut);
			msgIn = NetworkInterface.getMessage(conn);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msgIn;
	}
}
