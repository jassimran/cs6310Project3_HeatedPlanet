package EarthSim;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.gui.widget.earth.EarthPanel;
import edu.gatech.cs6310.project2.team13.initiative.PushingGeneratorController;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Consumer;
import edu.gatech.cs6310.project2.team13.utils.Logging;
import edu.gatech.cs6310.project2.team13.utils.Shared;

public class PushingVisualizationPanel extends Worker implements Runnable, Consumer {

	private EarthPanel ep;
	private Thread t;
	private int gridSpacing;
	private JFrame frame;
	

	private void createAndShowGui() {
		this.frame = new JFrame("Visualization Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().add(ep);
		frame.pack();      
		frame.setLocationRelativeTo(null);     
		frame.setVisible(Shared.getShowVizPanels());   
	}
	
	public PushingVisualizationPanel(int gridSpacing, int refreshRate, int timeInterval, DataBuffer db) {
		super(db);
		Dimension d = new Dimension(792,486);
		this.gridSpacing = gridSpacing;
		ep = new EarthPanel(gridSpacing,refreshRate,d,d,d);
		createAndShowGui();		
	}
	
	public void start(boolean separateThread) {
		if(separateThread) {
			if(t==null) {
				t = new Thread(this);
				t.start();
			}						
		} else {
			run();
		}
	}
	   
    public JFrame getFrame() {
        return this.frame;
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
			
	}

	@Override
	public void goGetData(Worker w) {
		Logging.writeOut("Enter-Pushing");				
		Logging.writeOut("Getting Data from Generator");
		PushingGeneratorController pgc = (PushingGeneratorController) w;
		if(ep!=null) {
			ep.updateGrid(pgc.getData());
			//ep.moveSunPosition(gridSpacing);
		}
		Logging.writeOut("Exit");
	}
	
	
	
	
}
