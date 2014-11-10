package simulation;

import presentation.earth.TemperatureGrid;

public interface SimulationEngine {
	
	public TemperatureGrid executeSimulationStep(SimulationSettings settings, int simulationTime, TemperatureGrid inputGrid);

}
