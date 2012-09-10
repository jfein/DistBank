package core.distsys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;
import java.util.HashSet;

public class Topology {

	private static final Set<SocketAddress> channelsOut = new HashSet<SocketAddress>();
	private static final Set<SocketAddress> channelsIn = new HashSet<SocketAddress>();

	private static final String TOPOLOGY_FILE = "topology.txt";

	public static boolean canSendTo(SocketAddress addr) {
		return channelsOut.contains(addr);
	}

	public static boolean canReceiveFrom(SocketAddress addr) {
		return channelsIn.contains(addr);
	}

	public static void setTopology(SocketAddress myAddress) {
		try {
			BufferedReader read = new BufferedReader(new FileReader(
					TOPOLOGY_FILE));

			while (read.ready()) {
				String t = read.readLine();
				String parts[] = t.split(" ");

				SocketAddress src = stringToSocketAddress(parts[0]);
				SocketAddress dest = stringToSocketAddress(parts[1]);

				if (src.equals(myAddress)) {
					System.out.println("I can send messages to " + dest);
					channelsOut.add(dest);
				}

				if (dest.equals(myAddress)) {
					System.out.println("I can get messages from " + src);
					channelsIn.add(src);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static SocketAddress stringToSocketAddress(String s) {
		String parts[] = s.split(":");
		return new InetSocketAddress(parts[0], Integer.parseInt(parts[1]));
	}
}
