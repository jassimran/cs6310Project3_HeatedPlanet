package controllers;

import domain.Simulation;
import presentation.PresentationEngine;
import services.PersistenceService;
import simulation.SimulationEngine;
import simulation.SimulationSettings;
import buffers.Buffer;
import events.Listener;

public abstract class AbstractControl implements Listener {

	/**
	 * Amount of simulated time since the start of the execution (in minutes).
	 */
	protected static int simulationTime;
	
	/**
	 * Amount of simulated time since the presentation engine has rendered.
	 */
	protected static int presentationTime;
	
	protected static SimulationSettings simulationSettings;
	protected static Thread simulationThread;
	protected static Thread presentationThread;
	protected static Thread eventDispatcherThread;
	
	protected static SimulationControl simulationControl;
	protected static PresentationControl presentationControl;
	
	// abstract control lock
	protected static Object abstractLock = new Object();
	
	/**
	 * The maximum amount of time (in milliseconds) to wait before checking
	 *  if control is still waiting for the buffer or an event.
	 */
	protected long timeout = 1000;
	
	// simulation buffer
	protected static Buffer buffer;
	
	// simulation engines
	protected static SimulationEngine simulationEngine;
	protected static PresentationEngine presentationEngine;
	
	// control simulation state
	private static boolean simulationRunning;
	private static boolean terminateSimulation;
	
	// number of simulation steps to complete
	protected static int simulationLength;
	
	// control simulation progress
	protected static int simulationIndex;
	protected static int presentationIndex;

	protected AbstractControl() {
		// initialize state
		simulationRunning = false;
		terminateSimulation = false;
		simulationTime = 0;
	}

	public static void setSimulationEngine(SimulationEngine simulationEngine) {
		AbstractControl.simulationEngine = simulationEngine;
	}

	public static void setPresentationEngine(PresentationEngine presentationEngine) {
		AbstractControl.presentationEngine = presentationEngine;
	}

	protected static void setSettings(SimulationSettings settings) {
		AbstractControl.simulationSettings = settings;
	}

	public static long getSimulationTime() {
		return simulationTime;
	}

	protected static void setBuffer(Buffer buffer) {
		AbstractControl.buffer = buffer;
	}
	
	/**
	 * @return true if a simulation with the given name exists
	 */
	public boolean simulationExists(String simulationName) {
		return (PersistenceService.getInstance().findBySimulationName(simulationName) != null)? true : false;
	}
	
	/**
	 * @return a Simulation based on the given SimulationSettings
	 */
	protected Simulation createSimulation(SimulationSettings simulationSettings) {
		
		// create simulation
		Simulation simulation = new Simulation();
		simulation.setName(simulationSettings.getName());
		simulation.setOrbitalEccentricity(simulationSettings.getEccentricity());
		simulation.setAxialTilt(simulationSettings.getAxialTilt());
		simulation.setTemporalAccuracy(simulationSettings.getTemporalAccuracy());
		simulation.setGeoAccuracy(simulationSettings.getGeoAccuracy());
		simulation.setLength(simulationSettings.getSimulationLength());
		simulation.setGridSpacing(simulationSettings.getGridSpacing());
		simulation.setPrecision(simulationSettings.getPrecision());
		simulation.setTimeStep(simulationSettings.getSimulationTimeStep());
		simulation.setNumberOfColumns(simulationSettings.getNumCellsX());
		simulation.setNumberOfRows(simulationSettings.getNumCellsY());
		
		return simulation;
	}
	
	/**
	 * @return true if simulation should be terminated
	 */
	public boolean isTerminateSimulation() {
		boolean terminate = false;
		
		synchronized (abstractLock) {
			if(terminateSimulation) {
				terminate = true;
			}
		}
		
		return terminate;
	}
	
	/**
	 * @return true if the number of produced simulation steps is equal to the simulation length
	 */
	public boolean isSimulationFinished() {
		boolean finished = false;
		
		synchronized (abstractLock) {
			if(simulationIndex == simulationLength) {
				finished = true;
			}
		}
		
		return finished;		
	}
	
	/**
	 * @return true if the number of rendered simulation steps is equal to the simulation length
	 */
	public boolean isPresentationFinished() {
		boolean finished = false;
		
		synchronized (abstractLock) {
			if(presentationIndex == simulationLength) {
				finished = true;
			}
		}
		
		return finished;		
	}
	
	/**
	 * Setter for terminateSimulation.
	 * @param value the new value of terminateSimulation
	 */
	protected static void setTerminateSimulation(boolean value) {
		synchronized (abstractLock) {
			terminateSimulation = value;
		}
	}
	
	/**
	 * @return true if simulation is running
	 */
	public boolean isSimulationRunning() {
		boolean running = false;
		
		synchronized (abstractLock) {
			if(simulationRunning) {
				running = true;
			}
		}
		
		return running;
	}
	
	/**
	 * Setter for simulationRunning.
	 * @param value the new value of simulationRunning
	 */
	protected static void setSimulationRunning(boolean value) {
		synchronized (abstractLock) {
			simulationRunning = value;
		}
	}
	
	/**
	 * Waits until notification is received.
	 */
	protected void awaitNotification() {
		while(waiting()) {
			try {
				synchronized (Thread.currentThread()) {
					Thread.currentThread().wait(timeout);
				}
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}
	
	/**
	 * Calculates the number of degrees the sun moves in the given amount of minutes.
	 * @param time the amount of time to use in minutes
	 * @return the number of degrees the sun moves in the given amount of time
	 */
	protected float calculateSunPosition(int time) {
		return (float)((time % 1440) * 360) / (float)1440;
	}
	
	protected abstract boolean waiting();

	public abstract void runSimulation(SimulationSettings settings);

	public abstract void stopSimulation();

	public abstract void pauseSimulation();

	public abstract void resumeSimulation();
	
	public abstract void handleStopSimulationEvent();
	
	public abstract void handlePauseSimulationEvent();
	
	public abstract void handleResumeSimulationEvent();
}