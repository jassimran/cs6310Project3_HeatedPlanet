package edu.gatech.cs6310.project2.team13.benchmark;

import java.io.OutputStream;
import java.io.PrintStream;

import edu.gatech.cs6310.project2.team13.arguments.BenchmarkArguments;

public class Constants {
	public static final int KB = 1024;
	public static final int MB = 1024*1024;
	public static final int GB = 1024*1024*1024;
	
	public enum Initiative {MEDIATING,PUSHING,PULLING};
	
	public static final boolean showPanelsForTesting = false;
	public static final int numberOfBenchmarkRounds = 15;
	public static final int numberOfIterationsForTesting = 1000;
	public static final double thresholdForTesting = 0.0001;
	
	/*
	 * Argument sets for Mediating
	 * 
	 * DR = Display Rate
	 * SI = Simulation Interval
	 * BS = Buffer Size
	 * GS = Grid Spacing
	 * ST = Simulation Thread (1/0 :: true/false)
	 * VT = Visualization Thread (1/0 :: true/false)
	 */
	//No Threading
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_0ST_0VT_M = new BenchmarkArguments(5, 5, 1, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_0ST_0VT_M = new BenchmarkArguments(1, 5, 1, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_0ST_0VT_M = new BenchmarkArguments(25, 5, 1, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_0ST_0VT_M = new BenchmarkArguments(5, 1, 1, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_0ST_0VT_M = new BenchmarkArguments(5, 25, 1, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_0ST_0VT_M = new BenchmarkArguments(5, 5, 5, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_0ST_0VT_M = new BenchmarkArguments(5, 5, 25, 15, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_0ST_0VT_M = new BenchmarkArguments(5, 5, 1, 1, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_0ST_0VT_M = new BenchmarkArguments(5, 5, 1, 5, false, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_0ST_0VT_M = new BenchmarkArguments(5, 5, 1, 45, false, false, Initiative.MEDIATING);
	//Simulation is in its own Thread
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_1ST_0VT_M = new BenchmarkArguments(5, 5, 1, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_1ST_0VT_M = new BenchmarkArguments(1, 5, 1, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_1ST_0VT_M = new BenchmarkArguments(25, 5, 1, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_1ST_0VT_M = new BenchmarkArguments(5, 1, 1, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_1ST_0VT_M = new BenchmarkArguments(5, 25, 1, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_1ST_0VT_M = new BenchmarkArguments(5, 5, 5, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_1ST_0VT_M = new BenchmarkArguments(5, 5, 25, 15, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_1ST_0VT_M = new BenchmarkArguments(5, 5, 1, 1, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_1ST_0VT_M = new BenchmarkArguments(5, 5, 1, 5, true, false, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_1ST_0VT_M = new BenchmarkArguments(5, 5, 1, 45, true, false, Initiative.MEDIATING);
	//Visualization is in its own thread
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_0ST_1VT_M = new BenchmarkArguments(5, 5, 1, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_0ST_1VT_M = new BenchmarkArguments(1, 5, 1, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_0ST_1VT_M = new BenchmarkArguments(25, 5, 1, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_0ST_1VT_M = new BenchmarkArguments(5, 1, 1, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_0ST_1VT_M = new BenchmarkArguments(5, 25, 1, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_0ST_1VT_M = new BenchmarkArguments(5, 5, 5, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_0ST_1VT_M = new BenchmarkArguments(5, 5, 25, 15, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_0ST_1VT_M = new BenchmarkArguments(5, 5, 1, 1, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_0ST_1VT_M = new BenchmarkArguments(5, 5, 1, 5, false, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_0ST_1VT_M = new BenchmarkArguments(5, 5, 1, 45, false, true, Initiative.MEDIATING);
	//Simulation and Visualization both in their own threads
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_1ST_1VT_M = new BenchmarkArguments(5, 5, 1, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_1ST_1VT_M = new BenchmarkArguments(1, 5, 1, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_1ST_1VT_M = new BenchmarkArguments(25, 5, 1, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_1ST_1VT_M = new BenchmarkArguments(5, 1, 1, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_1ST_1VT_M = new BenchmarkArguments(5, 25, 1, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_1ST_1VT_M = new BenchmarkArguments(5, 5, 5, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_1ST_1VT_M = new BenchmarkArguments(5, 5, 25, 15, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_1ST_1VT_M = new BenchmarkArguments(5, 5, 1, 1, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_1ST_1VT_M = new BenchmarkArguments(5, 5, 1, 5, true, true, Initiative.MEDIATING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_1ST_1VT_M = new BenchmarkArguments(5, 5, 1, 45, true, true, Initiative.MEDIATING);
	
	
	/*
	 * Argument sets for Pulling
	 * 
	 * DR = Display Rate
	 * SI = Simulation Interval
	 * BS = Buffer Size
	 * GS = Grid Spacing
	 * ST = Simulation Thread (1/0 :: true/false)
	 * VT = Visualization Thread (1/0 :: true/false)
	 */
	//No Threading
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_0ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_0ST_0VT_PL = new BenchmarkArguments(1, 5, 1, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_0ST_0VT_PL = new BenchmarkArguments(25, 5, 1, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_0ST_0VT_PL = new BenchmarkArguments(5, 1, 1, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_0ST_0VT_PL = new BenchmarkArguments(5, 25, 1, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_0ST_0VT_PL = new BenchmarkArguments(5, 5, 5, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_0ST_0VT_PL = new BenchmarkArguments(5, 5, 25, 15, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_0ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 1, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_0ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 5, false, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_0ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 45, false, false, Initiative.PULLING);
	//Simulation is in its own Thread
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_1ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_1ST_0VT_PL = new BenchmarkArguments(1, 5, 1, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_1ST_0VT_PL = new BenchmarkArguments(25, 5, 1, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_1ST_0VT_PL = new BenchmarkArguments(5, 1, 1, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_1ST_0VT_PL = new BenchmarkArguments(5, 25, 1, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_1ST_0VT_PL = new BenchmarkArguments(5, 5, 5, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_1ST_0VT_PL = new BenchmarkArguments(5, 5, 25, 15, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_1ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 1, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_1ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 5, true, false, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_1ST_0VT_PL = new BenchmarkArguments(5, 5, 1, 45, true, false, Initiative.PULLING);
	//Visualization is in its own thread
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_0ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_0ST_1VT_PL = new BenchmarkArguments(1, 5, 1, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_0ST_1VT_PL = new BenchmarkArguments(25, 5, 1, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_0ST_1VT_PL = new BenchmarkArguments(5, 1, 1, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_0ST_1VT_PL = new BenchmarkArguments(5, 25, 1, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_0ST_1VT_PL = new BenchmarkArguments(5, 5, 5, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_0ST_1VT_PL = new BenchmarkArguments(5, 5, 25, 15, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_0ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 1, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_0ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 5, false, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_0ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 45, false, true, Initiative.PULLING);
	//Simulation and Visualization both in their own threads
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_1ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_1ST_1VT_PL = new BenchmarkArguments(1, 5, 1, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_1ST_1VT_PL = new BenchmarkArguments(25, 5, 1, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_1ST_1VT_PL = new BenchmarkArguments(5, 1, 1, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_1ST_1VT_PL = new BenchmarkArguments(5, 25, 1, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_1ST_1VT_PL = new BenchmarkArguments(5, 5, 5, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_1ST_1VT_PL = new BenchmarkArguments(5, 5, 25, 15, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_1ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 1, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_1ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 5, true, true, Initiative.PULLING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_1ST_1VT_PL = new BenchmarkArguments(5, 5, 1, 45, true, true, Initiative.PULLING);
	
	/*
	 * Argument sets for Pushing
	 *
	 * DR = Display Rate
	 * SI = Simulation Interval
	 * BS = Buffer Size
	 * GS = Grid Spacing
	 * ST = Simulation Thread (1/0 :: true/false)
	 * VT = Visualization Thread (1/0 :: true/false)
	 */
	//No Threading
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_0ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_0ST_0VT_PH = new BenchmarkArguments(1, 5, 1, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_0ST_0VT_PH = new BenchmarkArguments(25, 5, 1, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_0ST_0VT_PH = new BenchmarkArguments(5, 1, 1, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_0ST_0VT_PH = new BenchmarkArguments(5, 25, 1, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_0ST_0VT_PH = new BenchmarkArguments(5, 5, 5, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_0ST_0VT_PH = new BenchmarkArguments(5, 5, 25, 15, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_0ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 1, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_0ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 5, false, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_0ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 45, false, false, Initiative.PUSHING);
	//Simulation is in its own Thread
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_1ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_1ST_0VT_PH = new BenchmarkArguments(1, 5, 1, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_1ST_0VT_PH = new BenchmarkArguments(25, 5, 1, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_1ST_0VT_PH = new BenchmarkArguments(5, 1, 1, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_1ST_0VT_PH = new BenchmarkArguments(5, 25, 1, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_1ST_0VT_PH = new BenchmarkArguments(5, 5, 5, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_1ST_0VT_PH = new BenchmarkArguments(5, 5, 25, 15, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_1ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 1, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_1ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 5, true, false, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_1ST_0VT_PH = new BenchmarkArguments(5, 5, 1, 45, true, false, Initiative.PUSHING);
	//Visualization is in its own thread
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_0ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_0ST_1VT_PH = new BenchmarkArguments(1, 5, 1, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_0ST_1VT_PH = new BenchmarkArguments(25, 5, 1, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_0ST_1VT_PH = new BenchmarkArguments(5, 1, 1, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_0ST_1VT_PH = new BenchmarkArguments(5, 25, 1, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_0ST_1VT_PH = new BenchmarkArguments(5, 5, 5, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_0ST_1VT_PH = new BenchmarkArguments(5, 5, 25, 15, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_0ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 1, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_0ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 5, false, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_0ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 45, false, true, Initiative.PUSHING);
	//Simulation and Visualization both in their own threads
	public static final BenchmarkArguments args5DR_5SI_1BS_15GS_1ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args1DR_5SI_1BS_15GS_1ST_1VT_PH = new BenchmarkArguments(1, 5, 1, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args25DR_5SI_1BS_15GS_1ST_1VT_PH = new BenchmarkArguments(25, 5, 1, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_1SI_1BS_15GS_1ST_1VT_PH = new BenchmarkArguments(5, 1, 1, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_25SI_1BS_15GS_1ST_1VT_PH = new BenchmarkArguments(5, 25, 1, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_5BS_15GS_1ST_1VT_PH = new BenchmarkArguments(5, 5, 5, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_25BS_15GS_1ST_1VT_PH = new BenchmarkArguments(5, 5, 25, 15, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_1GS_1ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 1, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_5GS_1ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 5, true, true, Initiative.PUSHING);
	public static final BenchmarkArguments args5DR_5SI_1BS_50GS_1ST_1VT_PH = new BenchmarkArguments(5, 5, 1, 45, true, true, Initiative.PUSHING);
	
	
	
	protected static final PrintStream outNull = new PrintStream(new OutputStream() {
        public void write(int b) {
            //DO NOTHING
        }
    });

}
