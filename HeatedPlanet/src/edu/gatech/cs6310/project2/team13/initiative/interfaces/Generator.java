package edu.gatech.cs6310.project2.team13.initiative.interfaces;

import edu.gatech.cs6310.project2.team13.data.EarthTemperatureGrid;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;

public interface Generator {
	/**
	 * Abstract method to return the data
	 */
	public abstract EarthTemperatureGrid getData();
	
	/**
	 * Abstract method to start execution of the simulation
	 */
	public abstract void execute(Controller c);
}
