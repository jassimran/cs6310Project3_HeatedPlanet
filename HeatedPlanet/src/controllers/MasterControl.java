package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import services.SimulationService;
import simulation.SimulationSettings;
import buffers.BufferImplementation;
import events.EventType;
import events.Listener;

public class MasterControl extends AbstractControl implements Listener {
	
	private List<Listener> listeners;
	
	// used services
	private SimulationService simulationService;
	
	public MasterControl() {
		super();
		
		listeners = new ArrayList<Listener>();
		
		simulationControl = null;
		presentationControl = null;
		
		simulationService = SimulationService.getInstance();
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

	public void handlePresentationEvent() {
		
		// check if simulation is complete
		boolean presentationComplete;
		synchronized (abstractLock) {
			presentationComplete = presentationIndex == simulationLength;
		}
		
		// handle simulation complete
		if(presentationComplete) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					handleSimulationComplete();
				}
			});
			return; // no more simulation steps to present
		}
		
		
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
	
	public void handleSimulationComplete() {
		// check preconditions
		synchronized (abstractLock) {
			if( (simulationIndex != simulationLength) || (presentationIndex != simulationLength)) {
				throw new RuntimeException("Precondition not met: simulation not complete on handleSimulationComplete");
			}
		}
		
		// notify listeners
		for(Listener l : listeners) {
			l.notify(EventType.SimulationFinishedEvent);
		}
	}

	/**
	 * Starts the simulation.
	 */
	@Override
	public void runSimulation(SimulationSettings settings) {
		// set settings 
		setSettings(settings);
		
		// create buffer
		setBuffer(new BufferImplementation(settings.getBufferSize()));
				
		// create simulation control
		simulationControl = new SimulationControl();
		simulationControl.addListener(this);
		
		// create presentation control
		presentationControl = new PresentationControl();
		presentationControl.addListener(this);
		
		// calculate simulation length (in terms of simulation steps to produce)
		synchronized (abstractLock) {
			simulationLength = simulationService.calculateSimulaitonLenght(simulationSettings.getSimulationLength(), simulationSettings.getSimulationTimeStep());
		}
		
		// reset simulation progress
		synchronized (abstractLock) {
			simulationIndex = 0;
			presentationIndex = 0;
		}
		
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
		} else if(settings.isPOption() && settings.isSOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = new Thread(simulationControl);
			presentationThread = new Thread(presentationControl);
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
		} else if(settings.isPOption()) {
			// set threads
			eventDispatcherThread = Thread.currentThread();
			simulationThread = Thread.currentThread();
			presentationThread = new Thread(presentationControl);
			// execute simulation
			presentationThread.start();
			simulationControl.executeSimulation();
		}
	}

	/**
	 * Stops the running simulation.
	 */
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

	/**
	 * Pauses the running simulation.
	 */
	@Override
	public void pauseSimulation() {
		setSimulationRunning(false);
		
		if(simulationSettings.isSOption()) {
			synchronized (simulationThread) {
				simulationThread.notify();
			}			
		} 
		
		if(simulationSettings.isPOption()) {
			synchronized (presentationThread) {
				presentationThread.notify();
			}			
		}
	}

	/**
	 * Resumes the paused simulation.
	 */
	@Override
	public void resumeSimulation() {
		setSimulationRunning(true);
		
		if(simulationSettings.isSOption()) {
			synchronized (simulationThread) {
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
		
		if(simulationSettings.isPOption()) {
			synchronized (presentationThread) {
				presentationThread.notify();
			}			
		}
	}

	@Override
	public void notify(EventType e) {
		if(e == EventType.SimulationEvent) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					handleSimulationEvent();
				}
			});
		} else if (e == EventType.PresentationEvent) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					handlePresentationEvent();
				}
			});
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
	public void handleStopSimulationEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePauseSimulationEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleResumeSimulationEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean waiting() {
		throw new UnsupportedOperationException();
	}
}