package simulation;

public class SimulationSettingsFactory {

	
	public static SimulationSettings createSimulationSettingsWithDefaults() {
		// create simulation settings
		SimulationSettings simulationSettings = new SimulationSettings();

		// set defaults
		simulationSettings.setGridSpacing(15); // 15 degrees; the size of a time zone
		simulationSettings.setNumCellsX(24); // 360 / grid spacing
		simulationSettings.setNumCellsY(12); // 180 / grid spacing
		simulationSettings.setSimulationTimeStep(1440); // 1 solar day in minutes
		simulationSettings.setPresentationDisplayRate(1000); //1000 milliseconds
		simulationSettings.setSimulationLength(12); // 12 months
		simulationSettings.setTemporalAccuracy(100); // 100 percent
		simulationSettings.setGeoAccuracy(100); // 100 percent
		simulationSettings.setPrecision(7); // the number of digits storable in a float
		simulationSettings.setName("Default Simulation");
		simulationSettings.setEccentricity(0.0167);  // The eccentricity of the Earth
		simulationSettings.setAxialTilt(23.44); // The tilt of the Earth
		simulationSettings.setBufferSize(1); 
		simulationSettings.setSOption(true);
		simulationSettings.setPOption(false);
		simulationSettings.setROption(false);
		simulationSettings.setTOption(false);
		
		return simulationSettings;		
	}
	
}
