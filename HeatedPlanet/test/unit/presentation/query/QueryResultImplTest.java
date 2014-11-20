package presentation.query;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
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
		Simulation simulation = getBootstrapSimulation();
		
		// when:
		QueryResultImpl qr = new QueryResultImpl(simulation);
		
		// then:
		assertEquals(25.00, qr.getMinTempCell().getTemperature(), 0);
		//assertEquals(expected, actual);
	}

	@Test
	public final void testGetMaxTempCell() {
		Simulation simulation = getBootstrapSimulation();
		
		// when:
		QueryResultImpl qr = new QueryResultImpl(simulation);
		
		// then:
		assertEquals(100.00, qr.getMaxTempCell().getTemperature(), 0);
	}

	@Test
	public final void testGetQueryGrids() {
		// given:
		Simulation simulation = getBootstrapSimulation();
		int simulationLength = SimulationService.getInstance().calculateSimulaitonLenght(simulation.getLength(), simulation.getTimeStep());
		
		// when:
		QueryResultImpl qr = new QueryResultImpl(simulation);
		
		// then:
		assertEquals(simulationLength, qr.getQueryGrids().size());
	}

	@Test
	public final void testGetMeanTempOverTime() {
		// given:
		Simulation simulation = getBootstrapSimulation();
		//DateTime baseDate = new DateTime(2014, 1, 4, 12, 0, 0);
		
		// when: 
		QueryResultImpl qr = new QueryResultImpl(simulation);
		List<QueryCell> meanTempsOverTime = qr.getMeanTempOverTime();
		
		// then:
		assertEquals(288, meanTempsOverTime.size());
		
		for(QueryCell cell : meanTempsOverTime){
			int row = cell.getRow();
			int column = cell.getColumn();
			assertEquals((column==5&&row==5)?25.00:100.00, cell.getTemperature(), 0);
		}
	}

	@Test
	public final void testGetMeanTempOverRegion() {
		// given:
		Simulation simulation = getBootstrapSimulation();
		DateTime baseDate = new DateTime(2014, 1, 4, 12, 0, 0);
		
		// when: 
		QueryResultImpl qr = new QueryResultImpl(simulation);
		List<QueryCell> meanTempsOverRegion = qr.getMeanTempOverRegion();
		
		// then:
		assertEquals(372, meanTempsOverRegion.size());
		
		for(QueryCell cell : meanTempsOverRegion){
			assertEquals(baseDate.toDate(), cell.getSimulatedDate());
			assertEquals(99.74, cell.getTemperature(), 0.01);
			baseDate = baseDate.plusMinutes(simulation.getTimeStep());
		}
	}

	private Simulation getBootstrapSimulation() {
		TypedQuery<Simulation> q = em.createNamedQuery("Simulation.findByName", Simulation.class);
		q.setParameter("name", "BootStrap Simulation");
		Simulation simulation = q.getSingleResult();
		return simulation;
	}
}
