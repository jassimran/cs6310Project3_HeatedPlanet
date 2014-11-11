package services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccuracyServiceTest {
	
	// service under test
	AccuracyService accuracyService;

	@Before
	public void setUp() throws Exception {
		accuracyService = AccuracyService.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateGapSizeAccuracyTen() {
		// given:
		int numberOfSamples = 100;
		int accuracy = 10;
		
		// when:
		int gapSize = accuracyService.calculateGapSize(numberOfSamples, accuracy);
		
		// then:
		assertTrue(gapSize == 10);
	}
	
	@Test
	public void testCalculateGapSizeAccuracyMax() {
		// given:
		int numberOfSamples = 100;
		int accuracy = 100;
		
		// when:
		int gapSize = accuracyService.calculateGapSize(numberOfSamples, accuracy);
		
		// then:
		assertTrue(gapSize == 0);
	}
	
	@Test
	public void testCalculateGapSizeAccuracyMin() {
		// given:
		int numberOfSamples = 100;
		int accuracy = 1;
		
		// when:
		int gapSize = accuracyService.calculateGapSize(numberOfSamples, accuracy);
		
		// then:
		assertTrue(gapSize == 99);
	}

}
