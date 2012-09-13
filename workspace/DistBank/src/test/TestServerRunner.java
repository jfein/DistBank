package test;

import java.io.IOException;

import core.node.NodeId;
import core.node.ServerNodeRuntime;

public class TestServerRunner {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		NodeId id = new NodeId(Integer.parseInt(args[0]));

		ServerNodeRuntime.init(id, new TestState(), new TestServerHandler());

		if (id.toString().equals("g0")) {
			NodeId dest = new NodeId(0);
			System.out.println("A");
			TestClient.setX(dest, 42);
			System.out.println("B");
			System.out.println("Other X: " + TestClient.getX(dest));
		}
	}
}
