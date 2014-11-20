package app.conf;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;

import persistence.EntityManagerFactory;
import services.AccuracyService;
import services.SimulationService;
import domain.EarthCell;
import domain.EarthGrid;
import domain.Simulation;

public class BootStrap {

	// persistence context
	static EntityManager em;
	
	public static void init() {
		// get persistence context
		em = EntityManagerFactory.createEntityManager();
		
		// create simulation query
		TypedQuery<Simulation> simulationQuery = em.createNamedQuery("Simulation.findAll", Simulation.class);
		
		// create initial data
		if(simulationQuery.getResultList().size() == 0 ) {
			// create simulation
			Simulation simulation = new Simulation();
			simulation.setName("BootStrap Simulation");
			simulation.setOrbitalEccentricity(0.026);
			simulation.setAxialTilt(25);
			simulation.setTemporalAccuracy(50);
			simulation.setGeoAccuracy(100);
			simulation.setLength(1);
			simulation.setTimeStep(60);
			simulation.setGridSpacing(15);
			simulation.setNumberOfColumns(24);
			simulation.setNumberOfRows(12);
			
			// get total number of grids
			int totalGrids = SimulationService.getInstance().calculateSimulaitonLenght(simulation.getLength(), simulation.getTimeStep()); // 744
			
			// get temporal gap size
			int gapSize = AccuracyService.getInstance().calculateGapSize(totalGrids, simulation.getTemporalAccuracy());
			
			// base date
			DateTime dateTime = new DateTime(2014, 1, 4, 12, 00);
			
			// create grids
			List<EarthGrid> grids = new ArrayList<EarthGrid>();
			for(int i = 1; i <= totalGrids; i = ( i + 1 ) + gapSize) {
				EarthGrid earthGrid = new EarthGrid();
				earthGrid.setIndex(i);
				earthGrid.setSimulatedDate(dateTime.toDate());
				earthGrid.setSimulation(simulation);
				grids.add(earthGrid);
				
				// crate cells
				List<EarthCell> cells = new ArrayList<EarthCell>();
				for(int x = 0 ; x < 24; x++) {
					for(int y = 0 ; y < 12; y ++) {
						EarthCell earthCell = new EarthCell();
						earthCell.setRow(y);
						earthCell.setColumn(x);
						earthCell.setTemperature((x==5&&y==5)?25.00:100.00);
						earthCell.setGrid(earthGrid);
						cells.add(earthCell);
					}
				}
				
				// add cells
				earthGrid.setNodeList(cells);
				dateTime = dateTime.plusMinutes(simulation.getTimeStep());
			}
			
			// set grids
			simulation.setTimeStepList(grids);
			
			// save simulation
			em.getTransaction().begin();
			em.persist(simulation);
			em.getTransaction().commit();
		}
	}
	
	public static void destroy() {
		em.close();
	}
	
}
