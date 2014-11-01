package buffers;

import java.util.ArrayList;
import java.util.List;

import presentation.earth.TemperatureGrid;


public class BufferImplementation implements Buffer {
	
	int capacity;
	List<TemperatureGrid> grids;
	
	public BufferImplementation(int capacity) {
		this.capacity = capacity;
		grids = new ArrayList<TemperatureGrid>();
	}

	@Override
	public synchronized boolean isEmpty() {
		return grids.size() == 0;
	}

	@Override
	public synchronized boolean isFull() {
		return grids.size() == capacity;
	}

	@Override
	public synchronized void put(TemperatureGrid grid) {
		// check preconditions
		if(isFull()) {
			throw new RuntimeException("Precondition not met [buffer.size() == capacity]");
		}
		
		// append grid
		int size = grids.size();
		grids.add(grid);
		
		// check postconditions
		if(grids.size() != size + 1) {
			throw new RuntimeException("Postcondition not met [buffer.size() != @pre.size + 1]");
		}		
		if(grids.size() > capacity) {
			throw new RuntimeException("Postcondition not met [buffer.size() > capacity]");
		}		
	}

	@Override
	public synchronized TemperatureGrid get() {
		// check preconditions
		if(isEmpty()) {
			throw new RuntimeException("Precondition not met [buffer.size() == 0]");
		}
		
		// remove from buffer, and return
		int size = grids.size();
		TemperatureGrid grid = grids.get(0);
		grids.remove(0);
		
		// check postconditions
		if(grids.size() != size - 1) {
			throw new RuntimeException("Postcondition not met [buffer.size() != @pre.size - 1]");
		}		
		
		
		return grid;
	}
}