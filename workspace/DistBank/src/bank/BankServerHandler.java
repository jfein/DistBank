package bank;

import core.network.server.ServerHandler;
import core.network.server.ServerNodeRuntime;
import bank.messages.BankResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BankServerHandler extends ServerHandler {

	public BankResponse handle(QueryRequest req) {
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		return new BankResponse(state.query(req.getSrcAccountId(), req.getSerialNumber()));
	}
	
	public BankResponse handle(WithdrawRequest req) {
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		return new BankResponse(state.withdraw(req.getSrcAccountId(), req.getAmount(), req.getSerialNumber()));
	}
	
	public BankResponse handle(DepositRequest req) {
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		System.out.println(" Got state. Call deposit.");
		return new BankResponse(state.deposit(req.getSrcAccountId(), req.getAmount(), req.getSerialNumber()));
	}
	
	public BankResponse handle(TransferRequest req) {
		System.out.println(" Handling Transfer request: " + req.getSerialNumber());
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		return new BankResponse(state.transfer(req.getSrcAccountId(), req.getDestAccountId(), req.getAmount(), req.getSerialNumber()));
	}

/*	public BankResponse handle(ChangeRequest req) {
		BankState state = ServerNodeRuntime.getNodeRuntimeState();
		state.setAmt(req.getNewAmt());
		return new BankResponse(state.getAmt());
	}*/

}
