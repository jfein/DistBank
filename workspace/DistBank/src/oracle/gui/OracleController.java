package oracle.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import core.app.AppId;
import core.app.AppState;

import oracle.OracleState;


public class OracleController extends AppState implements Runnable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1179691764220594147L;
	private OracleView oracleView;
	private OracleState oracleState;

	private String recoverCommand = "/Users/verakutsenko/Documents/DistBank/workspace/DistBank/LAUNCH_MAC.shell ";
	private AppId myAppId;
	
	public OracleController(AppId appId) {
		this.myAppId = appId;
		this.oracleView = new OracleView();

		oracleView.addFailListener(new FailListener());
		oracleView.addNotifyFailListener(new NotifyFailListener());
		oracleView.addRecoverListener(new RecoverListener());
		oracleView.addNotifyRecoverListener(new NotifyRecoverListener());
	}
	@Override
	public void run() {
		oracleView.setVisible(true);
	}
	
	public OracleState getOracleState() {
		return this.oracleState;
	}

	class FailListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Get input from oracle about node id to fail
			//Generate a fail request
			//send a DIE request to that nodeId
			
		
			
		}
		
	}
	class NotifyFailListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//Get input from oracle about node if to notify about
			//Send a failure notification to Node Id, to make it remove the node off it's mapping from app to nodes
			//change the state of the oracle and remove into a "fail list" (if we want to implement that)
		}
		
	}
	class RecoverListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//recover the node, send command line to start up the node
			Runtime rt = Runtime.getRuntime();
			try {
		    	Process pr = rt.exec("/Users/verakutsenko/Documents/DistBank/workspace/DistBank/LAUNCH_MAC.shell");
		        int exitVal = pr.waitFor();
		        System.out.println("Exited with error code "+exitVal);
		        
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	class NotifyRecoverListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//send a message to NODE ID, to tell it to add the nodeid to those who
			//were previously interested
			
		}
		
	}
	
}
