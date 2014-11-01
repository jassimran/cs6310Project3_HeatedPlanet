package presentation;

import presentation.earth.TemperatureGrid;

public interface PresentationEngine {
	
	public void renderSimulationStep(TemperatureGrid inputGrid);
	
	public void moveSunPosition(float degrees);
	
}
