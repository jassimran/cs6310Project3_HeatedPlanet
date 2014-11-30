package presentation;

import java.awt.Dimension;

import javax.swing.JFrame;

import presentation.earth.OrbitPanel;

public class OrbitUI extends JFrame {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6790468113314665609L;

	/**
	 * Preferred width
	 */
	private static final int PREFERRED_WIDTH = 300;

	/**
	 * Preferred height
	 */
	private static final int PREFERRED_HEIGHT = 300;

	/**
	 * Singleton instance
	 */
	private static OrbitUI instance;

	/**
	 * Orbit panel
	 */
	private OrbitPanel orbitPanel;

	/**
	 * Orbit's Semi Major Axis (a)
	 */
	private int semiMajorAxis;

	/**
	 * Orbit's eccentricity
	 */
	private double eccentricity;

	private OrbitUI(int semiMajorAxis, double eccentricity) {
		this.semiMajorAxis = semiMajorAxis;
		this.eccentricity = eccentricity;

		setTitle("Earth's Orbit");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		createGui();
	}

	/**
	 * Updates Earth's or planet's position with a coordinates array. Position 0
	 * contains X coordinate, while position 1 contains the Y coordinate.
	 * 
	 * @param coordinates
	 *            Array with 2 values, first value for X coordinate, second
	 *            value for Y coordinate.
	 */
	public void updatePosition(double[] coordinates) {
		orbitPanel.updatePlanet(coordinates);
	}

	/**
	 * Returns the singleton instance.
	 * 
	 * @param semiMajorAxis
	 *            Orbit's semi major axis.
	 * @param eccentricity
	 *            Orbit's eccentricity.
	 * @return OrbitUI singleton instance.
	 */
	public static OrbitUI getInstance(int semiMajorAxis, double eccentricity) {
		if (instance == null) {
			instance = new OrbitUI(semiMajorAxis, eccentricity);
		} else {
			instance.semiMajorAxis = semiMajorAxis;
			instance.eccentricity = eccentricity;

			instance.createGui();
		}

		instance.setVisible(true);

		return instance;
	}

	public static void destroy() {
		if (instance != null) {
			if (instance.orbitPanel != null) {
				instance.remove(instance.orbitPanel);
			}

			instance.orbitPanel = null;

			instance = null;
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		
	}

	private void createGui() {
		Dimension dimension = new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);

		getContentPane().setPreferredSize(dimension);

		setSize(dimension);
		setResizable(false);

		if (orbitPanel == null) {
			orbitPanel = new OrbitPanel();
		}

		orbitPanel.setOrbit(semiMajorAxis, eccentricity);

		add(orbitPanel);
	}
}
