package simplesimulation;

import presentation.earth.TemperatureGrid;

public class SimpleTemperatureGridImpl implements TemperatureGrid {

	private SimpleCell grid [][];
	
	private int simulationTime;
	
	
	// earth panel information
	private int numCellsY;
	private int numCellsX;
	private int degreeSeparation;
	
	protected SimpleTemperatureGridImpl(int numCellsY, int numCellsX, int degreeSeparation) {
		this.numCellsY = numCellsY;
		this.numCellsX = numCellsX;
		this.degreeSeparation = degreeSeparation;
		
		grid = new SimpleCell[numCellsY][numCellsX];
	}
	
	/**
	 * Sets temperature values to 288' Kelvin
	 */
	protected void initGrid() {
		int rows = numCellsY;
		int cols = numCellsX;
		
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
		int rows = numCellsY;
		int cols = numCellsX;
		int gs = degreeSeparation;
		int i = rows - (y + 1); int j = cols - (x + 1);	
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
		return numCellsY;
	}

	@Override
	public int getCols() {
		return numCellsX;
	}

}
