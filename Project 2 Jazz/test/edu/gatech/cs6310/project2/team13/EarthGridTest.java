package edu.gatech.cs6310.project2.team13;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.data.EarthGrid.GridSpacingException;

public class EarthGridTest {
	
	private EarthGrid grid;
	private final double DELTA = 1e-14; 
	
	/*
	@Before
	public void setUp() throws GridSpacingException {
		this.grid = new EarthGrid(15,15); // gridSpacingDegrees, timeInterval
		this.grid.runSimulationInstance();
	}
	*/
	
	@Test
	public void testSetGridSpacing1() throws GridSpacingException {
		EarthGrid grid = new EarthGrid(15,15);
		assertEquals(15,grid.getGridSpacing());
	}

	@Test(expected = EarthGrid.GridSpacingException.class)
	public void testSetGridSpacing2() throws GridSpacingException {
		new EarthGrid(0,15);
	}
	
	@Test(expected = EarthGrid.GridSpacingException.class)
	public void testSetGridSpacing3() throws GridSpacingException {
		new EarthGrid(181,15);
	}
	
	@Test
	public void testSetGridSpacing4() throws GridSpacingException {
		EarthGrid grid = new EarthGrid(17,15);
		assertEquals(15,grid.getGridSpacing());
	}
	
	@Test
	public void testSetGridSpacing5() throws GridSpacingException {
		EarthGrid grid = new EarthGrid(180,15);
		assertEquals(180,grid.getGridSpacing());
	}
	
	@Test
	public void getEquatorialProportion1()  {
		assertEquals(.0416666667,grid.getEquatorialProportion(), 0.0000000001);
	}
	
	@Test
	public void getRows1() {
		assertEquals(12,grid.getRows());
	}
	
	@Test
	public void getColumns1() {
		assertEquals(24,grid.getColumns());
	}
	
	@Test
	public void getNumberOfCells() {
		assertEquals(288,grid.getNumberOfCells());
	}
	
	@Test
	public void getGridDataRows1() {
		EarthGrid.EarthCell[][] data = grid.getGridData();
		assertEquals(12,data.length);
	}

	@Test
	public void getGridDataColumns1() {
		EarthGrid.EarthCell[][] data = grid.getGridData();
		assertEquals(24,data[0].length);
	}
	
