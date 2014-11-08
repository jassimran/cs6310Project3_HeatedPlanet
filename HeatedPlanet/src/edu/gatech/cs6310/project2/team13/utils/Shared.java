package edu.gatech.cs6310.project2.team13.utils;

public class Shared {
	private static int maxIters = 10;
	private static boolean showVizPanels = true;
	private static double StabilizationRate = 0.0001;
	
	/**
	 * @return The maximum number of iterations used for testing
	 */
	public static int getMaxIters() {
		return maxIters;
	}

	/**
	 * @param maxIters The maximum number of iterations to use for testing
	 */
	public static void setMaxIters(int maxIters) {
		Shared.maxIters = maxIters;
	}

	public static boolean getShowVizPanels() {
		return showVizPanels;
	}

	public static void setShowVizPanels(boolean showVizPanels) {
		Shared.showVizPanels = showVizPanels;
	}

	/**
	 * @return the stabilizationRate
	 */
	public static double getStabilizationRate() {
		return StabilizationRate;
	}

	/**
	 * @param stabilizationRate the stabilizationRate to set
	 */
	public static void setStabilizationRate(double stabilizationRate) {
		StabilizationRate = stabilizationRate;
	}
	
}
