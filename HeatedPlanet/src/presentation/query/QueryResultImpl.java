package presentation.query;

import java.util.ArrayList;
import java.util.List;

import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class QueryResultImpl implements QueryResult {
	
	private int numberOfRows;
	private int numberOfColumns;
	private List<QueryGrid> queryGrids;
	
	public QueryResultImpl(Simulation simulation){
		queryGrids = convertEarthGridsToQueryGrids(simulation.getTimeStepList());
	    numberOfColumns = 360 / simulation.getGridSpacing();      
	    numberOfRows = 180 / simulation.getGridSpacing();
	}

	public QueryCell getMinTempCell() {
		// TODO Compute the result and store it instead of calculating
		QueryCell minTempCell = null;

		for (QueryGrid grid : getQueryGrids()) {
			QueryCell currentGridMin = grid.getMinTempCell();
			if (minTempCell == null
					|| currentGridMin.getTemperature() < minTempCell
							.getTemperature())
				minTempCell = currentGridMin;
		}
		return minTempCell;
	}

	public QueryCell getMaxTempCell() {
		QueryCell maxTempCell = null;

		for (QueryGrid grid : getQueryGrids()) {
			QueryCell currentGridMax = grid.getMaxTempCell();
			if (maxTempCell == null
					|| currentGridMax.getTemperature() < maxTempCell
							.getTemperature())
				maxTempCell = currentGridMax;
		}
		return maxTempCell;
	}

	public List<QueryGrid> getQueryGrids() {
		return queryGrids;
	}

	/**
	 * Returns the mean temp over the time for each of the locations in the
	 * grid.  Requirement Number 4
	 */
	public List<QueryCell> getMeanTempOverTime() {
		List<QueryGrid> grids = getQueryGrids();
		List<QueryCell> resultCells = new ArrayList<QueryCell>();
		double[][] temps = new double[numberOfRows][numberOfColumns];
		for(int row = 0; row < numberOfRows; row++){
			for(int column = 0; column < numberOfColumns; column++){
				for(int gridIndex = 0; gridIndex < grids.size(); gridIndex++){
					QueryGrid grid = grids.get(gridIndex);
					QueryCell cell = grid.getQueryCell(row, column);
					if(gridIndex ==0)
						temps[row][column] = cell.getTemperature();
					else 
						temps[row][column] += cell.getTemperature();
					
					if(gridIndex == grids.size()-1){
						double meanTemp = temps[row][column] / grids.size();
						QueryCell newCell = new QueryCell();
						newCell.setRow(row);
						newCell.setColumn(column);
						newCell.setTemperature(meanTemp);
						resultCells.add(newCell);
					}
				}
			}
		}
		return resultCells;
	}

	/**
	 * Returns the mean temp over the selected region for each time step (grid)
	 * Requirement number 3
	 */
	public List<QueryCell> getMeanTempOverRegion() {
		List<QueryCell> meanTempsOverRegion = new ArrayList<QueryCell>();

		for (QueryGrid currentGrid : getQueryGrids()) {
			meanTempsOverRegion.add(currentGrid.getMeanTempCell());
		}

		return meanTempsOverRegion;
	}
	
	protected List<QueryGrid> convertEarthGridsToQueryGrids(
			List<EarthGrid> grids) {
		List<QueryGrid> queryGrids = new ArrayList<QueryGrid>();
		
		for(EarthGrid currentGrid : grids){
			queryGrids.add(convertEarthGridToQueryGrid(currentGrid));
		}
		
		return queryGrids;
	}

	protected QueryGrid convertEarthGridToQueryGrid(EarthGrid currentGrid) {
		QueryGrid retVal = new QueryGrid();
		retVal.setSimulatedDate(currentGrid.getSimulatedDate());
		retVal.setQueryCells(convertEarthCellsToQueryCells(currentGrid.getNodeList()));
		return retVal;
	}
	
	private List<QueryCell> convertEarthCellsToQueryCells(List<EarthCell> nodeList) {
		List<QueryCell> queryCells = new ArrayList<QueryCell>();
		
		for(EarthCell currentCell : nodeList){
			queryCells.add(convertEarthCellToQueryCell(currentCell));
		}
		
		return queryCells;
	}
	
	private QueryCell convertEarthCellToQueryCell(EarthCell currentCell) {
		QueryCell queryCell = new QueryCell();
		
		queryCell.setTemperature(currentCell.getTemperature());
		queryCell.setRow(currentCell.getRow());
		queryCell.setColumn(currentCell.getColumn());
		//TODO Set Latitude and Longitude
		// TODO Is this correct?  Where can we obtain the values
		//queryCell.setSimulatedDate(currentCell.);
		
		return queryCell;
	}
}