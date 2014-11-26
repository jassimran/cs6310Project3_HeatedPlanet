package edu.gatech.cs6310.project2.team13.benchmark;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import EarthSim.SimulationEngine;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.gui.widget.input.UserControls;
import edu.gatech.cs6310.project2.team13.utils.Shared;
import edu.gatech.cs6310.project2.team13.utils.Logging;

@BenchmarkOptions(callgc = false, benchmarkRounds = 15, warmupRounds = 5)
public class TimeMultiRound extends AbstractBenchmark{
	
	UserControls uic;
	private static final int testingMaxIters = 10000;
	private static final boolean showPanels = false;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Logging.turnOffLoggingAlways();
		Shared.setMaxIters(testingMaxIters);
		Shared.setShowVizPanels(showPanels);
		Shared.setStabilizationRate(-1.0);
		System.gc();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.gc();
	}

	@After
	public void tearDown() throws Exception {
		uic = null;
		System.gc();
	}

	@Test
	public void zeroMillis() throws Exception{
		Thread.sleep(0);
	}
	
	@Test
	public void twentyMillis() throws Exception{
		Thread.sleep(20);
	}
	
	@Test
	public void thousandMillis() throws Exception{
		Thread.sleep(1000);
	}
	
	@Test
	public void mediating1() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 1;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.MEDIATING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pushing1() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 1;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PUSHING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pulling1() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 1;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PULLING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}

	
	@Test
	public void mediating2() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.MEDIATING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pushing2() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PUSHING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pulling2() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PULLING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}

	
	@Test
	public void mediating3() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.MEDIATING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pushing3() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PUSHING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pulling3() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PULLING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}

	
	@Test
	public void mediating4() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.MEDIATING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pushing4() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PUSHING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}
	
	@Test
	public void pulling4() throws Exception{
		boolean showUserControl = false;
		int bufferLength = 1;
		int displayRate = 30;
		boolean separateThreadForSimulation = true;
		boolean separateThreadForPresentation = true;
		int controlMethod = SimulationEngine.PULLING;
		
		uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, true, false,15);
        uic.setVisible(showUserControl);
        uic.start();
        uic.close();
	}

}
