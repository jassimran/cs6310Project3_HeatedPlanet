package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import presentation.query.QueryResult;
import presentation.query.QueryResultFactory;
import services.PersistenceService;
import simulation.SimulationSettings;
import domain.Simulation;

public class QueryControl {

	private static PersistenceService persistenceService;
	//private static InterpolationService interpolationService;

	public QueryControl() {
		persistenceService = PersistenceService.getInstance();
		//interpolationService = InterpolationService.getInstance();
	}
	
	
	public List<String> getSimulationList(){
		List<String> simulationNames = new ArrayList<String>();
		List<Simulation> simulations = persistenceService.findAllSimulations();
		for(Simulation simulation : simulations){
			simulationNames.add(simulation.getName());
		}
		return simulationNames;
	}
	
	/**
	 * Gets the names of the simulations that match the user inputs
	 * @param axialTilt the requested axial tilt
	 * @param orbitalEccentricity the requested orbital eccentricity
	 * @param endingDate the requested ending date
	 * @return the matching simulation names based on the provided user inputs
	 */
	public List<String> getSimulationListByUserInputs(double axialTilt, double orbitalEccentricity, Date endingDate){
		List<String> simulationNames = new ArrayList<String>();
		List<Simulation> simulations = persistenceService.findSimulationsByUserInputs(axialTilt, orbitalEccentricity, endingDate);
		for(Simulation simulation : simulations){
			simulationNames.add(simulation.getName());
		}
		return simulationNames;		
	}
	
	/**
	 * Determines if a simulation name has already been used
	 * @param simulationName the simulation name to test
	 * @return True, if the simulation name is not unique. Otherwise, false.
	 */
	public boolean simulationNameExists(String simulationName) {
		Simulation simulation = persistenceService.findBySimulationName(simulationName);
		return simulation != null;
	}

	public QueryResult getQueryResultBySimulationName(String simulationName){
		Simulation selectedSimulation = persistenceService.findBySimulationName(simulationName);
		
		// TODO Determine if we need to interpolate
		return QueryResultFactory.buildQueryResult(selectedSimulation);
	}
	
	
	/**
	 * Computes the query results for the provided simulation based on the limitations provided
	 * @param selectedSimulation The Simulation that we are interested in
	 * @param startDate the start date that the user has requested
	 * @param endDate the end date that the user has requested
	 * @param startLat the starting latitude that the user has requested
	 * @param endLat the ending latitude that the user has requested
	 * @param startLong the starting longitude that the user has requested
	 * @param endLong the ending longitude that the user has requested
	 * @return the query results that need to be displayed to the user
	 */
	public QueryResult computeQueryResults(String simulationName,
			Date startDate, Date endDate, double startLat, double endLat,
			double startLong, double endLong) {

		Simulation selectedSimulation = persistenceService.findBySimulationName(simulationName);
		
		if(selectedSimulation == null){
			
			Double tilt = 23.44; // The tilt of the Earth
			int geographicAccuracy = 100; // 100 percent
			Double eccentricity = 0.0167; // The eccentricity of the Earth
			int gridSpacing = 15; // 15 degrees; the size of a time zone
			int temporalAccuracy = 100; // 100 percent 
			int timeStep = 1440; // 1 solar day in minutes
			int simulationLength = 12; // 12 months
			int precision = 7; // the number of digits storable in a float
			
			SimulationSettings settings = new SimulationSettings();
			settings.setSOption(true);
			settings.setPOption(false);
			settings.setROption(false);
			settings.setTOption(false);
			settings.numCellsX = 360 / gridSpacing;
			settings.numCellsY = 180 / gridSpacing;
			settings.setBufferSize(1);
			
			settings.setTemporalAccuracy(temporalAccuracy);
			settings.setGeoAccuracy(geographicAccuracy);
			settings.setPresentationDisplayRate(1 * 1000); // milliseconds
			settings.setSimulationTimeStep(timeStep); 
			settings.setGridSpacing(gridSpacing); 
			settings.setPrecision(precision);	
			settings.setAxialTilt(tilt); 
			settings.setEccentricity(eccentricity); 						
			settings.setSimulationLength(simulationLength); 
			
			//Generate the simulation name to use
			settings.setName(generateSimulationName(tilt, eccentricity, 
					simulationLength, gridSpacing, timeStep, 
					temporalAccuracy, geographicAccuracy));
			
			// TODO: Uncomment this after Jaime's branch is merged
			//this.runSimulation(settings);
		}
		
		// TODO Do we need to do something to force the system to wait until the simulation is completed?
		// I was thinking this might be necessary if it would be running on another thread
		selectedSimulation = persistenceService.findBySimulationName(simulationName);
		
//		// TODO: Perform geographic interpolation
//		for (EarthGrid currentGrid : selectedSimulation.getTimeStepList()) {
//			EarthGrid geoInterpolatedSimulation = interpolationService
//					.performGeographicInterpolation(selectedSimulation,
//							currentGrid);
//			// TODO: replace the grid in the list?
//		}
//
//		// TODO: Perform temporal interpolation
//		List<EarthGrid> temporalInterpolatedGrids = interpolationService
//				.performTemporalInterpolation(selectedSimulation,
//						selectedSimulation.getTimeStepList());
//		selectedSimulation.setTimeStepList(temporalInterpolatedGrids);

		return QueryResultFactory.buildQueryResult(selectedSimulation, startDate, endDate, startLat, endLat, startLong, endLong);

	}
	
	public String generateSimulationName(Double tilt, Double eccentricity,
			int simulationLength, int gridSpacing, int timeStep, int temporalAccuracy,
			int geographicAccuracy){
		
		String retVal = null;
		
		Object[] args = new Object[]
				{tilt, eccentricity, simulationLength, gridSpacing, timeStep,
				temporalAccuracy, geographicAccuracy, 1};
		
		final String format = "Tilt: %s Ecc: %s Len: %s GS: %d TS: %s TA: %s GA: %s Run: %s";
		
		retVal = String.format(format, args);
		
		while(simulationNameExists(retVal)){
			args[7] = ((Integer)args[7])+1;
			retVal = String.format(format, args);
		}
		
		return retVal;
	}
}
