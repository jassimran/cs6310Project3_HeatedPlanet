package edu.gatech.cs6310.project2.team13.buffer;

import edu.gatech.cs6310.project2.team13.data.EarthTemperatureGrid;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class DataBuffer {
	private int bufferSize = -1;
	private EarthTemperatureGrid[] dataBuffer = null;
	private int currentCount = -1;
	
	public DataBuffer(int size){
		bufferSize = size;
		dataBuffer = new EarthTemperatureGrid[size];
		currentCount = 0;
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
	public synchronized boolean isEmpty(){
		return currentCount == 0;
	}
	public synchronized boolean isFull(){
		return currentCount == bufferSize;
	}
	
	public synchronized EarthTemperatureGrid getNextData(){
		Logging.writeOut("Enter");
		if(!isEmpty()){
			EarthTemperatureGrid data = dataBuffer[0];
			for(int i = 0; i < currentCount-1; i++){
				dataBuffer[i] = dataBuffer[i+1];
			}
			dataBuffer[currentCount-1] = null;
			
			currentCount--;
			Logging.writeOut("Exit");
			return data;
		}else{
			Logging.writeOut("Exit");
			return null;
		}
	}
	public synchronized boolean addData(EarthTemperatureGrid grid){
		Logging.writeOut("Enter");
		if(!this.isFull()){
			dataBuffer[currentCount] = grid;
			currentCount++;
			Logging.writeOut("Adding grid to buffer 0 count = " + currentCount);
			Logging.writeOut("Exit");
			return true;
		}else{
			Logging.writeOut("Buffer is full");
			Logging.writeOut("Exit");
			return false;
		}
	}
}
