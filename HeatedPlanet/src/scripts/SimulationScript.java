package scripts;

import java.util.ArrayList;
import java.util.List;

import simulation.SimulationSettings;
import simulation.SimulationSettingsFactory;

public class SimulationScript {

	public static void main(String[] args) {
		List<SimulationSettings> simulations = new ArrayList<SimulationSettings>();
		
		// define simulations
		 
		SimulationSettings bootStrapSimulation = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		bootStrapSimulation.setName("BootStrap Simulation");
		bootStrapSimulation.setSOption(false);
		simulations.add(bootStrapSimulation);
		
		//Create simulations using createSimulations function.
		simulations.add(createSimulations(15, 1500, 12, 100, 100, 7, 23.44, 0, "R1.2"));
		simulations.add(createSimulations(15, 1500, 12, 100, 100, 7, 23.44, .0167, "R1.2"));
		simulations.add(createSimulations(15, 1500, 12, 100, 100, 7, 23.44, .4, "R1.3"));
		simulations.add(createSimulations(15, 1500, 12, 100, 100, 7, 23.44, .8, "R1.4"));
		simulations.add(createSimulations(15, 1500, 12, 100, 100, 7, 23.44, 1, "R1.5"));
		
		
		
		/*
		
		SimulationSettings simulation1 = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		simulation1.setName("R1.1");
		simulation1.setGridSpacing(15);
		simulation1.setSimulationTimeStep(1500);
		simulation1.setSimulationLength(12);
		simulation1.setTemporalAccuracy(100);
		simulation1.setGeoAccuracy(100);
		simulation1.setPrecision(7);
		simulation1.setAxialTilt(23.44);
		simulation1.setEccentricity(0);
		simulation1.setSOption(false);
		simulations.add(simulation1);
		
		SimulationSettings simulation2 = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		simulation2.setName("R1.2");
		simulation2.setGridSpacing(15);
		simulation2.setSimulationTimeStep(1500);
		simulation2.setSimulationLength(12);
		simulation2.setTemporalAccuracy(100);
		simulation2.setGeoAccuracy(100);
		simulation2.setPrecision(7);
		simulation2.setAxialTilt(23.44);
		simulation2.setEccentricity(0.0167);
		simulation2.setSOption(false);
		simulations.add(simulation2);
		
		SimulationSettings simulation3 = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		simulation3.setName("R1.3");
		simulation3.setGridSpacing(15);
		simulation3.setSimulationTimeStep(1500);
		simulation3.setSimulationLength(12);
		simulation3.setTemporalAccuracy(100);
		simulation3.setGeoAccuracy(100);
		simulation3.setPrecision(7);
		simulation3.setAxialTilt(23.44);
		simulation3.setEccentricity(0.4);
		simulation3.setSOption(false);
		simulations.add(simulation3);
		
		
		SimulationSettings simulation4 = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		simulation4.setName("R1.4");
		simulation4.setGridSpacing(15);
		simulation4.setSimulationTimeStep(1500);
		simulation4.setSimulationLength(12);
		simulation4.setTemporalAccuracy(100);
		simulation4.setGeoAccuracy(100);
		simulation4.setPrecision(7);
		simulation4.setAxialTilt(23.44);
		simulation4.setEccentricity(0.8);
		simulation4.setSOption(false);
		simulations.add(simulation4);
		
		SimulationSettings simulation5 = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		simulation5.setName("R1.5");
		simulation5.setGridSpacing(15);
		simulation5.setSimulationTimeStep(1500);
		simulation5.setSimulationLength(12);
		simulation5.setTemporalAccuracy(100);
		simulation5.setGeoAccuracy(100);
		simulation5.setPrecision(7);
		simulation5.setAxialTilt(23.44);
		simulation5.setEccentricity(1);
		simulation5.setSOption(false);
		simulations.add(simulation5);*/
		
		// create simulation driver
		SimulationDriver simulationDriver = new SimulationDriver();
		
		// execute simulations
		for(SimulationSettings simulation : simulations) {
			simulationDriver.runSimulation(simulation);
		}
		
	}
	//function to create simulations
	public static SimulationSettings createSimulations(int gridSpacing, int simulationTimeStep, int simulationLength, int temporalAccuracy,
			int geoAccuracy, int precision, double tilt, double eccentricity, String name)
	{
		SimulationSettings simulation1 = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
		simulation1.setName(name);
		simulation1.setGridSpacing(gridSpacing);
		simulation1.setSimulationTimeStep(simulationTimeStep);
		simulation1.setSimulationLength(simulationLength);
		simulation1.setTemporalAccuracy(temporalAccuracy);
		simulation1.setGeoAccuracy(geoAccuracy);
		simulation1.setPrecision(precision);
		simulation1.setAxialTilt(tilt);
		simulation1.setEccentricity(eccentricity);
		simulation1.setSOption(false);
		
		
		return simulation1;
	}

}
