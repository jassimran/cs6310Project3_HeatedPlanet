package edu.gatech.cs6310.project3.team17.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerFactory {
	private static javax.persistence.EntityManagerFactory emf;
	
	static {
		// create EntityManagerFactory
		emf = Persistence.createEntityManagerFactory("heatedplanet");
	}
	
	/**
	 * @return a new application-managed EntityManager
	 */
	public static synchronized EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

}
