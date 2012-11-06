package core.app;

import java.io.Serializable;

public class AppId implements Serializable {

	private static final long serialVersionUID = -4743307013369208857L;

	private Integer id;

	public AppId(Integer appId) {
		this.id = appId;
	}

	public Integer getAppId() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof AppId)
			return id.hashCode() == ((AppId) other).hashCode();
		return false;
	}

	@Override
	public String toString() {
		return id.toString();
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
