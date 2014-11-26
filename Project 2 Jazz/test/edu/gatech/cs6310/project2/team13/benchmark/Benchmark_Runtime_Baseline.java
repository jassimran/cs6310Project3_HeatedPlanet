package edu.gatech.cs6310.project2.team13.benchmark;

import static org.junit.Assert.*;

import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import EarthSim.SimulationEngine;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import edu.gatech.cs6310.project2.team13.arguments.BenchmarkArguments;
import edu.gatech.cs6310.project2.team13.gui.widget.input.UserControls;
import edu.gatech.cs6310.project2.team13.utils.Logging;
import edu.gatech.cs6310.project2.team13.utils.Shared;

@BenchmarkOptions(callgc = true, benchmarkRounds = Constants.numberOfBenchmarkRounds, warmupRounds = 5)
public class Benchmark_Runtime_Baseline extends AbstractBenchmark {
	
	UserControls uic;
	private static final int testingMaxIters = Constants.numberOfIterationsForTesting;
	private static final boolean showPanels = Constants.showPanelsForTesting;
	private static final boolean doTerminate = true;
	
	Runtime runtime = Runtime.getRuntime();
	
	private PrintStream original = System.out;
	
	
	@Test
	public void benchmark_ThreadSleep10() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_0ST_0VT_M);
	}
	
	@Test
	public void benchmark_ThreadSleep1000() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_0ST_0VT_PH);
	}
	
	@Test
	public void benchmark_ThreadSleep100() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_0ST_0VT_PL);
	}
	
	
	/*****************
	 *
	 * Generic methods
	 * @throws Exception 
	 * 
	 */
	
	private void runTest(BenchmarkArguments args) throws Exception{
		
		int sleepCount = -1;
		switch(args.getInitiative()){
			case MEDIATING:
				sleepCount = 10;
				break;
			case PULLING:
				sleepCount = 100;
				break;
			case PUSHING:
				sleepCount = 1000;
				break;
			default:
				throw new Exception("What are you doing here??");
		}
		
		Thread.sleep(sleepCount);
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Logging.turnOffLoggingAlways();
		Shared.setMaxIters(testingMaxIters);
		Shared.setShowVizPanels(showPanels);
		Shared.setStabilizationRate(Constants.thresholdForTesting);
	}
	
	@Before
	public void setUp() throws Exception {
		disableOutput();
	}

	@After
	public void tearDown() throws Exception {
		enableOutput();
	}
	
	private void disableOutput(){
		System.setOut(Constants.outNull);
	}
	
	private void enableOutput(){
		System.setOut(original);
	}
}
