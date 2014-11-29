package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import domain.EarthCell;

public class SimulationService {

	// singleton instance
	private static SimulationService serviceInstance;
		
	private SimulationService() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized SimulationService getInstance() {
		if(serviceInstance == null) {
			serviceInstance = new SimulationService();
		}
		return serviceInstance;
	}
	
	/**
	 * Calculates the number of grids required to complete a simulation 
	 * based on the the number of months to simulate and the time step to use.
	 * @param months months between 1 and 1200
	 * @param timeStep number of minutes between 1 and 1440 (1 day)
	 * @return the number of grids to produce in a simulation
	 */
	public synchronized int calculateSimulationLength(int months, int timeStep) {
		Calendar calendar = Calendar.getInstance();
		
		// calculate base time (12:00 PM, January 4, 2014)
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 4);
		calendar.set(Calendar.YEAR, 2014);
		long baseTime = calendar.getTimeInMillis();
		
		// calculate target time
		calendar.add(Calendar.MONTH, months);
		long targetTime = calendar.getTimeInMillis();
		
		// calculate number of grids
		int numberOfgrids = 0;
		long simulationTime = baseTime;
		while(simulationTime < targetTime) {
			calendar.setTimeInMillis(simulationTime);
			calendar.add(Calendar.MINUTE, timeStep);
			simulationTime = calendar.getTimeInMillis();
			numberOfgrids++;
		}
		
		return numberOfgrids;
	}
	
	public synchronized int calculateSimulationMonths(Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		
		// calculate base time (12:00 PM, January 4, 2014)
		startCalendar.set(Calendar.HOUR_OF_DAY, 12);
		startCalendar.set(Calendar.MINUTE, 00);
		startCalendar.set(Calendar.SECOND, 00);
		startCalendar.set(Calendar.MILLISECOND, 00);
		startCalendar.set(Calendar.MONTH, Calendar.JANUARY);
		startCalendar.set(Calendar.DAY_OF_MONTH, 4);
		startCalendar.set(Calendar.YEAR, 2014);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		
		return diffMonth + 1;
	}
	
	/**
	 * Returns the EarthCell in the given row and column.
	 * @param earthCells the list of EarthCells
	 * @param col the column
	 * @param row the row
	 * @return the EarthCell in the given row and column, or null if not found
	 */
	public EarthCell getEarthCell(List<EarthCell> earthCells, int col, int row, int numCols) {
		EarthCell earthCell = null;

		int colToUse = col;
		if(numCols > 0 && colToUse < 0)
			colToUse = numCols + col - 1;
		
		for(EarthCell e : earthCells) {
			if(e.getColumn() == col && e.getRow() == row) {
				earthCell = e;
				break;
			}
		}
		
		return earthCell;
	}

	/**
	 * @return a list of EarthCells within the given radius of the given earth cell
	 */
	public List<EarthCell> getNeighbors(EarthCell earthCell, int radius, int numCols) {
		// get cells in the same grid
		List<EarthCell> earthCells = earthCell.getGrid().getNodeList();
		
		// get row and column
		int row = earthCell.getRow();
		int col = earthCell.getColumn();
		
		// get neighbors
		List<EarthCell> neighbors = new ArrayList<EarthCell>();
		for(int y = row - radius; y <= row + radius; y ++) {
			for(int x = col - radius; x <= col + radius; x ++) {
				if(x == col && y == row) {
					continue;
				}
				
				EarthCell neighbor = getEarthCell(earthCells, x, y, numCols);
				if(neighbor != null) {
					neighbors.add(neighbor);
				}
			}
		}
		
		// return results
		return neighbors;
	}

	/**
	 * @return the average temperature of the given list of EarthCells
	 */
	public double calculateAverageTemperature(List<EarthCell> earthCells) {
		double totalTemp = 0;
		
		for(EarthCell earthCell : earthCells) {
			totalTemp += earthCell.getTemperature();
		}
		
		return totalTemp / earthCells.size();
	}
}
