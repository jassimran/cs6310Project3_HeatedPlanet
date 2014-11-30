package scripts;

import java.io.*;
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
			// measure execution time
			long executionTime = (new Date()).getTime() - simulationStart;

			// measure file size
			String filePath = System.getProperty("user.home")
					+ System.getProperty("file.separator") + "heatedplanet"
					+ System.getProperty("file.separator") + "db.mv.db";
			File file = new File(filePath);
			long fileSize = (file.exists()) ? file.length() : 0;

			// get simulation
			Simulation simulation = PersistenceService.getInstance()
					.findBySimulationName(simulationSettings.getName());

			// measure memory
			Runtime.getRuntime().gc();
			double totalMemory = (double) (Runtime.getRuntime().totalMemory() - Runtime
					.getRuntime().freeMemory()) / (1024 * 1024);

			// print simulation metrics
			System.out.println("Simulation name: " + simulation.getName());
			System.out.println("Total number of simulations: "
					+ simulation.getTimeStepList().size());
			System.out.println("Used memory in bytes: " + totalMemory);
			System.out.println("File size in bytes: " + fileSize);
			System.out.println("Execution time in millis: " + executionTime);

			printToFile(simulation.getName(),
					simulationSettings.getGridSpacing(),
					simulationSettings.getSimulationTimeStep(),
					simulationSettings.getSimulationLength(),
					simulationSettings.getTemporalAccuracy(),
					simulationSettings.getGeoAccuracy(),
					simulationSettings.getPrecision(),
					simulationSettings.getAxialTilt(),
					simulationSettings.getEccentricity(), executionTime,
					totalMemory);

			// remove simulation
			PersistenceService.getInstance().deleteSimulation(simulation);

			System.out.println("After deleting simulation...");
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
		// System.out.println("Executing Test: " + args[0] + "," + args[1] + ","
		// + args[2] + "," + args[3] + "," + args[4] + "," + args[5] + "," +
		// args[6] + "," + args[7] + "," + args[8]);

		// Creating simulation driver
		SimulationDriver simulationDriver = new SimulationDriver();

		simulationDriver.runSimulation(createSimulations(
				Integer.parseInt(args[0]), Integer.parseInt(args[1]),
				Integer.parseInt(args[2]), Integer.parseInt(args[3]),
				Integer.parseInt(args[4]), Integer.parseInt(args[5]),
				Double.parseDouble(args[6]), Double.parseDouble(args[7]),
				args[8]));

		System.out.println("End date: " + new Date());

		System.exit(0);
	}

	public static SimulationSettings createSimulations(int gridSpacing,
			int simulationTimeStep, int simulationLength, int temporalAccuracy,
			int geoAccuracy, int precision, double tilt, double eccentricity,
			String name) {
		SimulationSettings simulation = SimulationSettingsFactory
				.createSimulationSettingsWithDefaults();
		try {
			simulation.setName(name);
			simulation.setGridSpacing(gridSpacing);
			simulation.setSimulationTimeStep(simulationTimeStep);
			simulation.setSimulationLength(simulationLength);
			simulation.setTemporalAccuracy(temporalAccuracy);
			simulation.setGeoAccuracy(geoAccuracy);
			simulation.setPrecision(precision);
			simulation.setAxialTilt(tilt);
			simulation.setEccentricity(eccentricity);
			simulation.setSOption(false);
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return simulation;
	}

	public void printToFile(String name, int gridSpacing,
			int simulationTimeStep, int simulationLength, int temporalAccuracy,
			int geoAccuracy, int precision, double tilt, double eccentricity,
			long totaltime, double memused) {
		String fileIdentifier = name;
		File mem_file = new File(System.getProperty("user.home")
				+ System.getProperty("file.separator") + "heatedplanet"
				+ System.getProperty("file.separator") + fileIdentifier
				+ ".csv");

		if (!mem_file.exists()) {
			try {
				mem_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				FileWriter writer = new FileWriter(mem_file.getAbsoluteFile(),
						true);

				writer.append("Grid Spacing");
				writer.append(',');
				writer.append("Eccentricity");
				writer.append(',');
				writer.append("Precision");
				writer.append(',');
				writer.append("Temporal Accuracy");
				writer.append(',');
				writer.append("Geographical Accuracy");
				writer.append(',');
				writer.append("Simulation Name");
				writer.append(',');
				writer.append("Simulation length");
				writer.append(',');
				writer.append("Tilt");
				writer.append(',');
				writer.append("Time taken");
				writer.append(',');
				writer.append("Memory used");
				writer.append("\n");

				writer.append("\"" + gridSpacing + "\"");
				writer.append(',');
				writer.append("\"" + simulationTimeStep + "\"");
				writer.append(',');
				writer.append("\"" + simulationLength + "\"");
				writer.append(',');
				writer.append("\"" + temporalAccuracy + "\"");
				writer.append(',');
				writer.append("\"" + geoAccuracy + "\"");
				writer.append(',');
				writer.append("\"" + precision + "\"");
				writer.append(',');
				writer.append("\"" + tilt + "\"");
				writer.append(',');
				writer.append("\"" + eccentricity + "\"");
				writer.append(',');
				writer.append("\"" + totaltime + "\"");
				writer.append(',');
				writer.append("\"" + memused + "\"");
				writer.append(',');
				writer.append("\n");

				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}