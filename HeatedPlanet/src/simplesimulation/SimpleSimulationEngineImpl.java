package simplesimulation;

import presentation.earth.TemperatureGrid;
import simulation.SimulationEngine;
import simulation.SimulationSettings;

public class SimpleSimulationEngineImpl implements SimulationEngine {
	
	// daylight variables
	private int rows;
	private int cols;
	private int columnUnderTheSun;
	private int rowUnderTheSun;
	private double gcptz;
	private int daylightLeftLimit;
	private int daylightRightLimit;
	
	// simulation information
	private SimulationSettings simulationSettings;
	
	public SimpleSimulationEngineImpl(SimulationSettings simulationSettings) {
		this.simulationSettings = simulationSettings;
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
	 * Calculates the attenuation of the given column, which is proportional
	 * to the absolute distance from the given column and the column under the sun.
	 * @param column the given column
	 * @return the attenuation of the given column
	 */
	private double getAttenuationColumn(int column) {
		double distance = Math.abs(column - columnUnderTheSun);
		distance = distance * simulationSettings.getDegreeSeparation();
		return Math.abs(Math.cos(distance * Math.PI / 180));
	}
	
	/**
	 * Calculates the attenuation of the given row, which is proportional
	 * to the absolute distance from the given row and the equator.
	 * @param row the given row
	 * @return the attenuation of the given row
	 */
	private double getAttenuationRow(int row) {
		double distance = Math.abs(row - rowUnderTheSun);
		distance = distance * simulationSettings.getDegreeSeparation();
		return Math.cos(distance * Math.PI / 180);
	}
	
	@Override
	public synchronized TemperatureGrid executeSimulationStep(SimulationSettings settings, int simulationTime, TemperatureGrid inputGrid) {
		if(inputGrid==null) {
			SimpleTemperatureGridImpl simpleTemperatureGridImpl = new SimpleTemperatureGridImpl(simulationSettings);
			simpleTemperatureGridImpl.initGrid();
			inputGrid = simpleTemperatureGridImpl;
		}
		
		// create grid for new simulation
		SimpleTemperatureGridImpl temperatureGrid = new SimpleTemperatureGridImpl(simulationSettings);
		
		// calculate variables
		rows = simulationSettings.getNumCellsY();
		cols = simulationSettings.getNumCellsX();
		
		columnUnderTheSun = SimpleCell.columnUnderTheSun(simulationTime, cols); // westward from primary meridian
		columnUnderTheSun = (cols - columnUnderTheSun); // adjusted to map GRID coordinates
		
		gcptz = (cols / 24d);
		
		//TODO::Need to fix when columnUnderTheSun is < 0 and > cols
		daylightLeftLimit = (int) (columnUnderTheSun - (6 * gcptz));
		daylightRightLimit = (int) (columnUnderTheSun + (5 * gcptz));

		// Decimal Precision for anomalies calculation
		int anomalyDecimalPrecision = 5;
				
		// calculate delta time
		int deltaTime = settings.getSimulationTimeStep() * 60; // s
		
		// TODO: Verify if we need to check that the orbital period can be changed.
		int timeSinceLastPerihelion = simulationTime % SimulationUtils.ORBITAL_PERIOD;
		
		double radiusAtPerihelion = SimulationUtils.radiusTau(SimulationUtils.A, settings.getEccentricity(), SimulationUtils.trueAnomaly(0, SimulationUtils.ORBITAL_PERIOD,
				settings.getEccentricity(), anomalyDecimalPrecision));
		
		double radiusTau = SimulationUtils.radiusTau(SimulationUtils.A, settings.getEccentricity(), SimulationUtils.trueAnomaly(timeSinceLastPerihelion, SimulationUtils.ORBITAL_PERIOD,
				settings.getEccentricity(), anomalyDecimalPrecision));
		
		double eccentricityAttenuation = SimulationUtils.getEccentricityAttenuation(radiusAtPerihelion, radiusTau);
		
		double[] coordinates = SimulationUtils.position(SimulationUtils.A, settings.getEccentricity(), SimulationUtils.eccentricAnomaly(settings.getEccentricity(), SimulationUtils.meanAnomaly(timeSinceLastPerihelion, SimulationUtils.ORBITAL_PERIOD) ,anomalyDecimalPrecision));
		
//		System.out.println("ROTATIONAL ANGLE: " + Math.toDegrees(SimulationUtils.rotationalAngle(timeSinceLastPerihelion, settings.getEccentricity(), SimulationUtils.EARTH_PERIAPSIS, SimulationUtils.ORBITAL_PERIOD)));
		System.out.println("ROTATIONAL ANGLE: " + SimulationUtils.longitudeUnderSun(timeSinceLastPerihelion));
		
		// simulation constants
		double e = 2700; // kg/m3
		double cb = 1000; // J/kgK
		double H = 1; // m
		double Fcooling = 239.4 * 1.1; // W/m2  //NOTE::Added *1.5 as a cooling fake to assist with gaps in calculation
		double Fs = 1368; // W/m2
		
		double latitudeUnderSun = SimulationUtils.latitudeNoonSun(timeSinceLastPerihelion, settings.getEccentricity(), SimulationUtils.EARTH_PERIAPSIS, SimulationUtils.ORBITAL_PERIOD, settings.getAxialTilt());
		
		// Calculating grid row index corresponding to latitude where sun is hitting directly
		rowUnderTheSun = (int) (latitudeUnderSun / simulationSettings.getDegreeSeparation() + rows / 2);
		
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
				
				double originalTemp = inputGrid.getTemperature(x, y) + deltaT;
				
				// Attenuating temperature based on distance from the sun.
				originalTemp *= eccentricityAttenuation;
				
				simpleCell.t = round(originalTemp, settings.getPrecision());
				temperatureGrid.setTemperature(x, y, simpleCell);
			}
		}
		
		// set simulation time
		temperatureGrid.setSimulationTime(simulationTime);
		temperatureGrid.setLatitudeUnderSun(round(latitudeUnderSun,4));
		//TODO: Get LongitudeUnderSun
		temperatureGrid.setLongitudeUnderSun(12.0);
		temperatureGrid.setDistanceFromSun(round(radiusTau /1000000,4));
		temperatureGrid.setCoordinates(coordinates);
		
		return temperatureGrid;
	}

	/**
	 * Rounds a double to the specified number of digits of precision.
	 * @param originalTemp
	 * @param precision
	 * @return The rounded value.
	 */
	protected double round(double originalTemp, int precision) {
		return new Double(String.format("%." + precision +"f", originalTemp));
	}	
}
