package EarthSim.presentation;

import EarthSim.buffer.Buffer;
import EarthSim.core.Consumer;
import EarthSim.core.Grid;
import EarthSim.utils.Arguments;
import GUI.HeatedEarthGUI;

public class Presentation implements Consumer, Runnable {

    public HeatedEarthGUI gui;

    private final Buffer<Grid> buffer;
    private final Arguments arguments;

    private volatile boolean stop;
    private volatile boolean pause;

    public Presentation(Arguments arguments, Buffer<Grid> buffer) {
	this.buffer = buffer;
	this.arguments = arguments;

	stop = true;
	pause = false;
    }

    @Override
    public void run() {
	while (!stop && !isPresentationComplete()) {
	    if (pause) {
		// Sleep thread
		try {
		    System.out.println("Sleeping presentation.");
		    Thread.sleep(arguments.pauseSleepRate);
		} catch (InterruptedException ie) {
		    ie.printStackTrace();
		}
	    } else {
		System.out.println("Consuming");
		consume();
	    }
	}
	
	System.out.println("Exiting Presentation thread");
    }

    public boolean consume() {
	if (!isPresentationComplete()) {
	    try {
		gui.updateMap(buffer.get());
		
		System.out.println("Consuming presentation.");
		Thread.sleep(arguments.presentationSleepRate);
		
		System.out.println("After sleeping Consuming presentation.");
	    } catch (InterruptedException ie) {
		// Do nothing
	    }

	    return true;
	}

	return false;
    }
    
    public boolean isPresentationComplete() {
	return stop;
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
	System.out.println("Stopping presentation");
	
	stop = true;
    }
    
    public void reset() {
	
    }
}
