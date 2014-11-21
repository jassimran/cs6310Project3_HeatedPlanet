package controllers;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
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
import services.PersistenceService;

public class QueryControlTest {

	private static QueryControl qc;
	private EntityManager em;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BootStrap.init();
		qc = new QueryControl();
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
	public void testUniqueSimulationName() {
		
		// given:
		final String simulationName = "BootStrap Simulation";
		
		// then:
		assertTrue(qc.simulationNameExists(simulationName));
		
	}
	
	@Test
	public void testGetSimulationList() {
		// when:
		List<String> simulations = qc.getSimulationList();
		
		// then:
		assertEquals(1, simulations.size());
	}	

	@Test
	public void testGetSimulationListByUserInputs() {
		// given:
		double axialTilt = 25;
		double orbitalEccentricity = 0.026;
		Date endingDate = getBaseDate();
		endingDate = addMinutes(endingDate, 1440 *14); 
		// 14 days from the start (this just needs to be less than 
		// 1 month to find a match since the bootstrap simulation is set to 1 month)
		
		// when:
		List<String> simulations = qc.getSimulationListByUserInputs(axialTilt, orbitalEccentricity, endingDate);
		
		// then:
		assertEquals(1, simulations.size());
	}	
	
	
	
	public Date getBaseDate(){
		Calendar calendar = Calendar.getInstance();
		
		// calculate base time (12:00 PM, January 4, 2014)
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DAY_OF_MONTH, 4);
		calendar.set(Calendar.YEAR, 2014);
		
		return calendar.getTime();
	}
	
	public Date addMinutes(Date date, int mins){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, mins);
		
		return calendar.getTime();
	}
}
