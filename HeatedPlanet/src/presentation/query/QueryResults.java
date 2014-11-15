package presentation.query;

import java.util.Date;
import java.util.List;

import domain.EarthGrid;

public class QueryResults {
	private double minTemp;
	private Date minTempTime;
	private double minTempLat;
	private double minTempLong;
	private double maxTemp;
	private Date maxTempTime;
	private double maxTempLat;
	private double maxTempLong;
	private double meanTempOverTime;
	private double meanTempOverRegion;
	private List<EarthGrid> gridList;
	public double getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}
	public Date getMinTempTime() {
		return minTempTime;
	}
	public void setMinTempTime(Date minTempTime) {
		this.minTempTime = minTempTime;
	}
	public double getMinTempLat() {
		return minTempLat;
	}
	public void setMinTempLat(double minTempLat) {
		this.minTempLat = minTempLat;
	}
	public double getMinTempLong() {
		return minTempLong;
	}
	public void setMinTempLong(double minTempLong) {
		this.minTempLong = minTempLong;
	}
	public double getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}
	public Date getMaxTempTime() {
		return maxTempTime;
	}
	public void setMaxTempTime(Date maxTempTime) {
		this.maxTempTime = maxTempTime;
	}
	public double getMaxTempLat() {
		return maxTempLat;
	}
	public void setMaxTempLat(double maxTempLat) {
		this.maxTempLat = maxTempLat;
	}
	public double getMaxTempLong() {
		return maxTempLong;
	}
	public void setMaxTempLong(double maxTempLong) {
		this.maxTempLong = maxTempLong;
	}
	public double getMeanTempOverTime() {
		return meanTempOverTime;
	}
	public void setMeanTempOverTime(double meanTempOverTime) {
		this.meanTempOverTime = meanTempOverTime;
	}
	public double getMeanTempOverRegion() {
		return meanTempOverRegion;
	}
	public void setMeanTempOverRegion(double meanTempOverRegion) {
		this.meanTempOverRegion = meanTempOverRegion;
	}
	public List<EarthGrid> getGridList() {
		return gridList;
	}
	public void setGridList(List<EarthGrid> gridList) {
		this.gridList = gridList;
	}
}
