package edu.gatech.cs6310.project2.team13.gui.widget.earth;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class KeyImage {
	private URL keyURL = this.getClass().getResource("ColorKeyGradient.png");
	
	KeyImage() {
		keyURL = this.getClass().getResource("ColorKeyGradient.png");
	}
	
	BufferedImage getBufferedImage() {
		BufferedImage keyImg = null;
		try {
			keyImg = ImageIO.read(keyURL);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return keyImg;
	}
}
