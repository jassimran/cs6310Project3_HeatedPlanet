package edu.gatech.cs6310.project2.team13.initiative;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Consumer;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class MediatingConsumer extends Worker implements Consumer {

	public MediatingConsumer(DataBuffer b) {
		super(b);
	}

	@Override
	public void goGetData(Worker w) {
		Logging.writeOut("Enter");
		Logging.writeOut("Getting Data from Generator");
		MediatingGenerator g = (MediatingGenerator) w;
		g.getData();
		
		Logging.writeOut("Exit");
	}

}
