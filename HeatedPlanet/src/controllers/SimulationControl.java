package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingUtilities;

import presentation.earth.TemperatureGrid;
import services.AccuracyService;
import services.PersistenceService;
import simulation.SimulationSettings;
import buffers.BufferImplementation;
import domain.Simulation;
import events.EventType;
import events.Listener;

public class SimulationControl extends AbstractControl implements Listener, Runnable {
	
	private List<Listener> listeners;
		
	private long simulationStart;
	private long iddleTime;
	
	// used services
	private PersistenceService persistenceService;
	private AccuracyService accuracyService;
		
	public SimulationControl() {
		super();
		
		listeners = new ArrayList<Listener>();
		
		persistenceService = PersistenceService.getInstance();
		accuracyService = AccuracyService.getInstance();
	}

	/**
	 * Result of last simulation or null if none has run yet.
	 */
	private TemperatureGrid temperatureGrid = null;
	
	/**
	 * Executes simulation taking into consideration the chosen concurrency model.
	 */
	public void executeSimulation() {
		
		// reset simulation progress
		synchronized (abstractLock) {
			simulationIndex = 0;
		}
		
		// simulation metrics
		simulationStart = (new Date()).getTime();
		iddleTime = 0;
		
		// create simulation
		Simulation simulation = new Simulation();
		simulation.setName(simulationSettings.getName());
		simulation.setOrbitalEccentricity(simulationSettings.getEccentricity());
		simulation.setAxialTilt(simulationSettings.getAxialTilt());
		simulation.setTemporalAccuracy(simulationSettings.getTemporalAccuracy());
		simulation.setGeoAccuracy(simulationSettings.getGeoAccuracy());
		simulation.setLength(simulationSettings.getSimulationLength());
		
		// get total grids to produce
		int totalGrids;
		synchronized (abstractLock) {
			totalGrids = simulationLength;
		}
		
		// calculate accuracy gap
		int gapSize = accuracyService.calculateGapSize(totalGrids, simulation.getTemporalAccuracy());		
		int gapControl = gapSize; // use to place gaps between samples to persist
		
		while(!isTerminateSimulation() && !isSimulationFinished()) {
			// get current simulation time
			int simulationTime;
			synchronized (abstractLock) {
				simulationTime = AbstractControl.simulationTime;
			}
			
			if(preventStarvation()) { // prevents simulation to starve when running in common thread
				break;
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
			
			if(waiting()) { // wait if simulation paused, or buffer is full
				long iddleStart = (new Date()).getTime();
				awaitNotification();
				long iddleStop = (new Date()).getTime();
				iddleTime += iddleStop - iddleStart;
				// prevent buffer overflow
				if(isTerminateSimulation()) {
					break;
				}
			}
						
			// put results in buffer
			buffer.put(temperatureGrid);
			
			// notify listeners
			notifyListeners();
			
			if(!simulationSettings.isSOption()) { // if simulation not running in it's own thread, perform only one result
				if(simulationSettings.isTOption()) { // if simulation has the initiative
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							simulationControl.executeSimulation();
						}
					});					
				}
				break;
			}
		}
		
		// get simulation index
		int index;
		synchronized (abstractLock) {
			index = simulationIndex;
		}
		
		// print simulation metrics
		System.out.println("Total number of simulations (s): " + index);
		System.out.println("Used memory in bytes (s): " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
		System.out.println("Time in millis (s): " + (((new Date()).getTime()) - simulationStart));
		System.out.println("Idle in millis (s): " + iddleTime);
	}	
	
	/**
	 * @return true if control should be waiting for notification
	 */
	@Override
	protected boolean waiting() {
		boolean waiting = false;
		
		if((!isSimulationRunning() || buffer.isFull()) && !isTerminateSimulation()) {
			waiting = true;
		}
				
		return waiting;
	}
	
	/**
	 * Simulation starves if running in common thread and the simulation is paused
	 * before the running simulation step is complete; in which case is necessary
	 * to prevent the simulation to starve.
	 * @return true if it is necessary to prevent starvation
	 */
	protected boolean preventStarvation() {
		return !isSimulationRunning() && !simulationSettings.isSOption();
	}
	
	public void handlePresentationEvent() {
		if(simulationSettings.isSOption()) {
			synchronized(simulationThread) {
				simulationThread.notify();				
			}
		} else {
			// handle simulation
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					simulationControl.executeSimulation();
				}
			});
		}
	}

	@Override
	public void stopSimulation() {
		setSimulationRunning(false);
		setTerminateSimulation(true);
		
		synchronized (simulationThread) {
			simulationThread.notify();
		}
		
		synchronized (presentationThread) {
			presentationThread.notify();
		}
	}

	@Override
	public void pauseSimulation() {
		setSimulationRunning(false);
		
		if(simulationSettings.isPOption()) {
			synchronized (presentationThread) {
				presentationThread.notify();
			}			
		}
	}

	@Override
	public void resumeSimulation() {
		setSimulationRunning(true);
		
		if(!simulationSettings.isSOption()) {
			// handle simulation
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					simulationControl.executeSimulation();
				}
			});
		}
		
		if(simulationSettings.isPOption()) {
			synchronized (presentationThread) {
				presentationThread.notify();
			}			
		}
	}

	@Override
	public void notify(EventType e) {
		if(e == EventType.PresentationEvent) {
			handlePresentationEvent();
		} else if (e == EventType.StopSimulationEvent) {
			handleStopSimulationEvent();
		} else if (e == EventType.PauseSimulationEvent) {
			handlePauseSimulationEvent();
		} else if (e == EventType.ResumeSimulationEvent) {
			handleResumeSimulationEvent();
		}
	}
	
	/**
	 * Notify listeners of SimulationEvent.
	 */
	private void notifyListeners() {
		for(Listener l : listeners) {
			l.notify(EventType.SimulationEvent);
		}		
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
	public void runSimulation(SimulationSettings settings) {
		// set settings 
		setSettings(settings);
		
		// create buffer
		setBuffer(new BufferImplementation(settings.getBufferSize()));
		
		// define simulation control
		simulationControl = this;
				
		// crate presentation control
		presentationControl = new PresentationControl();
		this.addListener(presentationControl);
		
		// set simulation running
		setSimulationRunning(true);
		
		// define concurrency
		if(!settings.isPOption()&&!settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = Thread.currentThread();
			presentationThread = Thread.currentThread();
			// execute simulation
			this.executeSimulation();
		} else if(settings.isPOption() && settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = new Thread(this);
			presentationThread = new Thread(presentationControl);
			// execute simulation
			simulationThread.start();
			presentationThread.start();
		} else if(settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = new Thread(this);
			presentationThread = Thread.currentThread();
			// execute simulation
			simulationThread.start();
		} else if(settings.isPOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = Thread.currentThread();
			presentationThread = new Thread(presentationControl);
			// execute simulation
			presentationThread.start();
			this.executeSimulation();
		}
	}

	@Override
	public void run() {
		executeSimulation();
	}

	@Override
	public void handleStopSimulationEvent() {
		simulationThread.notify();		
	}

	@Override
	public void handlePauseSimulationEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleResumeSimulationEvent() {
		// TODO Auto-generated method stub
		
	}
}
