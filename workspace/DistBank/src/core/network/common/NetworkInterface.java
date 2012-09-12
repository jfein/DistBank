package core.network.common;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import core.node.NodeId;

public class NetworkInterface {

	public static synchronized <T extends Message> T getMessage(Socket conn)
			throws Exception {
		InputStream connIn = conn.getInputStream();
		ObjectInputStream in = new ObjectInputStream(connIn);
		return (T) in.readObject();
	}

	public static synchronized <T extends Message> void sendMessage(
			Socket conn, NodeId dest, T msg) throws Exception {
		if (!Topology.canSendTo(dest))
			throw new Exception("Error: This node cannot send to " + dest);
		OutputStream conOut = conn.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(conOut);
		out.writeObject(msg);
		out.flush();
	}

	public static synchronized <T extends Message> Socket sendMessageNewSocket(
			NodeId dest, T msg) throws Exception {
		if (!Topology.canSendTo(dest))
			throw new Exception("Error: This node cannot send to " + dest);
		Socket conn = new Socket();
		conn.connect(Topology.getAddress(dest));
		sendMessage(conn, dest, msg);
		return conn;
	}

}
