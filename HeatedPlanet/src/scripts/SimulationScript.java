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
		
		// create simulations
		simulations.add(createSimulations(180,259200,12,100,100,7,23.44,0.0167,"R13.8"));
		simulations.add(createSimulations(15,259200,12,100,100,7,23.44,0.0167,"R13.4,R5.7"));
		simulations.add(createSimulations(90,43200,12,100,100,7,23.44,0.0167,"R13.7"));
		simulations.add(createSimulations(15,43200,12,100,100,7,23.44,0.0167,"R13.3"));
		simulations.add(createSimulations(15,43200,12,100,100,7,23.44,0.0167,"R5.6"));
		simulations.add(createSimulations(15,2880,12,100,100,7,23.44,0.0167,"R5.5"));
		simulations.add(createSimulations(15,1500,1,100,100,7,23.44,0.0167,"R7.1"));
		simulations.add(createSimulations(15,1500,2,100,100,7,23.44,0.0167,"R7.2"));
		simulations.add(createSimulations(15,1500,6,100,100,7,23.44,0.0167,"R7.3"));
		simulations.add(createSimulations(180,1500,12,100,100,7,23.44,0.0167,"R3.7"));
		simulations.add(createSimulations(90,1500,12,100,100,7,23.44,0.0167,"R3.6"));
		simulations.add(createSimulations(30,1500,12,100,100,7,23.44,0.0167,"R13.6"));
		simulations.add(createSimulations(15,1500,12,1,1,0,23.44,0.0167,"R14.11"));
		simulations.add(createSimulations(15,1500,12,1,1,7,23.44,0.0167,"R14.6"));
		simulations.add(createSimulations(15,1500,12,1,100,7,23.44,0.0167,"R9.1"));
		simulations.add(createSimulations(15,1500,12,25,25,2,23.44,0.0167,"R14.12"));
		simulations.add(createSimulations(15,1500,12,25,25,7,23.44,0.0167,"R14.7"));
		simulations.add(createSimulations(15,1500,12,25,100,7,23.44,0.0167,"R9.2"));
		simulations.add(createSimulations(15,1500,12,50,50,4,23.44,0.0167,"R14.13"));
		simulations.add(createSimulations(15,1500,12,50,50,7,23.44,0.0167,"R14.8"));
		simulations.add(createSimulations(15,1500,12,50,100,7,23.44,0.0167,"R9.3"));
		simulations.add(createSimulations(15,1500,12,75,75,7,23.44,0.0167,"R14.14,R14.9"));
		simulations.add(createSimulations(15,1500,12,75,100,7,23.44,0.0167,"R9.4"));
		simulations.add(createSimulations(15,1500,12,100,1,7,23.44,0.0167,"R9.6"));
		simulations.add(createSimulations(15,1500,12,100,25,7,23.44,0.0167,"R9.7"));
		simulations.add(createSimulations(15,1500,12,100,50,7,23.44,0.0167,"R9.8"));
		simulations.add(createSimulations(15,1500,12,100,75,7,23.44,0.0167,"R9.9"));
		simulations.add(createSimulations(15,1500,12,100,100,0,23.44,0.0167,"R14.1,R9.11"));
		simulations.add(createSimulations(15,1500,12,100,100,2,23.44,0.0167,"R14.2,R9.12"));
		simulations.add(createSimulations(15,1500,12,100,100,4,23.44,0.0167,"R14.3,R9.13"));
		simulations.add(createSimulations(15,1500,12,100,100,7,-180,0,"R12.15"));
		simulations.add(createSimulations(15,1500,12,100,100,7,-180,0.0167,"R1.6,R12.8"));
		simulations.add(createSimulations(15,1500,12,100,100,7,-60,0.0167,"R1.7"));
		simulations.add(createSimulations(15,1500,12,100,100,7,-45,0.0167,"R12.16,R12.9"));
		simulations.add(createSimulations(15,1500,12,100,100,7,0,0.0167,"R1.8,R12.10"));
		simulations.add(createSimulations(15,1500,12,100,100,7,0,0.1,"R12.17"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0,"R1.1,R12.1"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.0167,"R1.2,R1.9"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.0167,"R11.1,R11.2,R11.3,R11.4,R11.5,R12.11,R12.2,R14.10,R14.4,R3.5,R7.4,R9.10,R9.14,R9.5"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.1,"R12.3"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.3,"R12.18"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.3,"R12.4"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.4,"R1.3"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.5,"R12.5"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.8,"R1.4"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,0.8,"R12.6"));
		simulations.add(createSimulations(15,1500,12,100,100,7,23.44,1,"R1.5,R12.7"));
		simulations.add(createSimulations(15,1500,12,100,100,7,45,0.0167,"R12.12"));
		simulations.add(createSimulations(15,1500,12,100,100,7,45,0.5,"R12.19"));
		simulations.add(createSimulations(15,1500,12,100,100,7,60,0.0167,"R1.10"));
		simulations.add(createSimulations(15,1500,12,100,100,7,90,0.0167,"R12.13"));
		simulations.add(createSimulations(15,1500,12,100,100,7,90,0.8,"R12.20"));
		simulations.add(createSimulations(15,1500,12,100,100,7,180,0.0167,"R1.11,R12.14"));
		simulations.add(createSimulations(15,1500,12,100,100,7,180,1,"R12.21"));
		simulations.add(createSimulations(15,1500,12,100,100,14,23.44,0.0167,"R14.15,R14.5,R9.15"));
		simulations.add(createSimulations(10,1500,12,100,100,7,23.44,0.0167,"R3.4"));
		simulations.add(createSimulations(5,1500,12,100,100,7,23.44,0.0167,"R3.3"));
		simulations.add(createSimulations(3,1500,12,100,100,7,23.44,0.0167,"R3.2"));
		simulations.add(createSimulations(15,1500,60,100,100,7,23.44,0.0167,"R7.5"));
		simulations.add(createSimulations(15,1500,120,100,100,7,23.44,0.0167,"R7.6"));
		simulations.add(createSimulations(15,720,12,100,100,7,23.44,0.0167,"R13.1,R13.1,R5.3,R5.4"));
		simulations.add(createSimulations(15,360,12,100,100,7,23.44,0.0167,"R5.2"));
		simulations.add(createSimulations(15,60,12,100,100,7,23.44,0.0167,"R5.1"));
				
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
