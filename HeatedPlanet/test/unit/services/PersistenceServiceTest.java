package services;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.EntityManagerFactory;
import app.conf.BootStrap;
import domain.Simulation;



public class PersistenceServiceTest {
	
	// service under test
	private PersistenceService persistenceService = PersistenceService.getInstance();

	// persistence context
	EntityManager em;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BootStrap.init();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BootStrap.destroy();
	}

	@Before
	public void setUp() throws Exception {
		em = EntityManagerFactory.createEntityManager();
	}

	@After
	public void tearDown() throws Exception {
		em.close();
	}	

//	@Test
//	public final void testPersistSimulation() {
//		em.getTransaction().begin();
//		final String simulationName = "Test Simulation Name";
//		
//		PersistenceService ps = PersistenceService.getInstance();
//		Simulation sim = new Simulation();
//		sim.setName(simulationName);
//		sim.setAxialTilt(0);
//		sim.setOrbitalEccentricity(0);
//		sim.setTimeStep(0);
//		sim.setLength(0);
//		sim.setGridSpacing(0);
//		sim.setPrecision(0);
//		
//		ps.persistSimulation(sim, null, 0);
//		
//		Simulation matchingSimulation = persistenceService.findBySimulationName(simulationName);
//		
//		assertTrue(matchingSimulation.equals(simulationName));
//		em.getTransaction().rollback();
//	}

	@Test
	public final void testFindAllSimulations() {
		// when:
		List<Simulation> simulations = persistenceService.findAllSimulations();
		
		// then:
		assertTrue(simulations.size() == 1);
		assertEquals("BootStrap Simulation", simulations.get(0).getName());
	}

	@Test
	public final void testFindBySimulationName() {
		// when:
		Simulation simulation = persistenceService.findBySimulationName("BootStrap Simulation");
		
		// then:
		assertTrue(simulation.getName().equals("BootStrap Simulation"));
	}

	@Test
	public final void testDeleteSimulation() {
		fail("Not implemented yet...");
	}
}
