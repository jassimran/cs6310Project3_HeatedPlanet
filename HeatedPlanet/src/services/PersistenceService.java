package services;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import persistence.EntityManagerFactory;
import presentation.earth.TemperatureGrid;
import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

/**
 * Singleton service used for persistence.
 * @author jsoto
 *
 */
public class PersistenceService {
	
	// singleton instance
	private static PersistenceService persistenceService;
	
	// persistence context
	private EntityManager em;
	
	private PersistenceService() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized PersistenceService getInstance() {
		if(persistenceService == null) {
			persistenceService = new PersistenceService();
			persistenceService.em = EntityManagerFactory.createEntityManager();
		}
		return persistenceService;
	}
	
	/**
	 * Persist the given simulation step. The simulation step is represented by the given TemperatureGrid and belongs to the given Simulation.
	 * @param simulation the Simulation associated with the given temperature grid
	 * @param temperatureGrid the TemperatureGrid representing the simulation step to persist
	 */
	public synchronized void persistSimulation(Simulation simulation, TemperatureGrid temperatureGrid, Integer index) {
		// start transaction
		em.getTransaction().begin();
		
		// persist simulation
		if(!em.contains(simulation)) {
			em.persist(simulation);
		}
		
		// get simulated time
		long simulatedDateInMillis = temperatureGrid.getSimulationTime() * 60 * 1000; 
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(simulatedDateInMillis);
		Date simulatedDate = calendar.getTime();
		
		// create EarthGrid
		EarthGrid earthGrid = new EarthGrid();
		earthGrid.setIndex(index);
		earthGrid.setSimulatedDate(simulatedDate);
		earthGrid.setSimulation(simulation);
		em.persist(earthGrid);
		
		// create EarthCells
		for(int y=0; y < temperatureGrid.getRows(); y++) {
			for(int x=0; x < temperatureGrid.getCols(); x++) {
				EarthCell earthCell = new EarthCell();
				earthCell.setRow(y);
				earthCell.setColumn(x);
				earthCell.setTemperature(temperatureGrid.getTemperature(x, y));
				earthCell.setGrid(earthGrid);
				em.persist(earthCell);
			}
		}
		
		// commit transaction
		em.getTransaction().commit();
	}

}
