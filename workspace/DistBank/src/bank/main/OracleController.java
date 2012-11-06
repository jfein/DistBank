package bank.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bank.gui.OracleView;

public class OracleController implements Runnable {

	private static OracleController oracleController = null;
	
	private OracleView oracleView;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		oracleView = new OracleView();
		
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
