package presentation;

import javax.swing.JFrame;

import presentation.earth.OrbitPanel;

public class OrbitUI extends JFrame {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6790468113314665609L;

	private OrbitPanel orbitPanel;
	
	// TODO: Remove and get it from arguments...
	private static final double E = 0.0167;
	
	public OrbitUI() {
		setTitle("Earth's Orbit");
		setSize(300,300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		orbitPanel = new OrbitPanel();
		
		//orbitPanel.setOrbit(SimulationUtils.A, E);
		
		add(orbitPanel);
	}
	
	public void updatePosition(double[] coordinates) {
		orbitPanel.updatePlanet(coordinates);
	}
}
