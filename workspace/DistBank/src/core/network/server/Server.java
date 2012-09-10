package core.network.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import core.network.common.Message;
import core.network.common.NetworkInterface;

public class Server extends Thread {

	private ServerSocket serverSocket;
	private ServerHandler serverHandler;

	public Server(InetSocketAddress myAddress, ServerHandler handler)
			throws IOException {
		this.serverSocket = new ServerSocket(myAddress.getPort());
		this.serverHandler = handler;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket conn = serverSocket.accept();

				Message msgIn = NetworkInterface.getMessage(conn);

				Message msgOut = serverHandler.handle(msgIn);

				NetworkInterface.sendMessage(conn, msgOut);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
