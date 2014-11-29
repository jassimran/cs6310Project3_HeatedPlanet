package presentation.earth;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

/**
 * Component that provides a visual representation of the orbit and the
 * transitional movement of the planet.
 */
public class OrbitPanel extends JComponent {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1580403163692972584L;

	/**
	 * Since we have an horizontal ellipse, this represents the percent of the
	 * overall panel width that will be used to display the orbit.
	 */
	private static final double WIDTH_RATIO = 0.9;

	/**
	 * Earth radius.
	 */
	private static final int EARTH_RADIUS = 8;

	/**
	 * Sun is almost 109.1 times the Earth Radius. For proper presentation we
	 * are assigning a specific value.
	 * 
	 * EARTH_RADIUS * 109.1
	 */
	private static final int SUN_RADIUS = 30;

	/**
	 * Dashing pattern.
	 */
	private final float[] dashes = { 5.0f };

	/**
	 * Orbit stroke.
	 */
	private final BasicStroke orbitStroke = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, dashes, 0.0f);

	/**
	 * Represents half of the ellipse's width or major axis.
	 */
	private double a;

	/**
	 * Represents half of the ellipse's height or minor axis.
	 */
	private double b;

	/**
	 * Represents the distance from the foci to the center.
	 */
	private double c;

	/**
	 * Represents the eccentricity of the ellipse.
	 */
	private double e;

	/**
	 * To make the ellipse true to natural dimensions we need a ratio to
	 * represent a scaled version of the real world.
	 */
	private double ratio;

	/**
	 * Orbit's width.
	 */
	private double orbitWidth;

	/**
	 * Orbit's height.
	 */
	private double orbitHeight;

	/**
	 * Flag that indicates when the dimension has been manually set.
	 */
	private boolean isDimensionSet;

	/**
	 * Earth's or planet's original x coordinate.
	 */
	private int earthX = -1;

	/**
	 * Earth's or planet's original y coordinate.
	 */
	private int earthY = -1;

	/**
	 * Sets the orbits physical characteristics.
	 * 
	 * @param semiMajorAxis
	 *            Half value of the major axis (semi major axis).
	 * @param eccentricity
	 *            Eccentricity of the orbit.
	 */
	public void setOrbit(int semiMajorAxis, double eccentricity) {
		a = semiMajorAxis;
		e = eccentricity;

		// c = e * a
		c = e * a;

		// b = sqrt(a^2 - c^2) | b = sqrt(a^2 - e^2 - a)
		b = Math.sqrt((a * a) - (c * c));

		if (!isDimensionSet) {
			orbitWidth = getWidth() == 0 ? 300 * WIDTH_RATIO : getWidth()
					* WIDTH_RATIO;
		}

		ratio = orbitWidth / (2 * a);

		orbitHeight = 2 * b * ratio;
	}

	@Override
	/**
	 * @inherit
	 */
	public void setSize(Dimension d) {
		super.setSize(d);

		orbitWidth = d.width * WIDTH_RATIO;

		isDimensionSet = true;
	}

	@Override
	/**
	 * @inherit
	 */
	public void setSize(int width, int height) {
		super.setSize(width, height);

		orbitWidth = width * WIDTH_RATIO;

		isDimensionSet = true;
	}

	/**
	 * Updates the planet position based on the provided coordinates, and
	 * adjusts the value to be represented in the scaled model.
	 * 
	 * @param x
	 *            Planet's X coordinate
	 * @param y
	 *            Planet's Y coordinate
	 */
	public void updatePlanet(double x, double y) {
		earthX = (int) Math.round(getWidth() * 0.5 - (ratio * x));
		earthY = (int) Math.round(getHeight() * 0.5 + (ratio * y));

		repaint();
	}

	/**
	 * Updates the planet position based on the provided coordinates, and
	 * adjusts the value to be represented in the scaled model.
	 * 
	 * @param coordinates
	 *            A double array containing two elements. First elements
	 *            corresponds to the x coordinate and second element corresponds
	 *            to the y coordinate.
	 */
	public void updatePlanet(double[] coordinates) {
		updatePlanet(coordinates[0], coordinates[1]);
	}

	@Override
	/**
	 * @inherit
	 */
	protected void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;

		graphics2D.setColor(Color.BLACK);
		graphics2D.fillRect(0, 0, getWidth(), getHeight());

		double orbitCenterX = (getWidth() - orbitWidth) * 0.5;
		double orbitCenterY = (getHeight() - orbitHeight) * 0.5;

		if (earthX == -1) {
			earthX = (int) (getWidth() * 0.5 - a * ratio);
			earthY = (int) (getHeight() * 0.5);
		}

		Ellipse2D.Double ellipse = new Ellipse2D.Double(orbitCenterX,
				orbitCenterY, orbitWidth, orbitHeight);

		graphics2D.setStroke(orbitStroke);
		graphics2D.setColor(Color.WHITE);
		graphics2D.draw(ellipse);

		int sunCenterX = (int) (getWidth() * 0.5 - (ratio * c));
		int sunCenterY = (int) (getHeight() * 0.5);

		drawSun(graphics2D, sunCenterX, sunCenterY, SUN_RADIUS);

		drawEarth(graphics2D, earthX, earthY, EARTH_RADIUS);
	}

	/**
	 * Draws the sun.
	 * 
	 * @param graphics
	 *            Graphics reference
	 * @param x
	 *            Sun's X coordinate
	 * @param y
	 *            Sun's Y coordinate
	 * @param radius
	 *            Sun radius
	 */
	protected void drawSun(Graphics graphics, int x, int y, int radius) {
		graphics.setColor(Color.ORANGE);
		graphics.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}

	/**
	 * Draws the earth or planet.
	 * 
	 * @param graphics
	 *            Graphics reference
	 * @param x
	 *            Earth's or planet's X coordinate
	 * @param y
	 *            Earth's or planet's Y coordinate
	 * @param radius
	 *            Earth's or planet's radius
	 */
	protected void drawEarth(Graphics graphics, int x, int y, int radius) {
		graphics.setColor(Color.BLUE);
		graphics.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}
}
