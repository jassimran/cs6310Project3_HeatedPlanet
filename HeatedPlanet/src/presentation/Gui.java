package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import presentation.earth.EarthPanel;
import simplesimulation.SimplePresentationEngineImpl;
import simplesimulation.SimpleSimulationEngineImpl;
import simulation.SimulationSettings;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import controllers.AbstractControl;
import controllers.AbstractControlFactory;
import edu.gatech.cs6310.project3.team17.GUI.QueryInterfaceUI;
import events.EventType;
import events.Listener;


public class Gui extends JFrame implements ActionListener, ChangeListener, Listener {

	static final int WIDTH = 800;
	static final int HEIGHT = 320;

	static final String ACTION_RUN = "Run";
	static final String ACTION_PAUSE = "Pause";
	static final String ACTION_RESUME = "Resume";
	static final String ACTION_STOP = "Stop";
	static final String ACTION_GRID_EDIT = "Grid_Spacing";
	static final String ACTION_NUM_EDIT = "Num_Edit";
	static final String ACTION_LAUNCH_QUERY = "Launch Query Interface";

	static final int DEFAULT_GRID_SPACING = 15;
	static final int DEFAULT_SIM_DELAY = 200;
	static final int DEFAULT_TEMP_ACCURACY = 100;
	static final int DEFAULT_GEO_ACCURACY = 100;
	static final int DEFAULT_PRECISION = 7;
	static final double DEFAULT_ECCENTRICITY = .0167;
	static final double DEFAULT_AXIAL_TILT = 23.44;
	static final int DEFAULT_TIME_STEP = 1;
	static final int DEFAULT_SIM_LENGTH = 12;

