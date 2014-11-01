package simplesimulation;

import presentation.PresentationEngine;
import presentation.earth.EarthPanel;
import presentation.earth.TemperatureGrid;

public class SimplePresentationEngineImpl implements PresentationEngine {
	
	private EarthPanel earthPanel;
	
	public SimplePresentationEngineImpl(EarthPanel earthPanel) {
		this.earthPanel = earthPanel;
	}

	@Override
	public void renderSimulationStep(TemperatureGrid inputGrid) {
		// render sample simulation
		earthPanel.updateGrid(inputGrid);
	}

	@Override
	public void moveSunPosition(float degrees) {
		earthPanel.moveSunPosition(degrees);
	}

}
