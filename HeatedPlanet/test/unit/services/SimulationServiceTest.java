package services;

import static org.junit.Assert.*;

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
	public void testCalculateSimulaitonLenght() {
		// given:
		int months = 1;
		int timeStep = 1440; // in minutes
		
		// when:
		int simulationLength = simulationService.calculateSimulaitonLenght(months, timeStep);
		
		// then:
		assertEquals(31, simulationLength);
	}

}
