package simplesimulation;

import java.math.BigInteger;

public class SimpleCell {
	double t;
	
	double getTemperature() {
		return t;
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @param rows
	 * @param cols
	 * @param gs
	 * @return the height or altitude of the cell in meters
	 */
	static float getCellHeight(int i, int j, int rows, int cols, int gs) {
		int d = (j + 1) * gs;
		double p = (double) gs / (double) 360;
		BigInteger C = new BigInteger("40030140"); // 4.003014x10^7
		BigInteger A = new BigInteger("510072000000000"); // 5.10072x10^14
		
		double originLatitude = (i - ((double)rows/(double)2)) * gs;
		double originLongitude = (j < ((double)cols/(double)2))? -d : 360 - d;
		
		double lv = C.intValue() * p;
		double lb = Math.cos(originLatitude  * (Math.PI / 180)) * lv;
		double lt = Math.cos((originLatitude + gs)  * (Math.PI / 180)) * lv;
		
//		double h = Math.sqrt((lv*lv) - (1/4) * ((lb-lt)*(lb-lt)));
		
		double h = Math.sqrt(Math.pow(lv, 2) - (0.25 * Math.pow((lb-lt), 2)));
		
		return (float) h;
	}
	
	/**
	 * Calculates the GRID column under the time t.
	 * @param t the time in minutes since the simulation's start
	 * @return the GRID column under the Sun at time t
	 */
	static int columnUnderTheSun(int time, int cols) {
		double sunPosition = (double)((time % 1440) * 360) / (double)1440;
		
		int x = (int) ((cols * (sunPosition/(double)360)) + ((double)cols/(double)2));
		
		int jt = (x % cols);
		
		return jt;
	}
}
