package controllers;

import java.util.List;

import services.PersistenceService;
import domain.Simulation;

public class QueryControl {

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
