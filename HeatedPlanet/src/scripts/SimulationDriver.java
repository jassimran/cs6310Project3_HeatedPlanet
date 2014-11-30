package scripts;

import java.io.File;
import java.util.Date;

import services.PersistenceService;
import simplesimulation.SimpleSimulationEngineImpl;
import simulation.SimulationSettings;
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
		control = AbstractControlFactory.getInstance().createControl(AbstractControlFactory.Q);
		control.addListener(this);
	}
	
	public void runSimulation(SimulationSettings simulationSettings) {
		// store simulation settings
		this.simulationSettings = simulationSettings;
		
		// create simulation engine
		AbstractControl.setSimulationEngine(new SimpleSimulationEngineImpl(simulationSettings));
		
		// run simulation
		simulationStart = (new Date()).getTime();
		control.runSimulation(simulationSettings);
	}

	@Override
	public void notify(EventType e) {
		if(e == EventType.SimulationFinishedEvent) {
			// measure execution time
			long executionTime = (new Date()).getTime() - simulationStart;
			
			// measure file size
			String filePath = System.getProperty("user.home") + System.getProperty("file.separator") 
					+ "heatedplanet" + System.getProperty("file.separator") + "db.mv.db";
			File file = new File(filePath);
			long fileSize = (file.exists())? file.length() : 0;
			
			// get simulation
			Simulation simulation = PersistenceService.getInstance().findBySimulationName(simulationSettings.getName());
			
			// measure memory
			Runtime.getRuntime().gc();
			double totalMemory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
			
			// print simulation metrics
			System.out.println("Simulation name: " + simulation.getName());
			System.out.println("Total number of simulations: " + simulation.getTimeStepList().size());
			System.out.println("Used memory in bytes: " + totalMemory);
			System.out.println("File size in bytes: " + fileSize);
			System.out.println("Execution time in millis: " + executionTime);
			
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
	
}