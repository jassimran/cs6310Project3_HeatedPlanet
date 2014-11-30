package scripts;

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
			// get simulation time stamp
			long simulationEnd = (new Date()).getTime();
			
			// get simulation
			Simulation simulation = PersistenceService.getInstance().findBySimulationName(simulationSettings.getName());
			
			// perform garbage collection
			Runtime.getRuntime().gc();
			
			// print simulation metrics
			System.out.println("Simulation name: " + simulation.getName());
			System.out.println("Total number of simulations: " + simulation.getTimeStepList().size());
			System.out.println("Used memory in bytes: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024));
			System.out.println("Time in millis: " + (simulationEnd - simulationStart));
			
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