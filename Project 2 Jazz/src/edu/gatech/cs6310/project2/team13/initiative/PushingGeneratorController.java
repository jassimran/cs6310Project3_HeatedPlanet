package edu.gatech.cs6310.project2.team13.initiative;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import EarthSim.PushingVisualizationPanel;
import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.data.EarthTemperatureGrid;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Generator;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class PushingGeneratorController extends Controller implements Generator {
	
	private boolean stopSignalled = false;
	private EarthGrid eg;
	
	public PushingGeneratorController(EarthGrid eg, Worker g, Worker c, DataBuffer b, boolean dT) {
		super(g, c, b, dT, eg);
		this.eg = eg;
		Logging.writeOut("Enter");
		setGeneratorToSelf();
		Logging.writeOut("Exit");
	}
	private void setGeneratorToSelf(){
		Logging.writeOut("Enter");
		super.setGenerator(this);
		Logging.writeOut("Exit");
	}

	@Override
	public EarthTemperatureGrid getData() {
		Logging.writeOut("Invoked");
		return this.getDataBuffer().getNextData();
	}

	@Override
	public void execute(Controller c) {
		Logging.writeOut("Enter");
		
		PushingGeneratorController controller = (PushingGeneratorController) c;
		
		Logging.writeOut("Running Simulation");
		EarthTemperatureGrid etg = eg.runSimulationInstance();
		boolean result = getDataBuffer().addData(etg);
		while(!result) {
			Logging.writeOut("Waiting for buffer to free.");
			result = getDataBuffer().addData(etg);
		}
		controller.dataReady();		
		Logging.writeOut("Exit");
	}

	private int i=0;
	private Timer timer;
	
	public void startInSingleThread() {
		stopSignalled=false;
		Logging.writeOut("Enter");
		i=1;
        //Create a timer.
        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (stopSignalled) {
                    timer.stop();
                } else if(stopForTesting(i)) {
                	Logging.writeOut(false,"Maximum Iteration reached. Stopping simulation.");
    				timer.stop();
                } else {
                	Logging.writeOut(false,"Running iteration "+i);
                	i++;
                	startNext();
                }
            }
        });
        timer.setInitialDelay(1);
        timer.start();
		Logging.writeOut("Exit");
	}
	
	
	@Override
	public void start() {
		stopSignalled=false;
		Logging.writeOut("Enter");
		
		i=1;
		while (!stopSignalled) {
			if(stopForTesting(i)){
				Logging.writeOut(false,"Maximum Iteration reached. Stopping simulation.");
				this.stop();
				break;
			}
			Logging.writeOut(false,"Running iteration "+i);
			i++;
			this.startNext();
		}
		
		Logging.writeOut("Exit");
	}

	@Override
	protected void startNext() {
		Logging.writeOut("Enter");
		
		this.setDataIsReady(false);
		
		((PushingGeneratorController) this.getGenerator()).execute(this);
		
		if(this.getDataIsReady()){
			((PushingVisualizationPanel) this.getConsumer()).goGetData(this.getGenerator());
		}else{
			Logging.writeErr("Data was not ready");
		}
		
		Logging.writeOut("Exit");
	}

	@Override
	protected void dataReady() {
		Logging.writeOut("Enter");
		
		this.setDataIsReady(true);
		
		Logging.writeOut("Exit");
	}
	
	@Override
	public void stop() {
		Logging.writeOut("Enter");
		Logging.writeOut("Setting STOP Signal...");
		stopSignalled = true;
		Logging.writeOut("Exit");
		
	}

	

}
