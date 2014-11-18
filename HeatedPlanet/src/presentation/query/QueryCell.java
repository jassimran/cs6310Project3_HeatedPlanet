package presentation.query;

import java.util.Date;

public class QueryCell {

	private int column;
	private int row;
	private double latitude;
	private double longitude;
	private double temperature;
	private Date simulatedDate;
	
	public QueryCell (double temp, Date simulatedDate){
		this.temperature = temp;
		this.simulatedDate = simulatedDate;
	}
	
	public QueryCell() {
		// TODO Auto-generated constructor stub
	}

	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Date getSimulatedDate() {
		return simulatedDate;
	}

	public void setSimulatedDate(Date simulatedDate) {
		this.simulatedDate = simulatedDate;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
}