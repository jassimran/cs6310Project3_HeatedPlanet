package edu.gatech.cs6310.project2.team13.initiative.abstracts;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;

public abstract class Worker {
	private DataBuffer buffer = null;
	
	public Worker(DataBuffer b){
		buffer = b;
	}
	
	public DataBuffer getDataBuffer() {
		return buffer;
	}

	public void setDataBuffer(DataBuffer buffer) {
		this.buffer = buffer;
	}
	
}
