package presentation.query;

import domain.Simulation;

public class QueryResultFactory {
	
	public static QueryResult buildQueryResult(Simulation simulation){
		return new QueryResultImpl(simulation);
	}

	public static QueryResult buildQueryResult(Simulation simulation,
			SimulationQuery simulationQuery) {
		return new QueryResultImpl(simulation, simulationQuery);
	}
	
}
