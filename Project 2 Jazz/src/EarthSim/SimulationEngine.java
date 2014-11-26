package EarthSim;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.data.EarthGrid.GridSpacingException;
import edu.gatech.cs6310.project2.team13.initiative.MediatingController;
import edu.gatech.cs6310.project2.team13.initiative.MediatingGenerator;
import edu.gatech.cs6310.project2.team13.initiative.PullingGenerator;
import edu.gatech.cs6310.project2.team13.initiative.PushingGeneratorController;

// TODO: This is a work in progress
public class SimulationEngine implements Runnable {

	public static final int MEDIATING=1;
	public static final int PULLING=2;
	public static final int PUSHING=3;
	private Thread t;

	private int gridSpacing=0;
	private int timeInterval=0;
	private EarthGrid grid;
	
	private int controlMethod;
	private boolean doTerminate;
	protected boolean separateThread=false;
	private MediatingController mediatingController;
	private MediatingGenerator mediatingGenerator;
	private PullingVisualizationPanel pullingConsumerController;
	private PullingGenerator pullingGenerator;
	private PushingGeneratorController pushingGeneratorController;
	
	public PullingVisualizationPanel getCVP() {
		return this.pullingConsumerController;
	}
	
	public SimulationEngine(DataBuffer db, int gridSpacing, int displayRate, int timeInterval, boolean doTerminate) throws GridSpacingException {
		this.controlMethod = PULLING;
		this.gridSpacing = gridSpacing;
		this.timeInterval = timeInterval;
		this.doTerminate = doTerminate;
		this.grid = new EarthGrid(gridSpacing,timeInterval);
		pullingGenerator = new PullingGenerator(grid, db);
		pullingConsumerController = new PullingVisualizationPanel(pullingGenerator,null,db,gridSpacing, displayRate, timeInterval, doTerminate, grid);
	}
	
	public SimulationEngine(MediatingVisualizationPanel mvp, DataBuffer db, int gridSpacing, int timeInterval, boolean doTerminate) throws GridSpacingException {
		this.controlMethod = MEDIATING;
		this.gridSpacing = gridSpacing;
		this.timeInterval = timeInterval;
		this.doTerminate = doTerminate;
		this.grid = new EarthGrid(gridSpacing,timeInterval);
		mediatingGenerator = new MediatingGenerator(grid, db);			
		mediatingController = new MediatingController(mediatingGenerator, mvp, db,doTerminate, grid);
	}

	public SimulationEngine(PushingVisualizationPanel pvp, DataBuffer db, int gridSpacing, int timeInterval, boolean doTerminate) throws GridSpacingException {
		this.controlMethod = PUSHING;
		this.gridSpacing = gridSpacing;
		this.timeInterval = timeInterval;
		this.doTerminate = doTerminate;
		this.grid = new EarthGrid(gridSpacing,timeInterval);
		pushingGeneratorController = new PushingGeneratorController(grid,null,pvp,db,doTerminate);
	}

	
	public void stop() {
		switch(controlMethod) {
		case MEDIATING:
			mediatingController.stop();
			break;
		case PULLING: 
			pullingConsumerController.stop();;				
			break;
		case PUSHING:
			pushingGeneratorController.stop();
			break;
		}						
	}
	
   public void pause() {   
	   stop();
	   /*
        try {            
            switch(controlMethod) {      
                case MEDIATING:
                    mediatingController.wait();
                    break;
                case PULLING: 
                    pullingConsumerController.wait();            
                    break;
                case PUSHING:
                    pushingGeneratorController.wait();
                    break;
            } 
        } catch (InterruptedException ie) {            
            ie.printStackTrace();
        }*/
    }

   public void unpause() throws InterruptedException {
	   start(separateThread);
	   /*
       switch(controlMethod) {      
           case MEDIATING:
               mediatingController.notify();
               break;
           case PULLING: 
               pullingConsumerController.notify();          
               break;
           case PUSHING:
               pushingGeneratorController.notify();
               break;
       }*/
   }
   
	public void start(boolean separateThread) {
		this.separateThread = separateThread;
		if(separateThread) {
			t = new Thread(this);
			t.start();
		} else {
			run();
		}
	}

	@Override
	public void run() {
		switch(controlMethod) {
		case MEDIATING:
			if(separateThread)
				mediatingController.start();
			else
				mediatingController.startInSingleThread();
			break;
		case PULLING: 
			if(separateThread)
				pullingConsumerController.start();
			else
				pullingConsumerController.startInSingleThread();
			break;
		case PUSHING:
			if(separateThread)
				pushingGeneratorController.start();
			else
				pushingGeneratorController.startInSingleThread();
			break;
		}				
	}
	
	/**
	 * @return the gridSpacing
	 */
	public int getGridSpacing() {
		return gridSpacing;
	}

	/**
	 * @param gridSpacing the gridSpacing to set
	 */
	public void setGridSpacing(int gridSpacing) {
		this.gridSpacing = gridSpacing;
	}

	/**
	 * @return the timeInterval
	 */
	public int getTimeInterval() {
		return timeInterval;
	}

	/**
	 * @param timeInterval the timeInterval to set
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
}
