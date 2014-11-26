package edu.gatech.cs6310.project2.team13.initiative;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Consumer;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class PullingConsumerController extends Controller implements Consumer {
	
	private boolean stopSignalled = false;

	public PullingConsumerController(PullingGenerator g, PullingConsumerController c, DataBuffer b, boolean dT, EarthGrid eg) {
		super(g, c, b, dT, eg);
		Logging.writeOut("Enter");
		this.setConsumerToSelf();
		Logging.writeOut("Exit");
	}
	private void setConsumerToSelf(){
		Logging.writeOut("Enter");
		super.setConsumer(this);
		Logging.writeOut("Exit");
	}

	@Override
	public void start() {
		stopSignalled=false;
		Logging.writeOut("Enter");
		
		int i=1;
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
		
		((PullingGenerator) this.getGenerator()).execute(this);
		
		if(this.getDataIsReady()){
			((PullingConsumerController) this.getConsumer()).goGetData(this.getGenerator());
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
	public void goGetData(Worker w) {
		Logging.writeOut("Enter");
		
		Logging.writeOut("Getting Data from Generator");
		PullingGenerator g = (PullingGenerator) w;
		g.getData();
		
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
