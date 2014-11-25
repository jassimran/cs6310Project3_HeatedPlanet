package simulation;

public class SimulationSettingsFactory {

	
	public static SimulationSettings createSimulationSettingsWithDefaults() {
		// create simulation settings
		SimulationSettings simulationSettings = new SimulationSettings();
		
		// set defaults
		simulationSettings.setGridSpacing(15);
		simulationSettings.setNumCellsX(24); // 360 / grid spacing
		simulationSettings.setNumCellsY(12); // 180 / grid spacing
		simulationSettings.setSimulationTimeStep(1440);
		simulationSettings.setPresentationDisplayRate(1000);
		simulationSettings.setSimulationLength(12);
		simulationSettings.setTemporalAccuracy(100);
		simulationSettings.setGeoAccuracy(100);
		simulationSettings.setPrecision(7);
		simulationSettings.setName("Default Simulation");
		simulationSettings.setEccentricity(0.0167);
		simulationSettings.setAxialTilt(23.44);
		
		return simulationSettings;		
	}
	
}
