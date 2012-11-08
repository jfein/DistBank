package core.app;

import java.io.Serializable;

/**
 * Represents a unique ID in the global space of all app IDs. This means that if
 * multiple types apps are running in the system, no two can have the same app
 * ID value. Typed to be for a specific app class.
 * 
 * @author JFein
 * 
 * @param <A>
 */
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
