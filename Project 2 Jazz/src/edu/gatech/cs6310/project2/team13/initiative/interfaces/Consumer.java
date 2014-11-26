package edu.gatech.cs6310.project2.team13.initiative.interfaces;

import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;

public interface Consumer {
	/**
	 * Abstract method to call when the data is ready
	 */
	public abstract void goGetData(Worker w);
}
