package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimulationService {

	// singleton instance
	private static SimulationService serviceInstance;
		
	private SimulationService() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized SimulationService getInstance() {
		if(serviceInstance == null) {
			serviceInstance = new SimulationService();
		}
		return serviceInstance;
	}
	
	/**
	 * Calculates the number of grids required to complete a simulation 
	 * based on the the number of months to simulate and the time step to use.
	 * @param months months between 1 and 1200
	 * @param timeStep number of minutes between 1 and 1440 (1 day)
	 * @return the number of grids to produce in a simulation
	 */
	public synchronized int calculateSimulationLength(int months, int timeStep) {
		Calendar calendar = Calendar.getInstance();
		
		// calculate base time (12:00 PM, January 4, 2014)
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 4);
		calendar.set(Calendar.YEAR, 2014);
		long baseTime = calendar.getTimeInMillis();
		
		// calculate target time
		calendar.add(Calendar.MONTH, months);
		long targetTime = calendar.getTimeInMillis();
		
		// calculate number of grids
		int numberOfgrids = 0;
		long simulationTime = baseTime;
		while(simulationTime < targetTime) {
			calendar.setTimeInMillis(simulationTime);
			calendar.add(Calendar.MINUTE, timeStep);
			simulationTime = calendar.getTimeInMillis();
			numberOfgrids++;
		}
		
		return numberOfgrids;
	}
	
	public synchronized int calculateSimulationMonths(Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		
		// calculate base time (12:00 PM, January 4, 2014)
		startCalendar.set(Calendar.HOUR_OF_DAY, 12);
		startCalendar.set(Calendar.MINUTE, 00);
		startCalendar.set(Calendar.SECOND, 00);
		startCalendar.set(Calendar.MILLISECOND, 00);
		startCalendar.set(Calendar.MONTH, Calendar.JANUARY);
		startCalendar.set(Calendar.DAY_OF_MONTH, 4);
		startCalendar.set(Calendar.YEAR, 2014);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		
		return diffMonth + 1;
	}
}
