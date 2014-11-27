package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import presentation.earth.TemperatureGrid;
import presentation.query.QueryResult;
import presentation.query.QueryResultFactory;
import presentation.query.SimulationQuery;
import services.AccuracyService;
import services.InterpolationService;
import services.PersistenceService;
import services.SimulationService;
import simulation.SimulationSettings;
import simulation.SimulationSettingsFactory;
import domain.EarthGrid;
import domain.Simulation;
import events.EventType;
import events.Listener;

public class QueryControl extends AbstractControl implements Runnable {
	
	// used services
	private PersistenceService persistenceService;
	private AccuracyService accuracyService;
	private SimulationService simulationService;
	private InterpolationService interpolationService;
	
	private List<Listener> listeners;
	
	/**
	 * Result of last simulation or null if none has run yet.
	 */
	private TemperatureGrid temperatureGrid;

	public QueryControl() {
		super();
		// initialize listeners
		listeners = new ArrayList<Listener>();
		// get services reference
		persistenceService = PersistenceService.getInstance();
		accuracyService = AccuracyService.getInstance();
		simulationService = SimulationService.getInstance();
		interpolationService = InterpolationService.getInstance();
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
		if(simulationNames.isEmpty()){
			SimulationSettings settings = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
			settings.setAxialTilt(axialTilt);
			settings.setEccentricity(orbitalEccentricity);
			int simulationMonths = simulationService.calculateSimulationMonths(endingDate);
			settings.setSimulationLength(simulationMonths);
			simulationNames.add(generateSimulationName(settings));
		}
		return simulationNames;		
	}
	
	/**
	 * Performs geographic and temporal interpolation on the given simulation.
	 */
	private void interpolate(Simulation simulation) {
		for (EarthGrid currentGrid : simulation.getTimeStepList()) {
			interpolationService.performGeographicInterpolation(currentGrid);
		}

		List<EarthGrid> temporalInterpolatedGrids = interpolationService.
				performTemporalInterpolation(simulation);
		simulation.setTimeStepList(temporalInterpolatedGrids);
		
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
		// find simulation
		Simulation selectedSimulation = persistenceService.findBySimulationName(simulationName);
		
		// check precondition
		if(selectedSimulation == null) {
			throw new RuntimeException("Precondition not met: searched by a non-existent simulation");
		}
		
		// interpolate
		interpolate(selectedSimulation);
		
		// build query result
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
	public QueryResult computeQueryResults(SimulationQuery simulationQuery) {
		// search for simulation
		Simulation selectedSimulation = persistenceService.findBySimulationName(simulationQuery.getSimulationName());
		
		// if simulation does not exist, notify
		if(selectedSimulation == null){			
			return null;
		}
		
		// interpolate
		interpolate(selectedSimulation);

		// build query result
		return QueryResultFactory.buildQueryResult(selectedSimulation, simulationQuery);

	}

	public String generateSimulationName(SimulationSettings settings) {
		
		String retVal = null;
		
		Object[] args = new Object[]
				{settings.getAxialTilt(), settings.getEccentricity(), settings.getSimulationLength(), 
				settings.getGridSpacing(), settings.getSimulationTimeStep(),
				settings.getTemporalAccuracy(), settings.getGeoAccuracy(), 1};
		
		final String format = "Tilt: %s Ecc: %s Len: %s GS: %d TS: %s TA: %s GA: %s Run: %s";
		
		retVal = String.format(format, args);
		
		while(simulationNameExists(retVal)){
			args[7] = ((Integer)args[7])+1;
			retVal = String.format(format, args);
		}
		
		return retVal;
	}
	
	/**
	 * Executes simulation taking into consideration the chosen concurrency model.
	 */
	public void executeSimulation() {
		
		// calculate simulation length (in terms of simulation steps to produce)
		synchronized (abstractLock) {
			simulationLength = simulationService.calculateSimulationLength(simulationSettings.getSimulationLength(), simulationSettings.getSimulationTimeStep());
		}
		
		// reset simulation progress
		synchronized (abstractLock) {
			simulationIndex = 0;
		}
				
		// create simulation
		Simulation simulation = createSimulation(simulationSettings);
		
		// get total grids to produce
		int totalGrids;
		synchronized (abstractLock) {
			totalGrids = simulationLength;
		}
		
		// calculate accuracy gap
		int gapSize = accuracyService.calculateGapSize(totalGrids, simulation.getTemporalAccuracy());		
		int gapControl = gapSize; // use to place gaps between samples to persist
		
		// reset temperature grid
		temperatureGrid = null;
		
		while(!isTerminateSimulation() && !isSimulationFinished()) {
			// get current simulation time
			int simulationTime;
			synchronized (abstractLock) {
				simulationTime = AbstractControl.simulationTime;
			}
						
			// execute simulation step
			temperatureGrid = simulationEngine.executeSimulationStep(simulationSettings, simulationTime, temperatureGrid);
			
			// get and increment simulation index
			int index;
			synchronized (abstractLock) {
				simulationIndex++;
				index = simulationIndex;
			}
			
			// persist simulation based on temporal accuracy
			if((++gapControl) == (gapSize+1)) {
				persistenceService.persistSimulation(simulation, temperatureGrid, index);
				gapControl = 0;					
			}
			
			// update simulation time
			synchronized (abstractLock) {
				AbstractControl.simulationTime += simulationSettings.getSimulationTimeStep();
			}
		}
		
		// notify listeners simulation is finished
		for(Listener listener : listeners) {
			listener.notify(EventType.SimulationFinishedEvent);
		}
	}

	@Override
	public void notify(EventType e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addListener(Listener l) {
		synchronized (listeners) {
			listeners.add(l);
		}
	}

	@Override
	public void removeListener(Listener l) {
		synchronized (listeners) {
			listeners.remove(l);
		}
	}

	@Override
	public void run() {
		executeSimulation();
	}

	@Override
	protected boolean waiting() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void runSimulation(SimulationSettings settings) {
		// set settings 
		setSettings(settings);
		
		// set simulation running
		setSimulationRunning(true);
		
		// define concurrency
		simulationThread = new Thread(this);
		
		// execute simulation
		simulationThread.start();
	}

	@Override
	public void stopSimulation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void pauseSimulation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resumeSimulation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handleStopSimulationEvent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePauseSimulationEvent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handleResumeSimulationEvent() {
		throw new UnsupportedOperationException();
	}
}
