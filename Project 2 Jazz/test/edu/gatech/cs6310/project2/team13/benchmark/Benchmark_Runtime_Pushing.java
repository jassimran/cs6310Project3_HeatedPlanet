package edu.gatech.cs6310.project2.team13.benchmark;

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
public class Benchmark_Runtime_Pushing extends AbstractBenchmark {
	
	UserControls uic;
	private static final int testingMaxIters = Constants.numberOfIterationsForTesting;
	private static final boolean showPanels = Constants.showPanelsForTesting;
	private static final boolean doTerminate = true;
	
	Runtime runtime = Runtime.getRuntime();
	
	private PrintStream original = System.out;
	
	
	
	@Test
	public void HeatedEarth_5DR_5SI_1BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_1DR_5SI_1BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args1DR_5SI_1BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_25DR_5SI_1BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args25DR_5SI_1BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_1SI_1BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_1SI_1BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_25SI_1BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_25SI_1BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_5BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_5BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_25BS_15GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_25BS_15GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_1GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_1GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_5GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_5GS_0ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_50GS_0ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_50GS_0ST_0VT_PH);
}

	@Test
	public void HeatedEarth_5DR_5SI_1BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_1DR_5SI_1BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args1DR_5SI_1BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_25DR_5SI_1BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args25DR_5SI_1BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_1SI_1BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_1SI_1BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_25SI_1BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_25SI_1BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_5BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_5BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_25BS_15GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_25BS_15GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_1GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_1GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_5GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_5GS_1ST_0VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_50GS_1ST_0VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_50GS_1ST_0VT_PH);
}

	@Test
	public void HeatedEarth_5DR_5SI_1BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_1DR_5SI_1BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args1DR_5SI_1BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_25DR_5SI_1BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args25DR_5SI_1BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_1SI_1BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_1SI_1BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_25SI_1BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_25SI_1BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_5BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_5BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_25BS_15GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_25BS_15GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_1GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_1GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_5GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_5GS_0ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_50GS_0ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_50GS_0ST_1VT_PH);
}

	@Test
	public void HeatedEarth_5DR_5SI_1BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_1DR_5SI_1BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args1DR_5SI_1BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_25DR_5SI_1BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args25DR_5SI_1BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_1SI_1BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_1SI_1BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_25SI_1BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_25SI_1BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_5BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_5BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_25BS_15GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_25BS_15GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_1GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_1GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_5GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_5GS_1ST_1VT_PH);
}
	@Test
	public void HeatedEarth_5DR_5SI_1BS_50GS_1ST_1VT_PH() throws Exception {
		runTest(Constants.args5DR_5SI_1BS_50GS_1ST_1VT_PH);
}
	
	
	/*****************
	 *
	 * Generic methods
	 * @throws Exception 
	 * 
	 */
	
	private void runTest(BenchmarkArguments args) throws Exception{
		
		int controlMethod = -1;
		switch(args.getInitiative()){
			case MEDIATING:
				controlMethod = SimulationEngine.MEDIATING;
				break;
			case PULLING:
				controlMethod = SimulationEngine.PULLING;
				break;
			case PUSHING:
				controlMethod = SimulationEngine.PUSHING;
				break;
			default:
				throw new Exception("What are you doing here??");
		}
		
		uic = new UserControls(
					args.getDisplayRate(),
					args.getBufferSize(),
					args.isSeparateSimulationThread(),
					args.isSeparateVisualizationThread(),
					controlMethod,
					doTerminate,
					showPanels,
					args.getGridSize()
				);
       uic.setVisible(showPanels);
       uic.start();
       uic.close();
       uic = null;
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
