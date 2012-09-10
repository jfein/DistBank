package core.network.common;

import java.io.Serializable;

public interface Message extends Serializable {
	
	public Integer getSrcAccountId();
	public Integer getSerialNumber();
}
