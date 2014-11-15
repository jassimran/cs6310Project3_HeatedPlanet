package presentation.query;

import java.util.Date;
import java.util.List;

public class QueryGrid {

	private int gridSpacing;
	private int numberColumns;
	private int numberRows;
	private Date simulatedDate;
	
	public QueryGrid(Date simulatedDate, int gridSpacing, int numberColumns, int numberRows){
		this.simulatedDate = simulatedDate;
		this.gridSpacing = gridSpacing;
		this.numberColumns = numberColumns;
		this.numberRows = numberRows;
	}
	
	public List<QueryCell> getQueryCells() {
		throw new UnsupportedOperationException();
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
	
	
	
	protected double getLatitude(int rowIndex){
		double retVal = (rowIndex-(numberRows/2)) * gridSpacing;
		return retVal;
	}
	
	protected double getLongitude(int colIndex){
		int d = (colIndex+1) * gridSpacing;
		if(colIndex < numberColumns/2)
			return -d;
		else
			return 360 - d;
	}

	public Date getSimulatedDate() {
		return simulatedDate;
	}

	public void setSimulatedDate(Date simulatedDate) {
		this.simulatedDate = simulatedDate;
	}
}