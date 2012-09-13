package bank;

import core.network.server.ServerHandler;
import core.node.ServerNodeRuntime;
import bank.messages.BankResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BankServerHandler extends ServerHandler {

	public BankResponse handle(QueryRequest req) {
		BankState state = ServerNodeRuntime.getNodeState();
		boolean success = state.query(req.getSrcAccountId(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BankResponse(balance, success);
	}

	public BankResponse handle(WithdrawRequest req) {
		BankState state = ServerNodeRuntime.getNodeState();
		boolean success = state.withdraw(req.getSrcAccountId(),
				req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BankResponse(balance, success);
	}

	public BankResponse handle(DepositRequest req) {
		BankState state = ServerNodeRuntime.getNodeState();
		boolean success = state.deposit(req.getSrcAccountId(), req.getAmount(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BankResponse(balance, success);
	}

	public BankResponse handle(TransferRequest req) {
		BankState state = ServerNodeRuntime.getNodeState();
		boolean success = state.transfer(req.getSrcAccountId(),
				req.getDestAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BankResponse(balance, success);
	}

}
