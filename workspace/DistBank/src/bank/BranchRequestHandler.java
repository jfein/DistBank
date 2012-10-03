package bank;

import core.network.RequestHandler;
import core.node.NodeRuntime;
import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchRequestHandler extends RequestHandler {

	public BranchResponse handle(QueryRequest req) {
		BranchState state = NodeRuntime.getNodeState();
		boolean success = state.query(req.getSrcAccountId(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handle(WithdrawRequest req) {
		BranchState state = NodeRuntime.getNodeState();
		boolean success = state.withdraw(req.getSrcAccountId(),
				req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handle(DepositRequest req) {
		BranchState state = NodeRuntime.getNodeState();
		boolean success = state.deposit(req.getSrcAccountId(), req.getAmount(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handle(TransferRequest req) {
		BranchState state = NodeRuntime.getNodeState();
		boolean success = state.transfer(req.getSrcAccountId(),
				req.getDestAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

}
