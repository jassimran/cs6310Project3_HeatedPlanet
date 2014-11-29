package presentation.earth;

/**
 * Implement this interface to allow for the retrieval of information pertaining
 * to a grid of temperatures. The top left corner of the grid is considered to
 * have the coordinates (0, 0); the bottom right corner will have coordinates
 * (x, y) in an <code>x by y</code> grid with x columns and y rows.
 * 
 * Based on code by Andrew Bernard, with several modifications.
 */
public interface TemperatureGrid {

	/**
	 * Gets the temperature in the cell specified by the x and y coordinates.
	 */
	double getTemperature(int x, int y);

	/**
	 * Gets the height of the cell at the specified coordinates.
	 */
	float getCellHeight(int x, int y);

	/**
	 * Gets the total number of rows in the grid.
	 */
	int getRows();

	/**
	 * Gets the total number of columns in the grid.
	 */
	int getCols();

	/**
	 * The time of the simulation represented by grid (in minutes).
	 */
	int getSimulationTime();

	/**
	 * Returns the latitude directly under the sun.
	 * 
	 * @return latitude under the sun.
	 */
	double getLatitudeUnderSun();

	/**
	 * Returns the longitude directly under the sun.
	 * 
	 * @return longitude under the sun.
	 */
	double getLongitudeUnderSun();

	/**
	 * Returns the distance between the planet and the sun.
	 * 
	 * @return distance from sun
	 */
	double getDistanceFromSun();

	/**
	 * Returns the planet's coordinates
	 * 
	 * @return planet's coordinates
	 */
	double[] getCoordinates();

}
