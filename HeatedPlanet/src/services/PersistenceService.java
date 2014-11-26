package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import persistence.EntityManagerFactory;
import presentation.earth.TemperatureGrid;
import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

/**
 * Singleton service used for persistence.
 * 
 * @author jsoto
 * 
 */
public class PersistenceService {

	// singleton instance
	private static PersistenceService serviceInstance;

	// persistence context
	private EntityManager em;

	// used services
	private AccuracyService accuracyService;

	private PersistenceService() {
		// TODO Auto-generated constructor stub
	}

	public static synchronized PersistenceService getInstance() {
		if (serviceInstance == null) {
			serviceInstance = new PersistenceService();
			// get persistence context
			serviceInstance.em = EntityManagerFactory.createEntityManager();
			// get used services
			serviceInstance.accuracyService = AccuracyService.getInstance();
		}
		return serviceInstance;
	}

	/**
	 * Persist the given simulation step. The simulation step is represented by
	 * the given TemperatureGrid and belongs to the given Simulation.
	 * 
	 * @param simulation
	 *            the Simulation associated with the given temperature grid
	 * @param temperatureGrid
	 *            the TemperatureGrid representing the simulation step to
	 *            persist
	 */
	public synchronized void persistSimulation(Simulation simulation,
			TemperatureGrid temperatureGrid, Integer index) {
		// start transaction
		em.getTransaction().begin();

		// persist simulation
		if (!em.contains(simulation)) {
			em.persist(simulation);
		}

		if (temperatureGrid != null) {
			// get simulated time
			int simulatedDateInMillis = temperatureGrid.getSimulationTime() * 60 * 1000;
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 00);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.DAY_OF_MONTH, 4);
			calendar.set(Calendar.YEAR, 2014);
			calendar.add(Calendar.MILLISECOND, simulatedDateInMillis);
			Date simulatedDate = calendar.getTime();

			// create EarthGrid
			EarthGrid earthGrid = new EarthGrid();
			earthGrid.setIndex(index);
			earthGrid.setSimulatedDate(simulatedDate);
			earthGrid.setSimulation(simulation);
			em.persist(earthGrid);
			
			// add the grid to the simulation so that it will be in memory the hibernate cache
			List<EarthGrid> earthGrids = simulation.getTimeStepList();
			if(earthGrids == null)
				earthGrids = new ArrayList<EarthGrid>();
			earthGrids.add(earthGrid);
			simulation.setTimeStepList(earthGrids);

			// calculate accuracy gap
			int totalCells = temperatureGrid.getRows()
					* temperatureGrid.getCols();
			int gapSize = accuracyService.calculateGapSize(totalCells,
					simulation.getGeoAccuracy());

			int gapControl = gapSize; // use to place gaps between samples to
										// persist

			// create EarthCells
			for (int y = 0; y < temperatureGrid.getRows(); y++) {
				for (int x = 0; x < temperatureGrid.getCols(); x++) {
					EarthCell earthCell = new EarthCell();
					earthCell.setRow(y);
					earthCell.setColumn(x);
					earthCell.setTemperature(temperatureGrid.getTemperature(x,
							y));
					earthCell.setGrid(earthGrid);
					if ((++gapControl) == (gapSize + 1)) {
						em.persist(earthCell);
						
						// add the cell to the simulation so that it will be in memory the hibernate cache
						List<EarthCell> earthCells = earthGrid.getNodeList();
						if(earthCells == null)
							earthCells = new ArrayList<EarthCell>();
						earthCells.add(earthCell);
						earthGrid.setNodeList(earthCells);;
						
						gapControl = 0;
					}
				}
			}
		}

		// commit transaction
		em.getTransaction().commit();
	}

	public List<Simulation> findAllSimulations() {
		TypedQuery<Simulation> query = em.createNamedQuery(
				"Simulation.findAll", Simulation.class);
		return query.getResultList();
	}

	public Simulation findBySimulationName(String simulationName) {
		TypedQuery<Simulation> typedQuery = em.createNamedQuery("Simulation.findByName", Simulation.class);
		typedQuery.setParameter("name", simulationName);

		try {
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Simulation> findSimulationsByUserInputs(double axialTilt,
			double orbitalEccentricity, Date endingDate) {
		
		String jpql = "SELECT s FROM Simulation s WHERE s.axialTilt = :axialTilt and s.orbitalEccentricity = :orbitalEccentricity and s.length >= :length";
		
		TypedQuery<Simulation> typedQuery = em.createQuery(jpql, Simulation.class);

		int simulationLengthInMonths = SimulationService.getInstance().calculateSimulationMonths(endingDate);

		typedQuery.setParameter("axialTilt", axialTilt);
		typedQuery.setParameter("orbitalEccentricity", orbitalEccentricity);
		typedQuery.setParameter("length", simulationLengthInMonths);

		List<Simulation> results = typedQuery.getResultList();

		return results;
	}
}
