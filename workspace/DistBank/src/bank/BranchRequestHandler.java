package bank;

import core.network.MessageHandler;
import core.node.NodeRuntime;
import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchRequestHandler extends MessageHandler {

	public BranchResponse handleRequest(QueryRequest req) {
		BranchState state = NodeRuntime.getState();
		boolean success = state.query(req.getSrcAccountId(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handleRequest(WithdrawRequest req) {
		BranchState state = NodeRuntime.getState();
		boolean success = state.withdraw(req.getSrcAccountId(),
				req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handleRequest(DepositRequest req) {
		BranchState state = NodeRuntime.getState();
		boolean success = state.deposit(req.getSrcAccountId(), req.getAmount(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handleRequest(TransferRequest req) {
		BranchState state = NodeRuntime.getState();
		boolean success = state.transfer(req.getSrcAccountId(),
				req.getDestAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

}
