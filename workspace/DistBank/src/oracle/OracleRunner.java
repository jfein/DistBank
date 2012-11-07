package oracle;

import core.node.NodeId;
import core.node.NodeRuntime;

public class OracleRunner {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Parameter Error: Enter 1 arg for branch name.");
			System.exit(-1);
		}

		NodeId id = new NodeId(Integer.parseInt(args[0]));

		System.out.println("Oracle Node " + id + " running.");

		(new NodeRuntime(id, OracleApp.class)).run();
	}

}
