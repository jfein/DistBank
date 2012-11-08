package oracle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import core.app.AppState;
import core.node.ConfiguratorClient;
import core.node.NodeId;

public class OracleGuiController extends AppState {

	private static final long serialVersionUID = -1179691764220594147L;
	private OracleGuiView oracleView;
	private OracleState oracleState;

	public OracleGuiController() {
		this.oracleView = new OracleGuiView();
		this.oracleState = new OracleState();

		oracleView.addFailListener(new FailListener());
		oracleView.addNotifyFailListener(new NotifyFailListener());
		oracleView.addRecoverListener(new RecoverListener());
		oracleView.addNotifyRecoverListener(new NotifyRecoverListener());

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
			NodeId node = oracleView.getNodeId();

			try {
				Runtime.getRuntime().exec("cmd /c start LAUNCH_ONE.cmd " + node);
			} catch (IOException e1) {
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
