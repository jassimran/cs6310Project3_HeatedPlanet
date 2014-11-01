package simulation;

import presentation.earth.TemperatureGrid;

public interface SimulationEngine {
	
	public TemperatureGrid executeSimulationStep(TemperatureGrid inputGrid, int simulationTime, int timeStep);

}
