package services;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimulationServiceTest {

	// service under test
	SimulationService simulationService;
	
	@Before
	public void setUp() throws Exception {
		simulationService = SimulationService.getInstance();
	}

	@After
	public void tearDown() throws Exception {
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
}
