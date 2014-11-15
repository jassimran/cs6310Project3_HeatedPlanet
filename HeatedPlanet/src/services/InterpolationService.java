package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class InterpolationService {
	// singleton instance
	private static InterpolationService serviceInstance;
	
	// used services
	private SimulationService simulationService;
		
	private InterpolationService() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized InterpolationService getInstance() {
		if(serviceInstance == null) {
			serviceInstance = new InterpolationService();
			serviceInstance.simulationService = SimulationService.getInstance();
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
			earthCells.add(newCell);
		}
		// Set cells in new Grid
		newGrid.setNodeList(earthCells);
		
		return newGrid;
	}
	
	public List<EarthGrid> performTemporalInterpolation(Simulation simulation, List<EarthGrid> list){
		
		int noOfGrids = simulationService.calculateSimulaitonLenght(simulation.getLength(), simulation.getTimeStep());
		
		while(list.size() < noOfGrids){
			List<EarthGrid> newGrids = new ArrayList<EarthGrid>();
			for(int i = 0; i< list.size()-1; i++){
				EarthGrid grid1 = list.get(i);
				EarthGrid grid2 = list.get(i+1);
				if(grid1.getIndex() < grid2.getIndex()-1){
					//TODO: Interpolate
					
					EarthGrid newGrid = null;
					newGrids.add(newGrid);	
				}		
			}
			list.addAll(newGrids);
			//TODO: sort list
		}
		
		return list;
	}
}
