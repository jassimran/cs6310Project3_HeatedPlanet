package edu.gatech.cs6310.project3.team17.persistence;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntityManagerFactoryTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test factory returns an EntityManger.
	 */
	@Test
	public void testCreateEntityManager() {
		// when:
		EntityManager em = EntityManagerFactory.createEntityManager();
		
		// then:
		assertNotNull(em);
	}

}
