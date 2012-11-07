package core.app;

import java.io.Serializable;

public class AppId<A extends App<?>> implements Serializable {

	private static final long serialVersionUID = -4743307013369208857L;

	private Integer id;
	private Class<A> appClass;

	public AppId(Integer appId, Class<A> appClass) {
		this.id = appId;
		this.appClass = appClass;
	}

	public Integer getAppId() {
		return id;
	}

	public Class<A> getAppClass() {
		return appClass;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof AppId)
			return id.hashCode() == ((AppId<?>) other).hashCode();
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
