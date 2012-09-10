package network.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Request implements Serializable {

	private static final long serialVersionUID = 405645146131844035L;

	public String function;
	public final Map<String, Object> fields = new HashMap<String, Object>();

	public Request(String function) {
		this.function = function;
	}

}
