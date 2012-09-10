package bank;

import core.network.server.ServerHandler;
import core.network.server.ServerNodeRuntime;
import bank.messages.BankResponse;
import bank.messages.ChangeRequest;
import bank.messages.QueryRequest;

public class BankServerHandler extends ServerHandler {

	public BankResponse handle(QueryRequest req) {
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		return new BankResponse(state.query(req.getSrcAccountId(), req.getSerialNumber()));
	}

	public BankResponse handle(ChangeRequest req) {
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		state.setAmt(req.getNewAmt());
		return new BankResponse(state.getAmt());
	}

}
