package presentation.earth;

/**
 * Implement this interface to allow for the retrieval of information 
 * pertaining to a grid of temperatures. The top left corner of the grid is 
 * considered to have the coordinates (0, 0); the bottom right corner will have
 * coordinates (x, y) in an <code>x by y</code> grid with x columns and y rows.
 * 
 * @author Andrew Bernard
 */
public interface TemperatureGrid {
  
  /**
   * Gets the temperature in the cell specified by the x and y coordinates.
   */
  public double getTemperature(int x, int y);
  
  /**
   * Gets the height of the cell at the specified coordinates.
   */
  public float getCellHeight(int x, int y);
  
  /**
   * Gets the total number of rows in the grid.
   */
  public int getRows();
  
  /**
   * Gets the total number of columns in the grid.
   */
  public int getCols();
  
  /**
   * The time of the simulation represented by grid (in minutes).
   */
  public int getSimulationTime();
  
  public double getLatitudeUnderSun();
  
  public double getLongitudeUnderSun();
  
  public double getDistanceFromSun();
  
}
