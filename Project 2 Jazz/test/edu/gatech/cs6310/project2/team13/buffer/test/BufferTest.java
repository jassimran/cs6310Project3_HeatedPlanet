package edu.gatech.cs6310.project2.team13.buffer.test;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthTemperatureGrid;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class BufferTest {

	public static void main(String[] args) {
		Logging.turnOnLogging();
		EarthTemperatureGrid retData = null;
		
		/*
		DataBuffer buffer1 = new DataBuffer(1);
		buffer1.addData(new EarthTemperatureGrid(1,1) );
		buffer1.addData(new EarthTemperatureGrid(1,1) );
		retData = buffer1.getNextData();
		logIsDataNull(retData);
		retData = buffer1.getNextData();
		logIsDataNull(retData);
		buffer1.addData(new EarthTemperatureGrid(1,1) );
		retData = buffer1.getNextData();
		logIsDataNull(retData);
		*/
		
		
		DataBuffer buffer5 = new DataBuffer(5);
		buffer5.addData(new EarthTemperatureGrid(1,1) );
		buffer5.addData(new EarthTemperatureGrid(2,2) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(1,1) );
		buffer5.addData(new EarthTemperatureGrid(2,2) );
		buffer5.addData(new EarthTemperatureGrid(3,3) );
		buffer5.addData(new EarthTemperatureGrid(4,4) );
		buffer5.addData(new EarthTemperatureGrid(5,5) );
		buffer5.addData(new EarthTemperatureGrid(6,6) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(1,1) );
		buffer5.addData(new EarthTemperatureGrid(2,2) );
		buffer5.addData(new EarthTemperatureGrid(3,3) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(2,1) );
		buffer5.addData(new EarthTemperatureGrid(3,1) );
		buffer5.addData(new EarthTemperatureGrid(4,4) );
		buffer5.addData(new EarthTemperatureGrid(5,5) );
		buffer5.addData(new EarthTemperatureGrid(6,6) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(1,1) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(2,2) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(3,3) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(4,4) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(5,5) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		buffer5.addData(new EarthTemperatureGrid(6,6) );
		retData = buffer5.getNextData();
		logIsDataNull(retData);
		
	}
	
	private static void logIsDataNull(EarthTemperatureGrid data){
		if(data == null){
			Logging.writeOut("Got Null Data");
		}else{
			Logging.writeOut("Got Data ("+data.getX()+","+data.getY()+")");
		}
	}
}
