package simplesimulation;

import presentation.earth.EarthPanel;
import presentation.earth.TemperatureGrid;

public class SimpleTemperatureGridImpl implements TemperatureGrid {

	private SimpleCell grid [][];
	
	private EarthPanel earthPanel;
	
	private int simulationTime;
	
	protected SimpleTemperatureGridImpl(EarthPanel earthPanel) {
		this.earthPanel = earthPanel;
		grid = new SimpleCell[earthPanel.getNumCellsY()][earthPanel.getNumCellsX()];
	}
	
	/**
	 * Sets temperature values to 288' Kelvin
	 */
	protected void initGrid() {
		int rows = earthPanel.getNumCellsY();
		int cols = earthPanel.getNumCellsX();
		
		for(int y=0; y<rows; y++) {
			for(int x=0; x<cols; x++) {
				SimpleCell simpleCell = new SimpleCell();
				simpleCell.t = 288.0;
				grid[y][x] = simpleCell;
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param simpleCell
	 */
	protected void setTemperature(int x, int y, SimpleCell simpleCell) {
		grid[y][x] = simpleCell;
	}
	
	@Override
	public double getTemperature(int x, int y) {
		return grid[y][x].getTemperature();
	}

	@Override
	public float getCellHeight(int x, int y) {
		int rows = earthPanel.getNumCellsY();
		//int cols = earthPanel.getNumCellsX();
		int gs = earthPanel.getDegreeSeparation();
		int i = rows - (y + 1); 
		//int j = cols - (x + 1);	
		float latTop = (i-(rows/2))*gs;
		float latBot = latTop + (float) gs;
		float height = (float) (Math.sin(Math.toRadians(latTop)) - Math
				.sin(Math.toRadians(latBot))) / 2;
		height = (float) Math.abs(height);
		return height;
		
		//return SimpleCell.getCellHeight(i, j, rows, cols, gs);
	}
	
	/**
	 * 
	 * @param simulationTime
	 */
	protected void setSimulationTime(int simulationTime) {
		this.simulationTime= simulationTime;
	}

	@Override
	public int getSimulationTime() {
		return simulationTime;
	}

	@Override
	public int getRows() {
		return earthPanel.getNumCellsY();
	}

	@Override
	public int getCols() {
		return earthPanel.getNumCellsX();
	}
}