	static final String START_TIME = "12:00 PM, Jan 4, 2014";
	static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"hh:mm a, MMM dd, yyyy");
	private static final long serialVersionUID = -15968456987503L;

	private JButton runButton = new JButton(ACTION_RUN);
	private JButton pauseButton = new JButton(ACTION_PAUSE);
	private JButton stopButton = new JButton(ACTION_STOP);
	
	private JButton initQueryButton = new JButton(ACTION_LAUNCH_QUERY);

	private ControlledEventTextField gridEdit = null;
	private JSlider gridSlider = null;

	private EventTextField stepEdit = null;
	private JSlider stepSlider = null;

	private EventTextField displayEdit = null;
	private JSlider displaySlider = null;
	
	private JRadioButton concurrency_Sim = null;
	private JRadioButton concurrency_Pres = null;

	private JRadioButton initiative_R = null;
	private JRadioButton initiative_T = null;
	private JRadioButton initiative_Neither = null;
	
	private JSlider simLengthSlider = null;
	private JLabel simLengthLabel = null;
	private EventTextField simLengthEdit = null;
	private JLabel monthsLabel = null;

    private JSlider tempAccuracySlider = null;
    private JLabel tempAccuracyLabel = null;
	private EventTextField tempAccuracyEdit = null;
    
    private JSlider geoAccuracySlider = null;
    private JLabel geoAccuracyLabel = null;
    private EventTextField geoAccuracyEdit = null;
    
    private JLabel percentLabel = null;
    
    private JLabel simNameLabel = null;
	private JTextField simName = null;
	
    private JLabel eccentricityLabel =null;
    private JTextField eccentricity = null;
    
    private JLabel axisTiltLabel = null;
    private JTextField axisTilt = null;
    
    private JLabel precisionLabel =null;
    private JTextField precision = null;
    
	JSpinner spinner = new JSpinner();
	
	private JLabel simTime = null;
	private Calendar simTimeCal = DATE_FORMAT.getCalendar();

	private EarthPanel EarthPanel = new EarthPanel(new Dimension(800, 420), new Dimension(800, 420), new Dimension(800, 420));

	// control
	AbstractControl control;
	
	private boolean simthread;
	private boolean presthread;
	private boolean simcontrol;
	private boolean prescontrol;
	private int buffer;
	
	private Gui(boolean simthread, boolean presthread, boolean simcontrol, boolean prescontrol, int buffer) {
		super("EarthSim");
		this.simthread = simthread;
		this.presthread = presthread;
		this.simcontrol = simcontrol;
		this.prescontrol = prescontrol;
		this.buffer = buffer;
		setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		createGui();
		createControl();
		this.setVisible(true);
		
		// TODO remove
		simName.setText("abc");
	}

	private static Gui instance = null;
	private JLabel lblDegrees;
	private JLabel lblMins;
	private JLabel lblSecs;
	private JLabel lblpercent;
	private JLabel lblmonths;

	//Singleton
	public static Gui getInstance(boolean simthread, boolean presthread, boolean simcontrol, boolean prescontrol, int buffer) {
		if (instance == null) {
			instance = new Gui(simthread, presthread, simcontrol, prescontrol, buffer);
		}
		return instance;
	}

	public static void destroy() {
		if (instance != null) {
			instance = null;
		}
	}

	private void createGui() {
		this.getContentPane().setPreferredSize(
				new Dimension(800, 700));

		//set the time
		try {
			simTimeCal.setTime(DATE_FORMAT
					.parse(START_TIME));
		} catch (ParseException e) {
		}

		JPanel Panel;
		Panel = new JPanel();
		Panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1,
				Color.blue));
		Panel.setLayout(new java.awt.BorderLayout());
		Panel.add(createPanel(), BorderLayout.CENTER);
		
		concurrency_Sim.setSelected(simthread);
		concurrency_Sim.setEnabled(false);
		concurrency_Pres.setSelected(presthread);
		concurrency_Pres.setEnabled(false);
		
		initiative_T.setSelected(simcontrol);
		initiative_R.setSelected(prescontrol);
		initiative_T.setEnabled(false);
		initiative_R.setEnabled(false);
		initiative_Neither.setEnabled(false);
		
		spinner.setValue(buffer);
		spinner.setEnabled(false);
		
		this.getContentPane().add(Panel, BorderLayout.NORTH);

		JPanel simulationPanel;
		simulationPanel = new JPanel();
		simulationPanel.setBackground(Color.gray);
		simulationPanel.add(EarthPanel);
		this.getContentPane().add(simulationPanel, BorderLayout.CENTER);

		//draw the grid
		EarthPanel.drawGrid(DEFAULT_GRID_SPACING);

		this.pack();
	}
	
	public class EccentricityInputVerifier extends InputVerifier{
		@Override
	    public boolean verify(JComponent input) {
	        String text = ((JTextField) input).getText();
	        try {
	        	if (Double.parseDouble(text) >= 0 && Double.parseDouble(text) <= 1)
	        		return true;
	        	else {
	        		JOptionPane.showMessageDialog(null, "Eccentricity must be between 0 and 1");
	        		return false;
	        	}
	        } catch (NumberFormatException e) {
	        	JOptionPane.showMessageDialog(null, "Eccentricity must be numeric value between 0 and 1");
        		return false;
	        }
	    }
	}
	
	public class AxialTiltInputVerifier extends InputVerifier{
		@Override
	    public boolean verify(JComponent input) {
	        String text = ((JTextField) input).getText();
	        try {
	        	if (Double.parseDouble(text) >= -180 && Double.parseDouble(text) <= 180)
	        		return true;
	        	else {
	        		JOptionPane.showMessageDialog(null, "Tilt must be between -180 and +180");
	        		return false;
	        	}
	        } catch (NumberFormatException e) {
	        	JOptionPane.showMessageDialog(null, "Tilt should be numeric value between -180 and +180");
        		return false;
	        }
	    }
	}
	
	public class PrecisionInputVerifier extends InputVerifier{
		@Override
	    public boolean verify(JComponent input) {
	        String text = ((JTextField) input).getText();
	        try {
	        	if (Integer.parseInt(text) >= 0 && Integer.parseInt(text) <= 16)
	        		return true;
	        	else {
	        		JOptionPane.showMessageDialog(null, "Precision must be between 0 and 16");
	        		return false;
	        	}
	        } catch (NumberFormatException e) {
	        	JOptionPane.showMessageDialog(null, "Precision should be Integer value between 0 and 16");
        		return false;
	        }
	    }
	}
	
	public class SimulationNameInputVerifier extends InputVerifier {
		@Override
	    public boolean verify(JComponent input) {
	        String simulationName = ((JTextField) input).getText();
        	if (simulationName.length() <1) { // verify not empty
        		showMessage("Please enter simulation name!");
        		return false;
        	}
        	if(control.simulationExists(simulationName)) { // verify unique
        		showMessage("Simulation name already exists!");
        		return false;
        	}
        	return true;
	    }
	}
	
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 275));
		panel.setLayout(new BorderLayout());

		int WIDTH_LABELS = WIDTH * 4 / 7 * 1
				/ 4;
		int WIDTH_EDITS = WIDTH * 4 / 7 * 3
				/ 4;

		JLabel tmpLabel = null;
		JLabel tmpUnits = null;

		Border simBorder = BorderFactory.createTitledBorder("Configuration ");
		JPanel configOpts = new JPanel(new BorderLayout());
		JPanel optionLabels = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		JPanel optionEdits = new JPanel(new FlowLayout(FlowLayout.LEFT));
		optionLabels.setPreferredSize(new Dimension(WIDTH_LABELS, HEIGHT));
		optionEdits.setPreferredSize(new Dimension(WIDTH_EDITS,HEIGHT));
		configOpts.setBorder(simBorder);
		int EDIT_BOX_WIDTH = 4;
		int LABEL_HEIGHT = 26;

		//Label and Slider for Grid Spacing
		tmpLabel = new JLabel("Grid Spacing:");
		lblDegrees = new JLabel("degrees");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS, LABEL_HEIGHT));
		gridEdit = new ControlledEventTextField(EDIT_BOX_WIDTH,DEFAULT_GRID_SPACING);
		gridEdit.setActionCommand(ACTION_GRID_EDIT);
		gridEdit.setEditable(false);
		gridEdit.addActionListener(this);
		gridSlider = new JSlider(JSlider.HORIZONTAL, 1, 180,DEFAULT_GRID_SPACING);
		gridSlider.setMajorTickSpacing(30);
		gridSlider.setMinorTickSpacing(15);
		gridSlider.setPaintTicks(true);
		gridSlider.setPaintTrack(true);
		gridSlider.addChangeListener(gridEdit);  
		optionLabels.add(tmpLabel);
		optionEdits.add(gridSlider);
		optionEdits.add(gridEdit);
		optionEdits.add(lblDegrees);

		//Label and Slider for Time Step
		tmpLabel = new JLabel("Sim Time Step:");
		lblMins = new JLabel("minutes");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
		stepEdit = new EventTextField(EDIT_BOX_WIDTH, DEFAULT_TIME_STEP);
		stepEdit.setEditable(false);
		stepSlider = new JSlider(JSlider.HORIZONTAL, 1, 1440, DEFAULT_TIME_STEP);
		stepSlider.setMajorTickSpacing(100);
		stepSlider.setMinorTickSpacing(50);
		stepSlider.setPaintTicks(true);
		stepSlider.setPaintTrack(true);
		stepSlider.addChangeListener(stepEdit);
		optionLabels.add(tmpLabel);
		optionEdits.add(stepSlider);
		optionEdits.add(stepEdit);
		optionEdits.add(lblMins);

		//Label and Slider for Display rate
		tmpLabel = new JLabel("Pres Display Rate:");
		lblSecs = new JLabel("seconds");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
		displayEdit = new EventTextField(EDIT_BOX_WIDTH, 1); 
		displayEdit.setEditable(false);
		displaySlider = new JSlider(JSlider.HORIZONTAL, 1, 60, 1);
		displaySlider.setMajorTickSpacing(10);
		displaySlider.setMinorTickSpacing(5);
		displaySlider.setPaintTicks(true);
		displaySlider.setPaintTrack(true);
		displaySlider.addChangeListener(displayEdit);
		optionLabels.add(tmpLabel);
		optionEdits.add(displaySlider);
		optionEdits.add(displayEdit);
		optionEdits.add(lblSecs);

		//Label and Slider for Concurrrenacy
		//tmpLabel = new JLabel("Concurrency:");
		//tmpUnits = new JLabel("         ");
		//tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,	LABEL_HEIGHT));
		concurrency_Sim = new JRadioButton("[-s]");
		concurrency_Pres = new JRadioButton("[-p]");

		//optionLabels.add(tmpLabel);
		
		//JLabel label_8 = new JLabel("         ");
		//optionEdits.add(label_8);
	//	optionEdits.add(concurrency_Sim);
	//	optionEdits.add(concurrency_Pres);
	//	optionEdits.add(tmpUnits);

		//tmpLabel = new JLabel("Buffer:");
		//tmpUnits = new JLabel("");
		//tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,	LABEL_HEIGHT));
		initiative_T = new JRadioButton("[-t]");
		initiative_R = new JRadioButton("[-r]");
		initiative_Neither = new JRadioButton("Neither");
		initiative_Neither.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(initiative_T);
		group.add(initiative_R);
		group.add(initiative_Neither);
		
	//	JLabel label = new JLabel("Initiative:");
	//	label.setPreferredSize(new Dimension(114, 26));
	//	optionLabels.add(label);
	//	optionLabels.add(tmpLabel);
		
		//optionEdits.add(initiative_T);
		//optionEdits.add(initiative_R);
		//optionEdits.add(initiative_Neither);
		
		//Simulation Length Label and Slider
		simLengthSlider = new JSlider(JSlider.HORIZONTAL, 1, 1200, DEFAULT_SIM_LENGTH);
        simLengthLabel = new JLabel();
        simLengthLabel.setPreferredSize(new Dimension(WIDTH_LABELS,	LABEL_HEIGHT));
        simLengthLabel.setText("Simulation Length:");
        lblmonths = new JLabel("months");
        simLengthSlider.setMajorTickSpacing(200);
        simLengthSlider.setMaximum(1200);        
        simLengthSlider.setMinimum(1);
        simLengthSlider.setMinorTickSpacing(100);
        //simLengthSlider.setPaintLabels(true);
        simLengthSlider.setPaintTicks(true);
        simLengthEdit = new EventTextField(EDIT_BOX_WIDTH, DEFAULT_SIM_LENGTH); 
		simLengthEdit.setEditable(false);
		simLengthSlider.addChangeListener(simLengthEdit);
		optionLabels.add(simLengthLabel);
		optionEdits.add(simLengthSlider);
		optionEdits.add(simLengthEdit);
		optionEdits.add(lblmonths);
		
		lblpercent = new JLabel("percent");
		    
		//Added temporal Accuracy Label and Slider
		tempAccuracyLabel = new JLabel();
		tempAccuracyLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
		tempAccuracyLabel.setText("Temp Accuracy:");
		tempAccuracySlider = new JSlider(JSlider.HORIZONTAL, 1, 100, DEFAULT_TEMP_ACCURACY);
		tempAccuracySlider.setMajorTickSpacing(10);
		tempAccuracySlider.setMaximum(100);
		tempAccuracySlider.setMinimum(1);
		tempAccuracySlider.setMinorTickSpacing(5);
	    //axisTiltSlider.setPaintLabels(true);
		tempAccuracySlider.setPaintTicks(true);
		tempAccuracySlider.setPaintTrack(true);
		tempAccuracyEdit = new EventTextField(EDIT_BOX_WIDTH, DEFAULT_TEMP_ACCURACY); 
		tempAccuracyEdit.setEditable(false);
		tempAccuracySlider.addChangeListener(tempAccuracyEdit);
		optionLabels.add(tempAccuracyLabel);
		optionEdits.add(tempAccuracySlider);
		optionEdits.add(tempAccuracyEdit);
		optionEdits.add(lblpercent);
		    
		lblpercent = new JLabel("percent");
		
		//Added Geographic Accuracy Label and Slider
		geoAccuracyLabel = new JLabel();
		geoAccuracyLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
		geoAccuracyLabel.setText("Geo Accuracy: ");
		geoAccuracySlider = new JSlider(JSlider.HORIZONTAL, 1, 100, DEFAULT_GEO_ACCURACY);
		geoAccuracySlider.setMajorTickSpacing(10);
		geoAccuracySlider.setMaximum(100);
		geoAccuracySlider.setMinimum(1);
		geoAccuracySlider.setMinorTickSpacing(5);
		geoAccuracySlider.setPaintTicks(true);
		geoAccuracySlider.setPaintTrack(true);
		geoAccuracyEdit = new EventTextField(EDIT_BOX_WIDTH, DEFAULT_GEO_ACCURACY); 
		geoAccuracyEdit.setEditable(false);
		geoAccuracySlider.addChangeListener(geoAccuracyEdit);
		optionLabels.add(geoAccuracyLabel);
		optionEdits.add(geoAccuracySlider);
		optionEdits.add(geoAccuracyEdit);
		optionEdits.add(lblpercent);
		    

		//Added precision Label and Text box
	    precisionLabel = new JLabel();
	    precisionLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
	    precisionLabel.setText("Precision: ");
	    precision = new JTextField(String.valueOf(DEFAULT_PRECISION),8);
	    optionLabels.add(precisionLabel);
	    optionEdits.add(precision);	 
	    precision.setInputVerifier(new PrecisionInputVerifier());
	    
	    
	    //Added simulation Label and Text box
		simNameLabel = new JLabel();
		simNameLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
		simNameLabel.setText("Save simulation as:");
		simName = new JTextField(28);	
		optionLabels.add(simNameLabel);
		//optionEdits.add(new JLabel(" "));
		optionEdits.add(simName);
		simName.setInputVerifier(new SimulationNameInputVerifier());
		
		configOpts.add(optionLabels, BorderLayout.WEST);
		configOpts.add(optionEdits, BorderLayout.EAST);
		
		panel.add(configOpts, BorderLayout.WEST);

		// Run Panel
		JPanel runAndTimePanel = new JPanel(new BorderLayout());
		Border runBorder = BorderFactory.createTitledBorder("Run ");
		JPanel runOpts = new JPanel();

		runButton.setActionCommand(ACTION_RUN);
		runButton.addActionListener(this);
		runButton.setEnabled(true); 
		runOpts.add(runButton);

		pauseButton.setActionCommand(ACTION_PAUSE);
		pauseButton.addActionListener(this);
		pauseButton.setEnabled(false);
		runOpts.add(pauseButton);

		stopButton.setActionCommand(ACTION_STOP);
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		runOpts.add(stopButton);

		//runOpts.setPreferredSize(new Dimension(290, 30));
		runOpts.setBorder(runBorder);
				
		// Time Panel
		JPanel simTimePanel = new JPanel();
		simTimePanel.setBorder(BorderFactory.createTitledBorder("Simulation Time"));
		simTime = new JLabel(START_TIME);
		simTime.setPreferredSize(new Dimension(310, 30));
		simTime.setFont(new Font("Arial Black",Font.BOLD, 12));
		simTimePanel.add(simTime);
	
		runOpts.add(simTimePanel, BorderLayout.SOUTH);
		runAndTimePanel.add(runOpts, BorderLayout.CENTER);
				
		
		//Query Panel
		JPanel initQueryPanel = new JPanel(new BorderLayout());
		Border initQueryBorder = BorderFactory.createTitledBorder("Launch ");		
		JPanel queryOpts = new JPanel();

		initQueryButton.setActionCommand(ACTION_LAUNCH_QUERY);
		initQueryButton.addActionListener(this);
		initQueryButton.setEnabled(true); 
		queryOpts.add(initQueryButton); 
		
		//queryOpts.setPreferredSize(new Dimension(30,30));
		queryOpts.setBorder(initQueryBorder);
		initQueryPanel.add(queryOpts, BorderLayout.CENTER);
		runAndTimePanel.add(initQueryPanel, BorderLayout.SOUTH);
		
		
		
		Border PFBorder = BorderFactory.createTitledBorder("Physical factors ");
		JPanel PFPanel = new JPanel(new BorderLayout());
		JPanel option2Labels = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		JPanel option2Edits = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		option2Labels.setPreferredSize(new Dimension( WIDTH_LABELS, HEIGHT));
		option2Edits.setPreferredSize(new Dimension(WIDTH_EDITS,HEIGHT));
		PFPanel.setPreferredSize(new Dimension(290, 97));	
		
		PFPanel.setBorder(simBorder);
	    
		//Added eccentricity Label and Text box
	    eccentricityLabel =new JLabel();
	    eccentricityLabel.setPreferredSize(new Dimension(WIDTH_LABELS,LABEL_HEIGHT));
	    eccentricityLabel.setText("Orbital eccentricity:");
	    eccentricity = new JTextField(String.valueOf(DEFAULT_ECCENTRICITY),6);
	    option2Labels.add(eccentricityLabel);
	    
	    option2Edits.add(eccentricity);
	    lblDegrees = new JLabel("   ");
	    option2Edits.add(lblDegrees);
	    
	    eccentricity.setInputVerifier(new EccentricityInputVerifier());
	 
	    
	   //Added axial tilt Textbox and Label
        axisTiltLabel = new JLabel();
        axisTiltLabel.setPreferredSize(new Dimension(WIDTH_LABELS, LABEL_HEIGHT));
        axisTiltLabel.setText("Axial Tilt:");
        axisTilt = new JTextField(String.valueOf(DEFAULT_AXIAL_TILT),6);
      
	    option2Labels.add(axisTiltLabel);
	    option2Edits.add(axisTilt);
	    axisTilt.setInputVerifier(new AxialTiltInputVerifier());
	    //lblDegrees = new JLabel("degrees");
	    //option2Edits.add(lblDegrees);
        //axisTiltSlider = new JSlider();
        //axisTiltSlider = new JSlider(JSlider.HORIZONTAL, -180.0, 180.0, DEFAULT_AXIAL_TILT);        
        //axisTiltSlider.setMajorTickSpacing(60);
        //axisTiltSlider.setMaximum(180);
        //axisTiltSlider.setMinimum(-180);
        //axisTiltSlider.setMinorTickSpacing(30);
        //axisTiltSlider.setPaintLabels(true);
        //axisTiltSlider.setPaintTicks(true);
        //axisTiltSlider.setPaintTrack(true);
        //axisTiltEdit = new EventTextField(EDIT_BOX_WIDTH, 1); 
        //axisTiltEdit.setEditable(false);
        //axisTiltSlider.addChangeListener(axisTiltEdit);
        //option2Labels.add(axisTiltLabel);
        //option2Edits.add(axisTiltSlider);
        //option2Edits.add(axisTiltEdit);
        
        
        PFPanel.add(option2Labels,BorderLayout.WEST);
		PFPanel.add(option2Edits, BorderLayout.EAST);
		PFPanel.setBorder(PFBorder);
		
		
		
		runAndTimePanel.add(PFPanel, BorderLayout.NORTH );
		panel.add(runAndTimePanel, BorderLayout.CENTER);
		runAndTimePanel.add(initQueryPanel, BorderLayout.SOUTH);

		return panel;
	}

	public void stateChanged(ChangeEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		    
		//get the command
		String command = e.getActionCommand();

		if (command.equals(ACTION_RUN)) {
			// check simulation name is valid
			InputVerifier simulaitonVerifier = new SimulationNameInputVerifier();
        	if (!simulaitonVerifier.verify(simName)) {
        		return;
        	}
    		//disable all controls
			this.setEnableAllUserOptions(false);
			//disable the run button
			runButton.setEnabled(false);
			//enable the stop button
			pauseButton.setEnabled(true);
			//disable the restart button
			stopButton.setEnabled(false);
			// run simulation
			runSimulation();
		} else if (command.equals(ACTION_PAUSE)) {
			//enable the restart button
			stopButton.setEnabled(true);
			// change pause button legend
			pauseButton.setText((control.isSimulationRunning())? ACTION_RESUME : ACTION_PAUSE);
			// pause simulation
			pauseSimulation();
		} else if (command.equals(ACTION_STOP)) {
			//enable all controls
			this.setEnableAllUserOptions(true);
			// disable pause button
			pauseButton.setEnabled(false);
			//disable the stop button
			stopButton.setEnabled(false);
			//enable the run button
			runButton.setEnabled(true);
			// terminate simulation
			stopSimulation();
			//reset the EarthPanel
			EarthPanel.reset();
			try {
				//set the time
				simTimeCal.setTime(DATE_FORMAT
						.parse(START_TIME));
				simTime.setText(DATE_FORMAT.format(simTimeCal
						.getTime()));
			} catch (ParseException e2) {
			}				
		} else if (command.equals(ACTION_GRID_EDIT)) {
			//update the visual grid with new value
			EarthPanel.drawGrid(gridEdit.getValue());
		} else if (command.equals(ACTION_LAUNCH_QUERY)){
			//For launching query interface
			//But before that, disable all controls
			this.setEnableAllUserOptions(false);
			//disable the run button
			runButton.setEnabled(false);
			//disable the stop button
			pauseButton.setEnabled(false);
			//disable the restart button
			stopButton.setEnabled(false);
			QueryInterfaceUI gui = new QueryInterfaceUI();
			gui.launchQueryInterface();
			
		}
	}

	/**
	 * Executes a simulation based on the selected settings.
	 */
	private void runSimulation() {
		// create settings object
		SimulationSettings simulationSettings = new SimulationSettings();
		simulationSettings.setSOption(concurrency_Sim.isSelected());
		simulationSettings.setPOption(concurrency_Pres.isSelected());
		simulationSettings.setROption(initiative_R.isSelected());
		simulationSettings.setTOption(initiative_T.isSelected());
		simulationSettings.setNumCellsX(EarthPanel.getNumCellsX());
		simulationSettings.setNumCellsY(EarthPanel.getNumCellsY());
		simulationSettings.setDegreeSeparation(EarthPanel.getDegreeSeparation());
		simulationSettings.setBufferSize((Integer) spinner.getValue());
		
		//TODO: fetch value from interface
		simulationSettings.setTemporalAccuracy(25);
		
		//TODO: fetch value from interface
		simulationSettings.setGeoAccuracy(25);
		
		// set presentation display rate
		simulationSettings.setPresentationDisplayRate(displayEdit.getValue() * 1000); // milliseconds
		simulationSettings.setSimulationTimeStep(stepEdit.getValue());
		simulationSettings.setGridSpacing(gridEdit.getValue());
		
		// set temperature precision
		simulationSettings.setPrecision(Integer.parseInt(precision.getText()));		
		simulationSettings.setAxialTilt(Double.parseDouble(axisTilt.getText()));
		simulationSettings.setEccentricity(Double.parseDouble(eccentricity.getText().toString()));
		simulationSettings.setName(simName.getText());
		simulationSettings.setSimulationLength(simLengthEdit.getValue()); // default 12
		simulationSettings.setTemporalAccuracy(tempAccuracyEdit.getValue());
		simulationSettings.setGeoAccuracy(geoAccuracyEdit.getValue());
		
		// wire simulation engines
		AbstractControl.setSimulationEngine(new SimpleSimulationEngineImpl(simulationSettings));
		AbstractControl.setPresentationEngine(new SimplePresentationEngineImpl(EarthPanel));
		
		// run simulation
		control.runSimulation(simulationSettings);
	}

	private void createControl() {
		// create control based on r|t
		if(prescontrol) {
			control = AbstractControlFactory.getInstance().createControl(AbstractControlFactory.BA);
		} else if (simcontrol) {
			control = AbstractControlFactory.getInstance().createControl(AbstractControlFactory.AB);
		} else { 
			control = AbstractControlFactory.getInstance().createControl(AbstractControlFactory.MAB);
		}		
		// register UI as listener for control events
		control.addListener(this);
	}
	
	/**
	 * Pauses a running simulation.
	 */
	private void pauseSimulation() {
		if(control.isSimulationRunning()) {
			control.pauseSimulation();
		} else {
			control.resumeSimulation();
		}
	}
	
	/**
	 * Stops a running simulation.
	 */
	private void stopSimulation() {
		if(!control.isTerminateSimulation()) {
			control.stopSimulation();
		}
	}

	//enable/disable all controls
	private void setEnableAllUserOptions(boolean bEnable) {
		gridEdit.setEnabled(bEnable);
		gridSlider.setEnabled(bEnable);
		stepEdit.setEnabled(bEnable);
		stepSlider.setEnabled(bEnable);
		displayEdit.setEnabled(bEnable);
		displaySlider.setEnabled(bEnable);
		concurrency_Sim.setEnabled(bEnable);
		concurrency_Pres.setEnabled(bEnable);
		initiative_T.setEnabled(bEnable);
		initiative_R.setEnabled(bEnable);
		initiative_Neither.setEnabled(bEnable);		
		spinner.setEnabled(bEnable);
		simLengthEdit.setEnabled(bEnable);
		tempAccuracyEdit.setEnabled(bEnable);
		tempAccuracySlider.setEnabled(bEnable);
		geoAccuracyEdit.setEnabled(bEnable);
		geoAccuracySlider.setEnabled(bEnable);
		simLengthEdit.setEnabled(bEnable);
		simLengthSlider.setEnabled(bEnable);
		precision.setEnabled(bEnable);
		simName.setEnabled(bEnable);
		axisTilt.setEnabled(bEnable); 
		eccentricity.setEnabled(bEnable);		
	}

	public void updateClock() {
		try {
			//set the time
			simTimeCal.add(Calendar.MINUTE, stepEdit.getValue());
			simTime.setText(DATE_FORMAT.format(simTimeCal
					.getTime()));
		} catch (Exception e2) {
		}			
	}
	



	@Override
	public void notify(EventType e) {
		if(e == EventType.SimulationFinishedEvent) {
			// notify user
			JOptionPane.showMessageDialog(this, "Simulation complete!");
			
			// TODO handle GUI post simulation logic (e.g. reset for new simulation)
		}
	}

	@Override
	public void addListener(Listener l) {
		throw new NotImplementedException();
	}

	@Override
	public void removeListener(Listener l) {
		throw new NotImplementedException();
	}

	/**
	 * Shows the given message in dialog.
	 * @param message the message to show
	 */
	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

}
/*Event Text Field class
 * to keep sliders and text field in synch
 */
