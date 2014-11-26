package controllers;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Simulation;
import services.PersistenceService;

public class QueryControlTest {

	@Test
	public void testUniqueSimulationName() {
		
		final String simulationName = "Test Unique Name";
		
		PersistenceService ps = PersistenceService.getInstance();
		Simulation sim = new Simulation();
		sim.setName(simulationName);
		sim.setAxialTilt(0);
		sim.setOrbitalEccentricity(0);
		sim.setTimeStep(0);
		sim.setLength(0);
		sim.setGridSpacing(0);
		sim.setPrecision(0);
		
		ps.persistSimulation(sim, null, 0);
		
		assertTrue(new QueryControl().simulationNameExists(simulationName));
		
	}

}
