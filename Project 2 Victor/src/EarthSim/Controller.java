package EarthSim;

import EarthSim.buffer.Buffer;
import EarthSim.buffer.RBuffer;
import EarthSim.buffer.TBuffer;
import EarthSim.core.Grid;
import EarthSim.presentation.Presentation;
import EarthSim.simulation.Simulation;
import EarthSim.utils.Arguments;
import EarthSim.utils.MemoryThread;
import GUI.HeatedEarthGUI;

public class Controller {

    public Arguments arguments;
    
    private Thread simulationThread;
    private Thread presentationThread;
    private Thread masterThread;
    
    private Thread memoryThread;
    private MemoryThread memory;

    private Simulation simulation;
    private Presentation presentation;
    private Master master;

    Buffer<Grid> buffer;

    public Controller(Arguments arguments) {
	this.arguments = arguments;
    }

    public void init() {
	// Creating the shared memory object based on the initiative

	switch (arguments.initiative) {
	case T:
	    buffer = new TBuffer<Grid>(arguments.bufferSize);
	    break;
	case R:
	    buffer = new RBuffer<Grid>(arguments.bufferSize);
	    break;
	case NONE:
	default:
	    buffer = new Buffer<Grid>(arguments.bufferSize);
	    break;
	}

	// Creating the simulation object
	simulation = new Simulation(arguments, buffer);

	// Creating the presentation object
	presentation = new Presentation(arguments, buffer);
	
	// Creating master
	master = new Master(arguments, simulation, presentation);

	// Creating GUI
	HeatedEarthGUI gui = new HeatedEarthGUI(this);

	// Providing reference to UI
	presentation.gui = gui;
    }

    public void start() {
	// Checking arguments
	System.out.println("Starting with: " + arguments);

	// Creating simulation thread
	if (arguments.runSimulationOwnThread) {
	    simulationThread = new Thread(simulation);
	}

	// Starting simulation
	simulation.start();

	// Creating presentation thread
	if (arguments.runPresentationOwnThread) {
	    presentationThread = new Thread(presentation);
	}

	// Starting presentation
	presentation.start();

	// Starting master
	master.start();
	
	// Creating master thread
	masterThread = new Thread(master);

	// Starting threads

	if (simulationThread != null) {
	    System.out.println("Running simulation on its own thread");
	    simulationThread.start();
	}

	if (presentationThread != null) {
	    System.out.println("Running presentation on its own thread");
	    presentationThread.start();
	}

	if (masterThread != null) {
	    System.out.println("Running master");
	    masterThread.start();
	}
	
	memory = new MemoryThread();
	
	memoryThread = new Thread(memory);
	
	memoryThread.start();
	
    }

    public void pause() {
	try {
	simulation.pause();
	presentation.pause();
	master.pause();
	} 
	catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void resume() {
	simulation.resume();
	presentation.resume();
	master.resume();
    }

    public void stop() {
	//pause();
	
	simulation.stop();
	presentation.stop();
	master.stop();
	
	memory.stop();
	
	if (simulationThread != null) {
	    try {
		simulationThread.join();
	    } catch (InterruptedException e) {
		System.out.println("Simulation Thread");
		e.printStackTrace();
	    }
	}

	simulationThread = null;

	if (presentationThread != null) {
	    try {
		presentationThread.join();
	    } catch (InterruptedException e) {
		System.out.println("Presentation Thread");
		e.printStackTrace();
	    }
	}

	presentationThread = null;
	
	if (masterThread != null) {
	    try {
		masterThread.join();
	    } catch (InterruptedException e) {
		System.out.println("Master Thread");
		e.printStackTrace();
	    }
	}
	
	masterThread = null;
	
	if (memoryThread != null) {
	try {
	    memoryThread.join();
	}catch(InterruptedException ie) {
	    
	}
	}
	
	memoryThread = null;
	

	simulation.reset();
	presentation.reset();
    }
}
