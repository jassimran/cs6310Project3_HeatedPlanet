package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingUtilities;

import presentation.earth.TemperatureGrid;
import simulation.SimulationSettings;
import buffers.BufferImplementation;
import events.EventType;
import events.Listener;

public class SimulationControl extends AbstractControl implements Listener, Runnable {
	
	private List<Listener> listeners;
	
	private int counter = 0;
	
	private long simulationStart;
	
	private long iddleTime = 0;
	
	public SimulationControl() {
		super();
		
		listeners = new ArrayList<Listener>();
		
		simulationControl = null;
		presentationControl = null;
	}
		
	/**
	 * Result of last simulation or null if none has run yet.
	 */
	private TemperatureGrid temperatureGrid = null;
	
	/**
	 * Executes simulation taking into consideration the chosen concurrency model.
	 */
	public void executeSimulation() {
		
		if(counter == 0) {
			simulationStart = (new Date()).getTime();
		}
		
		while(!isTerminateSimulation()) {
			// get current simulation time
			int simulationTime;
			synchronized (abstractLock) {
				simulationTime = AbstractControl.simulationTime;
			}
			
			if(preventStarvation()) { // prevents simulation to starve
				break;
			}
			
			// get simulation time step
			int timeStep = simulationSettings.getSimulationTimeStep();
			
			// execute simulation step
			temperatureGrid = simulationEngine.executeSimulationStep(temperatureGrid, simulationTime, timeStep);
			
			if(waiting()) { // wait if simulation paused, or buffer is full
				long iddleStart = (new Date()).getTime();
				awaitNotification();
				long iddleStop = (new Date()).getTime();
				iddleTime += iddleStop - iddleStart;
			}
			
			// update simulation time
			synchronized (abstractLock) {
				AbstractControl.simulationTime += simulationSettings.getSimulationTimeStep();
			}
						
			// put results in buffer
			buffer.put(temperatureGrid);
			
			// TODO increment counter
			counter++;
			
			// TODO print system info
			if(counter==24) {
				System.out.println("Used memory in bytes (s): " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
				System.out.println("Time in millis (s): " + (((new Date()).getTime()) - simulationStart));
				System.out.println("Idle in millis (s): " + iddleTime);
			}
			
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