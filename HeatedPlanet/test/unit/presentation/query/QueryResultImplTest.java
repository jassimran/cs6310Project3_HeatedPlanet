package presentation.query;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Simulation;
import persistence.EntityManagerFactory;
import services.SimulationService;
import app.conf.BootStrap;

public class QueryResultImplTest {

	private EntityManager em;
	
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

	@Test
	public final void testGetMinTempCell() {
		// given:
		TypedQuery<Simulation> q = em.createNamedQuery("Simulation.findByName", Simulation.class);
		q.setParameter("name", "BootStrap Simulation");
		Simulation simulation = q.getSingleResult();
		
		// when:
		QueryResultImpl qr = new QueryResultImpl(simulation);
		
		// then:
		assertEquals(25.00, qr.getMinTempCell().getTemperature(), 0);
		//assertEquals(expected, actual);
	}

	@Test
	public final void testGetMaxTempCell() {
		// given:
		TypedQuery<Simulation> q = em.createNamedQuery("Simulation.findByName", Simulation.class);
		q.setParameter("name", "BootStrap Simulation");
		Simulation simulation = q.getSingleResult();
		
		// when:
		QueryResultImpl qr = new QueryResultImpl(simulation);
		
		// then:
		assertEquals(100.00, qr.getMaxTempCell().getTemperature(), 0);
	}

	@Test
	public final void testGetQueryGrids() {
		// given:
		TypedQuery<Simulation> q = em.createNamedQuery("Simulation.findByName", Simulation.class);
		q.setParameter("name", "BootStrap Simulation");
		Simulation simulation = q.getSingleResult();
		int simulationLength = SimulationService.getInstance().calculateSimulaitonLenght(simulation.getLength(), simulation.getTimeStep());
		
		// when:
		QueryResultImpl qr = new QueryResultImpl(simulation);
		
		// then:
		assertEquals(simulationLength, qr.getQueryGrids().size());
	}

	@Test
	public final void testGetMeanTempOverTime() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetMeanTempOverRegion() {
		fail("Not yet implemented"); // TODO
	}

}
