package oracle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import core.app.AppId;
import core.app.AppState;
import core.node.ConfiguratorClient;

public class OracleGuiController extends AppState implements Runnable {

	private static final long serialVersionUID = -1179691764220594147L;
	private OracleGuiView oracleView;
	private OracleState oracleState;

	private String recoverCommand = "/Users/verakutsenko/Documents/DistBank/workspace/DistBank/LAUNCH_MAC.shell ";
	private AppId myAppId;

	public OracleGuiController(AppId appId) {
		this.myAppId = appId;
		this.oracleView = new OracleGuiView();
		this.oracleState = new OracleState();

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
			ConfiguratorClient.fail(oracleView.getNodeId());
		}
	}

	class NotifyFailListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			oracleState.registerNodeFailure(oracleView.getNodeId());
		}
	}

	class RecoverListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// recover the node, send command line to start up the node
			Runtime rt = Runtime.getRuntime();
			try {
				Process pr = rt.exec("/Users/verakutsenko/Documents/DistBank/workspace/DistBank/LAUNCH_MAC.shell");
				int exitVal = pr.waitFor();
				System.out.println("Exited with error code " + exitVal);

			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	class NotifyRecoverListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			oracleState.registerNodeRecovery(oracleView.getNodeId());
		}
	}

}
