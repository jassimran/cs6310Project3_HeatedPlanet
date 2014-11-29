package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class InterpolationService {
	
	private static final int RADIUS_OF_INTEREST = 3;
	
	// singleton instance
	private static InterpolationService serviceInstance;
	
	// used services
	private SimulationService simulationService;
	private AccuracyService accuracyService;
		
	private InterpolationService() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized InterpolationService getInstance() {
		if(serviceInstance == null) {
			serviceInstance = new InterpolationService();
			serviceInstance.simulationService = SimulationService.getInstance();
			serviceInstance.accuracyService = AccuracyService.getInstance();
		}
		return serviceInstance;
	}
	
	/**
	 * It gets the average between the grid 1 and grid2 and that average is saved into the resultGrid. 
	 * Grid1's index will always be less than Grid2's index and they should atleast differ by 1. Index 
	 * of Result Grid is going to be the floor of average of Grid1's index and Grid2's index. Number of 
	 * cells in grid1 and grid2 must be same.
	 * @param grid1
	 * @param grid2
	 * @param timeStep in minutes
	 * @return resultGrid
	 */
	public EarthGrid interpolate(EarthGrid grid1, EarthGrid grid2, int timeStep ){
		// Create new Grid
		EarthGrid newGrid = new EarthGrid();
		
		// Calculate the new Index
		int newIndex = (grid1.getIndex()+grid2.getIndex())/2;
		newGrid.setIndex(newIndex);
		
		// Calculate new Date
		Calendar cal = Calendar.getInstance();
		cal.setTime(grid1.getSimulatedDate());
		cal.add(Calendar.MINUTE, timeStep * (newIndex-grid1.getIndex()));
		newGrid.setSimulatedDate(cal.getTime());
		
		// Interpolate cells
		List<EarthCell> earthCells = new ArrayList<EarthCell>();
		for(int i = 0; i < grid1.getNodeList().size(); i++){
			EarthCell cell1 = grid1.getNodeList().get(i);
			EarthCell cell2 = grid2.getNodeList().get(i);
			EarthCell newCell = new EarthCell();
			newCell.setRow(cell1.getRow());
			newCell.setColumn(cell1.getColumn());
			double temp = (cell1.getTemperature()+cell2.getTemperature())/2;
			newCell.setTemperature(temp);
			newCell.setGrid(newGrid);
			earthCells.add(newCell);
		}
		// Set cells in new Grid
		newGrid.setNodeList(earthCells);
		
		return newGrid;
	}
	
	/**
	 * Interpolates the given simulation to full-temporal resolution.
	 * @param simulation the simulation to interpolated
	 * @return the simulation in full-temporal resolution
	 */
	public void performTemporalInterpolation(Simulation simulation) {
		// get grid list
		List<EarthGrid> list = simulation.getTimeStepList();
		
		// create index-based comparator
		Comparator<EarthGrid> comp = new EarthGridComparator();
		
		// sort initial list
		Collections.sort(list, comp);
		
		// calculate the number of grids in full resolution
		int totalGrids = simulationService.calculateSimulationLength(simulation.getLength(), simulation.getTimeStep());
		
		// calculate current gap size
		int gapSize = accuracyService.calculateGapSize(totalGrids, simulation.getTemporalAccuracy());
		
		// interpolate to full-res
		while(list.size() < (totalGrids - gapSize)) {
			List<EarthGrid> newGrids = new ArrayList<EarthGrid>();
			for(int i = 0; i< list.size()-1; i++) {
				EarthGrid grid1 = list.get(i);
				EarthGrid grid2 = list.get(i+1);
				if(grid1.getIndex() < grid2.getIndex()-1) {
					EarthGrid newGrid = interpolate(grid1, grid2, simulation.getTimeStep());
					newGrid.setSimulation(simulation);
					newGrids.add(newGrid);	
				}		
			}
			list.addAll(newGrids);
			Collections.sort(list, comp);
		}
		
		simulation.setTimeStepList(list);
	}
	
	private class EarthGridComparator implements Comparator<EarthGrid>{

		@Override
		public int compare(EarthGrid grid1, EarthGrid grid2) {
			Integer index1 = grid1.getIndex();
			Integer index2 = grid2.getIndex();
			
			return index1.compareTo(index2);
		}
		
	}

	public void performGeographicInterpolation(EarthGrid earthGrid) {
		// get simulation & cells list
		Simulation simulation = earthGrid.getSimulation();
		List<EarthCell> earthCells = earthGrid.getNodeList();
		
		// calculate total cells
		int totalCells = simulation.getNumberOfRows() * simulation.getNumberOfColumns();
		int numRows = simulation.getNumberOfRows();
		int numCols = simulation.getNumberOfColumns();
		
		// calculate values for cells not present
		while( earthCells.size() < totalCells) {
			for(int y = 0; y < numRows; y++) {
				for(int x = 0; x < numCols; x++) {
					EarthCell currentCell = getEarthCell(earthCells, x, y);
					if(currentCell == null){
						EarthCell earthCell = new EarthCell();
						earthCell.setColumn(x);
						earthCell.setRow(y);
						earthCell.setGrid(earthGrid);
						
						// get neighbors within area of interest
						List<EarthCell> neighbors = simulationService.getNeighbors(earthCell, RADIUS_OF_INTEREST, numCols);
						
						// get average temperature
						double temp = simulationService.calculateAverageTemperature(neighbors);
						
						earthCell.setTemperature(temp);
						earthCell.setGrid(earthGrid);
						earthCells.add(earthCell);
					}
				}
			}
		}
	}
	
	private EarthCell getEarthCell(List<EarthCell> nodeList, int x, int y) {
		for(EarthCell cell : nodeList){				
			if(cell.getRow() == y && cell.getColumn() == x)
				return cell;
		}
		return null;
	}
}
