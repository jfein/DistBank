package test;

import java.io.IOException;
import java.net.InetSocketAddress;

import core.network.server.ServerNodeRuntime;

public class TestServerRunner {

	public static void main(String[] args) throws IOException {
		String host = "localhost";
		int port = Integer.parseInt(args[0]);
		InetSocketAddress myAddress = new InetSocketAddress(host, port);

		System.out.println("Starting on port " + port);

		new ServerNodeRuntime(myAddress, new TestState(),
				new TestServerHandler());

		if (port == 4001) {
			InetSocketAddress dest = new InetSocketAddress("localhost", 4000);
			System.out.println("A");
			TestClient.setX(dest, 42);
			System.out.println("B");
			System.out.println("Other X: " + TestClient.getX(dest));
		}
	}
}
