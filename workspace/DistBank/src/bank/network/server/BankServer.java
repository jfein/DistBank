package bank.network.server;

import bank.distsys.BankState;
import bank.network.messages.BankResponse;
import bank.network.messages.ChangeRequest;
import bank.network.messages.QueryRequest;
import abstraction.distsys.Runtime;
import abstraction.network.server.Server;

public class BankServer extends Server {

	public static BankResponse serve(QueryRequest req) {
		BankState state = (BankState) Runtime.getRuntime().getState();
		return new BankResponse(state.getAmt());
	}

	public static BankResponse serve(ChangeRequest req) {
		BankState state = (BankState) Runtime.getRuntime().getState();
		state.setAmt(req.getNewAmt());
		return new BankResponse(state.getAmt());
	}

}
