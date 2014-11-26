package edu.gatech.cs6310.project2.team13.initiative;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.data.EarthTemperatureGrid;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Generator;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class MediatingGenerator extends Worker implements Generator {

	private EarthGrid eg;
	private EarthTemperatureGrid etg;
	
	public MediatingGenerator(EarthGrid eg, DataBuffer b) {		
		super(b);
		this.eg = eg;
	}

	public EarthTemperatureGrid getData() {
		Logging.writeOut("Invoked");
		return etg;
	}

	public void execute(Controller c) {
		Logging.writeOut("Enter");
		MediatingController controller = (MediatingController) c;
		
		Logging.writeOut("Running Simulation");
		etg = eg.runSimulationInstance();
		controller.dataReady();
		Logging.writeOut("Exit");
	}

}
