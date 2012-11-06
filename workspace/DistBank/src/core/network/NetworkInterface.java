package core.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.network.messages.InitConnMessage;
import core.network.messages.Message;
import core.node.NodeId;
import core.node.NodeRuntime;

public class NetworkInterface implements Runnable {

	private final Topology topology;
	private final Map<NodeId, Socket> connsIn;
	private final Map<NodeId, Socket> connsOut;

	public NetworkInterface() {
		topology = new Topology(NodeRuntime.NODES_FILE,
				NodeRuntime.TOPOLOGY_FILE);
		connsIn = new HashMap<NodeId, Socket>();
		connsOut = new HashMap<NodeId, Socket>();
	}

	public Set<NodeId> whoNeighbors() {
		return topology.getChannelsOut();
	}

	public Set<NodeId> whoNeighborsIn() {
		return topology.getChannelsIn();
	}

	public boolean canSendTo(NodeId id) {
		if (id == null)
			return false;
		return topology.getChannelsOut().contains(id);
	}

	public boolean canReceiveFrom(NodeId id) {
		if (id == null)
			return false;
		return topology.getChannelsIn().contains(id);
	}

	public InetSocketAddress getNodeAddress(NodeId nodeId) {
		return topology.getNodeToServerAddress().get(nodeId);
	}

	/**
	 * Gets a message from the designated src. Blocks on retrieving the message.
	 * Synchronizes on the connection in, so only one thread can block and read
	 * a message at once.
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public <T extends Message> T getMessage(NodeId src) throws Exception {
		if (!canReceiveFrom(src))
			throw new Exception("Error: This node cannot receive from " + src);

		Socket connIn;

		synchronized (connsIn) {
			connIn = connsIn.get(src);
		}

		// Only let one person receive on this channel at a time
		synchronized (connIn) {
			return getMessage(connIn);
		}
	}

	/**
	 * Send a message to the destination. Synchronizes on th e connection out,
	 * so only one message can be sent on a channel at a time.
	 * 
	 * @param dest
	 * @param msg
	 * @throws Exception
	 */
	public <T extends Message> void sendMessage(NodeId dest, T msg)
			throws Exception {
		if (!canSendTo(dest))
			throw new Exception("Error: This node cannot send to " + dest);

		Socket connOut;

		// Open socket if it's not available
		synchronized (connsOut) {
			if (!connsOut.containsKey(dest) || connsOut.get(dest).isClosed()) {
				connOut = openConnOut(dest);
				connsOut.put(dest, connOut);
			} else {
				connOut = connsOut.get(dest);
			}
		}

		// Only let one person send on this channel at a time
		synchronized (connOut) {
			sendMessage(connOut, msg);
		}
	}

	/**
	 * Main server thread. Listens for new connections. When retrieving a valid
	 * connection in, creates a new MessageListener thread to block on the
	 * incoming connection listening for messages.
	 */
	@Override
	public void run() {
		InetSocketAddress myAddress = getNodeAddress(NodeRuntime.getId());

		// Create server socket
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(myAddress.getPort());
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		// Create thread pool
		ExecutorService threadPool = Executors.newFixedThreadPool(topology
				.getChannelsIn().size());

		while (!serverSocket.isClosed()) {
			Socket connIn = null;

			try {

				connIn = serverSocket.accept();

				// Get the first InitConnMessage on the channelIn
				InitConnMessage initConnMessage = getMessage(connIn);
				NodeId src = initConnMessage.getSenderNodeId();

				// Make sure we can receive from the client
				if (!canReceiveFrom(src)) {
					throw new Exception("Received connection from client "
							+ "that we cannot receive messages from");
				}

				// Add the new connIn, close any existing
				synchronized (connsIn) {
					if (connsIn.containsKey(src)) {
						Socket oldConnIn = connsIn.get(src);
						synchronized (oldConnIn) {
							oldConnIn.close();
						}
					}
					connsIn.put(src, connIn);
				}

				// Start a MessageListener to block on incoming messages on this
				// new connIn
				threadPool.execute(new MessageListener(src));

			} catch (Exception e) {
				e.printStackTrace();
				try {
					connIn.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private <T extends Message> T getMessage(Socket connIn) throws Exception {
		InputStream streamIn = connIn.getInputStream();
		ObjectInputStream in = new ObjectInputStream(streamIn);
		T obj = (T) in.readObject();
		// Thread.sleep(1000); // SIMULATE NETWORK DELAY
		return obj;
	}

	private <T extends Message> void sendMessage(Socket connOut, T msg)
			throws Exception {
		OutputStream streamOut = connOut.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(streamOut);
		out.writeObject(msg);
		out.flush();
	}

	private Socket openConnOut(NodeId dest) {
		Socket connOut = null;
		try {
			connOut = new Socket();
			connOut.connect(getNodeAddress(dest));
			sendMessage(connOut, new InitConnMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connOut;
	}

}
