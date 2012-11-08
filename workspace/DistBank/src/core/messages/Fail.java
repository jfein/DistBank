package core.messages;

import core.node.NodeRuntime;

public class Fail extends Request {

	private static final long serialVersionUID = -5720738233390812581L;

	public Fail() {
		super(NodeRuntime.oracleAppId, null);
	}
}
