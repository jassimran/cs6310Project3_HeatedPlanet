package presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import presentation.earth.EarthPanel;
import simplesimulation.SimplePresentationEngineImpl;
import simplesimulation.SimpleSimulationEngineImpl;
import simplesimulation.SimulationUtils;
import simulation.SimulationSettings;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import controllers.AbstractControl;
import controllers.AbstractControlFactory;
import events.EventType;
import events.Listener;

public class Gui extends JFrame implements ActionListener, ChangeListener,
		Listener {

	static final int WIDTH = 800;
	static final int HEIGHT = 320;

	static final String ACTION_RUN = "Run";
	static final String ACTION_PAUSE = "Pause";
	static final String ACTION_RESUME = "Resume";
	static final String ACTION_STOP = "Stop";
	static final String ACTION_GRID_EDIT = "Grid_Spacing";
	static final String ACTION_NUM_EDIT = "Num_Edit";
	static final String ACTION_LAUNCH_QUERY = "Launch Query Interface";
	static final String ACTION_SHOW_ORBIT = "ShowOrbit";

	static final int DEFAULT_GRID_SPACING = 15;
	static final int DEFAULT_SIM_DELAY = 200;
	static final double DEFAULT_ECCENTRICITY = .0167;
	static final double DEFAULT_AXIAL_TILT = 23.44;
	static final int DEFAULT_TIME_STEP = 1440;
	static final int DEFAULT_SIM_LENGTH = 12;

	/*
	 * These arguments will be set through the command line.
	 */
	public static int DEFAULT_TEMP_ACCURACY = 100;
	public static int DEFAULT_GEO_ACCURACY = 100;
	public static int DEFAULT_PRECISION = 7;

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

	private JSlider simLengthSlider = null;
	private JLabel simLengthLabel = null;
	private EventTextField simLengthEdit = null;

	private JSlider tempAccuracySlider = null;
	private JLabel tempAccuracyLabel = null;
	private EventTextField tempAccuracyEdit = null;

	private JSlider geoAccuracySlider = null;
	private JLabel geoAccuracyLabel = null;
	private EventTextField geoAccuracyEdit = null;

	private JLabel simNameLabel = null;
	private JTextField simName = null;

	private JLabel eccentricityLabel = null;
	private JTextField eccentricity = null;

	private JLabel axisTiltLabel = null;
	private JTextField axisTilt = null;

	private JLabel precisionLabel = null;
	private JTextField precision = null;

	private JLabel simTime = null;
	private Calendar simTimeCal = DATE_FORMAT.getCalendar();

	private JLabel orbitalPos = null;
	private JButton showOrbitButton = null;
	private JLabel rotationalPos = null;
	private JLabel rotationalPosResult = null;

	private EarthPanel EarthPanel = new EarthPanel(new Dimension(800, 420),
			new Dimension(800, 420), new Dimension(800, 420));
	private OrbitUI orbitUI;
	private QueryInterfaceUI queryInterfaceUI;

	// control
	AbstractControl control;

	private boolean simthread;
	private boolean presthread;
	private boolean simcontrol;
	private boolean prescontrol;
	private int buffer;

	private Gui(boolean simthread, boolean presthread, boolean simcontrol,
			boolean prescontrol, int buffer) {
		super("PlanetSim");
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
	}

	private static Gui instance = null;
	private JLabel lblDegrees;
	private JLabel lblMinutes;
	private JLabel lblSeconds;
	private JLabel labelTAPercent;
	private JLabel labelGAPercent;
	private JLabel lblMonths;

	// Singleton
	public static Gui getInstance(boolean simthread, boolean presthread,
			boolean simcontrol, boolean prescontrol, int buffer) {
		if (instance == null) {
			instance = new Gui(simthread, presthread, simcontrol, prescontrol,
					buffer);
		}
		return instance;
	}

	public static void destroy() {
		if (instance != null) {
			instance = null;
		}
	}

	private void createGui() {
		// set the time
		try {
			simTimeCal.setTime(DATE_FORMAT.parse(START_TIME));
		} catch (ParseException e) {
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 750);

		createPanel();

		// draw the grid
		EarthPanel.drawGrid(DEFAULT_GRID_SPACING);
	}

	public class EccentricityInputVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String text = ((JTextField) input).getText();
			try {
				if (Double.parseDouble(text) >= 0
						&& Double.parseDouble(text) <= 1)
					return true;
				else {
					JOptionPane.showMessageDialog(null,
							"Eccentricity must be between 0 and 1");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Eccentricity must be numeric value between 0 and 1");
				return false;
			}
		}
	}

	public class AxialTiltInputVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String text = ((JTextField) input).getText();
			try {
				if (Double.parseDouble(text) >= -180
						&& Double.parseDouble(text) <= 180)
					return true;
				else {
					JOptionPane.showMessageDialog(null,
							"Tilt must be between -180 and +180");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Tilt should be numeric value between -180 and +180");
				return false;
			}
		}
	}

	public class PrecisionInputVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String text = ((JTextField) input).getText();
			try {
				if (Integer.parseInt(text) >= 0 && Integer.parseInt(text) <= 16)
					return true;
				else {
					JOptionPane.showMessageDialog(null,
							"Precision must be between 0 and 16");
					return false;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Precision should be Integer value between 0 and 16");
				return false;
			}
		}
	}

	public class SimulationNameInputVerifier extends InputVerifier {
		@Override
		public boolean verify(JComponent input) {
			String simulationName = ((JTextField) input).getText();
			if (simulationName.length() < 1) { // verify not empty
				showMessage("Please enter simulation name!");
				return false;
			}
			if (control.simulationExists(simulationName)) { // verify unique
				showMessage("Simulation name already exists!");
				return false;
			}
			return true;
		}
	}

	private final int EDIT_BOX_WIDTH = 4;

	private JPanel createPanel() {
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// --- Configuration Panel --- //

		JPanel configOpts = new JPanel();
		configOpts
				.setBorder(BorderFactory.createTitledBorder("Configuration "));
		configOpts.setBounds(6, 6, 450, 290);
		contentPane.add(configOpts);
		configOpts.setLayout(null);

		// Label and Slider for Grid Spacing

		JLabel gridSpacingLabel = new JLabel("Grid Spacing:");
		gridSpacingLabel.setBounds(15, 31, 125, 16);
		gridSpacingLabel.setToolTipText("Set Grid Spacing");
		configOpts.add(gridSpacingLabel);

		gridEdit = new ControlledEventTextField(EDIT_BOX_WIDTH,
				DEFAULT_GRID_SPACING);
		gridEdit.setBounds(330, 25, 50, 28);
		gridEdit.setActionCommand(ACTION_GRID_EDIT);
		gridEdit.setEditable(false);
		gridEdit.setHorizontalAlignment(SwingConstants.RIGHT);
		gridEdit.addActionListener(this);
		configOpts.add(gridEdit);

		gridSlider = new JSlider(JSlider.HORIZONTAL, 1, 180,
				DEFAULT_GRID_SPACING);
		gridSlider.setBounds(150, 25, 180, 29);
		gridSlider.setMajorTickSpacing(30);
		gridSlider.setMinorTickSpacing(15);
		gridSlider.setPaintTicks(true);
		gridSlider.setPaintTrack(true);
		gridSlider.addChangeListener(gridEdit);
		configOpts.add(gridSlider);

		lblDegrees = new JLabel("degrees");
		lblDegrees.setBounds(385, 31, 61, 16);
		configOpts.add(lblDegrees);

		// Label and Slider for Time Step

		JLabel timestepLabel = new JLabel("Sim Time Step:");
		timestepLabel.setBounds(15, 61, 125, 16);
		timestepLabel.setToolTipText("Set Simulation Time Step");
		configOpts.add(timestepLabel);

		stepEdit = new EventTextField(EDIT_BOX_WIDTH, DEFAULT_TIME_STEP);
		stepEdit.setBounds(330, 55, 50, 28);
		stepEdit.setEditable(false);
		stepEdit.setHorizontalAlignment(SwingConstants.RIGHT);
		configOpts.add(stepEdit);

		stepSlider = new JSlider(JSlider.HORIZONTAL, 1, 525600,
				DEFAULT_TIME_STEP);
		stepSlider.setBounds(150, 55, 180, 29);
		stepSlider.setMajorTickSpacing(72000);
		stepSlider.setMinorTickSpacing(14400);
		stepSlider.setPaintTicks(true);
		stepSlider.setPaintTrack(true);
		stepSlider.addChangeListener(stepEdit);
		configOpts.add(stepSlider);

		lblMinutes = new JLabel("minutes");
		lblMinutes.setBounds(385, 61, 61, 16);
		configOpts.add(lblMinutes);

		// Label and Slider for Presentation Display Rate

		JLabel displayRateLabel = new JLabel("Pres Display Rate:");
		displayRateLabel.setBounds(15, 91, 125, 16);
		displayRateLabel.setToolTipText("Set Presentation Display Rate");
		configOpts.add(displayRateLabel);

		displayEdit = new EventTextField(EDIT_BOX_WIDTH, 1);
		displayEdit.setBounds(330, 85, 50, 28);
		displayEdit.setEditable(false);
		displayEdit.setHorizontalAlignment(SwingConstants.RIGHT);
		configOpts.add(displayEdit);

		displaySlider = new JSlider(JSlider.HORIZONTAL, 1, 60, 1);
		displaySlider.setBounds(150, 85, 180, 29);
		displaySlider.setMajorTickSpacing(10);
		displaySlider.setMinorTickSpacing(5);
		displaySlider.setPaintTicks(true);
		displaySlider.setPaintTrack(true);
		displaySlider.addChangeListener(displayEdit);
		configOpts.add(displaySlider);

		lblSeconds = new JLabel("seconds");
		lblSeconds.setBounds(385, 91, 61, 16);
		configOpts.add(lblSeconds);

		// Label and Slider for Simulation Length

		simLengthLabel = new JLabel("Simulation Length:");
		simLengthLabel.setBounds(15, 121, 125, 16);
		simLengthLabel.setToolTipText("Set Simulation Length");
		configOpts.add(simLengthLabel);

		simLengthEdit = new EventTextField(EDIT_BOX_WIDTH, DEFAULT_SIM_LENGTH);
		simLengthEdit.setBounds(330, 115, 50, 28);
		simLengthEdit.setEditable(false);
		simLengthEdit.setHorizontalAlignment(SwingConstants.RIGHT);
		configOpts.add(simLengthEdit);

		simLengthSlider = new JSlider(JSlider.HORIZONTAL, 1, 1200,
				DEFAULT_SIM_LENGTH);
		simLengthSlider.setBounds(150, 115, 180, 29);
		simLengthSlider.setMajorTickSpacing(200);
		simLengthSlider.setMaximum(1200);
		simLengthSlider.setMinimum(1);
		simLengthSlider.setMinorTickSpacing(100);
		// simLengthSlider.setPaintLabels(true);
		simLengthSlider.setPaintTicks(true);
		simLengthSlider.addChangeListener(simLengthEdit);
		configOpts.add(simLengthSlider);

		lblMonths = new JLabel("months");
		lblMonths.setBounds(385, 121, 61, 16);
		configOpts.add(lblMonths);

		// Label and Slider for Temporal Accuracy

		tempAccuracyLabel = new JLabel("Temp Accuracy:");
		tempAccuracyLabel.setBounds(15, 151, 125, 16);
		tempAccuracyLabel.setToolTipText("Set Temporal Accuracy");
		configOpts.add(tempAccuracyLabel);

		tempAccuracyEdit = new EventTextField(EDIT_BOX_WIDTH,
				DEFAULT_TEMP_ACCURACY);
		tempAccuracyEdit.setBounds(330, 145, 50, 28);
		tempAccuracyEdit.setEditable(false);
		tempAccuracyEdit.setHorizontalAlignment(SwingConstants.RIGHT);
		configOpts.add(tempAccuracyEdit);

		tempAccuracySlider = new JSlider(JSlider.HORIZONTAL, 1, 100,
				DEFAULT_TEMP_ACCURACY);
		tempAccuracySlider.setBounds(150, 145, 180, 29);
		tempAccuracySlider.setMajorTickSpacing(10);
		tempAccuracySlider.setMaximum(100);
		tempAccuracySlider.setMinimum(1);
		tempAccuracySlider.setMinorTickSpacing(5);
		// axisTiltSlider.setPaintLabels(true);
		tempAccuracySlider.setPaintTicks(true);
		tempAccuracySlider.setPaintTrack(true);
		tempAccuracySlider.addChangeListener(tempAccuracyEdit);
		configOpts.add(tempAccuracySlider);

		labelTAPercent = new JLabel("percent");
		labelTAPercent.setBounds(385, 151, 61, 16);
		configOpts.add(labelTAPercent);

		// Label and Slider for Geographic Accuracy

		geoAccuracyLabel = new JLabel("Geo Accuracy:");
		geoAccuracyLabel.setBounds(15, 181, 125, 16);
		geoAccuracyLabel.setToolTipText("Set Geographic Accuracy");
		configOpts.add(geoAccuracyLabel);

		geoAccuracyEdit = new EventTextField(EDIT_BOX_WIDTH,
				DEFAULT_GEO_ACCURACY);
		geoAccuracyEdit.setBounds(330, 175, 50, 28);
		geoAccuracyEdit.setEditable(false);
		geoAccuracyEdit.setHorizontalAlignment(SwingConstants.RIGHT);
		configOpts.add(geoAccuracyEdit);

		geoAccuracySlider = new JSlider(JSlider.HORIZONTAL, 1, 100,
				DEFAULT_GEO_ACCURACY);
		geoAccuracySlider.setBounds(150, 175, 180, 29);
		geoAccuracySlider.setMajorTickSpacing(10);
		geoAccuracySlider.setMaximum(100);
		geoAccuracySlider.setMinimum(1);
		geoAccuracySlider.setMinorTickSpacing(5);
		geoAccuracySlider.setPaintTicks(true);
		geoAccuracySlider.setPaintTrack(true);
		geoAccuracySlider.addChangeListener(geoAccuracyEdit);
		configOpts.add(geoAccuracySlider);

		labelGAPercent = new JLabel("percent");
		labelGAPercent.setBounds(385, 181, 61, 16);
		configOpts.add(labelGAPercent);

		// Label and Textfield for Precision

		precisionLabel = new JLabel("Precision:");
		precisionLabel.setBounds(15, 211, 140, 16);
		precisionLabel.setToolTipText("Set Precision");
		configOpts.add(precisionLabel);

		precision = new JTextField(String.valueOf(DEFAULT_PRECISION), 8);
		precision.setBounds(160, 205, 134, 28);
		precision.setHorizontalAlignment(SwingConstants.RIGHT);
		precision.setInputVerifier(new PrecisionInputVerifier());
		configOpts.add(precision);

		// Label and Textfield for Save Simulation As

		simNameLabel = new JLabel("Save Simulation As:");
		simNameLabel.setBounds(15, 241, 140, 16);
		simNameLabel.setToolTipText("Set a unique simulation name");
		configOpts.add(simNameLabel);

		simName = new JTextField(28);
		simName.setBounds(160, 235, 134, 28);
		simName.setHorizontalAlignment(SwingConstants.RIGHT);
		simName.setInputVerifier(new SimulationNameInputVerifier());
		configOpts.add(simName);

		// --- Physical Factors Panel --- //

		JPanel physicalFactorsPanel = new JPanel();
		physicalFactorsPanel.setBounds(456, 6, 342, 90);
		physicalFactorsPanel.setBorder(BorderFactory
				.createTitledBorder("Physical factors"));
		contentPane.add(physicalFactorsPanel);
		physicalFactorsPanel.setLayout(null);

		eccentricityLabel = new JLabel("Orbital Eccentricity:");
		eccentricityLabel.setBounds(10, 26, 160, 16);
		physicalFactorsPanel.add(eccentricityLabel);

		axisTiltLabel = new JLabel("Axial Tilt:");
		axisTiltLabel.setBounds(10, 60, 160, 16);
		axisTiltLabel.setToolTipText("In degrees");
		physicalFactorsPanel.add(axisTiltLabel);

		eccentricity = new JTextField(String.valueOf(DEFAULT_ECCENTRICITY), 6);
		eccentricity.setBounds(180, 20, 60, 28);
		eccentricity.setHorizontalAlignment(SwingConstants.RIGHT);
		eccentricity.setToolTipText("Between 0 and 1");
		eccentricity.setInputVerifier(new EccentricityInputVerifier());
		physicalFactorsPanel.add(eccentricity);

		axisTilt = new JTextField(String.valueOf(DEFAULT_AXIAL_TILT), 6);
		axisTilt.setBounds(180, 50, 60, 28);
		axisTilt.setHorizontalAlignment(SwingConstants.RIGHT);
		axisTilt.setToolTipText("In degrees");
		axisTilt.setInputVerifier(new AxialTiltInputVerifier());
		physicalFactorsPanel.add(axisTilt);

		// --- Run Panel --- //

		JPanel runPanel = new JPanel();
		runPanel.setBorder(BorderFactory.createTitledBorder("Run"));
		runPanel.setBounds(456, 96, 342, 54);
		contentPane.add(runPanel);
		runPanel.setLayout(null);

		runButton = new JButton("Run");
		runButton.setBounds(10, 20, 100, 20);
		runButton.setActionCommand(ACTION_RUN);
		runButton.addActionListener(this);
		runButton.setEnabled(true);
		runPanel.add(runButton);

		pauseButton = new JButton("Pause");
		pauseButton.setBounds(120, 20, 100, 20);
		pauseButton.setActionCommand(ACTION_PAUSE);
		pauseButton.addActionListener(this);
		pauseButton.setEnabled(false);
		runPanel.add(pauseButton);

		stopButton = new JButton("Stop");
		stopButton.setBounds(230, 20, 100, 20);
		stopButton.setActionCommand(ACTION_STOP);
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		runPanel.add(stopButton);

		// --- Time & Position --- //

		JPanel timePositionPanel = new JPanel();
		timePositionPanel.setBorder(BorderFactory
				.createTitledBorder("Time & Position"));
		timePositionPanel.setBounds(456, 150, 342, 90);
		contentPane.add(timePositionPanel);
		timePositionPanel.setLayout(null);

		simTime = new JLabel(START_TIME);
		simTime.setHorizontalAlignment(SwingConstants.CENTER);
		simTime.setBounds(10, 16, 330, 16);
		timePositionPanel.add(simTime);

		orbitalPos = new JLabel("Dist from sun: N/A");
		orbitalPos.setBounds(10, 32, 330, 16);
		timePositionPanel.add(orbitalPos);

		rotationalPos = new JLabel("Latitude : N/A");
		rotationalPos.setBounds(10, 48, 200, 16);
		timePositionPanel.add(rotationalPos);

		rotationalPosResult = new JLabel("Longitude: N/A");
		rotationalPosResult.setBounds(10, 64, 200, 16);
		timePositionPanel.add(rotationalPosResult);

		showOrbitButton = new JButton("Show Orbit");
		showOrbitButton.setBounds(210, 60, 120, 20);
		showOrbitButton.setActionCommand(ACTION_SHOW_ORBIT);
		showOrbitButton.addActionListener(this);
		showOrbitButton.setEnabled(false);
		timePositionPanel.add(showOrbitButton);

		// --- Query Panel --- //

		JPanel queryUIPanel = new JPanel();
		queryUIPanel.setBounds(456, 240, 342, 56);
		queryUIPanel.setBorder(BorderFactory
				.createTitledBorder("Query Interface"));
		contentPane.add(queryUIPanel);
		queryUIPanel.setLayout(null);

		initQueryButton = new JButton("Launch Query Interface");
		initQueryButton.setBounds(71, 21, 200, 23);
		initQueryButton.setActionCommand(ACTION_LAUNCH_QUERY);
		initQueryButton.addActionListener(this);
		initQueryButton.setEnabled(true);
		queryUIPanel.add(initQueryButton);

		// --- Earth Panel --- //

		EarthPanel.setBounds(0, 0, 800, 420);
		JPanel earthPanel = new JPanel();
		earthPanel.setBounds(6, 296, 800, 420);
		earthPanel.setBackground(Color.gray);
		earthPanel.setLayout(null);
		earthPanel.add(EarthPanel);
		contentPane.add(earthPanel);

		return contentPane;
	}

	public void stateChanged(ChangeEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// get the command
		String command = e.getActionCommand();

		if (ACTION_RUN.equals(command)) {
			// check simulation name is valid
			InputVerifier simulaitonVerifier = new SimulationNameInputVerifier();
			if (!simulaitonVerifier.verify(simName)) {
				return;
			}
			// disable all controls
			this.setEnableAllUserOptions(false);
			// disable the run button
			runButton.setEnabled(false);
			// enable the stop button
			pauseButton.setEnabled(true);
			// disable the restart button
			stopButton.setEnabled(false);
			// enable the show orbit button
			showOrbitButton.setEnabled(true);
			// run simulation
			runSimulation();
		} else if (ACTION_PAUSE.equals(command)) {
			// enable the restart button
			stopButton.setEnabled(true);
			// change pause button legend
			pauseButton.setText(ACTION_RESUME);
			// change pause button action
			pauseButton.setActionCommand(ACTION_RESUME);
			// pause simulation
			pauseSimulation();
		} else if (ACTION_RESUME.equals(command)) {
			// enable the restart button
			stopButton.setEnabled(false);
			// change pause button legend
			pauseButton.setText(ACTION_PAUSE);
			// change pause button action
			pauseButton.setActionCommand(ACTION_PAUSE);
			// resume simulation
			resumeSimulation();
		} else if (ACTION_STOP.equals(command)) {
			// terminate simulation
			stopSimulation();
			
			executeAfterStop();
		} else if (ACTION_GRID_EDIT.equals(command)) {
			// update the visual grid with new value
			EarthPanel.drawGrid(gridEdit.getValue());
		} else if (ACTION_LAUNCH_QUERY.equals(command)) {
			// For launching query interface
			// But before that, disable all controls
			this.setEnableAllUserOptions(false);
			// disable the run button
			runButton.setEnabled(false);
			// disable the stop button
			pauseButton.setEnabled(false);
			// disable the restart button
			stopButton.setEnabled(false);
			// QueryInterfaceUI gui = new QueryInterfaceUI();
			// gui.launchQueryInterface();
			QueryInterfaceUI tmpQueryInterfaceUI = QueryInterfaceUI
					.getInstance();

			if (!tmpQueryInterfaceUI.equals(queryInterfaceUI)) {
				queryInterfaceUI = tmpQueryInterfaceUI;

				queryInterfaceUI.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						setEnableAllUserOptions(true);

						runButton.setEnabled(true);
					}
				});
			}
		} else if (ACTION_SHOW_ORBIT.equals(command)) {
			// Disable show button
			showOrbitButton.setEnabled(false);

			// Showing orbit pop up
			OrbitUI tmpOrbitUI = OrbitUI.getInstance(SimulationUtils.A,
					simulationSettings.getEccentricity());

			if (!tmpOrbitUI.equals(orbitUI)) {
				orbitUI = tmpOrbitUI;

				orbitUI.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
						showOrbitButton.setEnabled(true);
					}
				});
			}
		}
	}
	
	private void executeAfterStop() {
		// enable all controls
		this.setEnableAllUserOptions(true);
		pauseButton.setText(ACTION_PAUSE);
		pauseButton.setActionCommand(ACTION_PAUSE);
		// reset pause button
		pauseButton.setEnabled(false);
		// disable the stop button
		stopButton.setEnabled(false);
		// enable the run button
		runButton.setEnabled(true);
		// disable the show orbit button
		showOrbitButton.setEnabled(false);
		// terminate the orbit UI if any
		if (orbitUI != null && orbitUI.isShowing()) {
			orbitUI.setVisible(false);
			orbitUI.dispose();
		}
		// reset the EarthPanel
		EarthPanel.reset();
		try {
			// set the time
			simTimeCal.setTime(DATE_FORMAT.parse(START_TIME));
			simTime.setText(DATE_FORMAT.format(simTimeCal.getTime()));
		} catch (ParseException e2) {
		}
	}

	private volatile SimulationSettings simulationSettings = new SimulationSettings();

	/**
	 * Executes a simulation based on the selected settings.
	 */
	private void runSimulation() {
		// create settings object
		simulationSettings.setSOption(simthread);
		simulationSettings.setPOption(presthread);
		simulationSettings.setROption(prescontrol);
		simulationSettings.setTOption(simcontrol);
		simulationSettings.setNumCellsX(EarthPanel.getNumCellsX());
		simulationSettings.setNumCellsY(EarthPanel.getNumCellsY());
		simulationSettings
				.setDegreeSeparation(EarthPanel.getDegreeSeparation());
		simulationSettings.setBufferSize(buffer);

		// set presentation display rate
		simulationSettings
				.setPresentationDisplayRate(displayEdit.getValue() * 1000); // milliseconds
		simulationSettings.setSimulationTimeStep(stepEdit.getValue());
		simulationSettings.setGridSpacing(gridEdit.getValue());

		// set temperature precision
		simulationSettings.setPrecision(Integer.parseInt(precision.getText()));
		simulationSettings.setAxialTilt(Double.parseDouble(axisTilt.getText()));
		simulationSettings.setEccentricity(Double.parseDouble(eccentricity
				.getText().toString()));
		simulationSettings.setName(simName.getText());
		simulationSettings.setSimulationLength(simLengthEdit.getValue()); // default
																			// 12
		simulationSettings.setTemporalAccuracy(tempAccuracyEdit.getValue());
		simulationSettings.setGeoAccuracy(geoAccuracyEdit.getValue());

		// wire simulation engines
		AbstractControl.setSimulationEngine(new SimpleSimulationEngineImpl(
				simulationSettings));
		AbstractControl.setPresentationEngine(new SimplePresentationEngineImpl(
				EarthPanel));

		// run simulation
		control.runSimulation(simulationSettings);
	}

	private void createControl() {
		// create control based on r|t
		if (prescontrol) {
			control = AbstractControlFactory.getInstance().createControl(
					AbstractControlFactory.BA);
		} else if (simcontrol) {
			control = AbstractControlFactory.getInstance().createControl(
					AbstractControlFactory.AB);
		} else {
			control = AbstractControlFactory.getInstance().createControl(
					AbstractControlFactory.MAB);
		}
		// register UI as listener for control events
		control.addListener(this);
	}

	/**
	 * Pauses a running simulation.
	 */
	private void pauseSimulation() {
		if (control.isSimulationRunning()) {
			control.pauseSimulation();
		} else {
			throw new RuntimeException("Paused a not running simulation");
		}
	}

	/**
	 * Resumes a paused simulation.
	 */
	private void resumeSimulation() {
		if (!control.isSimulationRunning()) {
			control.resumeSimulation();
		} else {
			throw new RuntimeException("Resume a running simulation");
		}
	}

	/**
	 * Stops a running simulation.
	 */
	private void stopSimulation() {
		if (!control.isTerminateSimulation()) {
			control.stopSimulation();
		}
	}

	// enable/disable all controls
	private void setEnableAllUserOptions(boolean bEnable) {
		gridEdit.setEnabled(bEnable);
		gridSlider.setEnabled(bEnable);
		stepEdit.setEnabled(bEnable);
		stepSlider.setEnabled(bEnable);
		displayEdit.setEnabled(bEnable);
		displaySlider.setEnabled(bEnable);
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
		initQueryButton.setEnabled(bEnable);
	}

	public void updateClock(double latitude, double longitude,
			double distFromSun, double[] coordinates) {
		try {
			// set the time
			simTimeCal.add(Calendar.MINUTE, stepEdit.getValue());
			simTime.setText(DATE_FORMAT.format(simTimeCal.getTime()));

			orbitalPos.setText("Dist from Sun = " + String.valueOf(distFromSun)
					+ " million km");
			rotationalPos.setText("Latitude: " + String.valueOf(latitude)
					+ "  degrees");
			rotationalPosResult.setText("Longitude: "
					+ String.valueOf(longitude) + "  degrees");

			if (orbitUI != null && orbitUI.isShowing()) {
				orbitUI.updatePosition(coordinates);
			}

		} catch (Exception e2) {
			System.out.print("Updation failed!!!");
		}
	}

	@Override
	public void notify(EventType e) {
		if (e == EventType.SimulationFinishedEvent) {
			// notify user
			JOptionPane.showMessageDialog(this, "Simulation complete!");

			executeAfterStop();
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
	 * 
	 * @param message
	 *            the message to show
	 */
	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

}

/*
 * Event Text Field class to keep sliders and text field in synch
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
		// fire the event
		this.fireActionPerformed();
	}
}

/*
 * Controlled Event Text Field to restrict the slider value of grid spacing to
 * values that evenly divides 180 degrees
 */
class ControlledEventTextField extends EventTextField {
	private static final long serialVersionUID = 1346347586797802332L;

	public ControlledEventTextField(int cols, int initVal) {
		super(cols, initVal);
	}

	public void stateChanged(ChangeEvent e) {
		Object eventSource = e.getSource();
		if (eventSource.getClass().getName().equals("javax.swing.JSlider")) {
			JSlider slider = (JSlider) eventSource;
			try {
				// get the value of the slider
				float val = (Float.valueOf(slider.getValue())).floatValue();

				// need to restrict to allowed numbers
				int[] allowedNumbers = { 1, 2, 3, 4, 5, 6, 9, 10, 12, 15, 18,
						20, 30, 36, 45, 60, 90, 180 };
				int finalNum = 0;
				// get the largest integer less than or equal to gs that evenly
				// divides 180 degrees
				for (int i = 0; i < allowedNumbers.length; i++) {
					if (val >= allowedNumbers[i]) {
						finalNum = allowedNumbers[i];
					}
				}

				// if the number is between 0 & 180
				if (val > 0 && val <= 180) {
					// set the value of text
					this.setText(new String(Integer.valueOf(finalNum)
							.toString()));
				}
			}
			// catch number format exception
			catch (NumberFormatException z) {
			}

		}
		// fire the event
		this.fireActionPerformed();
	}
}
