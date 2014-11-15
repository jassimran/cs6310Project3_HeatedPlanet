package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import presentation.query.QueryCell;
import presentation.query.QueryGrid;
import presentation.query.QueryResult;
import presentation.query.QueryResultImpl;
import services.InterpolationService;
import services.PersistenceService;
import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class QueryControl {

	private static PersistenceService persistenceService;
	private static InterpolationService interpolationService;

	public QueryControl() {
		persistenceService = PersistenceService.getInstance();
		interpolationService = InterpolationService.getInstance();
	}

	/**
	 * Determines if a simulation name has already been used
	 * @param simulationName the simualtion name to test
	 * @return True, if the simualation name is not unique. Otherwise, false.
	 */
	public boolean simulationNameExists(String simulationName) {
		return findSimulationByName(simulationName) != null;
	}

	/**
	 * Finds a simulation based on the name provided
	 * @param simulationName the simulation name to search for
	 * @return the Simulation that matches based on the name
	 */
	public Simulation findSimulationByName(String simulationName) {
		List<Simulation> simulations = persistenceService
				.searchSimulations(simulationName);
		if (simulations.isEmpty())
			return null;
		else
			return simulations.get(0);
	}

	/**
	 * Computes the query results for the provided simulation based on the limitations provided
	 * @param selectedSimulation The Simulation that we are interested in
	 * @param startDate the start date that the user has requested
	 * @param endDate the end date that the user has requested
	 * @param startLat the starting latitude that the user has requested
	 * @param endLat the ending latitude that the user has requested
	 * @param startLong the starting longitude that the user has requested
	 * @param endLong the ending longitude that the user has requested
	 * @return the query results that need to be displayed to the user
	 */
	public QueryResult computeQueryResults(Simulation selectedSimulation,
			Date startDate, Date endDate, double startLat, double endLat,
			double startLong, double endLong) {

		// TODO: Perform geographic interpolation
		for (EarthGrid currentGrid : selectedSimulation.getTimeStepList()) {
			EarthGrid geoInterpolatedSimulation = interpolationService
					.performGeographicInterpolation(selectedSimulation,
							currentGrid);
			// TODO: replace the grid in the list?
		}

		// TODO: Perform temporal interpolation
		List<EarthGrid> temporalInterpolatedGrids = interpolationService
				.performTemporalInterpolation(selectedSimulation,
						selectedSimulation.getTimeStepList());
		selectedSimulation.setTimeStepList(temporalInterpolatedGrids);

		List<EarthGrid> matchingGrids = filterGrids(selectedSimulation,
				startDate, endDate, startLat, endLat, startLong, endLong);

		// TODO is this how / where we want to convert to a QueryGrid?
		List<QueryGrid> queryGrids = convertEarthGridsToQueryGrids(matchingGrids);
		
		QueryResultImpl result = new QueryResultImpl();
		result.setQueryGrids(queryGrids);

		return result;

	}

	protected List<QueryGrid> convertEarthGridsToQueryGrids(
			List<EarthGrid> matchingGrids) {
		List<QueryGrid> grids = new ArrayList();
		
		for(EarthGrid currentGrid : matchingGrids){
			grids.add(convertEarthGridToQueryGrid(currentGrid));
		}
		
		return grids;
	}

	protected QueryGrid convertEarthGridToQueryGrid(EarthGrid currentGrid) {
		QueryGrid retVal = new QueryGrid();
		retVal.setSimulatedDate(currentGrid.getSimulatedDate());
		retVal.setQueryCells(convertEarthCellsToQueryCells(currentGrid.getNodeList()));
		return null;
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

	private List<EarthGrid> filterGrids(Simulation selectedSimulation,
			Date startDate, Date endDate, double startLat, double endLat,
			double startLong, double endLong) {

		List<EarthGrid> matchingGrids;
		// Filter the time steps to only include those we are interested in
		// examining
		for (EarthGrid timeStep : selectedSimulation.getTimeStepList()) {
			if ((timeStep.getSimulatedDate().equals(startDate) || timeStep
					.getSimulatedDate().after(startDate))
					&& (timeStep.getSimulatedDate().equals(endDate) || timeStep
							.getSimulatedDate().before(endDate))) {

				List<EarthCell> matchingCells = filterCells(timeStep, startLat,
						endLat, startLong, endLong);

				timeStep.setNodeList(matchingCells);

				matchingGrids.add(timeStep);
			}
		}
		return matchingGrids;
	}

	private List<EarthCell> filterCells(EarthGrid timeStep, double startLat,
			double endLat, double startLong, double endLong) {

		List<EarthCell> matchingCells;
		// if all lat/long params are 0 use the whole planet
		if (startLat == 0 && endLat == 0 && startLong == 0 && endLong == 0)
			matchingCells = timeStep.getNodeList();
		else {
			for (EarthCell currentCell : timeStep.getNodeList()) {

				double cellLat = timeStep.getLatitude(currentCell.getRow());
				double cellLong = timeStep.getLongitude(currentCell.getColumn());

				boolean latMatch = false;
				if (startLat > 0 && endLat < 0) {
					if ((cellLat >= startLat && cellLat <= 180)
							|| cellLat >= -180 && cellLat <= endLat) {
						latMatch = true;
					}
				} else if (cellLat >= startLat && cellLat <= endLat) {
					latMatch = true;
				}

				boolean longMatch = false;
				if (startLong > 0 && endLong < 0) {
					if ((cellLong >= startLong && cellLong <= 90)
							|| cellLong >= -90 && cellLong <= endLong) {
						longMatch = true;
					}
				} else if (cellLong >= startLong && cellLong <= endLong) {
					longMatch = true;
				}

				if (latMatch && longMatch)
					matchingCells.add(currentCell);

			}
			return matchingCells;
		}
	}
}
