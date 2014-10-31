import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by fhaynes on 10/2/14.
 */
public class EarthGridPanel extends JPanel {
    BufferedImage earthMap = null;
    int gridSpacing;
    int xCells, yCells;
    int pxPerXCell, pxPerYCell;
    int imgWidth, imgHeight, radius;

    public EarthGridPanel(Dimension size) {
        super();
        //JLabel label1 = new JLabel("Test");
        //this.add(label1);
        this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(1024,512));
        try {
            earthMap = ImageIO.read(getClass().getResourceAsStream("/images/MapOfEarth.jpg"));
        } catch (IOException e) {
            //System.out.println(e);
        }
        setGranularity(15);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("H!");
        g.drawImage(earthMap, 0, 0, null);
        drawMapGrid(g);

    }

    private void drawMapGrid(Graphics g) {
        //System.out.println("And we're drawing!");
        g.setColor(Color.red);
        for(int x = 0; x <= imgWidth; x+= pxPerXCell) {
            //System.out.println("Drawing from "+x+",0 to "+x+","+imgHeight);
            g.drawLine(x, 0, x, imgHeight);
        }

        for (int l = 0; l <= 90; l += this.gridSpacing) {
            int y = (int)Utility.getDistToEquator(l, radius);
            g.drawLine(0, radius-y, imgWidth, radius-y);
            g.drawLine(0, radius+y, imgWidth, radius+y);
        }

    }

    void setGranularity(int gridSpacing) {
        //System.out.println("Setting granularity!");
        this.gridSpacing = gridSpacing;
        xCells = 360 / this.gridSpacing;
        yCells = 180 / this.gridSpacing;
        //System.out.println("xCells is: "+xCells);
        //System.out.println("yCells is: "+yCells);
        pxPerXCell = earthMap.getWidth() / xCells;
        pxPerYCell = earthMap.getHeight() / yCells;
        //System.out.println("pxPerXCell is: "+pxPerXCell);
        imgWidth = xCells * pxPerXCell;
        imgHeight = yCells * pxPerYCell;
        radius = imgHeight / 2;
    }

    /*public void paint(Graphics g) {
        drawMapGrid(g);
    }*/

}
