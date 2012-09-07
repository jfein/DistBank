package network.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Topology {

	public static final String TOPOLOGY_FILE = "topology.txt";

	public static List<Set<SocketAddress>> getMyChannels(SocketAddress myAddress) {
		Set<SocketAddress> channelsOut = new HashSet<SocketAddress>();
		Set<SocketAddress> channelsIn = new HashSet<SocketAddress>();

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

		List<Set<SocketAddress>> channels = new ArrayList<Set<SocketAddress>>();
		channels.add(channelsOut);
		channels.add(channelsIn);

		return channels;
	}

	public static SocketAddress stringToSocketAddress(String s) {
		String parts[] = s.split(":");
		return new InetSocketAddress(parts[0], Integer.parseInt(parts[1]));
	}
}
