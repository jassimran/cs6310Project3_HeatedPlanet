package presentation.query;

import java.util.Date;

import domain.Simulation;

public class QueryResultFactory {
	
	public static QueryResult buildQueryResult(Simulation simulation){
		return new QueryResultImpl(simulation);
	}

	public static QueryResult buildQueryResult(Simulation simulation,
			Date startDate, Date endDate, double startLat, double endLat,
			double startLong, double endLong) {
		return new QueryResultImpl(simulation, startDate, endDate, startLat, endLat, startLong, endLong);
	}
	
}
