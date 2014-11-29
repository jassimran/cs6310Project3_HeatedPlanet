package controllers;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import presentation.query.QueryResult;
import presentation.query.SimulationQuery;
import simulation.SimulationSettings;
import events.EventType;
import events.Listener;
import app.conf.BootStrap;

public class AbstractControlTest {
	
	// class under test
	AbstractControl control;

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
		control = new AbstractControl() {			
			@Override
			public void removeListener(Listener l) {
			}
			
			@Override
			public void notify(EventType e) {				
			}
			
			@Override
			public void addListener(Listener l) {
			}
			
			@Override
			protected boolean waiting() {
				return false;
			}
			
			@Override
			public void stopSimulation() {
			}
			
			@Override
			public void runSimulation(SimulationSettings settings) {				
			}
			
			@Override
			public void resumeSimulation() {
			}
			
			@Override
			public void pauseSimulation() {
			}
			
			@Override
			public void handleStopSimulationEvent() {
			}
			
			@Override
			public void handleResumeSimulationEvent() {
			}
			
			@Override
			public void handlePauseSimulationEvent() {
			}

			@Override
			public QueryResult getQueryResultBySimulationName(String simulationName) {
				return null;
			}

			@Override
			public QueryResult computeQueryResults(SimulationQuery simulationQuery) {
				return null;
			}

			@Override
			public List<String> getSimulationListByUserInputs(double axialTilt,
					double orbitalEccentricity, Date endingDate) {
				return null;
			}

			@Override
			public List<String> getSimulationList() {
				return null;
			}
		};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimulationExists() {
		// given:
		String simulationName = "BootStrap Simulation";
		
		// when:
		boolean exists = control.simulationExists(simulationName);
		
		// then:
		assertTrue(exists);
	}

}
