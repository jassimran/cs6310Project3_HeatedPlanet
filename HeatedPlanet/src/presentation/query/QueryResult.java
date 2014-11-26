package presentation.query;

import java.util.List;

public interface QueryResult {

	public QueryCell getMinTempCell();

	public QueryCell getMaxTempCell();

	public List<QueryGrid> getQueryGrids();

	public List<QueryCell> getMeanTempOverTime();

	public List<QueryCell> getMeanTempOverRegion();
}