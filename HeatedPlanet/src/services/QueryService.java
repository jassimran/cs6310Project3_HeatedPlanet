package services;

import java.util.List;

import domain.Simulation;

public class QueryService {

	public boolean simulationNameExists(String simulationName){
		return findSimulationByName(simulationName) != null;
	}
	
	public Simulation findSimulationByName(String simulationName){
		List<Simulation> simulations = PersistenceService.getInstance().searchSimulations(simulationName);
		if(simulations.isEmpty())
			return null;
		else
			return simulations.get(0);
	}
}
