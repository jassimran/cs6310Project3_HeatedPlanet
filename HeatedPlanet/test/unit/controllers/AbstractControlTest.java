package controllers;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import services.AccuracyService;

public class AbstractControlTest {

	
	@Test
	public void testGetTotalGridCountLength1TimeStep1440() {
		// given:
		int simulationLength = 1; // one month
		int simulationTimeStep = 1440; // one day in minutes
		
		// when:
		int totalGridCount = AbstractControl.getTotalGridCount(simulationLength, simulationTimeStep);
		
		// then:
		assertTrue(totalGridCount == 31);
	}

	@Test
	public void testGetTotalGridCountLength2TimeStep1440() {
		// given:
		int simulationLength = 2; // two months
		int simulationTimeStep = 1440; // one day in minutes
		
		// when:
		int totalGridCount = AbstractControl.getTotalGridCount(simulationLength, simulationTimeStep);
		
		// then:
		assertTrue(totalGridCount == 60);
	}
	
	@Test
	public void testGetTotalGridCountLength2TimeStep720() {
		// given:
		int simulationLength = 2; // two months
		int simulationTimeStep = 720; // half of a day in minutes
		
		// when:
		int totalGridCount = AbstractControl.getTotalGridCount(simulationLength, simulationTimeStep);
		
		// then:
		assertTrue(totalGridCount == 120);
	}
	
	@Test
	public void testSimulationLengthElapsedLength1MinutesElapsed44640() {
		// given:
		int simulationLength = 1; // one month
		int simulationMinutesElapsed = 44640; // one month in minutes
		
		// when:
		boolean lengthElapsed = AbstractControl.simulationLengthElapsed(simulationLength, simulationMinutesElapsed);
		
		// then:
		assertTrue(lengthElapsed);
	}
	
	@Test
	public void testSimulationLengthElapsedLength1MinutesElapsed44639() {
		// given:
		int simulationLength = 1; // one month
		int simulationMinutesElapsed = 44639; // one month in minutes minus 1
		
		// when:
		boolean lengthElapsed = AbstractControl.simulationLengthElapsed(simulationLength, simulationMinutesElapsed);
		
		// then:
		assertFalse(lengthElapsed);
	}
	
	@Test
	public void testSimulationLengthElapsedLength1MinutesElapsed86400() {
		// given:
		int simulationLength = 1; // one month
		int simulationMinutesElapsed = 86400; // one month in minutes
		
		// when:
		boolean lengthElapsed = AbstractControl.simulationLengthElapsed(simulationLength, simulationMinutesElapsed);
		
		// then:
		assertTrue(lengthElapsed);
	}
}
