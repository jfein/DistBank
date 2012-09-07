package network.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.Set;

import network.server.Server;

public class NetworkInterface implements Runnable {

	private Set<SocketAddress> channelsOut;
	private Set<SocketAddress> channelsIn;

	private InetSocketAddress mySocket;

	public NetworkInterface(String myIp, int myPort) {
		mySocket = new InetSocketAddress(myIp, myPort);

		List<Set<SocketAddress>> channels = Topology.getMyChannels(mySocket);
		channelsOut = channels.get(0);
		channelsIn = channels.get(1);

		// Start a thread to get requests
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			getRequestSendResponse();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getRequestSendResponse() throws IOException {
		ServerSocket server = new ServerSocket(mySocket.getPort());

		while (true) {
			try {
				Socket conn = server.accept();

				System.out.println("Got conn from "
						+ conn.getRemoteSocketAddress());

				// Get Request
				InputStream connIn = conn.getInputStream();
				ObjectInputStream in = new ObjectInputStream(connIn);
				Request req = (Request) in.readObject();

				System.out.println("Got Request");

				// Handle the request to form a response
				Class[] params = { Request.class };
				Response resp = (Response) Server.class.getMethod(req.function,
						params).invoke(null, req);

				System.out.println("Made Response");

				// Check we can send messages to the remote host
				if (!channelsOut.contains(conn.getRemoteSocketAddress())) {
					continue;
				}

				// Send response
				OutputStream conOut = conn.getOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(conOut);
				out.writeObject(resp);
				out.flush();

				System.out.println("Sent Response");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Opens socket, sends a request, waits for a response
	public Response sendRequestGetResponse(SocketAddress a, Request req) {
		try {
			// Check we can send messages to remote host
			if (!channelsOut.contains(a)) {
				throw new Exception();
			}

			// Establish connection
			Socket conn = new Socket();
			conn.connect(a);

			System.out.println("Established connection");

			// Send Request
			OutputStream conOut = conn.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(conOut);
			out.writeObject(req);
			out.flush();

			System.out.println("Sent Request");

			// Get response
			InputStream connIn = conn.getInputStream();
			ObjectInputStream in = new ObjectInputStream(connIn);
			Response resp = (Response) in.readObject();

			System.out.println("Got Response");

			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Response(-1);
	}

}
