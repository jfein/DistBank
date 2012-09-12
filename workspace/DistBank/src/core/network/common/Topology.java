package core.network.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import core.node.NodeId;

public class Topology {

	private static final String TOPOLOGY_FILE = "topology_file.txt";
	private static final String NODE_MAPPING_FILE = "node_mapping.txt";

	private static final Set<NodeId> channelsOut = new HashSet<NodeId>();
	private static final Set<NodeId> channelsIn = new HashSet<NodeId>();
	private static final Map<NodeId, InetSocketAddress> nodeToAddress = new HashMap<NodeId, InetSocketAddress>();

	public static boolean canSendTo(NodeId other) {
		return channelsOut.contains(other);
	}

	public static boolean canReceiveFrom(NodeId other) {
		return channelsIn.contains(other);
	}

	public static InetSocketAddress getAddress(NodeId nodeId) {
		System.out.println("Node id in getAddress: " + nodeId);
		return nodeToAddress.get(nodeId);
	}

	public static void setTopology(NodeId myId) {
		try {
			BufferedReader read1 = new BufferedReader(new FileReader(
					NODE_MAPPING_FILE));

			while (read1.ready()) {
				String t = read1.readLine();
				String parts[] = t.split(" ");

				NodeId id = new NodeId(parts[0]);
				InetSocketAddress addr = stringToSocketAddress(parts[1]);
				System.out.println("Adding node id: " + id.getNodeId() + " :: " + addr.getHostName() + ":" + addr.getPort());
				nodeToAddress.put(id, addr);
			}

			BufferedReader read2 = new BufferedReader(new FileReader(
					TOPOLOGY_FILE));

			while (read2.ready()) {
				String t = read2.readLine();
				String parts[] = t.split(" ");

				NodeId src = new NodeId(parts[0]);
				NodeId dest = new NodeId(parts[1]);

				if (src.equals(myId)) {
					System.out.println("I can send messages to " + dest);
					channelsOut.add(dest);
				}

				if (dest.equals(myId)) {
					System.out.println("I can get messages from " + src);
					channelsIn.add(src);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static InetSocketAddress stringToSocketAddress(String s) {
		String parts[] = s.split(":");
		return new InetSocketAddress(parts[0], Integer.parseInt(parts[1]));
	}
}
