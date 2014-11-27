package presentation.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class QueryResultImpl implements QueryResult {

	private QueryCell minTempCell;
	private QueryCell maxTempCell;
	List<QueryCell> meanTempOverTimeCells;
	List<QueryCell> meanTempOverRegionCells;
	private int numberOfRows;
	private int numberOfColumns;
	private List<QueryGrid> queryGrids;

	public QueryResultImpl(Simulation simulation) {
		initialize(simulation);
	}

	public QueryResultImpl(Simulation simulation, SimulationQuery simulationQuery) {

		simulation.setTimeStepList(filterGrids(simulation, simulationQuery));
		initialize(simulation);
	}

	private void initialize(Simulation simulation) {
		queryGrids = convertEarthGridsToQueryGrids(simulation.getTimeStepList());
		numberOfColumns = simulation.getNumberOfColumns();
		numberOfRows = simulation.getNumberOfRows();
		minTempCell = computeMinTempCell();
		maxTempCell = computeMaxTempCell();
		meanTempOverTimeCells = computeMeanTempOverTime();
		meanTempOverRegionCells = computeMeanTempOverRegion();
	}

	protected QueryCell computeMinTempCell() {
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

	public QueryCell getMinTempCell() {
		return minTempCell;
	}

	protected QueryCell computeMaxTempCell() {
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

	public QueryCell getMaxTempCell() {
		return maxTempCell;
	}

	public List<QueryGrid> getQueryGrids() {
		return queryGrids;
	}

	/**
	 * Computes the mean temp over the time for each of the locations in the
	 * grid. Requirement Number 4
	 */
	protected List<QueryCell> computeMeanTempOverTime() {
		List<QueryGrid> grids = getQueryGrids();
		List<QueryCell> resultCells = new ArrayList<QueryCell>();
		double[][] temps = new double[numberOfRows][numberOfColumns];
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				for (int gridIndex = 0; gridIndex < grids.size(); gridIndex++) {
					QueryGrid grid = grids.get(gridIndex);
					QueryCell cell = grid.getQueryCell(row, column);
					if(cell==null)
					{
						System.out.println("null cell");
					}
					if (gridIndex == 0)
						temps[row][column] = cell.getTemperature();
					else
						temps[row][column] += cell.getTemperature();

					if (gridIndex == grids.size() - 1) {
						double meanTemp = temps[row][column] / grids.size();
						QueryCell newCell = new QueryCell();
						newCell.setRow(row);
						newCell.setColumn(column);
						newCell.setTemperature(meanTemp);
						newCell.setLatitude(cell.getLatitude());
						newCell.setLongitude(cell.getLongitude());
						resultCells.add(newCell);
					}
				}
			}
		}
		return resultCells;
	}

	/**
	 * Returns the mean temp over the time for each of the locations in the
	 * grid. Requirement Number 4
	 */
	public List<QueryCell> getMeanTempOverTime() {
		return meanTempOverTimeCells;
	}

	/**
	 * Computes the mean temp over the selected region for each time step (grid)
	 * Requirement number 3
	 */
	protected List<QueryCell> computeMeanTempOverRegion() {
		List<QueryCell> meanTempsOverRegion = new ArrayList<QueryCell>();

		for (QueryGrid currentGrid : getQueryGrids()) {
			meanTempsOverRegion.add(currentGrid.getMeanTempCell());
		}

		return meanTempsOverRegion;
	}

	/**
	 * Returns the mean temp over the selected region for each time step (grid)
	 * Requirement number 3
	 */
	public List<QueryCell> getMeanTempOverRegion() {
		return meanTempOverRegionCells;
	}

	protected List<QueryGrid> convertEarthGridsToQueryGrids(
			List<EarthGrid> grids) {
		List<QueryGrid> queryGrids = new ArrayList<QueryGrid>();

		for (EarthGrid currentGrid : grids) {
			queryGrids.add(convertEarthGridToQueryGrid(currentGrid));
		}

		return queryGrids;
	}

	protected QueryGrid convertEarthGridToQueryGrid(EarthGrid currentGrid) {
		QueryGrid retVal = new QueryGrid();
		retVal.setSimulatedDate(currentGrid.getSimulatedDate());
		retVal.setQueryCells(convertEarthCellsToQueryCells(currentGrid
				.getNodeList()));
		return retVal;
	}

	private List<QueryCell> convertEarthCellsToQueryCells(
			List<EarthCell> nodeList) {
		List<QueryCell> queryCells = new ArrayList<QueryCell>();

		for (EarthCell currentCell : nodeList) {
			queryCells.add(convertEarthCellToQueryCell(currentCell));
		}

		return queryCells;
	}

	private QueryCell convertEarthCellToQueryCell(EarthCell currentCell) {
		Simulation simulation = currentCell.getGrid().getSimulation();
		double cellLat = calculateLatitude(simulation.getGridSpacing(),
				simulation.getNumberOfRows(), currentCell.getRow());
		double cellLong = calculateLongitude(simulation.getGridSpacing(),
				simulation.getNumberOfColumns(), currentCell.getColumn());

		QueryCell queryCell = new QueryCell();

		queryCell.setTemperature(currentCell.getTemperature());
		queryCell.setRow(currentCell.getRow());
		queryCell.setColumn(currentCell.getColumn());
		queryCell.setLatitude(cellLat);
		queryCell.setLongitude(cellLong);
		queryCell.setSimulatedDate(currentCell.getGrid().getSimulatedDate());

		return queryCell;
	}

	private List<EarthGrid> filterGrids(Simulation simulation, SimulationQuery query) {

		// Simulation selectedSimulation =
		// persistenceService.findBySimulationName(simulationName);

		List<EarthGrid> matchingGrids = new ArrayList<EarthGrid>();
		// Filter the time steps to only include those we are interested in
		// examining
		for (EarthGrid timeStep : simulation.getTimeStepList()) {
			if ((timeStep.getSimulatedDate().equals(query.getStartDate()) || timeStep
					.getSimulatedDate().after(query.getStartDate()))
					&& (timeStep.getSimulatedDate().equals(query.getEndDate()) || timeStep
							.getSimulatedDate().before(query.getEndDate()))) {

				List<EarthCell> matchingCells = filterCells(simulation,
						timeStep, query);

				timeStep.setNodeList(matchingCells);

				matchingGrids.add(timeStep);
			}
		}
		return matchingGrids;
	}

	private List<EarthCell> filterCells(Simulation simulation,
			EarthGrid timeStep, SimulationQuery query) {

		List<EarthCell> matchingCells = new ArrayList<EarthCell>();
		// if all lat/long params are 0 use the whole planet
		if (query.getStartLat() == 0 && query.getEndLat() == 0 
				&& query.getStartLong() == 0 && query.getEndLong() == 0)
			matchingCells = timeStep.getNodeList();
		else {
			for (EarthCell currentCell : timeStep.getNodeList()) {

				double cellLat = calculateLatitude(simulation.getGridSpacing(),
						simulation.getNumberOfRows(), currentCell.getRow());
				double cellLong = calculateLongitude(
						simulation.getGridSpacing(),
						simulation.getNumberOfColumns(),
						currentCell.getColumn());

				boolean latMatch = false;
				if (query.getStartLat() > 0 && query.getEndLat() < 0) {
					if ((cellLat >= query.getStartLat() && cellLat <= 180)
							|| cellLat >= -180 && cellLat <= query.getEndLat()) {
						latMatch = true;
					}
				} else if (cellLat >= query.getStartLat() && cellLat <= query.getEndLat()) {
					latMatch = true;
				}

				boolean longMatch = false;
				if (query.getStartLong() > 0 && query.getEndLong() < 0) {
					if ((cellLong >= query.getStartLong() && cellLong <= 90)
							|| cellLong >= -90 && cellLong <= query.getEndLong()) {
						longMatch = true;
					}
				} else if (cellLong >= query.getStartLong() && cellLong <= query.getEndLong()) {
					longMatch = true;
				}

				if (latMatch && longMatch)
					matchingCells.add(currentCell);
			}
		}
		return matchingCells;
	}

	public double calculateLatitude(int gridSpacing, int numberRows,
			int rowIndex) {
		double retVal = (rowIndex - (numberRows / 2)) * gridSpacing;
		return retVal;
	}

	public double calculateLongitude(int gridSpacing, int numberColumns,
			int colIndex) {
		int d = (colIndex + 1) * gridSpacing;
		if (colIndex < numberColumns / 2)
			return -d;
		else
			return 360 - d;
	}
}