package bank.branch;

import core.app.App;
import core.app.AppId;
import bank.messages.BranchResponse;
import bank.messages.DepositRequest;
import bank.messages.QueryRequest;
import bank.messages.TransferRequest;
import bank.messages.WithdrawRequest;

/**
 * This is the main app that runs a single branch on a node. Holds the branch's
 * BranchState and AppId. Contains request handlers to handle requests made by a
 * separate app using a BranchClient. This file contains overloaded handle
 * methods that differ in the arguments they take which are the request objects
 * generated in BranchClient.java(WithdrawRequest, DepositRequest, QueryRequest,
 * TransferRequest). Each handle method handles the distinct Request objects
 * appropriately. For example, the handle for a WithdrawRequest, calls the
 * BranchState's withdraw method and waits for the BranchState's function to
 * return success or failure, then the handle method returns a BranchResponse
 * object with the balance of this account and whether it was successful.
 */
public class BranchApp extends App<BranchState> {

	public BranchApp(AppId<BranchApp> appId) {
		super(appId);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected BranchState newState() {
		return new BranchState((AppId<BranchApp>) getAppId());
	}

	public BranchResponse handleRequest(QueryRequest req) {
		BranchState state = getState();
		boolean success = state.query(req.getSrcAccountId(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(req.getSenderAppId(), balance, success);
	}

	public BranchResponse handleRequest(WithdrawRequest req) {
		BranchState state = getState();
		boolean success = state.withdraw(req.getSrcAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(req.getSenderAppId(), balance, success);
	}

	public BranchResponse handleRequest(DepositRequest req) {
		BranchState state = getState();
		boolean success = state.deposit(req.getSrcAccountId(), req.getAmount(), req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(req.getSenderAppId(), balance, success);
	}

	public BranchResponse handleRequest(TransferRequest req) {
		BranchState state = getState();
		boolean success = state.transfer(req.getSrcAccountId(), req.getDestAccountId(), req.getAmount(),
				req.getSerialNumber());
		double balance = state.getBalance(req.getSrcAccountId());
		return new BranchResponse(req.getSenderAppId(), balance, success);
	}

}
