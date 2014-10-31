package EarthSim.simulation;

import java.util.Random;

import EarthSim.buffer.Buffer;
import EarthSim.core.Earth;
import EarthSim.core.Grid;
import EarthSim.core.Producer;
import EarthSim.utils.Arguments;

public class Simulation implements Producer, Runnable {

    private final Buffer<Grid> buffer;
    private final Arguments arguments;

    private volatile boolean stop;
    private volatile boolean pause;

    private Earth earth;

    public Simulation(Arguments arguments, Buffer<Grid> buffer) {
	this.buffer = buffer;
	this.arguments = arguments;

	stop = true;
	pause = false;

	earth = new Earth(arguments);
    }

    @Override
    public void run() {
	while (!stop && !isSimulationComplete()) {
	    System.out.println("-------- Simulation pause: " + pause + " - stop " + stop);
	    
	    if (pause) {
		// Sleep thread
		try {
		    System.out.println("************* Sleeping simulation");
		    Thread.sleep(arguments.pauseSleepRate);
		} catch (InterruptedException ie) {
		    ie.printStackTrace();
		}
	    } else {
		System.out.println("Producing");
		produce();
	    }
	}
	
	System.out.println("Exiting Simulation thread");
    }

    public boolean produce() {
	if (!isSimulationComplete()) {
	    try {
		earth.iterate(arguments.timestep);

		buffer.put(earth.getTmpGrid());

		System.out.println("Sleeping simulation after putting");
		Thread.sleep(arguments.simulationSleepRate);
		System.out.println("After Sleeping simulation after putting");
	    } catch (InterruptedException ie) {
		// Do nothing
	    }

	    return true;
	}

	return false;
    }

    public boolean isSimulationComplete() {
	return stop;
    }
    
    public synchronized void start() {
	pause = false;
	stop = false;
    }

    public synchronized void pause() {
	pause = true;
    }

    public synchronized void resume() {
	pause = false;
    }

    public synchronized void stop() {
	System.out.println("Stopping simulation");
	
	stop = true;
    }
    
    public void reset() {
	
    }
}
