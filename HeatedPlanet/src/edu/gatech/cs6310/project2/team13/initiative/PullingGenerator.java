package edu.gatech.cs6310.project2.team13.initiative;

import EarthSim.PullingVisualizationPanel;
import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.data.EarthTemperatureGrid;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Generator;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class PullingGenerator extends Worker implements Generator {

	private EarthGrid eg;
	private EarthTemperatureGrid etg;
	
	public PullingGenerator(EarthGrid eg, DataBuffer b) {
		super(b);
		this.eg = eg;
	}

	@Override
	public EarthTemperatureGrid getData() {
		Logging.writeOut("Invoked");
		return this.getDataBuffer().getNextData();
	}

	@Override
	public void execute(Controller c) {
		Logging.writeOut("Enter");		
		PullingVisualizationPanel controller = (PullingVisualizationPanel) c;		
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

}
