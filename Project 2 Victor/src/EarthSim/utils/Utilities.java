package EarthSim.utils;


public class Utilities {
	public static double Force(double mass, double distance, double time){
		return (mass*distance)/Math.pow(time, 2);
	}

}
