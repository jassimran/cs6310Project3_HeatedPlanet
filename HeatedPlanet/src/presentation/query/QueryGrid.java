package presentation.query;

import java.util.Date;
import java.util.List;

public class QueryGrid {

//	private int gridSpacing;
//	private int numberColumns;
//	private int numberRows;
	private Date simulatedDate;
	private List<QueryCell> queryCells;
	
//	public QueryGrid(Date simulatedDate, int gridSpacing, int numberColumns, int numberRows){
//		this.simulatedDate = simulatedDate;
//		this.gridSpacing = gridSpacing;
//		this.numberColumns = numberColumns;
//		this.numberRows = numberRows;
//	}
	
	public List<QueryCell> getQueryCells() {
		return queryCells;
	}

	public void setQueryCells(List<QueryCell> queryCells) {
		this.queryCells = queryCells;
	}
	
	public QueryCell getMinTempCell() {
		QueryCell minTempCell = null;

		for (QueryCell cell : getQueryCells()) {
			if (minTempCell == null
					|| cell.getTemperature() < minTempCell.getTemperature())
				minTempCell = cell;
		}
		return minTempCell;
	}

	public QueryCell getMaxTempCell() {
		QueryCell maxTempCell = null;

		for (QueryCell cell : getQueryCells()) {
			if (maxTempCell == null
					|| cell.getTemperature() > maxTempCell.getTemperature())
				maxTempCell = cell;
		}
		return maxTempCell;
	}

	public QueryCell getMeanTempCell() {
		double totalTemp = 0;
		
		for (QueryCell cell : getQueryCells()) {
			totalTemp += cell.getTemperature();
		}

		if (getGridSize() > 0) {
			double meanTemp = totalTemp / getGridSize();
			return new QueryCell(meanTemp, getSimulatedDate());
		}
		
		return new QueryCell();
	}



	public int getGridSize() {
		throw new UnsupportedOperationException();
	}

	public QueryCell getQueryCell(int x) {
		throw new UnsupportedOperationException();
	}
	
	
	


	public Date getSimulatedDate() {
		return simulatedDate;
	}

	public void setSimulatedDate(Date simulatedDate) {
		this.simulatedDate = simulatedDate;
	}


}