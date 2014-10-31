package GUI;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import EarthSim.core.Cell;
import EarthSim.core.Earth;
import EarthSim.core.Grid;

/**
 * Use this class to display an image of the earth with a grid drawn on top. All the methods that could
 * be used to update the grid are given package level access - these methods should be interacted with 
 * from the {@link EarthPanel}.
 * 
 * @author Andrew Bernard
 */
public class EarthGridDisplay extends JPanel {
	private static final long serialVersionUID = -1108120968981962997L;
	private static final float OPACITY = 0.65f;  
	private static final int DEFAULT_CELL_TEMP = 15; //degrees in celsius
	private TemperatureColorPicker colorPicker = TemperatureColorPicker.getInstance();

	private BufferedImage imgTransparent;
	private BufferedImage earthImage;
	private float[] scales = { 1f, 1f, 1f, OPACITY }; //last index controls the transparency
	private float[] offsets = new float[4];
	private int degreeSeparation;
	private int pixelsPerCellX; //number of pixels per latitudal division
	private int pixelsPerCellY; //number of pixels per longitudal division
	private int imgWidth; // in pixels
	private int imgHeight; // in pixels
	private int numCellsX;
	private int numCellsY;
	private int radius;
	private boolean paintInitialColors = true;
	private TemperatureGrid grid;

	/**
	 * Constructs a display grid with a default grid spacing.
	 * 
	 * @param defaultGridPacing in degrees
	 */
	EarthGridDisplay(int defaultGridPacing) {
		earthImage = new EarthImage().getBufferedImage();    
		setGranularity(defaultGridPacing);
		setIgnoreRepaint(true);
	}

	/**
	 * Sets the granularity of the grid.
	 * 
	 * @param degreeSeparation the latitude and longitude degree separations 
	 * between the cells in the grid
	 */
	void setGranularity(int degreeSeparation) {
		this.degreeSeparation = degreeSeparation;

		numCellsX = 360 / degreeSeparation;      
		pixelsPerCellX = earthImage.getWidth() / numCellsX;
		imgWidth = numCellsX * pixelsPerCellX;

		numCellsY = 180 / degreeSeparation;
		pixelsPerCellY = earthImage.getHeight() / numCellsY;
		imgHeight = numCellsY * pixelsPerCellY;
		radius = imgHeight/2;

		//create an image capable of transparency; then draw our image into it
		imgTransparent = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = imgTransparent.getGraphics();
		g.drawImage(earthImage, 0, 0, imgWidth, imgHeight, null);  
	}

	public void paint(Graphics g) {
		//the order in which these are called does matter
		System.out.println("Entered Paint");
		if(paintInitialColors){
			System.out.println("painting initial colors");	
			initCellColors(g);
		}
		else {
			fillCellColors(g);
			System.out.println("painting new cell colors");
		}
		drawTransparentImage(g);
		drawGrid(g);
		System.out.println("Finished Paint in EarthGridDisplay");
	}

	private void initCellColors(Graphics g) {
		g.setColor(colorPicker.getColor(DEFAULT_CELL_TEMP));
		g.fillRect(0, 0, imgWidth, imgHeight);
	}

	/**
	 * Updates the display with the values from the temperature grid.
	 * 
	 * @param grid the grid to get the new temperature values from
	 */
	void updateGrid(TemperatureGrid grid) {
		System.out.println("Entered UpdateGrid in EArthGridDisplay");
		this.grid = grid;
		paintInitialColors = false;    
		this.repaint();
		System.out.println("Finished repainting");
	}

	/**
	 * Gets the radius of the earth.
	 * 
	 * @return the radius of the earth in pixels
	 */
	int getRadius() {
		return radius;
	}

	/**
	 * This is used implicitly by swing to do it's layout job properly
	 */
	public int getWidth() {
		return imgWidth;
	}

