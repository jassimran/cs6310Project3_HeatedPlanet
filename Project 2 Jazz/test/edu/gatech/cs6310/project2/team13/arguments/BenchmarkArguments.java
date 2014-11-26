package edu.gatech.cs6310.project2.team13.arguments;

import java.util.Calendar;
import java.util.Random;

import edu.gatech.cs6310.project2.team13.benchmark.Constants.Initiative;

public class BenchmarkArguments {
	private int displayRate;
	private int simulationInterval;
	private int bufferSize;
	private int gridSize;
	
	private boolean separateSimulationThread;
	private boolean separateVisualizationThread;
	
	private Initiative initiative;
	
	
	public BenchmarkArguments(int displayRate, int simulationInterval, int bufferSize, int gridSize,
			boolean separateSimulationThread, boolean separateVisualizationThread, Initiative initiative)
	{
		this.setDisplayRate(displayRate);
		this.setSimulationInterval(simulationInterval);
		this.setBufferSize(bufferSize);
		this.setGridSize(gridSize);
		this.setSeparateSimulationThread(separateSimulationThread);
		this.setSeparateVisualizationThread(separateVisualizationThread);
		this.setInitiative(initiative);
	}
	
	public static int randomIntArgument(int HighValue, int LowValue){
		Random r = new Random(Calendar.MILLISECOND%Calendar.SECOND);
		return r.nextInt(HighValue-LowValue)+LowValue;
	}
	
	/**
	 * @return the separateVisualizationThread
	 */
	public boolean isSeparateVisualizationThread() {
		return separateVisualizationThread;
	}


	/**
	 * @param separateVisualizationThread the separateVisualizationThread to set
	 */
	private void setSeparateVisualizationThread(boolean separateVisualizationThread) {
		this.separateVisualizationThread = separateVisualizationThread;
	}


	/**
	 * @return the simulationInterval
	 */
	public int getSimulationInterval() {
		return simulationInterval;
	}


	/**
	 * @param simulationInterval the simulationInterval to set
	 */
	private void setSimulationInterval(int simulationInterval) {
		this.simulationInterval = simulationInterval;
	}


	/**
	 * @return the bufferSize
	 */
	public int getBufferSize() {
		return bufferSize;
	}


	/**
	 * @param bufferSize the bufferSize to set
	 */
	private void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}


	/**
	 * @return the gridSize
	 */
	public int getGridSize() {
		return gridSize;
	}


	/**
	 * @param gridSize the gridSize to set
	 */
	private void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}


	/**
	 * @return the separateSimulationThread
	 */
	public boolean isSeparateSimulationThread() {
		return separateSimulationThread;
	}


	/**
	 * @param separateSimulationThread the separateSimulationThread to set
	 */
	private void setSeparateSimulationThread(boolean separateSimulationThread) {
		this.separateSimulationThread = separateSimulationThread;
	}


	/**
	 * @return the displayRate
	 */
	public int getDisplayRate() {
		return displayRate;
	}


	/**
	 * @param displayRate the displayRate to set
	 */
	private void setDisplayRate(int displayRate) {
		this.displayRate = displayRate;
	}


	/**
	 * @return the initiative
	 */
	public Initiative getInitiative() {
		return initiative;
	}


	/**
	 * @param initiative the initiative to set
	 */
	private void setInitiative(Initiative initiative) {
		this.initiative = initiative;
	}
}
