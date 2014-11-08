package edu.gatech.cs6310.project2.team13.gui.widget.earth;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.JPanel;

import edu.gatech.cs6310.project2.team13.data.EarthGrid.GridSpacingException;

/**
 * Use this class to display an image of the earth with a grid drawn on top. All the methods that could
 * be used to update the grid are given package level access - these methods should be interacted with 
 * from the {@link EarthPanel}.
 * 
 * @author Andrew Bernard
 */
public class EarthGridDisplay extends JPanel {
  private static final long serialVersionUID = -1108120968981962997L;
  private static final float OPACITY = 0.15f;  
  private static final int DEFAULT_CELL_TEMP = 288; //degrees in kelvins
  private TemperatureColorPicker colorPicker = TemperatureColorPicker.getInstance();
  
  private BufferedImage imgTransparent;
  private BufferedImage earthImage;
  private float[] scales = { 1f, 1f, 1f, OPACITY }; //last index controls the transparency
  private float[] offsets = new float[4];
  private int degreeSeparation;
  private int pixelsPerCellX; //number of pixels per latitudinal division
  private int pixelsPerCellY; //number of pixels per longitudinal division
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
 * @throws GridSpacingException 
   */
  public EarthGridDisplay(int defaultGridPacing) {
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
    
    // X is columns
    numCellsX = 360 / degreeSeparation;
    pixelsPerCellX = earthImage.getWidth() / numCellsX;
    imgWidth = numCellsX * pixelsPerCellX;

    // Y is rows
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
    if(paintInitialColors)
      initCellColors(g);
    else 
      fillCellColors(g);
    drawTransparentImage(g);
    drawGrid(g);
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
    this.grid = grid;
    paintInitialColors = false;
    //this.paintImmediately(this.getBounds());
    this.repaint();
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
  
  private int[] cellHeights;
  
  private void fillCellColors(Graphics g) {
    int cellX=0, cellY=0;
    int cellWidth = pixelsPerCellX;
    
    for (int x = 0; x < numCellsX; x++) {      
      for (int y = 0; y < numCellsY; y++) {
    	  if(grid!=null && cellHeights!=null ) {
    	        double newTemp = grid.getTemperature(x, y);
    	        int colorValue = new Double(newTemp).intValue();
    	        g.setColor(colorPicker.getColor(colorValue));
    	        Color test = g.getColor();
	        	g.fillRect(cellX, cellY, cellWidth, cellHeights[y]);
	        	cellY += cellHeights[y];
    	  }
      }      
      cellX += cellWidth;
      cellY = 0;
    }
  }
  
  private void drawTransparentImage(Graphics g) {    
    RescaleOp rop = new RescaleOp(scales, offsets, null);
    Graphics2D g2d = (Graphics2D)g;
    g2d.drawImage(imgTransparent, rop, 0, 0);
  }
    
  private void drawGrid(Graphics g) {
    g.setColor(Color.black);
    
    //draw longitude lines
    for(int x = 0; x <= imgWidth; x += pixelsPerCellX) {
      g.drawLine(x, 0, x, imgHeight);      
    }
    
    int seq1=0;
    int seq2=0;
    int lastY=0;
    cellHeights = new int[numCellsY];
    //draw scaled latitude lines
    for(int lat = 0; lat <= 90; lat += degreeSeparation) {
      int y = (int)Util.getDistToEquator(lat, radius);
      if(lat==0) {
    	  seq2=numCellsY/2;
    	  seq1=seq2-1;
      } else {
    	  cellHeights[seq2]=y-lastY;
    	  cellHeights[seq1]=y-lastY;
    	  seq1--;
    	  seq2++;    	  
      }
      lastY=y;
      g.drawLine(0, radius-y, imgWidth, radius-y);
      g.drawLine(0, radius+y, imgWidth, radius+y);
    }
    
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
