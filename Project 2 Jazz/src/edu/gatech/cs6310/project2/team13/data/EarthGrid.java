package edu.gatech.cs6310.project2.team13.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.gatech.cs6310.project2.team13.utils.Shared;

public class EarthGrid {

	public static final double RADIUS_OF_EARTH = 6.371* Math.pow(10,6);
	public static final double CIRCUMFERENCE_OF_EARTH = 2 * Math.PI * RADIUS_OF_EARTH;
	public static final double SURFACE_AREA_OF_EARTH = 4 * Math.PI * Math.pow(RADIUS_OF_EARTH,2);
	public static final double VISIBLE_SURFACE_AREA_OF_EARTH = 2 * Math.PI * Math.pow(RADIUS_OF_EARTH,2);
	
	public static final double POWER_RECEIVED_FROM_SUN = 1.740 * Math.pow(10,17);
	public static final double SOLAR_CONSTANT = POWER_RECEIVED_FROM_SUN / SURFACE_AREA_OF_EARTH;
	public static final double ALDEBO = 0.3;
	public static final double EMISSIVITY = 0.612;
	
	public static final double STABILIZATION_RATE = Shared.getStabilizationRate();
	
	//It is equivalent to 288 in Kelvins.
	//Updated as graphical representations use Celsius for measurements.
	public static final double AVERAGE_TEMPERATURE_OF_EARTH = 288;
	public static final double AVERAGE_TEMPERATURE_OF_SUN = 278;
	
	private static final List<Integer> VALID_GRID_SPACINGS = new ArrayList<Integer>(Arrays.asList(
			new Integer[] {1,2,3,4,5,6,9,10,12,15,18,20,30,36,45,60,90,180}
		));
		
	private int gridSpacing=VALID_GRID_SPACINGS.get(0);;
	private int timeSinceStart=0;
	private int timeInterval;
	private double totalArea;
	private double areaDrift;
	private double currentAverageTemperatureAtCenter = 0.0d;
	private double lastAverageTemperatureAtCenter = 0.0d;
	
	private EarthCell[][] gridData;
	private EarthTemperatureGrid temperatureGrid;
		
	public EarthGrid(int gridSpacingDegrees, int timeInterval) throws GridSpacingException {
		if(gridSpacingDegrees<1 || gridSpacingDegrees>180)
			throw new GridSpacingException("The grid spacing must be a value between 1 and 180");
		if(VALID_GRID_SPACINGS.contains(new Integer(gridSpacingDegrees)))
			gridSpacing = gridSpacingDegrees;
		else {
			for(int j=VALID_GRID_SPACINGS.size()-1; j>=0; j--) {
				if(VALID_GRID_SPACINGS.get(j)<gridSpacingDegrees) {
					gridSpacing = VALID_GRID_SPACINGS.get(j);
					break;
				}
			}
		}

		totalArea = 0.0d;
		this.timeInterval = timeInterval;
		temperatureGrid = new EarthTemperatureGrid(getRows(),getColumns());
		gridData = new EarthCell[getRows()][getColumns()];
		for (int r=0; r<getRows(); r++) {
			for (int c=0; c<getColumns(); c++) {
				EarthCell cell = new EarthCell(r,c);
				gridData[r][c] = cell;
				totalArea += cell.getArea();
				temperatureGrid.setTemperature(translateRow(r), translateColumn(c), cell.currTemp);
			}
		}
		areaDrift = SURFACE_AREA_OF_EARTH - totalArea;
	}
	
	public int translateColumn(int c) {
		int value = (getColumns()/2) - c;
		if (value<0)
			value = getColumns()+value;
		return value;
	}
	
	public int translateRow(int r) {
		return (getRows()-r)-1;
	}
	
	public EarthTemperatureGrid getTemperatureGrid() {
		return temperatureGrid;
	}
	
	public int getTimeSinceStart() {
		return timeSinceStart;
	}
	
