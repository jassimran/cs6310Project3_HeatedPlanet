package scripts;

import java.util.Date;

import services.PersistenceService;
import simplesimulation.SimpleSimulationEngineImpl;
import simulation.SimulationSettings;
import simulation.SimulationSettingsFactory;
import controllers.AbstractControl;
import controllers.AbstractControlFactory;
import domain.Simulation;
import events.EventType;
import events.Listener;

public class SimulationDriver implements Listener {

	private AbstractControl control;

	private SimulationSettings simulationSettings;

	// metrics
	private long simulationStart;

	public SimulationDriver() {
		// create control
		control = AbstractControlFactory.getInstance().createControl(
				AbstractControlFactory.Q);
		control.addListener(this);
	}

	public void runSimulation(SimulationSettings simulationSettings) {
		// store simulation settings
		this.simulationSettings = simulationSettings;

		// create simulation engine
		AbstractControl.setSimulationEngine(new SimpleSimulationEngineImpl(
				simulationSettings));

		// run simulation
		simulationStart = (new Date()).getTime();
		control.runSimulation(simulationSettings);
	}

	@Override
	public void notify(EventType e) {
		if (e == EventType.SimulationFinishedEvent) {
			// get simulation time stamp
			long simulationEnd = (new Date()).getTime();

			// get simulation
			Simulation simulation = PersistenceService.getInstance()
					.findBySimulationName(simulationSettings.getName());

			// perform garbage collection
			Runtime.getRuntime().gc();

			// print simulation metrics
			System.out.println("Simulation name: " + simulation.getName());
			System.out.println("Total number of simulations: "
					+ simulation.getTimeStepList().size());
			System.out.println("Used memory in bytes: "
					+ (double) (Runtime.getRuntime().totalMemory() - Runtime
							.getRuntime().freeMemory()) / (1024 * 1024));
			System.out.println("Time in millis: "
					+ (simulationEnd - simulationStart));

			// remove simulation
			PersistenceService.getInstance().deleteSimulation(simulation);
		}
	}

	@Override
	public void addListener(Listener l) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeListener(Listener l) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {

		// Printing start time:
		System.out.println("-------------------------");
		System.out.println("Start date: " + new Date());
		System.out.println("Executing Test: " + args[0] + "," + args[1] + ","
				+ args[2] + "," + args[3] + "," + args[4] + "," + args[5] + ","
				+ args[6] + "," + args[7] + "," + args[8]);

		// Creating simulation driver
		SimulationDriver simulationDriver = new SimulationDriver();

//		simulationDriver.runSimulation(createSimulations(
//				Integer.parseInt(args[0]), Integer.parseInt(args[1]),
//				Integer.parseInt(args[2]), Integer.parseInt(args[3]),
//				Integer.parseInt(args[4]), Integer.parseInt(args[5]),
//				Double.parseDouble(args[6]), Double.parseDouble(args[7]),
//				args[8]));

		System.out.println("End date: " + new Date());
	}

	public static SimulationSettings createSimulations(int gridSpacing,
			int simulationTimeStep, int simulationLength, int temporalAccuracy,
			int geoAccuracy, int precision, double tilt, double eccentricity,
			String name) {
		SimulationSettings simulation1 = SimulationSettingsFactory
				.createSimulationSettingsWithDefaults();
		try {

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

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return simulation1;

	}

}