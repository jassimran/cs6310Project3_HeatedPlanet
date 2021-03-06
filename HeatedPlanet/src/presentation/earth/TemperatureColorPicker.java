package presentation.earth;

import java.awt.Color;

/**
 * Use this class to get a color representation of a temperature. 
 * This is implemented as a singleton.
 * 
 * @author Andrew Bernard
 */
public class TemperatureColorPicker {
        private static TemperatureColorPicker instance = null;
        
        private TemperatureColorPicker() {      }
        
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
         * @param temperature in celsius
         * @return the temperature color
         */
        Color getColor(int temperature) {
        	int b = 0;
        	int g = 0;
        	int r = 0;
        	
        	float temp = (float) temperature;

        	final float HIGH_MAX = 342.0f; // max registered temperature 330.928 K
        	final float HIGH_MIN = 312.0f;

        	final float LOW_MAX = 260.0f;
        	final float LOW_MIN = 200.0f; // min registered temperature 185.372 K

        	if (temp > HIGH_MAX) {
        		temp = HIGH_MAX;
        	}

        	if (temp < LOW_MIN) {
        		temp = LOW_MIN;
        	}

        	if (temp <= LOW_MAX) {
        		b = Math.round((LOW_MAX - temp) / (LOW_MAX - LOW_MIN) * 255);
        	} else if (temp >= HIGH_MIN) {
        		r = Math.round((temp - HIGH_MIN) / (HIGH_MAX - HIGH_MIN) * 255);
        	} else {
        		g = Math.round((temp - LOW_MAX) / (HIGH_MIN - LOW_MAX) * 255);
        	}

        	return new Color(r, g, b);
        }

        
        Color getColorOld(int temperature) {
    int b = 0;
                int g = 0;
                int r = 0;

                if (temperature <= -100) {
                        b = 170;
                        g = 100;
                        r = 170;
                }
                else if (temperature <= -46) {
                        temperature = -1 * temperature;
                        b = 255;
                        g = 145 - (temperature * 10) % 115;
                        r = 255;
                }
                else if (temperature <= -23 && temperature > -46) {
                        temperature = -1 * temperature;
                        b = 255;
                        g = 145;
                        r = 145 + (temperature * 5) % 115;
                }
                else if (temperature < 0 && temperature > -23) {
                        temperature = -1 * temperature;
                        b = 255;
                        g = 145;
                        r = 145 - (temperature * 5);
                }
                else if (temperature == 0) {
                        b = 225;
                        g = 145;
                        r = 145;
                }
                else if (temperature > 0 && temperature < 23) {
                        b = 255;
                        g = 145 + (temperature * 5);
                        r = 145;
                }
                else if (temperature >= 23 && temperature < 46) {
                        b = 255 - (temperature * 5) % 115;
                        g = 255;
                        r = 145;
                }
                else if (temperature >= 46 && temperature < 69) {
                        b = 145;
                        g = 255;
                        r = 145 + (temperature * 5) % 115;
                }
                else if (temperature >= 69 && temperature < 92) {
                        b = 145;
                        g = 255 - (temperature * 5) % 115;
                        r = 255;
                }
                else {
                        b = 145 - (temperature * 10) % 115;
                        g = 145 - (temperature * 10) % 115;
                        r = 255;
                }
                
                return new Color(r, g, b);
  }
        
}
