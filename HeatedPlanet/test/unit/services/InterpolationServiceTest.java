package services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.EarthCell;
import domain.EarthGrid;

public class InterpolationServiceTest {

	// service under test
	InterpolationService interpolationService;

	@Before
	public void setUp() throws Exception {
		interpolationService = InterpolationService.getInstance();
	}

	@After
	public void tearDown() throws Exception {
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
	
	@Test
	public void test() {
		// given:
		List<EarthCell> earthCells = new ArrayList<EarthCell>();
		earthCells.add(new EarthCell(null, 0, 0, 25.00));
		earthCells.add(new EarthCell(null, 0, 1, 75.00));
		earthCells.add(new EarthCell(null, 1, 0, 45.00));
		earthCells.add(new EarthCell(null, 1, 1, 19.00));
		
		EarthGrid grid1 = mock(EarthGrid.class);
		when(grid1.getIndex()).thenReturn(1);
		when(grid1.getSimulatedDate()).thenReturn(getBaseDate());
		when(grid1.getNodeList()).thenReturn(earthCells);
		
		EarthGrid grid2 = mock(EarthGrid.class);
		when(grid2.getIndex()).thenReturn(3);
		when(grid2.getNodeList()).thenReturn(earthCells);
		
		int timeStep = 10;
		
		// when:
		EarthGrid earthGrid = interpolationService.interpolate(grid1, grid2, timeStep);
		
		// then:
		assertEquals(2, earthGrid.getIndex());
		assertEquals(addMinutes(getBaseDate(),timeStep), earthGrid.getSimulatedDate());
		assertEquals(earthCells, earthGrid.getNodeList());
	}

}
