package oracle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class OracleController implements Runnable {

	private static OracleController oracleController = null;
	
	private OracleView oracleView;
	private OracleState oracleState;

	private String recoverCommand = "/Users/verakutsenko/Documents/DistBank/workspace/DistBank/LAUNCH_MAC.shell ";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		oracleView = new OracleView();
		oracleState = new OracleState();
		oracleView.addFailListener(new FailListener());
		oracleView.addNotifyFailListener(new NotifyFailListener());
		oracleView.addRecoverListener(new RecoverListener());
		oracleView.addNotifyRecoverListener(new NotifyRecoverListener());
		
		oracleView.setVisible(true);
		OracleController.oracleController = this;
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
