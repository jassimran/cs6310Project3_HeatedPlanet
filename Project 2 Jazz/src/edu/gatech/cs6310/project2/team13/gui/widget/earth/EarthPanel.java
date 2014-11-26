package edu.gatech.cs6310.project2.team13.gui.widget.earth;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.gatech.cs6310.project2.team13.data.EarthGrid.GridSpacingException;

/**
 * A {@link JPanel} composed of the the earth and sun display components.
 * 
 * @author Andrew Bernard
 */
public class EarthPanel extends JPanel {

  private static final long serialVersionUID = -1108120537851962997L;
  private Calendar myCalendar;
  private SunDisplay sunDisplay;
  private EarthGridDisplay earth;
  private JLabel timeDisplay;
  private SimpleDateFormat sdf;
  private BufferedImage colorKey;
  private KeyImage keyImg ;
  private JLabel picLabel;
  private int drawRate;
  private int currentIter = 0;
  
  /**
   * Constructor - sets up the panel with the earth and sun display components using a
   * {@link BoxLayout} with {@link BoxLayout#PAGE_AXIS}.
   * 
   * @param minSize used in calling {@link #setMinimumSize(Dimension)}
   * @param maxSize used in calling {@link #setMaximumSize(Dimension)}
   * @param prefSize used in calling {@link #setPreferredSize(Dimension)}
 * @throws GridSpacingException 
   */
  public EarthPanel(int gridSpacing, int refreshRate, Dimension minSize, Dimension maxSize, Dimension prefSize) {
    super();
    
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setMinimumSize(minSize);
    setMaximumSize(maxSize);
    setPreferredSize(prefSize);
    
    myCalendar = GregorianCalendar.getInstance();
    this.drawRate = refreshRate;

    earth = new EarthGridDisplay(gridSpacing);
    earth.setAlignmentX(Component.LEFT_ALIGNMENT);
        
    sunDisplay = new SunDisplay(earth.getWidth(),360);
    sunDisplay.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    timeDisplay = new JLabel("");
    timeDisplay.setAlignmentX(Component.LEFT_ALIGNMENT);
    timeDisplay.setAlignmentY(Component.CENTER_ALIGNMENT);
    
    keyImg = new KeyImage();
    colorKey = keyImg.getBufferedImage();
    picLabel = new JLabel(new ImageIcon(colorKey));
    picLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    picLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
    
    add(sunDisplay);
    add(earth);
    add(timeDisplay);
    add(picLabel);
  }
  
  /**
   * Draws the grid.
   * 
   * @param degreeSeparation the latitude and longitude degree separations 
   * between the cells in the grid
   */
  public void drawGrid(int degreeSeparation) {
    earth.setGranularity(degreeSeparation);
    sunDisplay.drawSunPath(earth.getWidth());
    repaint();
  }
  
  /**
   * Gets the radius of the earth.
   * 
   * @return the radius of the earth in pixels
   */
  public int getRadius() {
    return earth.getRadius();
  }
      
  /**
   * Updates the display with the values from the temperature grid.
   * 
   * @param grid the grid to get the new temperature values from
   */
  public void updateGrid(TemperatureGrid grid) {
	  //Logging.turnOnLogging();
	  //Logging.writeOut(false,"Should I updateGrid? currentIter="+this.currentIter+", displayRate="+this.drawRate);
	  if(currentIter%this.drawRate == 0){
		//Logging.writeOut(false,"Yes!");
	  	earth.updateGrid(grid);
	  	timeDisplay.setText(sdf.format(grid.getSimulationDate()));  	
	  	repaint();
	  }
   	  moveSunPosition(grid.getSimulationDate());
	  this.currentIter++;
  }
  
  /**
   * Moves the sun's position the specified number of degrees.
   * 
   * @param degrees the number of degrees to move the sun
   */
  public void moveSunPosition(float degrees) {
	  sunDisplay.moveSunPosition(degrees);
	  if(currentIter%this.drawRate == 0) repaint();
  }
  
  public void moveSunPosition(Date simulationTime) {
	  myCalendar.setTime(simulationTime);
	  int hourOfDay = myCalendar.get(Calendar.HOUR_OF_DAY);
	  int minutesSinceMidnight = (hourOfDay*60) + myCalendar.get(Calendar.MINUTE);	 
	  float degrees = 360*(minutesSinceMidnight/1440.0f);
	  sunDisplay.moveSunPosition(degrees);
	  if(currentIter%this.drawRate == 0) repaint();
  }
  
  /**
   * Resets the earth display and sun position.
   */
  public void reset() {
    sunDisplay.reset();
    earth.reset();
    repaint();
  }
  
  /**
   * Sets the opacity of the map image on a scale of 0 to 1, with 0 being 
   * completely transparent.
   * 
   * @param value the opacity value
   */
  public void setMapOpacity(float value) {
    earth.setMapOpacity(value);
    repaint();
  }

}