	@Test
	public void getGridColumnNeighborWest1() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[5][5];
		assertEquals(grid.getGridData()[5][6],referenceCell.getWest());
	}
	
	@Test
	public void getGridColumnNeighborEast1() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[5][5];
		assertEquals(grid.getGridData()[5][4],referenceCell.getEast());
	}
	
	@Test
	public void getGridColumnNeighborNorth1() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[5][5];
		assertEquals(grid.getGridData()[6][5],referenceCell.getNorth());
	}
		
	@Test
	public void getGridColumnNeighborSouth1() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[5][5];
		assertEquals(grid.getGridData()[4][5],referenceCell.getSouth());
	}
	
	@Test
	public void getGridColumnNeighborWest2() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][23];
		assertEquals(grid.getGridData()[0][0],referenceCell.getWest());
	}
	
	@Test
	public void getGridColumnNeighborEast2() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][23];
		assertEquals(grid.getGridData()[0][22],referenceCell.getEast());
	}
	
	@Test
	public void getGridColumnNeighborNorth2() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][23];
		assertEquals(grid.getGridData()[1][23],referenceCell.getNorth());
	}
		
	@Test
	public void getGridColumnNeighborSouth2() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][23];
		assertEquals(grid.getGridData()[0][11],referenceCell.getSouth());
	}
	
	@Test
	public void getGridColumnNeighborWest3() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][0];
		assertEquals(grid.getGridData()[11][1],referenceCell.getWest());
	}
	
	@Test
	public void getGridColumnNeighborEast3() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][0];
		assertEquals(grid.getGridData()[11][23],referenceCell.getEast());
	}
	
	@Test
	public void getGridColumnNeighborNorth3() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][0];
		assertEquals(grid.getGridData()[11][12],referenceCell.getNorth());
	}
		
	@Test
	public void getGridColumnNeighborSouth3() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][0];
		assertEquals(grid.getGridData()[10][0],referenceCell.getSouth());
	}
	
	@Test
	public void getCellAtNorthPole() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][0];
		assertEquals(referenceCell,grid.getCellByCoordinates(90, 0));
	}
		
	@Test
	public void getCellAtSouthPole() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][0];
		assertEquals(referenceCell, grid.getCellByCoordinates(-90, 0));
	}
	
	@Test
	public void getCellAtEquator() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[6][0];
		assertEquals(referenceCell, grid.getCellByCoordinates(0, 0));
	}
	
	@Test
	public void getCellByCoordinates4() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][12];
		assertEquals(referenceCell, grid.getCellByCoordinates(90, 180));
	}
	
	@Test
	public void getCellByCoordinates5() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][12];
		assertEquals(referenceCell, grid.getCellByCoordinates(90, -180));
	}
	
	@Test
	public void getCellByCoordinates6() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][13];
		assertEquals(referenceCell, grid.getCellByCoordinates(90, 179));
	}
	
	@Test
	public void getOriginLatitudeByRow1() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[6][12];
		assertEquals(0,referenceCell.getOriginLatitude());
	}
	
	@Test
	public void getOriginLongitudeByColumn1() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[6][12];
		assertEquals(165,referenceCell.getOriginLongitude());	
	}
	
	@Test
	public void getOriginLatitudeByRow2() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][0];
		assertEquals(-90,referenceCell.getOriginLatitude());
	}
	
	@Test
	public void getOriginLongitudeByColumn2() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[0][0];
		assertEquals(-15,referenceCell.getOriginLongitude());	
	}

	@Test
	public void getOriginLatitudeByRow3() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][23];
		assertEquals(75,referenceCell.getOriginLatitude());
	}
	
	@Test
	public void getOriginLongitudeByColumn3() {
		EarthGrid.EarthCell referenceCell = grid.getGridData()[11][23];
		assertEquals(0,referenceCell.getOriginLongitude());	
	}
	
	   
	@Test
	public void getRotationalAngle1() {
		assertEquals(3,grid.getRotationalAngle()); // (15 % 1440) * 360/1440 = 3.75
	}
	
	@Test
	public void getColumnUnderSun1() {
	    // (360/15 * 15/360 + (360/15)/2) % (360/15) = (24 * 0 + 24/2) % 24 = 12 % 24 = 12
		assertEquals(12,grid.getColumnUnderSun()); 
	}

	@Test
	public void getGridCellsPerTimeZoneAtEquator1() {
	    assertEquals(1,grid.getGridCellsPerTimeZoneAtEquator()); // (360/15)/24 = 1
	}
	
    @Test
    public void getEastMostColumnInSunshine1() {
        assertEquals(17,grid.getEastmostColumnInSunshine()); // 12+(5*1) = 17
    }
    
    @Test
    public void getWestMostColumnInSunshine1() {
        assertEquals(6,grid.getWestmostColumnInSunshine()); // 12-(6*1) = 6
    }
    
    @Test
    public void getHeatAttenuationEquator1() {
        assertEquals(1,grid.getHeatAttenuationEquator()); // 24/24 = 1
    }
    
    @Test
    public void avgGridCellSize1() {
        // 4 * Math.PI * Math.pow((6.371 * Math.pow(10,6)), 2) / (12*24) = 1771057194131.209...
        assertEquals(1771057194131.2092890753623171992,grid.avgGridCellSize(),DELTA);
    }
    
    @Test
    public void testColumnRangeUnderSun1() {
    	assertEquals(12,grid.getColumnUnderSun());
    	assertEquals(6,grid.getWestmostColumnInSunshine());
    	assertEquals(17,grid.getEastmostColumnInSunshine());
    	  
    	assertTrue(grid.getGridData()[6][6].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][5].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][4].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][3].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][2].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][1].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][0].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][23].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][22].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][21].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][20].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][19].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][18].isCellInDaylight());
    	assertTrue(grid.getGridData()[6][17].isCellInDaylight());
    	
    	EarthGrid newGrid = null;
    	try {
    		newGrid = new EarthGrid(15,720);
    		newGrid.runSimulationInstance();
    		assertEquals(0,newGrid.getColumnUnderSun());
        	assertEquals(18,newGrid.getWestmostColumnInSunshine());
        	assertEquals(5,newGrid.getEastmostColumnInSunshine());
        	newGrid.runSimulationInstance();
        	assertEquals(12,newGrid.getColumnUnderSun());
        	assertEquals(6,newGrid.getWestmostColumnInSunshine());
        	assertEquals(17,newGrid.getEastmostColumnInSunshine());
        	
    	} catch (GridSpacingException gse) {
    		gse.printStackTrace();
    	}
    	    	
    }

	@Test
	public void testSmallGrid() {
		EarthGrid eg;
		try {
			eg = new EarthGrid(15,15);			
			for(int k=1;k<=10;k++) {
				System.out.println("Running simulation #1: " + eg.getTimeSinceStart() + "minutes since start.");
				eg.runSimulationInstance();
				for(int i=0;i<eg.getRows();i++) {
					for (int j=0;j<eg.getColumns();j++) {
						System.out.println("temperature["+i+"]["+j+"] = " + eg.getGridData()[i][j].getCurrTemp());
					}
				}
				
			}
		} catch (GridSpacingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

    
    /*  TODO first, write tests for all EarthCell's methods so we know what # to expect below.
     *      
    //Represented by Beta
    public double relativeSizeFactor(int i, int j){
        EarthCell cell = new EarthCell(i,j);
        return cell.getArea()/avgGridCellSize();
    }
     
    @Test
    public void relativeSizeFactor1() {
        assertEquals(?D,grid.relativeSizeFactor(0,0),DELTA); // ?/1771057194131.209
    }
    
    //Represented by Gamma
    public double relativeTempFactor(int i, int j){
        EarthCell cell = new EarthCell(i,j);
        return cell.getCurrTemp()/AVERAGE_TEMPERATURE_OF_EARTH; 
    }
    
    @Test
    public void relativeTempFactor1() {
        assertEquals(?D,grid.relativeTempFactor(0,0),DELTA); // ?/14.85
    }
    
    */
    
    @Test
    public void getEquatorialAttenuation1() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[0][0];
        // Math.cos(Math.toRadians(rotationalAngle=3)) = 0.9986295347545738
        assertEquals(0.9986295347545738D,referenceCell.getEquatorialAttenuation(),DELTA);
    }
  
    @Test
    public void getEquatorialAttenuation2() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[11][23];
        // Math.cos(Math.toRadians(rotationalAngle=3)) = 0.9986295347545738
        assertEquals(0.9986295347545738D,referenceCell.getEquatorialAttenuation(),DELTA);
    }
    
    @Test
    public void getAttenuation1() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[6][12]; 
        assertEquals(1D,referenceCell.getAttenuation(),DELTA);
    }
    
    @Test
    public void getAttenuation2() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[11][23]; 
        assertFalse(referenceCell.isCellInDaylight());
        assertEquals(0D,referenceCell.getAttenuation(),DELTA);
    }
    
    @Test
    public void getNeighborsTemp1() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[11][23];  
        assertEquals(14.85D,referenceCell.getNeighborsTemp(),DELTA);
    }
    
    /*
    @Test
    public void getSunTemp1() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[6][0]; 
        assertTrue(referenceCell.isCellInDaylight());
        assertEquals(14.85D,referenceCell.getHeatFromSun(),DELTA);
    }
    
    @Test
    public void getSunTemp2() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[6][12]; 
        assertFalse(referenceCell.isCellInDaylight());
        assertEquals(0D,referenceCell.getHeatFromSun(),DELTA);
    }
    */
    @Test
    public void differentSizeTest1() {
    	EarthGrid testGrid;
		try {
			testGrid = new EarthGrid(90,15);
			assertEquals(4,testGrid.getColumns());
		} catch (GridSpacingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    @Test public void testLatitudeLongitudeCalculations() {
    	assertEquals(grid.getGridData()[0][0],grid.getCellByCoordinates(-90, 0));
    	assertEquals(grid.getGridData()[1][0],grid.getCellByCoordinates(-75, 0));
    	assertEquals(grid.getGridData()[2][0],grid.getCellByCoordinates(-60, 0));
    	assertEquals(grid.getGridData()[3][0],grid.getCellByCoordinates(-45, 0));
    	assertEquals(grid.getGridData()[4][0],grid.getCellByCoordinates(-30, 0));
    	assertEquals(grid.getGridData()[5][0],grid.getCellByCoordinates(-15, 0));
    	assertEquals(grid.getGridData()[6][0],grid.getCellByCoordinates(0, 0));
    	assertEquals(grid.getGridData()[7][0],grid.getCellByCoordinates(15, 0));
    	assertEquals(grid.getGridData()[8][0],grid.getCellByCoordinates(30, 0));
    	assertEquals(grid.getGridData()[9][0],grid.getCellByCoordinates(45, 0));
    	assertEquals(grid.getGridData()[10][0],grid.getCellByCoordinates(60, 0));
    	assertEquals(grid.getGridData()[11][0],grid.getCellByCoordinates(75, 0));    
    	assertEquals(grid.getGridData()[0][1],grid.getCellByCoordinates(-90, -15));
    	assertEquals(grid.getGridData()[0][5],grid.getCellByCoordinates(-90, -75));
    	assertEquals(grid.getGridData()[0][12],grid.getCellByCoordinates(-90, -180));
    	assertEquals(grid.getGridData()[0][13],grid.getCellByCoordinates(-90, 165));
    	assertEquals(grid.getGridData()[0][23],grid.getCellByCoordinates(-90, 15));
    }
    /*
    template code:
    @Test
    public void 1() {
        EarthGrid.EarthCell referenceCell = grid.getGridData()[11][23];  
        assertEquals(D,referenceCell.(),DELTA);
    }
*/
    
}
