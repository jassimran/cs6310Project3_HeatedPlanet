package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sun.org.apache.bcel.internal.generic.NEW;

import presentation.PresentationEngine;
import simulation.SimulationEngine;
import simulation.SimulationSettings;
import buffers.Buffer;

public abstract class AbstractControl {

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

	protected AbstractControl() {
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

	public static void setBuffer(Buffer buffer) {
		AbstractControl.buffer = buffer;
	}
	

	public static int getSimulationLength() {
		return simulationSettings.getSimulationLength();
	}

	private static int totalGridCount = 0;
	/**
	 * Calculates the total number of grids that the simulation will generate.
	 * @param simulationLength The total length of the simulation in months.
	 * @param simulationTimeStep The time step of the simulation in minutes.
	 * @return the number of grids that the simulation will produce in total.
	 */
	public static int getTotalGridCount(int simulationLength, int simulationTimeStep) {
		if(totalGridCount == 0 ) {
			int totalMinutesElapsed = simulationTimeStep;
			while(!simulationLengthElapsed(simulationLength, totalMinutesElapsed)){
				totalGridCount ++;
				totalMinutesElapsed += simulationTimeStep;
			}
		}
		return totalGridCount;
	}
	

	/**
	 * Determines whether the simulation will have elapsed based on the provided number of minutes that have elapsed.
	 * @param simulationMinutesElapsed is the number of minutes that have elapsed in the simulation.
	 * @return true if the simulation should end.
	 */
	public static boolean simulationLengthElapsed(int simulationLength, int simulationMinutesElapsed) {
		//The start of the simulation is Jan 4
		String startTime = "12:00 AM, Jan 4, 2000";
		DateFormat dateFormat = new SimpleDateFormat("hh:mm a, MMM dd, yyyy");
		
		java.util.Calendar cal = dateFormat.getCalendar();

		try {
			cal.setTime(dateFormat.parse(startTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.add(java.util.Calendar.MINUTE, simulationMinutesElapsed);
		
		int monthsElapsed = ((cal.get(Calendar.YEAR)-2000)*12) + cal.get(Calendar.MONTH);
		
		if(simulationLength >= monthsElapsed)
			return false;
		return true;
	}

	/**
	 * @return true if the requested length of the simulation has elapsed
	 */
	public static boolean simulationLengthElapsed() {
		return simulationLengthElapsed(simulationSettings.getSimulationLength(), simulationTime);
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