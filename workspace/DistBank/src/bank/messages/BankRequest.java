package bank.messages;

import java.io.Serializable;

public interface BankRequest extends Serializable {
	public Integer getSrcAccountId();
	public Integer getSerialNumber();
}
