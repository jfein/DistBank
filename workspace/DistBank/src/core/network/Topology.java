package core.network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import core.node.NodeId;
import core.node.NodeRuntime;

public class Topology {

	private final Set<NodeId> channelsOut = new HashSet<NodeId>();
	private final Set<NodeId> channelsIn = new HashSet<NodeId>();

	private final Map<NodeId, InetSocketAddress> nodeToServerAddress = new HashMap<NodeId, InetSocketAddress>();

	public Topology(String nodeMappingFile, String topologyFile) {
		NodeId myId = NodeRuntime.getId();

		try {
			// Parse node_mapping.txt
			BufferedReader read1 = new BufferedReader(new FileReader(
					nodeMappingFile));

			while (read1.ready()) {
				String t = read1.readLine();
				String parts[] = t.split(" ");

				NodeId id = new NodeId(Integer.parseInt(parts[0]));
				InetSocketAddress addr = stringToSocketAddress(parts[1]);
				nodeToServerAddress.put(id, addr);
			}

			// Parse topology_file.txt
			BufferedReader read2 = new BufferedReader(new FileReader(
					topologyFile));

			while (read2.ready()) {
				String t = read2.readLine();
				String parts[] = t.split(" ");

				NodeId src = new NodeId(Integer.parseInt(parts[0]));
				NodeId dest = new NodeId(Integer.parseInt(parts[1]));

				// TODO: make bidirectional
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

	public Set<NodeId> getChannelsOut() {
		return channelsOut;
	}

	public Set<NodeId> getChannelsIn() {
		return channelsIn;
	}

	public Map<NodeId, InetSocketAddress> getNodeToServerAddress() {
		return nodeToServerAddress;
	}

	private static InetSocketAddress stringToSocketAddress(String s) {
		String parts[] = s.split(":");
		return new InetSocketAddress(parts[0], Integer.parseInt(parts[1]));
	}
}
