package EarthSim;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid;
import edu.gatech.cs6310.project2.team13.gui.widget.earth.EarthPanel;
import edu.gatech.cs6310.project2.team13.initiative.PullingConsumerController;
import edu.gatech.cs6310.project2.team13.initiative.PullingGenerator;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Controller;
import edu.gatech.cs6310.project2.team13.initiative.abstracts.Worker;
import edu.gatech.cs6310.project2.team13.initiative.interfaces.Consumer;
import edu.gatech.cs6310.project2.team13.utils.Logging;
import edu.gatech.cs6310.project2.team13.utils.Shared;

public class PullingVisualizationPanel extends Controller implements Runnable,Consumer {

	private EarthPanel ep;
	private Thread t;
	private boolean stopSignalled = false;
	private int gridSpacing;
	private JFrame frame;

	private void createAndShowGui() {
		frame = new JFrame("Visualization Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().add(ep);
		frame.pack();      
		frame.setLocationRelativeTo(null);     
		frame.setVisible(Shared.getShowVizPanels()); 
	}
	
	public PullingVisualizationPanel(PullingGenerator g, PullingConsumerController c, DataBuffer db, int gridSpacing, int refreshRate, int timeInterval, boolean dT, EarthGrid eg) {
		super(g,c,db,dT,eg);
		Logging.writeOut("Enter");
		this.gridSpacing = gridSpacing;
		this.setConsumerToSelf();
		Dimension d = new Dimension(792,486);
		ep = new EarthPanel(gridSpacing,refreshRate,d,d,d);
		createAndShowGui();		
		Logging.writeOut("Exit");
	}
	
	private void setConsumerToSelf() {
		Logging.writeOut("Enter");
		super.setConsumer(this);
		Logging.writeOut("Exit");
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

	}

	@Override
	public void goGetData(Worker w) {
		Logging.writeOut("Enter-Mediating");
		Logging.writeOut("Getting Data from Generator");
		PullingGenerator g = (PullingGenerator) w;
		if(ep!=null) {
			ep.updateGrid(g.getData());
			//ep.moveSunPosition(gridSpacing);
		}
		Logging.writeOut("Exit");
	}

	@Override
	public void stop() {
		Logging.writeOut("Setting STOP Signal...");
		stopSignalled = true;
		Logging.writeOut("Exit");
	}

	@Override
	protected void startNext() {
		Logging.writeOut("Enter");
		this.setDataIsReady(false);
		((PullingGenerator)this.getGenerator()).execute(this);
		if(this.getDataIsReady()) {
			((PullingVisualizationPanel)this.getConsumer()).goGetData(this.getGenerator());
		} else {
			Logging.writeErr("Data was not ready");
		}
		Logging.writeOut("Exit");
	}

	@Override
	public void dataReady() {
		Logging.writeOut("Enter");
		this.setDataIsReady(true);
		Logging.writeOut("Exit");		
	}
	
	private int i=0;
	private Timer timer;

	public void startInSingleThread() {
		stopSignalled=false;
		Logging.writeOut("Enter");
		i=1;
        //Create a timer.
        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (stopSignalled) {
                    timer.stop();
                } else if(stopForTesting(i)) {
                	Logging.writeOut(false,"Maximum Iteration reached. Stopping simulation.");
    				timer.stop();
                } else {
                	Logging.writeOut(false,"Running iteration "+i);
                	i++;
                	startNext();
                }
            }
        });
        timer.setInitialDelay(1);
        timer.start();
		Logging.writeOut("Exit");
	}
	
	@Override
	public void start() {
		stopSignalled=false;
		Logging.writeOut("Enter");
		i=1;
		while(!stopSignalled) {
			if(stopForTesting(i)){
				Logging.writeOut(false,"Maximum Iteration reached. Stopping simulation.");
				this.stop();
				break;
			}
			Logging.writeOut(false,"Running iteration " + i);
			i++;
			this.startNext();		
		}
		Logging.writeOut("Exit");
		
	}
	
}
