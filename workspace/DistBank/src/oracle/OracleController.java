package oracle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OracleController implements Runnable {

	private static OracleController oracleController = null;
	
	private OracleView oracleView;
	private OracleState oracleState;
	
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
			// TODO Auto-generated method stub
			//Get input from oracle about node id to fail
			//Generate a fail request
			//set fail request to that node
			//
			
		}
		
	}
	class NotifyFailListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class RecoverListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			}
		}
	class NotifyRecoverListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
