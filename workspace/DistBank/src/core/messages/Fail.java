package core.messages;

import core.node.NodeRuntime;

/**
 * Request to tell a server to fail. This is the only request that does not
 * expect a response, since the node will be failed and won't be able to send a
 * response.
 */
public class Fail extends Request {

	private static final long serialVersionUID = -5720738233390812581L;

	public Fail() {
		super(NodeRuntime.oracleAppId, null);
	}
}
