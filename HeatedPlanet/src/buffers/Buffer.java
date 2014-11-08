package buffers;

import presentation.earth.TemperatureGrid;


public interface Buffer {

	public void put(TemperatureGrid grid);

	public TemperatureGrid get();

	public boolean isEmpty();

	public boolean isFull();
}