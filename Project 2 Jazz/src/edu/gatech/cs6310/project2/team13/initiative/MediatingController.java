package edu.gatech.cs6310.project2.team13.initiative;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import EarthSim.MediatingVisualizationPanel;
import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class MediatingController extends Controller {
	
	private Timer timer;

	public MediatingController(MediatingGenerator g, MediatingVisualizationPanel c, DataBuffer b, boolean dT, EarthGrid eg) {
		super(g, c, b, dT, eg);
		Logging.turnOnLogging();
		Logging.writeOut("Enter");
		
		Logging.writeOut("Exit");
	}

	private int i=0;
	
	public void startInSingleThread() {
		Logging.writeOut("Enter");
		stopSignalled=false;
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
		Logging.writeOut("Enter");
		stopSignalled=false;
		i=1;
		while(!stopSignalled) {
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
		
		((MediatingGenerator) this.getGenerator()).execute(this);
		
		if(this.getDataIsReady()){
			((MediatingVisualizationPanel) this.getConsumer()).goGetData(this.getGenerator());
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
