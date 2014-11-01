package edu.gatech.cs6310.project2.team13.initiative.test;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.initiative.PushingConsumer;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class pushingTest {

	public static void main(String[] args) {
		Logging.turnOnLogging();
		Logging.writeOut("Starting Test");
		
		DataBuffer buffer = new DataBuffer(1);
		
		PushingConsumer pc = new PushingConsumer(buffer);
		/*
		PushingGeneratorController c = new PushingGeneratorController(null,pc, buffer);
		
		c.start();
		*/
		Logging.writeOut("Ending Test");
	}

}
