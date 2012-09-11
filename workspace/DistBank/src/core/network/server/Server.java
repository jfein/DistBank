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
		while (!serverSocket.isClosed()) {
			Socket conn = null;
			try {
				conn = serverSocket.accept();

				System.out.println("New Connection from "
						+ conn.getRemoteSocketAddress());

				Message msgIn = NetworkInterface.getMessage(conn);

				System.out.println("Got Request");

				Message msgOut = serverHandler.handle(msgIn);

				System.out.println("Request Handled");

				NetworkInterface.sendMessage(conn, msgIn.getReturnAddress(),
						msgOut);

				System.out.println("Sent Response");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			}
		}
	}
}
