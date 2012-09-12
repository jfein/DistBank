package core.network.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import core.distsys.Topology;
import core.distsys.NodeId;

public class NetworkInterface {

	public static <T extends Message> T getMessage(Socket conn)
			throws IOException, ClassNotFoundException {
		InputStream connIn = conn.getInputStream();
		ObjectInputStream in = new ObjectInputStream(connIn);
		return (T) in.readObject();
	}

	public static <T extends Message> void sendMessage(Socket conn,
			NodeId dest, T msg) throws Exception {
		if (!Topology.canSendTo(dest))
			throw new Exception("Can't send to " + dest);
		OutputStream conOut = conn.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(conOut);
		out.writeObject(msg);
		out.flush();
	}

	public static <T extends Message> Socket sendMessage(NodeId dest, T msg)
			throws Exception {
		if (!Topology.canSendTo(dest))
			throw new Exception("Can't send to " + dest);
		Socket conn = new Socket();
		conn.connect(Topology.getAddress(dest));
		sendMessage(conn, dest, msg);
		return conn;
	}

}
