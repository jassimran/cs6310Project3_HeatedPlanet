package edu.gatech.cs6310.project2.team13.initiative.abstracts;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.utils.Shared;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public abstract class Controller extends Worker {
	
	private Worker generator;
	private Worker consumer;
	private boolean dataIsReady = false;
	protected boolean stopSignalled = false;	
	protected boolean doTerminate;
	protected int maxIters = Shared.getMaxIters();
	private EarthGrid grid;
	
	public Controller(Worker g, Worker c, DataBuffer b, boolean dT, EarthGrid eg){
		super(b);
		this.setGenerator(g);
		this.setConsumer(c);
		this.setGrid(eg);
		this.doTerminate = dT;
	}
	
	protected boolean stopForTesting(int currentIter){
		if(doTerminate && currentIter > 1){
			if(grid.hasStabilized() || currentIter >= maxIters){
				return true;
			}
		}
		return false;
	}
	
	protected boolean getDataIsReady() {
		return dataIsReady;
	}
	protected void setDataIsReady(boolean dataIsReady) {
		this.dataIsReady = dataIsReady;
	}
	
	/**
	 * Abstract method to call to start the simulation
	 */
	public abstract void start();
	
	/**
	 * Abstract method to call to stop the simulation
	 */
	public abstract void stop();
	
	/**
	 * Abstract method to call when the system is ready to 
	 * start the next cycle
	 */
	protected abstract void startNext();
	
	/**
	 * Abstract method to call when the data is ready
	 */
	protected abstract void dataReady();

	protected Worker getGenerator() {
		return generator;
	}

	protected void setGenerator(Worker generator) {
		this.generator = generator;
	}

	protected Worker getConsumer() {
		return consumer;
	}

	protected void setConsumer(Worker consumer) {
		this.consumer = consumer;
	}

	/**
	 * @return the grid
	 */
	protected EarthGrid getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	protected void setGrid(EarthGrid grid) {
		this.grid = grid;
	}
}
