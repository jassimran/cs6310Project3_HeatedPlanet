package edu.gatech.cs6310.project3.team17.domain;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Simulation;
import edu.gatech.cs6310.project3.team17.persistence.EntityManagerFactory;

public class SimulationTest {
	
	EntityManager em;

	@Before
	public void setUp() throws Exception {
		em = EntityManagerFactory.createEntityManager();
	}

	@After
	public void tearDown() throws Exception {
		em.close();
	}

	@Test
	public void testSave() {
		// given:
		Simulation simulation = new Simulation();
		simulation.setName("Unit Test");
		
		// when:
		em.getTransaction().begin();
		em.persist(simulation);
		em.getTransaction().commit();
		
		// then:
		assertTrue(em.contains(simulation));
	}

}
