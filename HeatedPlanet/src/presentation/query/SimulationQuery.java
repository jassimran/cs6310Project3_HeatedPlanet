package presentation.query;

import java.util.Date;

public class SimulationQuery {

	public SimulationQuery(String simulationName, double axialTilt,
			double orbitalEccentricity, Date startDate, Date endDate,
			double startLat, double endLat, double startLong, double endLong) {
		super();
		
		if(simulationName == null){
			throw new RuntimeException("Invariant violated: The simulation name can not be null.");
		}
		
		this.simulationName = simulationName;
		this.axialTilt = axialTilt;
		this.orbitalEccentricity = orbitalEccentricity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startLat = startLat;
		this.endLat = endLat;
		this.startLong = startLong;
		this.endLong = endLong;
	}
	
	private String simulationName;
	private double axialTilt; 
	private double orbitalEccentricity;
	private Date startDate; 
	private Date endDate;
	private double startLat; 
	private double endLat;
	private double startLong;
	private double endLong;
	
	public String getSimulationName() {
		if(simulationName == null){
			throw new RuntimeException("Invariant violated: The simulation name can not be null.");
		}
		return simulationName;
	}
	public void setSimulationName(String simulationName) {
		if(simulationName == null){
			throw new RuntimeException("Invariant violated: The simulation name can not be null.");
		}
		this.simulationName = simulationName;
	}
	public double getAxialTilt() {
		return axialTilt;
	}
	public void setAxialTilt(double axialTilt) {
		this.axialTilt = axialTilt;
	}
	public double getOrbitalEccentricity() {
		return orbitalEccentricity;
	}
	public void setOrbitalEccentricity(double orbitalEccentricity) {
		this.orbitalEccentricity = orbitalEccentricity;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getStartLat() {
		return startLat;
	}
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public double getStartLong() {
		return startLong;
	}
	public void setStartLong(double startLong) {
		this.startLong = startLong;
	}
	public double getEndLong() {
		return endLong;
	}
	public void setEndLong(double endLong) {
		this.endLong = endLong;
	}	
	
}
