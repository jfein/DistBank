package bank.branch;

import core.app.App;
import core.app.AppId;
import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

public class BranchApp extends App<BranchState> {

	public BranchApp(AppId appId) {
		super(appId);
	}

	@Override
	protected BranchState newState() {
		return new BranchState();
	}

	public BranchResponse handleRequest(QueryRequest req) {
		BranchState state = getState();
		boolean success = state.query(req.getSrcAccountId(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handleRequest(WithdrawRequest req) {
		BranchState state = getState();
		boolean success = state.withdraw(req.getSrcAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handleRequest(DepositRequest req) {
		BranchState state = getState();
		boolean success = state.deposit(req.getSrcAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

	public BranchResponse handleRequest(TransferRequest req) {
		BranchState state = getState();
		boolean success = state.transfer(req.getSrcAccountId(), req.getDestAccountId(), req.getAmount(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(balance, success);
	}

}
