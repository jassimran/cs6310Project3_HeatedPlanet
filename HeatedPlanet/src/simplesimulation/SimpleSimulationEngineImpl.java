package simplesimulation;

import presentation.earth.EarthPanel;
import presentation.earth.TemperatureGrid;
import simulation.SimulationEngine;

public class SimpleSimulationEngineImpl implements SimulationEngine {

	private EarthPanel earthPanel;
	
	// daylight variables
	private int rows;
	private int cols;
	private int columnUnderTheSun;
	private int rowAtTheEquator;
	private double gcptz;
	private int daylightLeftLimit;
	private int daylightRightLimit;
	
	public SimpleSimulationEngineImpl(EarthPanel earthPanel) {
		this.earthPanel = earthPanel;
	}
	
	/**
	 * @return true if the given column is under day light
	 */
	private boolean isUnderDayLight(int column) {
		return ((daylightLeftLimit <= column && column <= daylightRightLimit) 
			|| (cols + daylightLeftLimit <= column) 
			|| (column <= (daylightRightLimit - cols)));
	}
	
	/**
	 * @return the attenuation of the given column
	 */
	
	/**
	 * Calculates the attenuation of the given column, which is proportional
	 * to the absolute distance from the given column and the column under the sun.
	 * @param column the given column
	 * @return the attenuation of the given column
	 */
	private double getAttenuationColumn(int column) {
		double distance = Math.abs(column - columnUnderTheSun);
		distance = distance * earthPanel.getDegreeSeparation();
		return Math.abs(Math.cos(distance * Math.PI / 180));
	}
	
	/**
	 * Calculates the attenuation of the given row, which is proportional
	 * to the absolute distance from the given row and the equator.
	 * @param row the given row
	 * @return the attenuation of the given row
	 */
	private double getAttenuationRow(int row) {
		double distance = Math.abs(row - rowAtTheEquator);
		distance = distance * earthPanel.getDegreeSeparation();
		return Math.cos(distance * Math.PI / 180);
	}
	
	@Override
	public synchronized TemperatureGrid executeSimulationStep(TemperatureGrid inputGrid, int simulationTime, int timeStep) {
		if(inputGrid==null) {
			SimpleTemperatureGridImpl simpleTemperatureGridImpl = new SimpleTemperatureGridImpl(earthPanel);
			simpleTemperatureGridImpl.initGrid();
			inputGrid = simpleTemperatureGridImpl;
		}
		
		// create grid for new simulation
		SimpleTemperatureGridImpl temperatureGrid = new SimpleTemperatureGridImpl(earthPanel);
		
		// calculate variables
		rows = earthPanel.getNumCellsY();
		cols = earthPanel.getNumCellsX();
		
		columnUnderTheSun = SimpleCell.columnUnderTheSun(simulationTime, cols); // westward from primary meridian
		columnUnderTheSun = (cols - columnUnderTheSun); // adjusted to map GRID coordinates
		
		rowAtTheEquator = (int) ((double) rows / (double) 2);
		
		gcptz = ((double) cols / (double) 24);
		
			
		//TODO::Need to fix when columnUnderTheSun is < 0 and > cols
		daylightLeftLimit = (int) (columnUnderTheSun - (6 * gcptz));
		daylightRightLimit = (int) (columnUnderTheSun + (5 * gcptz));

				
		// calcualte delta time
		int deltaTime = timeStep * 60; // s
		
		// simulation constants
		double e = 2700; // kg/m3
		double cb = 1000; // J/kgK
		double H = 1; // m
		double Fcooling = 239.4 * 1.1; // W/m2  //NOTE::Added *1.5 as a cooling fake to assist with gaps in calculation
		double Fs = 1368; // W/m2
		
		// TODO execute simple simulation
		for(int y=0; y<rows; y++) {
			for(int x=0; x<cols; x++) {
				
				// calculate attenuation
				double attenuation = getAttenuationColumn(x) * getAttenuationRow(y);
				
				// calculate net flux of heat
				double Fnet = (Fs * attenuation) - Fcooling;
								
				// calculate temperature change
				double deltaT;
				if(isUnderDayLight(x)) {
					deltaT = (Fnet / (e*cb*H)) * deltaTime;
				} else {
					deltaT = ((Fcooling * -1) / (e*cb*H)) * deltaTime;
				}
				
				// set temperature
				SimpleCell simpleCell = new SimpleCell();
				simpleCell.t = inputGrid.getTemperature(x, y) + deltaT;
				temperatureGrid.setTemperature(x, y, simpleCell);
			}
		}
		
		// set simulation time
		temperatureGrid.setSimulationTime(simulationTime);
		
		return temperatureGrid;
	}
	
}
