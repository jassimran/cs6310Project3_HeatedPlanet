package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import presentation.query.QueryCell;
import presentation.query.QueryGrid;
import presentation.query.QueryResult;
import presentation.query.QueryResultImpl;
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

	public boolean simulationNameExists(String simulationName) {
		return findSimulationByName(simulationName) != null;
	}

	public Simulation findSimulationByName(String simulationName) {
		List<Simulation> simulations = persistenceService
				.searchSimulations(simulationName);
		if (simulations.isEmpty())
			return null;
		else
			return simulations.get(0);
	}

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

		List<QueryGrid> queryGrids = convertEarthGridsToQueryGrids(matchingGrids);
		
		QueryResultImpl result = new QueryResultImpl();
		result.setQueryGrids(queryGrids);

		return result;

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

				double cellLat = 0; // TODO get the latitude for the
									// current cell
				double cellLong = 0; // TODO get the longitude for the
										// current cell

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
