package simplesimulation;

public class SimpleCell {
	double t;
	
	double getTemperature() {
		return t;
	}
	
	/**
	 * @param i
	 * @param j
	 * @param rows
	 * @param cols
	 * @param gs
	 * @return the height or altitude of the cell in meters
	 */
	static float getCellHeight(int i, int j, int rows, int cols, int gs) {
		double p = gs / 360d;
		int C = 40030140;
		
		double originLatitude = (i - (rows/2d)) * gs;
		
		double lv = C * p;
		double lb = Math.cos(originLatitude  * (Math.PI / 180)) * lv;
		double lt = Math.cos((originLatitude + gs)  * (Math.PI / 180)) * lv;
		
		double h = Math.sqrt(Math.pow(lv, 2) - (0.25 * Math.pow((lb-lt), 2)));
		
		return (float) h;
	}
	
	/**
	 * Calculates the GRID column under the time t.
	 * @param t the time in minutes since the simulation's start
	 * @return the GRID column under the Sun at time t
	 */
	static int columnUnderTheSun(int time, int cols) {
		double sunPosition = ((time % 1440) * 360d) / 1440d;
		
		int x = (int) ((cols * (sunPosition/360d)) + (cols/2d));
		
		int jt = (x % cols);
		
		return jt;
	}
}