class EventTextField extends JTextField implements ChangeListener {

	private static final long serialVersionUID = -5784976484527241312L;

	public EventTextField(int cols, int initVal) {
		super(cols);
		this.setText(Integer.valueOf(initVal).toString());
	}

	public int getValue() {
		return Integer.valueOf(this.getText()).intValue();
	}

	public void stateChanged(ChangeEvent e) {
		Object eventSource = e.getSource();
		if (eventSource.getClass().getName().equals("javax.swing.JSlider")) {
			JSlider slider = (JSlider) eventSource;
			this.setText(Integer.valueOf(slider.getValue()).toString());
		}
		//fire the event
		this.fireActionPerformed();
	}
}
/*Controlled Event Text Field to 
 * restrict the slider value of grid spacing to 
 * values that evenly divides 180 degrees
 */
class ControlledEventTextField extends EventTextField 
{
	private static final long serialVersionUID = 1346347586797802332L;

	public ControlledEventTextField(int cols, int initVal) {
		super(cols, initVal);
	}
	public void stateChanged(ChangeEvent e) {
		Object eventSource = e.getSource();
		if (eventSource.getClass().getName().equals("javax.swing.JSlider")) {
			JSlider slider = (JSlider) eventSource;
			try{
			//get the value of the slider
			float val = (Float.valueOf(slider.getValue())).floatValue();

			//need to restrict to allowed numbers
			int[] allowedNumbers = { 1, 2, 3, 4, 5, 6, 9, 10, 12, 15, 18, 20,
					30, 36, 45, 60, 90, 180 };
			int finalNum = 0;
			//get the largest integer less than or equal to gs that evenly divides 180 degrees
			for (int i = 0; i < allowedNumbers.length; i++) {
				if (val >= allowedNumbers[i]) {
					finalNum = allowedNumbers[i];
				}
			}

			//if the number is between 0 & 180
			if (val > 0 && val <= 180) {
				//set the value of text
				this.setText(new String(Integer.valueOf(finalNum).toString()));
			}
			}
			//catch number format exception
			catch (NumberFormatException z){}
			
		}
		//fire the event
		this.fireActionPerformed();
	}

}

