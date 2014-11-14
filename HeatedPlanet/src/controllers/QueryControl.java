package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import services.PersistenceService;
import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class QueryControl {

	public boolean simulationNameExists(String simulationName) {
		return findSimulationByName(simulationName) != null;
	}

	public Simulation findSimulationByName(String simulationName) {
		List<Simulation> simulations = PersistenceService.getInstance()
				.searchSimulations(simulationName);
		if (simulations.isEmpty())
			return null;
		else
			return simulations.get(0);
	}

	public QueryResults computeQueryResults(Simulation selectedSimulation,
			Date startDate, Date endDate, double startLat, double endLat,
			double startLong, double endLong) {
		// double minTemp, minTempLat, minTempLong, maxTemp, maxTempLat,
		// maxTempLong,

		List<EarthGrid> matchingGrids = new ArrayList<EarthGrid>();

		// Filter the time steps to only include those we are interested in
		// examining
		for (EarthGrid timeStep : selectedSimulation.getTimeStepList()) {
			if ((timeStep.getSimulatedDate().equals(startDate) || timeStep
					.getSimulatedDate().after(startDate))
					&& (timeStep.getSimulatedDate().equals(endDate) || timeStep
							.getSimulatedDate().before(endDate))) {
				
				List<EarthCell> matchingCells = new ArrayList<EarthCell>();
				
				for(EarthCell currentCell : timeStep.getNodeList()){
					//if(currentCell.)
				}
				
				timeStep.setNodeList(matchingCells);
				
				matchingGrids.add(timeStep);
			}
		}

		QueryResults results = new QueryResults();
		results.setGridList(matchingGrids);

		return results;

	}
}
