package EarthSim.utils;
// Temporary
public class MemoryThread implements Runnable {

    private volatile boolean stop;
    
    public MemoryThread() {
	stop = false;
    }
    
    @Override
    public void run() {
	// TODO Auto-generated method stub
	
	
	while (!stop) {
	    
	    Runtime runtime = Runtime.getRuntime();
	 	runtime.gc();
	 	double memory = (double)(runtime.totalMemory() - runtime.freeMemory());
	 	
	 	System.out.println("Memory: " + memory);
	 	
	 	try {
	 	  Thread.sleep(1000);  
	 	} catch (InterruptedException ie) {
	 	    
	 	}
	}
	
	System.out.println("Exiting memory thread.");
	
    }
    
    public void stop() {
	stop = true;
    }
    
}
