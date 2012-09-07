package network.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Response implements Serializable {

	private static final long serialVersionUID = 6563624096030696231L;

	public Integer status;
	public final Map<String, Object> fields = new HashMap<String, Object>();

	public Response(int status) {
		this.status = status;
	}

}