	public EarthTemperatureGrid runSimulationInstance() {
			
		for(int i=1;i<=timeInterval;i++) {
			timeSinceStart++;
			// Start by calculating the heating factor for each cell
			double totalHeat = 0.0d;
			for (int r=0; r<getRows(); r++) {
				for (int c=0; c<getColumns(); c++) {
					totalHeat += gridData[r][c].calculateCurrTemp();
				}
			}
			double averageCoolingFactor = -1* totalHeat / this.getNumberOfCells();
			//double totalRSF = 0.0d;
			//double totalCooling = 0.0d;
			for (int r=0; r<getRows(); r++) {
				for (int c=0; c<getColumns(); c++) {
					EarthCell currentCell = gridData[r][c];
					double neighborTemp = currentCell.neighborTemp;
					double averageTemp = (neighborTemp + currentCell.currTemp) / 2;
					//totalRSF += this.relativeSizeFactor(r,c);				
					double coolingTemperature = averageCoolingFactor * this.relativeSizeFactor(r, c) * this.relativeTempFactor(r,c);
					//totalCooling += coolingTemperature;
					double newTemp = averageTemp + currentCell.additionalHeatFromSun+coolingTemperature; // Cooling Temperature is negative so we add it.
					currentCell.currTemp = newTemp;
					gridData[r][c] = currentCell;
					temperatureGrid.setTemperature(translateRow(r), translateColumn(c), currentCell.currTemp);
				}
			}			
		}
	    Calendar calendar = new GregorianCalendar(2000,0,1);
	    calendar.add(Calendar.MINUTE, timeSinceStart);
	    temperatureGrid.setSimulationDate(calendar.getTime());
	    getStabilizationMetrics();
		return temperatureGrid;
	}
		
	// Per domainModel.pdf p3
	public int getRotationalAngle() {
		return (timeSinceStart % 1440) * 360/1440;
	}
	
	// Per domainModel.pdf p3
	public int getColumnUnderSun() {	
		int columns = getColumns();
		int result = (columns * getRotationalAngle()/360) + (columns/2);
		return result % columns;
	}
	
	// Per domainModel.pdf p3
	public int getGridCellsPerTimeZoneAtEquator() {
		return getColumns() / 24;
	}
	
    // Per domainModel.pdf p3
	public int getWestmostColumnInSunshine() {
		int value = getColumnUnderSun()+(5*getGridCellsPerTimeZoneAtEquator());
		if(value >= getColumns())
			value = value - getColumns();
		return value;
	}
	
    // Per domainModel.pdf p3
	public int getEastmostColumnInSunshine() {
		int value = getColumnUnderSun()-(6*getGridCellsPerTimeZoneAtEquator());
		if(value<0) // normalize
			value = getColumns() + value; 
		return value;		
	}
	
	
	// TODO: Question from James -- what is this function? It's not referenced anywhere -- can this be deleted? 
	// Answer: It is just calculating the number of columns/degrees covered by Earth's rotation per hour
	// Point 13 under simplifying assumptions: The Earth rotates uniformly, once per day (24-hour period).
	// This is exactly 15degrees per hour..
	// Lets keep it for now, we are gonna need it soon.
	public int getHeatAttenuationEquator() {
		return getColumns() / 24;
	}
	
	// "The proportion of the Equator used by one unit of grid spacing"
	// -- domainModel.pdf p1
	public double getEquatorialProportion() {
		return gridSpacing / 360.0d;
	}
	
	private double getAverageTemperature(int row) {
		if(row>getRows())
			return 0;
		else {
			double totalTemp = 0.0d;
			for(int i=0;i<getColumns();i++) {
				totalTemp += gridData[row][i].currTemp;
			}
			return totalTemp / getColumns();
		}
	}
	
	private double getAverageTemperature() {
		int row = getRows()/2;
		return getAverageTemperature(row);
	}
	
	private void getStabilizationMetrics() {
		this.lastAverageTemperatureAtCenter = this.currentAverageTemperatureAtCenter;
		this.currentAverageTemperatureAtCenter = getAverageTemperature();
	}
	
	public boolean hasStabilized() {
		if(Math.abs(lastAverageTemperatureAtCenter-currentAverageTemperatureAtCenter)<STABILIZATION_RATE){
			return true;
		}else
			return false;
	}
	
	public int getRows() {
		return 180 / gridSpacing;
	}
	
	public int getColumns() {
		return 360 / gridSpacing;
	}
	
	public int getNumberOfCells() {
		return getRows() * getColumns();
	}
	
	public int getGridSpacing() {
		return gridSpacing;
	}
	
	public EarthCell[][] getGridData() {
		return gridData;
	}
	
	//Represented by Eta 
	public double avgGridCellSize(){
		return (SURFACE_AREA_OF_EARTH-areaDrift)/getNumberOfCells();
	}
	
	//Represented by Beta
	// TODO: Write test
	public double relativeSizeFactor(int i, int j){
		//EarthCell cell = new EarthCell(i,j);
		return gridData[i][j].getArea()/avgGridCellSize();
	}
	
