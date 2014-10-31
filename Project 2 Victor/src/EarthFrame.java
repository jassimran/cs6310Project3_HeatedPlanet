import javax.swing.*;
import java.awt.*;

/**
 * Created by fhaynes on 10/4/14.
 */
public class EarthFrame extends JFrame {
    public EarthFrame() {
        super("Earth Frame");
        EarthGridPanel ev = new EarthGridPanel(new Dimension(800,600));
        this.setSize(1024, 768);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(1024, 768));
        this.getContentPane().add(ev);

        this.pack();
    }
}
