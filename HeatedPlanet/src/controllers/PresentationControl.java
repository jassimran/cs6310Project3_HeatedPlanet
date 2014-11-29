package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingUtilities;

import presentation.Gui;
import presentation.earth.TemperatureGrid;
import presentation.query.QueryResult;
import presentation.query.SimulationQuery;
import buffers.BufferImplementation;
import simulation.SimulationSettings;
import events.*;

public class PresentationControl extends AbstractControl implements Runnable {

	private List<Listener> listeners;
	
	private long lastRenderization; // time stamp of the last renderization (in milliseconds)
	private int lastSimulationTimeRendered; // simulation time of the last simulation rendered
	
	public PresentationControl() {
		super();
		
		listeners = new ArrayList<Listener>();
		lastSimulationTimeRendered = 0;
	}
	
	protected void renderSimulation() {
				
		while(!isTerminateSimulation() && !isPresentationFinished()) {
			
			if(preventStarvation() && !buffer.isFull()) { // prevents simulation to starve if running in same thread
				break;
			}
			
			if(waiting()) {
				if(!simulationSettings.isPOption()) { // event dispatcher should not wait
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							presentationControl.renderSimulation();
						}
					});
					break;
				}
				awaitNotification();
			}
			
			// get results from buffer
			TemperatureGrid temperatureGrid = buffer.get();
			
			// update presentation time
			synchronized (abstractLock) {
				AbstractControl.presentationTime += simulationSettings.getSimulationTimeStep();
			}
			
			// calculate sun position
			float sunPositionDelta = calculateSunPosition(temperatureGrid.getSimulationTime()-lastSimulationTimeRendered );
			
			//update the visual clock
			Gui.getInstance(false, false, false, false, 10).updateClock(temperatureGrid.getLatitudeUnderSun(), 
					temperatureGrid.getLongitudeUnderSun(), temperatureGrid.getDistanceFromSun(), temperatureGrid.getCoordinates());

			// move sun
			presentationEngine.moveSunPosition(sunPositionDelta);
			
			// render simulation step
			presentationEngine.renderSimulationStep(temperatureGrid);
						
			// update time stamp
			lastRenderization = now();
			lastSimulationTimeRendered = temperatureGrid.getSimulationTime();
			
			// increment presentation index
			synchronized (abstractLock) {
				presentationIndex++;
			}
			
			// notify listeners
			notifyListeners();
			
			if(!simulationSettings.isPOption()) { // if presentation not running in it's own thread, render only one result
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						presentationControl.renderSimulation();
					}
				});
				break;
			}
		}
	}
	
	protected float calculateSunPosition(SimulationSettings simulationSettings) {
		return (float)(simulationSettings.getSimulationTimeStep() % 1440) * 360 / (float)1440;
	}

	/**
	 * @return true if control should be waiting for notification
	 */
	@Override
	protected boolean waiting() {
		boolean waiting = false;
		
		if((!isSimulationRunning() || buffer.isEmpty() || !timeHasEllapsed()) && !isTerminateSimulation()) {
			waiting = true;
		}
				
		return waiting;
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
	 * Presentation starves if running in common thread and the simulation is paused
	 * before the running presentation step is complete; in which case is necessary
	 * to prevent the simulation to starve.
	 * @return true if it is necessary to prevent starvation
	 */
	protected boolean preventStarvation() {
		return !isSimulationRunning() && !simulationSettings.isPOption();
	}
	
	/**
	 * @return the amount of milliseconds from the epoch to the current instant
	 */
	private long now() {
		return (new Date()).getTime();
	}
	
	/**
	 * 
	 * @return true if the time since last renderization is greater than then presentation display rate
	 */
	private boolean timeHasEllapsed() {
		return (now() - lastRenderization) >= simulationSettings.getPresentationDisplayRate();
	}
	
	public void handleSimulationEvent() {
		if(simulationSettings.isPOption()) {
			synchronized (presentationThread) {
				presentationThread.notify();				
			}
		} else {
			// handle presentation
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					presentationControl.renderSimulation();
				}
			});
		}
	}

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

	public void pauseSimulation() {
		setSimulationRunning(false);
		
		synchronized (simulationThread) {
			simulationThread.notify();
		}
	}

	public void resumeSimulation() {
		setSimulationRunning(true);
		
		synchronized (presentationThread) {
			presentationThread.notify();
		}
	}

	@Override
	public void notify(EventType e) {
		if (e == EventType.SimulationEvent) {
			handleSimulationEvent();
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
			l.notify(EventType.PresentationEvent);
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
		
		// create simulation control
		simulationControl = new SimulationControl();
		this.addListener(simulationControl);
		
		// define presentation control control
		presentationControl = this;
		
		// set simulation running
		setSimulationRunning(true);
		
		// define concurrency
		if(!settings.isPOption()&&!settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = Thread.currentThread();
			presentationThread = Thread.currentThread();
			// execute simulation
			simulationControl.executeSimulation();
			presentationControl.renderSimulation();
		} else if(settings.isPOption() && settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = new Thread(simulationControl);
			presentationThread = new Thread(this);
			// execute simulation
			simulationThread.start();
			presentationThread.start();
		} else if(settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = new Thread(simulationControl);
			presentationThread = Thread.currentThread();
			// execute simulation
			simulationThread.start();
			presentationControl.renderSimulation();
		} else if(settings.isPOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = Thread.currentThread();
			presentationThread = new Thread(this);
			// execute simulation
			presentationThread.start();
			simulationControl.executeSimulation();
		}
	}

	@Override
	public void run() {
		renderSimulation();		
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

	@Override
	public QueryResult getQueryResultBySimulationName(String simulationName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public QueryResult computeQueryResults(SimulationQuery simulationQuery) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getSimulationListByUserInputs(double axialTilt,
			double orbitalEccentricity, Date endingDate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getSimulationList() {
		throw new UnsupportedOperationException();
	}
}
