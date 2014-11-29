package services;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import presentation.earth.EarthPanel;
import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;
import app.conf.BootStrap;

public class SimulationServiceTest {

	// service under test
	SimulationService simulationService  = SimulationService.getInstance();
	
	@BeforeClass
	public static void setupBeforeClass() {
		BootStrap.init();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		BootStrap.destroy();
	}

	@Test
	public void testCalculateSimulationLength1() {
		// given:
		int months = 1;
		int timeStep = 1440; // in minutes
		
		// when:
		int simulationLength = simulationService.calculateSimulationLength(months, timeStep);
		
		// then:
		assertEquals(31, simulationLength);
	}
	
	@Test
	public void testCalculateSimulationLength2() {
		// given:
		int months = 2;
		int timeStep = 1440; // in minutes
		
		// when:
		int simulationLength = simulationService.calculateSimulationLength(months, timeStep);
		
		// then:
		assertEquals(31+28, simulationLength);
	}

	@Test
	public void testCalculateSimulationMonths1() throws ParseException {
		// given:		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date endingDate = sdf.parse("01/15/2014");
		
		// when:
		int simulationMonths = simulationService.calculateSimulationMonths(endingDate);
		
		// then:
		assertEquals(1, simulationMonths);
	}
	
	@Test
	public void testCalculateSimulationMonths2() throws ParseException {
		// given:
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date endingDate = sdf.parse("02/15/2014");
		
		// when:
		int simulationMonths = simulationService.calculateSimulationMonths(endingDate);
		
		// then:
		assertEquals(2, simulationMonths);
	}

	@Test
	public void testGetNeighbors() {
		// given:
		int radiusOfInterest = 5;
		Simulation simulation = PersistenceService.getInstance().findBySimulationName("BootStrap Simulation");
		EarthGrid earthGrid = simulation.getTimeStepList().get(186);
		EarthCell earthCell = simulationService.getEarthCell(earthGrid.getNodeList(), 5, 5, simulation.getNumberOfColumns());
		
		// when:
		List<EarthCell> neighbors = simulationService.getNeighbors(earthCell, radiusOfInterest, simulation.getNumberOfColumns());
		
		// then:
		assertEquals(120, neighbors.size());
	}
	
}