	//Represented by Tavg
	// TODO: Write test
	/* QUESTION:  Is the average temperature of the Earth in Kelvins due to Solar 
	 * radiation (TE = 288 degree) equal to the average Temperature of all cells 
	 * of earth? If yes, the below method can be declared as a constant value.
	 * MY THOUGHTS: It should be.
	 * I agree, delete. ("7. Across the entire surface of the Earth, total cooling exactly balances solar heating.")
	 * (why is this averaging initial temp instead of current temp?) -- George
	 */
	public double avgTempOfAllCells(){
		double totalTemp = 0.0d;
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getColumns(); c++) {
				
				//EarthCell cell = new EarthCell(r,c);
				//gridData[r][c] = cell;
				//totalTemp = totalTemp+cell.getCurrTemp();
				totalTemp += gridData[r][c].getCurrTemp();
			}
		}
		return totalTemp/getNumberOfCells();		
	}
	
	//Represented by Gamma
	//Note from James -- updated to divide by Average Temperature of All Cells, not Average Temperature of Earth
	// TODO: Write test
	public double relativeTempFactor(int i, int j){
		//EarthCell cell = new EarthCell(i,j);
		//return gridData[i][j].getCurrTemp()/AVERAGE_TEMPERATURE_OF_EARTH; 
		return gridData[i][j].getCurrTemp() / avgTempOfAllCells();
	}
	
	public EarthCell getCellByCoordinates(int latitude, int longitude) {
		int i = (latitude / gridSpacing) + (getRows()/2);
		if(i==getRows()) i--;
		int j;
		int theta = ((longitude+gridSpacing)/gridSpacing)-1;
		if(longitude<0) {
			j = (-1*theta)-1;
		} else {
			j = getColumns()-(theta-1);
		}
		/*
		if(longitude<=0)
			j = -1* (longitude/gridSpacing);
		else 
			j = getColumns() - (longitude/gridSpacing);
		*/
		return gridData[i][j];
	}
	
	/*
	 * Class representing each cell in the Earth grid.
	 */
	public class EarthCell {
		private int i; // row, corresponds to latitude
		private int j; // column,  "       "  longitude
		private double currTemp = AVERAGE_TEMPERATURE_OF_EARTH;
		private double additionalHeatFromSun = 0.0d;
		private double neighborTemp = 0.0d;

		public EarthCell(int row, int column) {
			i = row;
			j = column;
		}
		
		//Represented by alpha
		public double getEquatorialAttenuation() {
			int rotationalAngle =  Math.abs(getRotationalAngle());
			if(rotationalAngle<90)
				return Math.cos(Math.toRadians(rotationalAngle)); 
			else 
				return 0.0d;
		}
		
        //TODO: document        
        public boolean isCellInDaylight() {
        	int west = getWestmostColumnInSunshine();
        	int east = getEastmostColumnInSunshine();
        	if(west>east)
        		return (j<=west && j>=east);
        	else
        		return (j<=west || j>=east);
        }
        
        //TODO: document
		public double getAttenuation() {
			if(isCellInDaylight()) {	
				int originLongitude = getOriginLongitude(); //
				int d;
				//Being done Twice - check this!!!!!!!!!!!
				if(originLongitude>=0)
					d=360-originLongitude;
				else
					d=-1*originLongitude;
				
				//Updated to get Attenuation as per dnoon
				int noonLongitude = (getColumnUnderSun()-1) * getGridSpacing();
				int degreesToNoon = Math.abs(d-(noonLongitude));
				double equatorialHeatAttenuation;
				if(degreesToNoon<90)
					equatorialHeatAttenuation=Math.cos(Math.toRadians(degreesToNoon));
				else
					equatorialHeatAttenuation=0;
				double combinedAttenuation = Math.cos(Math.toRadians(Math.abs(this.getOriginLatitude()))) * equatorialHeatAttenuation;
				return combinedAttenuation;
				
				//return Math.cos(Math.toRadians(getOriginLatitude()) * getEquatorialAttenuation());
			} else
				return 0.0d;
		}
		
		//Represented by Tt
		public double getCurrTemp() {
			return currTemp;
		}
		
		
		

		// TODO: Clarify formula with feedback from Prof. on Piazza...
		public double calculateCurrTemp() {
			additionalHeatFromSun = getHeatFromSun();
			neighborTemp = getNeighborsTemp();
			return additionalHeatFromSun;
		}
								
	    // TODO: Is IF needed?
		//Represented by Tneighbor
		public double getNeighborsTemp(){
			return (getEastProportion() * getEast().getCurrTemp()) + 
					(getWestProportion() * getWest().getCurrTemp()) + 
					(getSouthProportion() * getSouth().getCurrTemp()) +
					(getNorthProportion() * getNorth().getCurrTemp());
		}
		
		//Represented by Tsun
		// TODO: Document
		public double getHeatFromSun(){
			double attenuation = getAttenuation();
			//return AVERAGE_TEMPERATURE_OF_SUN * attenuation;
			return attenuation;
		}
		
		// Per domainModel.pdf p1
		public EarthCell getWest() {			
			return EarthGrid.this.gridData[i][(j+1)%getColumns()];
		}
		
		// Per domainModel.pdf p1
		public EarthCell getEast() {
			return EarthGrid.this.gridData[i][(j-1+getColumns())%getColumns()];
		}
		
		// Per domainModel.pdf p2
		public EarthCell getNorth() {
			if(i+1<=getRows()-1) {
				return EarthGrid.this.gridData[i+1][j];
			} else {
				return EarthGrid.this.gridData[i][(j+(getColumns()/2))%getColumns()];
			}		
		}
		
		// Per domainModel.pdf p2
		public EarthCell getSouth() {
			if(i-1>=0) {
				return EarthGrid.this.gridData[i-1][j];
			} else {
				return EarthGrid.this.gridData[i][(j+(getColumns()/2))%getColumns()];
			}
		}
		
		// "The origin of a cell is at its lower left hand corner" 
		// -- domainModel.pdf p2
		public int getOriginLatitude() {
			return (i-(getRows()/2)) * gridSpacing;
		}
		
		// Per domainModel.pdf p2
		public int getOriginLongitude() {
			int d = (j+1) * gridSpacing;
			if(j<(getColumns()/2)) 
				return -1*d;
			else 
				return 360-d;
		}
			
		// Untested
		// "Length of each of the vertical sides of the cell in meters" -- domainModel.pdf p2
		public double getLengthSide() {
			return CIRCUMFERENCE_OF_EARTH * getEquatorialProportion();
		}
		
		// Untested
		public double getLengthBase() {
			return Math.cos(Math.toRadians(getOriginLatitude())) * getLengthSide();
			// return 2 * Math.PI * RADIUS_OF_EARTH * Math.cos(Math.toRadians(getOrigin-Latitude())) * getEquatorialProportion();
		}
		
		// Untested
		public double getLengthTop() {
			return RADIUS_OF_EARTH * Math.cos(Math.toRadians(getOriginLatitude()+gridSpacing)) * 2 * Math.PI * getEquatorialProportion();
		}
		
		// Untested
		public float getHeight() {
			double side = getLengthSide();
			double base = getLengthBase();
			double top = getLengthTop();
			double value1 = Math.pow(side,2);
			double value2 = Math.pow(base-top, 2)/4;
			float retVal =new Double(Math.sqrt(value1-value2)).floatValue(); 
			return retVal;
			
			
			//return new Double(Math.sqrt(Math.pow(getLengthSide(),2.0)- 1/4 * (Math.pow(getLengthBase()-getLengthTop(),2)))).floatValue();
		}
		
		// Untested
		public double getPerimeter() {
			return getLengthTop() + getLengthBase() + 2*getLengthSide();
		}
		
		//Represented by small a
		// Untested
		public double getArea() {
			double newArea = 0.5 * (getLengthTop() + getLengthBase()) * getHeight();
			return newArea;
		}
		
		// Untested
		public double getXCoordinate() {
			int part1 = (j + (getColumns()/2)) % getColumns();
			int part2 = ((getColumns()/2)-1);
			return (part1-part2) * (CIRCUMFERENCE_OF_EARTH/getColumns());
		}
		
		// Untested
		public double getYCoordinate() {
			return (i-(getRows()/2)) * (CIRCUMFERENCE_OF_EARTH/getRows());
		}
		
		//Untested
		public double getEastProportion() {
			return getLengthSide()/getPerimeter();
		}
		
		//Untested
		public double getWestProportion() {
			return getLengthSide()/getPerimeter();
		}

		//Untested
		public double getSouthProportion() {
			return getLengthBase()/getPerimeter();
		}
		
		//Untested
		public double getNorthProportion() {
			return getLengthTop()/getPerimeter();
		}
				
	}
	
	public class GridSpacingException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7757192330698746486L;
		private String message;
		public GridSpacingException(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
	}
}


