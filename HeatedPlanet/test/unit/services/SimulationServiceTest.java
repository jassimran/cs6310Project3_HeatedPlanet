package services;

import static org.junit.Assert.*;

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
	public void testCalculateSimulationMonths1() {
		// given:		
		Date endingDate = new Date(2014, 1, 15);
		
		// when:
		int simulationMonths = simulationService.calculateSimulationMonths(endingDate);
		
		// then:
		assertEquals(1, simulationMonths);
	}
	
	@Test
	public void testCalculateSimulationMonths2() {
		// given:
		Date endingDate = new Date(2014, 2, 15);
		
		// when:
		int simulationMonths = simulationService.calculateSimulationMonths(endingDate);
		
		// then:
		assertEquals(2, simulationMonths);
	}
}