	private void fillCellColors(Graphics g) {
		int cellX=0, cellY=0;
		int cellWidth = pixelsPerCellX;
		
		int row;
		int col;
		
		int cellHeight = 0;
		double tY = 0d;
		
		Cell tmpCell = null;
		int yCounter = 0;
		
		int dif = 0;
		
		for (int i = 75 ; i >= -90; i = i - 15) {
		    for (int j = -180; j <= 165 ; j = j + 15) {
			//System.out.println("Testing: [" + i + "," + j + "] = "+ earth.rid(i) + " - " + earth.cjQ(j));
			row = ((Grid)grid).rid(i);
			col = ((Grid)grid).cjQ(j);
			tmpCell = ((Grid)grid).getCell(row, col);
			double newTmp = grid.getTemperature(row, col);
			
			int colorValue = new Double(newTmp).intValue();
			 cellHeight = (int)grid.getCellHeight(row, col);
			 
			 int y = (int)Util.getDistToEquator(j, radius);
			 
			 tY = (tmpCell.Lv / Earth.R) * 198;
			 System.out.println("~~~~~~~~~~" + tY + "--     " + test2.get(yCounter) );
			 
//			 System.out.println("Lv: " + tmpCell.Lv + " - Height: " + tmpCell.h);
//			 System.out.println("Height: " + tmpCell.h + " - calculated height: " + cellWidth);
//			 System.out.println("Distance to equator: " + y);
			 System.out.println("Vertical line: " + tmpCell.Lv + " " + (tmpCell.Lv / Earth.R) * 198);
			g.setColor(colorPicker.getColor(colorValue));
			//g.fillRect(cellX, cellY, cellWidth, cellHeight);
			g.fillRect(cellY, cellX,pixelsPerCellX, (int)tY);
			cellY += pixelsPerCellX;
			
			System.out.println(tmpCell.d + ":" + tmpCell.Q + ">>>>>>>>>> " + cellX + "," + cellY + "," + cellWidth + "," + cellHeight);
		    }
		    cellX += (int) tY;
		    cellY = 0;
		    yCounter++;
		    
		    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		    System.out.println(">>>>>>>>>> " + cellY + "," + cellX + "," + cellWidth + "," + cellHeight);
		}
		
		/*for (int x = 0; x < numCellsX; x++) {      
			for (int y = 0; y < numCellsY; y++) {
				double newTemp = grid.getTemperature(x, y);
				int colorValue = new Double(newTemp).intValue();
				int cellHeight = (int)grid.getCellHeight(x, y);

				g.setColor(colorPicker.getColor(colorValue));
				g.fillRect(cellX, cellY, cellWidth, cellHeight);
				cellY += cellHeight;
			}      
			cellX += cellWidth;
			cellY = 0;
		}*/

		
		/*
				for(int x = 0; x <= imgWidth; x += pixelsPerCellX) {
			g.drawLine(x, 0, x, imgHeight);      
		}


		//draw scaled latitude lines
		for(int lat = 0; lat <= 90; lat += degreeSeparation) {
			int y = (int)Util.getDistToEquator(lat, radius);
			g.drawLine(0, radius-y, imgWidth, radius-y);
			g.drawLine(0, radius+y, imgWidth, radius+y);

		}
		
		 */

	}

	private void drawTransparentImage(Graphics g) {    
		RescaleOp rop = new RescaleOp(scales, offsets, null);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(imgTransparent, rop, 0, 0);
	}
	
	private HashSet<Integer> testSet;
	private List<Integer> test2 ;

	private void drawGrid(Graphics g) {
		g.setColor(Color.black);
		//draw longitude lines
		for(int x = 0; x <= imgWidth; x += pixelsPerCellX) {
			g.drawLine(x, 0, x, imgHeight);      
		}

		
		testSet = new HashSet<Integer>();

		//draw scaled latitude lines
		for(int lat = 0; lat <= 90; lat += degreeSeparation) {
			int y = (int)Util.getDistToEquator(lat, radius);
			g.drawLine(0, radius-y, imgWidth, radius-y);
			g.drawLine(0, radius+y, imgWidth, radius+y);
			
			testSet.add(radius-y);
			testSet.add(radius+y);

		}
		
		System.out.println("Vector is" + testSet);
		
		test2 = new ArrayList<Integer>(testSet);
		 Collections.sort(test2);
		

		g.setColor(Color.blue);
		g.drawLine(imgWidth/2, 0, imgWidth/2, imgHeight); //prime meridian
		g.drawLine(0, imgHeight/2, imgWidth, imgHeight/2); // equator
	}

	/**
	 * Sets the opacity of the map image on a scale of 0 to 1, with 0 being 
	 * completely transparent.
	 * 
	 * @param value the opacity value
	 */
	void setMapOpacity(float value) {
		scales[3] = value;
	}

	void reset() {
		paintInitialColors = true;
	}

}