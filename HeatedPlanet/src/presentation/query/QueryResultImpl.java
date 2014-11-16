package presentation.query;

import java.util.ArrayList;
import java.util.List;

import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class QueryResultImpl implements QueryResult {
	
	private List<QueryGrid> queryGrids;
	
	public QueryResultImpl(Simulation simulation){
		queryGrids = convertEarthGridsToQueryGrids(simulation.getTimeStepList());
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
	 * grid.
	 */
	public List<QueryCell> getMeanTempOverTime() {

		List<QueryCell> meanTempsOverRegion = new ArrayList<QueryCell>();

		List<QueryGrid> queryGrids = getQueryGrids();
		for (int i = 0; i < queryGrids.size(); i++) {
			QueryGrid currentGrid = queryGrids.get(i);
			for (int j = 0; j < currentGrid.getGridSize(); j++) {
				QueryCell currentQueryCell = currentGrid.getQueryCell(j);

				// If this is the first grid, initialize the return value (mean
				// temps over region)
				if (i == 0) {

					// Make a new Query Cell to hold the totals
					QueryCell newMeanTempQueryCell = new QueryCell();

					// TODO newMeanTempQueryCell.setLatitude(latitude);
					// TODO newMeanTempQueryCell.setLongitude(longitude);
					newMeanTempQueryCell.setTemperature(currentQueryCell.getTemperature());
				}
				// else (this is a subsequent grid after the first one)
				else{
					QueryCell existingMeanQueryCell = meanTempsOverRegion.get(j);
					double newTemp = existingMeanQueryCell.getTemperature() + currentQueryCell.getTemperature();
					existingMeanQueryCell.setTemperature(newTemp);
				}
				
				// If this is the last iteration
				if(i+1 == queryGrids.size()){
					QueryCell existingMeanQueryCell = meanTempsOverRegion.get(j);
					double meanTemp = existingMeanQueryCell.getTemperature() / currentGrid.getQueryCells().size();
					existingMeanQueryCell.setTemperature(meanTemp);
				}
			}
		}

		return meanTempsOverRegion;

	}

	/**
	 * Returns the mean temp over the selected region for each time step (grid)
	 */
	public List<QueryCell> getMeanTempOverRegion() {
		List<QueryCell> meanTempsOverTime = new ArrayList<QueryCell>();

		for (QueryGrid currentGrid : getQueryGrids()) {
			meanTempsOverTime.add(currentGrid.getMeanTempCell());
		}

		return meanTempsOverTime;
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
		// TODO Is this correct?  Where can we obtain the values
		//queryCell.setSimulatedDate(currentCell.);
		
		return queryCell;
	}
}