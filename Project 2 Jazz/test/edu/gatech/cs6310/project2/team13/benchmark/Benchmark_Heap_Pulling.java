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
public class Benchmark_Heap_Pulling extends AbstractBenchmark {
	
	UserControls uic;
	private static final int testingMaxIters = Constants.numberOfIterationsForTesting;
	private static final boolean showPanels = Constants.showPanelsForTesting;
	private static final boolean doTerminate = true;
	
	long memoryBefore, memoryAfter, maxMemoryBefore,maxMemoryAfter;
	Runtime runtime = Runtime.getRuntime();
	
	private PrintStream original = System.out;
	
	
	@Test
	public void HeatedEarth_5DR_5SI_1BS_1GS_0ST_0VT_PL() throws Exception{
		System.out.print("*"+this.getClass().getSimpleName()+".HeatedEarth_5DR_5SI_1BS_1GS_0ST_0VT_PL,");
		disableOutput();
		runTest(Constants.args5DR_5SI_1BS_1GS_0ST_0VT_PL);
		enableOutput();
	}
	
	@Test
	public void HeatedEarth_5DR_5SI_1BS_5GS_0ST_0VT_PL() throws Exception{
		System.out.print("*"+this.getClass().getSimpleName()+".HeatedEarth_5DR_5SI_1BS_5GS_0ST_0VT_PL,");
		disableOutput();
		runTest(Constants.args5DR_5SI_1BS_5GS_0ST_0VT_PL);
		enableOutput();
	}
	
	@Test
	public void HeatedEarth_5DR_5SI_1BS_15GS_0ST_0VT_PL() throws Exception{
		System.out.print("*"+this.getClass().getSimpleName()+".HeatedEarth_5DR_5SI_1BS_15GS_0ST_0VT_PL,");
		disableOutput();
		runTest(Constants.args5DR_5SI_1BS_15GS_0ST_0VT_PL);
		enableOutput();
	}
	
	@Test
	public void HeatedEarth_5DR_5SI_1BS_50GS_0ST_0VT_PL() throws Exception{
		System.out.print("*"+this.getClass().getSimpleName()+".HeatedEarth_5DR_5SI_1BS_50GS_0ST_0VT_PL,");
		disableOutput();
		runTest(Constants.args5DR_5SI_1BS_50GS_0ST_0VT_PL);
		enableOutput();
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
		System.out.println("*Test,MemoryUsedBeforeKB,MemoryUsedAfterKB,MaxMemoryBeforeKB,MaxMemoryAfterKB");
		Logging.turnOffLoggingAlways();
		Shared.setMaxIters(testingMaxIters);
		Shared.setShowVizPanels(showPanels);
		Shared.setStabilizationRate(Constants.thresholdForTesting);
		System.gc();
	}
	
	@Before
	public void setUp() throws Exception {
		runtime.gc();
		System.gc();
		memoryBefore = (runtime.totalMemory()-runtime.freeMemory())/Constants.KB;
		maxMemoryBefore = runtime.maxMemory()/Constants.KB;
	}

	@After
	public void tearDown() throws Exception {
		memoryAfter = (runtime.totalMemory()-runtime.freeMemory())/Constants.KB;
		maxMemoryAfter = runtime.maxMemory()/Constants.KB;
		System.out.println(memoryBefore+","+memoryAfter+","+maxMemoryBefore+","+maxMemoryAfter);
	}
	
	private void disableOutput(){
		System.setOut(Constants.outNull);
	}
	
	private void enableOutput(){
		System.setOut(original);
	}
}
