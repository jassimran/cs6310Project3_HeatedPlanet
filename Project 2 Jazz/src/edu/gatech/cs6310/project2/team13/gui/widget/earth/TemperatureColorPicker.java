package edu.gatech.cs6310.project2.team13.gui.widget.earth;

import java.awt.Color;

import edu.gatech.cs6310.project2.team13.utils.Logging;

/**
 * Use this class to get a color representation of a temperature. 
 * This is implemented as a singleton.
 * 
 * @author Andrew Bernard  
 */
public class TemperatureColorPicker {
	private final int ABS_MINTEMP = 230;
	private final int ABS_MIDTEMP = 288;
	private final int ABS_MAXTEMP = 350;
	
	private static TemperatureColorPicker instance = null;
	
	private TemperatureColorPicker() {	}
	
	static TemperatureColorPicker getInstance() {
		if(instance == null) {
			instance = new TemperatureColorPicker();
		}
		return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("This is a singleton - please use the getInstance() method.");
	}	
	
	/**
	 * Maps a color to the given temperature.
	 * 
	 * @param temperature in Kelvin
	 * @return the temperature color
	 */
	Color getColor(int tempInK) {
		int temperature = tempInK;
		float b = 0;
		float g = 0;
		float r = 0;
		
		int minTemp;
		int maxTemp;
		
		int minBlue = 0;
		int minGreen = 0;
		int minRed = 0;
		
		int maxBlue = 0;
		int maxGreen = 0;
		int maxRed = 0;
		
		if(temperature < ABS_MINTEMP){
			temperature = ABS_MINTEMP;
		}else if(temperature > ABS_MAXTEMP){
			temperature = ABS_MAXTEMP;
		}
		
		Logging.turnOnLogging();
		if(temperature <= ABS_MIDTEMP){
			//Between Blue and Yellow
			minTemp = ABS_MINTEMP;
			maxTemp = ABS_MIDTEMP;
			
			minBlue = 255;
			minGreen = 0;
			minRed = 0;
			
			maxBlue = 0;
			maxGreen = 255;
			maxRed = 255;
		}else{
			//Between Yellow and Red
			minTemp = ABS_MIDTEMP;
			maxTemp = ABS_MAXTEMP;
			
			minBlue = 0;
			minGreen = 255;
			minRed = 255;
			
			maxBlue = 0;
			maxGreen = 0;
			maxRed = 255;
		}
		
		float numerator = temperature-minTemp;
		float denominator = maxTemp-minTemp;
		float frac = numerator/denominator;
		
		r = ((frac*(maxRed-minRed) )+minRed)/255;
		g = ((frac*(maxGreen-minGreen))+minGreen)/255;
		b = ((frac*(maxBlue-minBlue))+minBlue)/255;
		
		return new Color(r, g, b);
  }
	
}
