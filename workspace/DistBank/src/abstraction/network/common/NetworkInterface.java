package abstraction.network.common;

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

import abstraction.distsys.Runtime;

public class NetworkInterface extends Thread {

	private InetSocketAddress myAddress;

	public NetworkInterface(InetSocketAddress myAddress) {
		this.myAddress = myAddress;
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
		ServerSocket server = new ServerSocket(myAddress.getPort());

		while (true) {
			try {
				Socket conn = server.accept();

				System.out.println("Got conn from "
						+ conn.getRemoteSocketAddress());

				// Get Request
				InputStream connIn = conn.getInputStream();
				ObjectInputStream in = new ObjectInputStream(connIn);
				Message msgIn = (Message) in.readObject();

				System.out.println("Got Request");

				// Handle the request to form a response
				Message msgOut = Runtime.getRuntime().getServer().serve(msgIn);

				System.out.println("Made Response");

				// Send response
				OutputStream conOut = conn.getOutputStream();
				ObjectOutputStream out = new ObjectOutputStream(conOut);
				out.writeObject(msgOut);
				out.flush();

				System.out.println("Sent Response");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Opens socket, sends a request, waits for a response
	public static Message sendRequestGetResponse(SocketAddress a, Message req) {
		try {
			// Check we can send messages to remote host & get a response
			if (!Topology.channelsOut.contains(a)
					|| !Topology.channelsIn.contains(a)) {
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
			Message resp = (Message) in.readObject();

			System.out.println("Got Response");

			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
