package edu.gatech.cs6310.project2.team13.data;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.gatech.cs6310.project2.team13.gui.widget.earth.TemperatureGrid;

public class EarthTemperatureGrid implements TemperatureGrid {

	private double[][] temperature;
	int x;
	int y;
	private Date simulationDate;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public EarthTemperatureGrid(int x, int y) 
	{
		this.x=x;
		this.y=y;
		temperature = new double[x][y];
	    Calendar calendar = new GregorianCalendar(2000,0,1);
	    simulationDate = calendar.getTime();    
	}
		
	public void setTemperature(int x, int y, double theTemp) {
		temperature[x][y] = theTemp;
	}
		
	public Date getSimulationDate() {
		return this.simulationDate;
	}
	
	public void setSimulationDate(Date theDate) {
		simulationDate = theDate;
	}
	
	@Override
	public double getTemperature(int x, int y) {
		// Note: internally, X is rows and Y is columns
		// From UI panel, X is columns and Y is rows, so we flip
		return temperature[y][x];
	}
	
}
