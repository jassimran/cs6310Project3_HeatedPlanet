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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import presentation.earth.EarthPanel;
import simplesimulation.SimplePresentationEngineImpl;
import simplesimulation.SimpleSimulationEngineImpl;
import simulation.SimulationEngine;
import simulation.SimulationSettings;
import controllers.AbstractControl;
import controllers.MasterControl;
import controllers.PresentationControl;
import controllers.SimulationControl;

public class Gui extends JFrame implements ActionListener, ChangeListener {

	static final int WIDTH = 800;
	static final int HEIGHT = 220;

	static final String ACTION_RUN = "Run";
	static final String ACTION_PAUSE = "Pause";
	static final String ACTION_RESUME = "Resume";
	static final String ACTION_STOP = "Stop";
	static final String ACTION_GRID_EDIT = "Grid_Spacing";
	static final String ACTION_NUM_EDIT = "Num_Edit";

	static final int DEFAULT_GRID_SPACING = 15;
	static final int DEFAULT_SIM_DELAY = 200;

	static final String START_TIME = "12:00 PM, Dec 31, 1999";
	static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"hh:mm a, MMM dd, yyyy");
	private static final long serialVersionUID = -15968456987503L;

	private JButton runButton = new JButton(ACTION_RUN);
	private JButton pauseButton = new JButton(ACTION_PAUSE);
	private JButton stopButton = new JButton(ACTION_STOP);

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

	JSpinner spinner = new JSpinner();
	
	private long millisToPuse = 0;
	
	private JLabel simTime = null;
	private Calendar simTimeCal = DATE_FORMAT.getCalendar();

	private EarthPanel EarthPanel = new EarthPanel(new Dimension(
			800, 400), new Dimension(
					800, 400), new Dimension(
							800, 400));

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
		this.setVisible(true);
	}

	private static Gui instance = null;
	private JLabel lblDegrees;
	private JLabel lblMins;
	private JLabel lblSecs;

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
				new Dimension(800, 620));

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

	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 220));
		panel.setLayout(new BorderLayout());

		int WIDTH_LABELS = WIDTH * 4 / 7 * 1
				/ 4;
		int WIDTH_EDITS = WIDTH * 4 / 7 * 3
				/ 4;

		JLabel tmpLabel = null;
		JLabel tmpUnits = null;

		Border simBorder = BorderFactory
				.createTitledBorder("Configuration ");
		JPanel configOpts = new JPanel(new BorderLayout());
		JPanel optionLabels = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		JPanel optionEdits = new JPanel(new FlowLayout(FlowLayout.LEFT));
		optionLabels.setPreferredSize(new Dimension(
				WIDTH_LABELS, HEIGHT));
		optionEdits.setPreferredSize(new Dimension(WIDTH_EDITS,
				HEIGHT));
		configOpts.setBorder(simBorder);
		int EDIT_BOX_WIDTH = 4;
		int LABEL_HEIGHT = 26;

		tmpLabel = new JLabel("Grid Spacing:");
		lblDegrees = new JLabel("degrees");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,
				LABEL_HEIGHT));
		gridEdit = new ControlledEventTextField(EDIT_BOX_WIDTH,
				DEFAULT_GRID_SPACING);
		gridEdit.setActionCommand(ACTION_GRID_EDIT);
		gridEdit.setEditable(false);
		gridEdit.addActionListener(this);
		gridSlider = new JSlider(JSlider.HORIZONTAL, 1, 180,
				DEFAULT_GRID_SPACING);
		gridSlider.setMajorTickSpacing(30);
		gridSlider.setMinorTickSpacing(15);
		gridSlider.setPaintTicks(true);
		gridSlider.setPaintTrack(true);
		gridSlider.addChangeListener(gridEdit);
		optionLabels.add(tmpLabel);
		optionEdits.add(gridSlider);
		optionEdits.add(gridEdit);
		optionEdits.add(lblDegrees);

		tmpLabel = new JLabel("Sim Time Step:");
		lblMins = new JLabel("minutes");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,
				LABEL_HEIGHT));
		stepEdit = new EventTextField(EDIT_BOX_WIDTH, 10);
		stepEdit.setText("1");
		stepEdit.setEditable(false);
		stepSlider = new JSlider(JSlider.HORIZONTAL, 1, 1440, 10);
		stepSlider.setMajorTickSpacing(100);
		stepSlider.setMinorTickSpacing(50);
		stepSlider.setPaintTicks(true);
		stepSlider.setPaintTrack(true);
		stepSlider.addChangeListener(stepEdit);
		optionLabels.add(tmpLabel);
		optionEdits.add(stepSlider);
		optionEdits.add(stepEdit);
		optionEdits.add(lblMins);

		tmpLabel = new JLabel("Pres Display Rate:");
		lblSecs = new JLabel("seconds");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,
				LABEL_HEIGHT));
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

		tmpLabel = new JLabel("Concurrency:");
		tmpUnits = new JLabel("         ");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,
				LABEL_HEIGHT));
		concurrency_Sim = new JRadioButton("[-s]");
		concurrency_Pres = new JRadioButton("[-p]");

		optionLabels.add(tmpLabel);
		
		JLabel label_8 = new JLabel("         ");
		optionEdits.add(label_8);
		optionEdits.add(concurrency_Sim);
		optionEdits.add(concurrency_Pres);
		optionEdits.add(tmpUnits);

		tmpLabel = new JLabel("Buffer:");
		tmpUnits = new JLabel("");
		tmpLabel.setPreferredSize(new Dimension(WIDTH_LABELS,
				LABEL_HEIGHT));
		initiative_T = new JRadioButton("[-t]");
		initiative_R = new JRadioButton("[-r]");
		initiative_Neither = new JRadioButton("Neither");
		initiative_Neither.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(initiative_T);
		group.add(initiative_R);
		group.add(initiative_Neither);
		
		JLabel label = new JLabel("Initiative:");
		label.setPreferredSize(new Dimension(114, 26));
		optionLabels.add(label);
		optionLabels.add(tmpLabel);
		
		JLabel label_9 = new JLabel("         ");
		optionEdits.add(label_9);
		
		JLabel label_12 = new JLabel("         ");
		optionEdits.add(label_12);
		
		JLabel label_11 = new JLabel("         ");
		optionEdits.add(label_11);
		
		JLabel label_6 = new JLabel("         ");
		optionEdits.add(label_6);
		
		JLabel label_7 = new JLabel("         ");
		optionEdits.add(label_7);
		
		JLabel label_10 = new JLabel("         ");
		optionEdits.add(label_10);
		optionEdits.add(initiative_T);
		optionEdits.add(initiative_R);
		optionEdits.add(initiative_Neither);

		configOpts.add(optionLabels, BorderLayout.WEST);
		configOpts.add(optionEdits, BorderLayout.EAST);
		
		JLabel label_1 = new JLabel("         ");
		optionEdits.add(label_1);
		
		JLabel label_5 = new JLabel("         ");
		optionEdits.add(label_5);
		
		JLabel label_4 = new JLabel("         ");
		optionEdits.add(label_4);
		
		JLabel label_2 = new JLabel("         ");
		optionEdits.add(label_2);
		
		JLabel label_3 = new JLabel("         ");
		optionEdits.add(label_3);
		
		spinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		optionEdits.add(spinner);
		panel.add(configOpts, BorderLayout.WEST);

		JPanel runAndTimePanel = new JPanel(new BorderLayout());
		Border runBorder = BorderFactory.createTitledBorder("Run ");
		JPanel runOpts = new JPanel();
		runOpts.setBorder(runBorder);

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

		runAndTimePanel.add(runOpts, BorderLayout.CENTER);

		JPanel simTimePanel = new JPanel();
		simTimePanel.setBorder(BorderFactory
				.createTitledBorder("Simulation Time"));
		simTime = new JLabel(START_TIME);
		simTime.setFont(new Font("Arial Black", Font.BOLD, 12));
		simTimePanel.add(simTime);
		runAndTimePanel.add(simTimePanel, BorderLayout.SOUTH);

		panel.add(runAndTimePanel, BorderLayout.CENTER);

		return panel;
	}

	public void stateChanged(ChangeEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//get the command
		String command = e.getActionCommand();

		if (command.equals(ACTION_RUN)) {
			//disable all controls
			this.setEnableAllUserOptions(false);
			//disable the run button
			runButton.setEnabled(false);
			//enable the stop button
			pauseButton.setEnabled(true);
			//disable the restart button
			stopButton.setEnabled(false);

			//TODO::RUN SIMULATION HERE
			runSimulation();
		} else if (command.equals(ACTION_PAUSE)) {
			//enable the restart button
			stopButton.setEnabled(true);
			// change pause button legend
			pauseButton.setText((control.isSimulationRunning())? ACTION_RESUME : ACTION_PAUSE);

			//TODO::PAUSE SIMULATION HERE
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
			//TODO::STOP SIMULATION HERE
			if(control.isSimulationRunning()) {
				control.stopSimulation();
			}	
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
		}
	}

	/**
	 * Executes a simulation based on the selected settings.
	 */
	private void runSimulation() {
		// create settings object
		SimulationSettings simulationSettings = new SimulationSettings();
		//Set simulation Settings according to Gui Selections
		simulationSettings.setSOption(concurrency_Sim.isSelected());
		simulationSettings.setPOption(concurrency_Pres.isSelected());
		simulationSettings.setROption(initiative_R.isSelected());
		simulationSettings.setTOption(initiative_T.isSelected());

		
		simulationSettings.numCellsX = EarthPanel.getNumCellsX();
		simulationSettings.numCellsY = EarthPanel.getNumCellsY();
		simulationSettings.setBufferSize((Integer) spinner.getValue());
		
		// set presentation display rate
		simulationSettings.setPresentationDisplayRate(displayEdit.getValue() * 1000); // milliseconds
		simulationSettings.setSimulationTimeStep(stepEdit.getValue());
		simulationSettings.SetGridSpacing(gridEdit.getValue());
		
		// create simulation engines
		SimulationEngine simulationEngine = new SimpleSimulationEngineImpl(EarthPanel);
		PresentationEngine presentationEngine = new SimplePresentationEngineImpl(EarthPanel);
		
		// initialize AbstractControl
		AbstractControl.setSimulationEngine(simulationEngine);
		AbstractControl.setPresentationEngine(presentationEngine);
		
		// create control based on r|t
		if(simulationSettings.isROption()) {
			control = new PresentationControl();
		} else if (simulationSettings.isTOption()) {
			control = new SimulationControl();
		} else {
			control = new MasterControl();
		}
		
		// run simulation
		millisToPuse = (new Date()).getTime();
		control.runSimulation(simulationSettings);
	}
	
	/**
	 * Pauses a running simulation.
	 */
	private void pauseSimulation() {
		if(control.isSimulationRunning()) {
			millisToPuse = (new Date()).getTime() - millisToPuse;
			System.out.println("Stabiliation time in millis: " + millisToPuse);
			control.pauseSimulation();
		} else {
			control.resumeSimulation();
		}
	}
	
	/**
	 * Stops a running simulation.
	 */
	private void stopSimulation() {
		if(control.isSimulationRunning()) {
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

