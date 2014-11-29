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
                float temp = (float)temperature;
                
                final float TEMP_BLUE_MIN = (float)276; // 0 degrees celsius
                final float TEMP_BLUE_MAX = (float)279; 	// 38F  min registered temperature 185.372 K
                //final float TEMP_GREEN_MIN = (float)284.5;
                final float TEMP_RED_MIN = (float)293;
                final float TEMP_RED_MAX = (float)299; 	// max registered temperature 330.928 K
                
                
                if(temp <= TEMP_BLUE_MIN) { 
                	// Pure Blue
                    b = 255;
                }
                else if(temp >= TEMP_RED_MAX) { 
                	// Pure Red
                    r = 255;
                }  
                else if (temp < TEMP_BLUE_MAX){ 
                	// Shade of BLUE
                	double proportion = (temp - TEMP_BLUE_MIN) / (TEMP_BLUE_MAX - TEMP_BLUE_MIN);
                	b = (int) (35 + (220 * proportion));
                }
                else if (temp > TEMP_RED_MIN){ 
                	//Shade of RED
                	double proportion = (temp - TEMP_RED_MIN) / (TEMP_RED_MAX - TEMP_RED_MIN);
                	r = (int) (35 + (220 * proportion));
                }
                else { 
                	// Shade of Green
                    double proportion = (temp - TEMP_BLUE_MAX) / (TEMP_RED_MIN - TEMP_BLUE_MAX);
                    g = (int) (35 + (220 * proportion));
                }
                if(r > 255)
                	r = 255;
                if(r < 0)
                	r = 0;
                if(g > 255)
                	g = 255;
            	if(g < 0)
            		g = 0;
            	if(b > 255)
            		b = 255;
            	if(b < 0)
            		b = 0;
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
