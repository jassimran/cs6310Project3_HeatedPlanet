package EarthSim;

import EarthSim.presentation.Presentation;
import EarthSim.simulation.Simulation;
import EarthSim.utils.Arguments;

public class Master implements Runnable {
    private Arguments arguments;

    private volatile boolean pause;
    private volatile boolean stop;

    private Simulation simulation;
    private Presentation presentation;

    public Master(Arguments arguments, Simulation simulation,
	    Presentation presentation) {
	this.arguments = arguments;

	pause = false;
	stop = false;

	this.simulation = simulation;
	this.presentation = presentation;
    }

    @Override
    public void run() {
	boolean simulationComplete = false;
	boolean presentationComplete = false;

	while ((!simulationComplete || !presentationComplete) && !stop) {
	    if (pause) {
		System.out.println("Pausing master");
		try {
		    Thread.sleep(arguments.pauseSleepRate);
		} catch (InterruptedException ie) {
		    // Do nothing
		}
	    } else {
		// We are running the simulation on its own thread so we
		// don't need to control it from here, otherwise we
		// need to produce
		simulationComplete = arguments.runSimulationOwnThread ? true
			: !simulation.produce();

		// We are running the presentation on its own thread so we
		// don't need to control it from here, otherwise we
		// need to consume
		presentationComplete = arguments.runPresentationOwnThread ? true
			: !presentation.consume();

		System.out.println("Master: " + simulationComplete + " : "
			+ presentationComplete);
		try {
		    System.out.println("Sleeping simulation after putting");
		    Thread.sleep(arguments.masterSleepRate);
		    System.out
			    .println("After Sleeping simulation after putting");
		} catch (InterruptedException ie) {
		    // Do nothing
		}
	    }
	}

	System.out.println("Exiting Master thread");
    }

    public void start() {
	pause = false;
	stop = false;
    }

    public void pause() {
	pause = true;
    }

    public void resume() {
	pause = false;
    }

    public void stop() {
	System.out.println("Stopping master.");
	stop = true;
    }
}
