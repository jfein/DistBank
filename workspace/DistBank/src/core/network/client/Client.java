package core.network.client;

import java.net.Socket;
import java.net.SocketAddress;

import core.network.common.Message;
import core.network.common.NetworkInterface;

public abstract class Client {

	protected static <T extends Message> T exec(SocketAddress dest,
			Message msgOut) {
		try {
			Socket conn = NetworkInterface.sendMessage(dest, msgOut);
			return NetworkInterface.getMessage(conn);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
