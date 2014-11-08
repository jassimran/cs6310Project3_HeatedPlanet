/**
 * "A mechanism should be provided that allows the user to interactively accomplish the following.
 * Initialize or change the values of the Simulation Settings*
 * Presentation display rate: Control the rate at which the presentation is displayed (which is 
 * different from the Simulation Time Step described above). This rate can be understand by using an 
 * analogy with time-lapse photography (TLP). In TLP, pictures of a flower are taken every five minutes 
 * and shown one per second. The first number (five minutes) corresponds to the Simulation Time Step; 
 * the second (one per second) corresponds to the Presentation Display Rate
 * Start a simulation running
 * Pause the simulation
 * Restart a paused simulation
 * Stop the simulation" -- https://s3.amazonaws.com/content.udacity-data.com/courses/gt-cs6310/projects/project2/project2.html 10/12/14
 * 
 * *Simulation settings:
 * Run Simulation in its own thread (boolean)
 * Run Presentation in its own thread (boolean)
 * Initiative: Simulation xor Presentation xor UI
 * Buffer size
 * "Grid spacing: positive integer angular degrees between 1deg and 180deg; default is 15deg (one time zone). 
 * If the input values does not evenly divide into 180deg, you should reduce it to the next lower number 
 * that does so.
 * Simulation time step: positive integer number of minutes between 1 and 1440 (1 day); default is 1 
 * minute. This value represents how much simulated time passes between temperature recalculations. 
 * It is not the length of the simulation nor is it the rate of presentation refresh; rather it controls 
 * the mapping from simulation steps to physical time units."
 */
package edu.gatech.cs6310.project2.team13.gui.widget.input;

import java.awt.event.ActionEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import EarthSim.MediatingVisualizationPanel;
import EarthSim.PullingVisualizationPanel;
import EarthSim.PushingVisualizationPanel;
import EarthSim.SimulationEngine;
import edu.gatech.cs6310.project2.team13.buffer.DataBuffer;
import edu.gatech.cs6310.project2.team13.data.EarthGrid.GridSpacingException;
import edu.gatech.cs6310.project2.team13.utils.Logging;
/**
 *
 */  
public class UserControls extends javax.swing.JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final int TEXTBOXCOLUMNWIDTH = 5;
    
    // (From NetBeans Swing Designer while following 
    // http://docs.oracle.com/javase/tutorial/uiswing/learn/)  
    private javax.swing.JButton startButton; // start/stop the simulation
    private javax.swing.JButton pauseButton; // un/pause the simulation
    private javax.swing.JLabel displayRateLabel;
    private javax.swing.JTextField displayRateField; // Presentation display rate    
    private javax.swing.JLabel bufferSizeLabel;
    private javax.swing.JTextField bufferSizeField;
    private javax.swing.JLabel gridSpacingLabel;   
    private javax.swing.JLabel minuteIntervalLabel;
    private javax.swing.JTextField minuteIntervalField;
    private javax.swing.JRadioButton guiHasInitRButton;
    private javax.swing.JRadioButton presenHasInitRButton;
    private javax.swing.JCheckBox presenInOwnThread;
    private javax.swing.JRadioButton simHasInitRButton;
    private javax.swing.JCheckBox simInOwnThread;
    private javax.swing.ButtonGroup initiativeButtonGroup;
    
 // - Combo box to display a list of options for grid  spacing
 	private javax.swing.JComboBox gridSpaceList ;
    private MediatingVisualizationPanel mvp;
    private PullingVisualizationPanel puvp;
    private PushingVisualizationPanel pvp;
    
    private int displayRate;
    private int bufferSize;
    private int gridSpacing = 15;
    private int minuteInterval = 15;
    private int CM; // control method
    private boolean STFP; // separate thread for presentation t/f
    private boolean STFS; // separate thread for simulation t/f
    private boolean doTerminate; // Set to terminate the simulation after threshold has been met
    SimulationEngine engine = null;

	
    /** Construct JFrame Earth Heat Simulation */
    public UserControls(int displayRate, int bufferSize, boolean separateThreadForSimulation,
    		boolean separateThreadForPresentation, int controlMethod, boolean doTerminate) {

    	this.displayRate = displayRate;
    	this.bufferSize = bufferSize;
    	this.STFS = separateThreadForSimulation;
    	this.STFP = separateThreadForPresentation;
    	this.doTerminate = doTerminate;
    	this.CM = controlMethod;
        this.setSize(600, 100);        
        this.initComponents();
        this.setVisible(true);  
    }
    
    /*public UserControls(int displayRate, int bufferSize, boolean separateThreadForSimulation,
    		boolean separateThreadForPresentation, int controlMethod, boolean doTerminate, boolean showPanel) {

    	this.displayRate = displayRate;
    	this.bufferSize = bufferSize;
    	this.STFS = separateThreadForSimulation;
    	this.STFP = separateThreadForPresentation;
    	this.doTerminate = doTerminate;
    	this.CM = controlMethod;
        this.setSize(600, 100);        
        this.initComponents();
        this.setVisible(showPanel);  
    }*/
    
    public UserControls(int displayRate, int bufferSize, boolean separateThreadForSimulation,
    		boolean separateThreadForPresentation, int controlMethod, boolean doTerminate, boolean showPanel, int gridSize) {
    	this.displayRate = displayRate;
    	this.bufferSize = bufferSize;
    	this.gridSpacing = gridSize;
    	this.STFS = separateThreadForSimulation;
    	this.STFP = separateThreadForPresentation;
    	this.doTerminate = doTerminate;
    	this.CM = controlMethod;
        this.setSize(600, 100);        
        this.initComponents();
        this.setVisible(showPanel);  
    }
    
    /**
     * "Grid spacing: positive integer angular degrees between 1deg and 180deg; default is 15deg (one time zone). 
     * If the input values does not evenly divide into 180deg, you should reduce it to the next lower number that does so."
     */
    public void populateComboBox(){
    	gridSpaceList.addItem("1");
    	gridSpaceList.addItem("2");
    	gridSpaceList.addItem("3");
    	gridSpaceList.addItem("4");
    	gridSpaceList.addItem("5");
    	gridSpaceList.addItem("6");
    	gridSpaceList.addItem("9");
    	gridSpaceList.addItem("10");
    	gridSpaceList.addItem("12");
    	gridSpaceList.addItem("15");
    	gridSpaceList.addItem("18");
    	gridSpaceList.addItem("20");
    	gridSpaceList.addItem("30");
    	gridSpaceList.addItem("36");
    	gridSpaceList.addItem("45");
    	gridSpaceList.addItem("60");
    	gridSpaceList.addItem("90");
    	gridSpaceList.addItem("180");
    }
    
    /** 
     * Called from constructor to initialize widgets.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Earth Heat Simulation");
        setName("Earth Heat Simulation");

        startButton = new javax.swing.JButton();
        gridSpaceList = new javax.swing.JComboBox();
        populateComboBox();
        startButton.setText("Start");
        startButton.setName("startButton"); // NOI18N
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        pauseButton = new javax.swing.JButton();
        pauseButton.setText("Pause");
        pauseButton.setName("pauseButton"); // NOI18N
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        
        displayRateLabel = new javax.swing.JLabel();
        displayRateLabel.setText("Display rate");
        displayRateLabel.setToolTipText("Presentation frames per second");
        
        displayRateField = new JTextField();
        displayRateField.setColumns(TEXTBOXCOLUMNWIDTH);
        displayRateField.setText(Integer.toString(this.displayRate));
        displayRateField.setName("displayRateField"); // NOI18N
        displayRateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayRateFieldActionPerformed(evt);
            }
        });

        bufferSizeLabel = new javax.swing.JLabel();
        bufferSizeLabel.setText("Buffer size");
        bufferSizeLabel.setToolTipText("in bytes");
        
        bufferSizeField = new javax.swing.JTextField();
        bufferSizeField.setColumns(TEXTBOXCOLUMNWIDTH);
        bufferSizeField.setText(Integer.toString(bufferSize));
        bufferSizeField.setName("bufferSizeField"); // NOI18N
        bufferSizeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bufferSizeFieldActionPerformed(evt);
            }
        });
        
        gridSpacingLabel = new javax.swing.JLabel();
        gridSpacingLabel.setText("Grid spacing");
        gridSpacingLabel.setToolTipText("in degrees");
        
       
        gridSpaceList.setName("gridSpacingField"); // NOI18N
        gridSpaceList.setSelectedIndex(9);
        gridSpaceList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridSpacingFieldActionPerformed(evt);
            }
        });

        minuteIntervalLabel = new javax.swing.JLabel();
        minuteIntervalLabel.setText("Sim interval");
        minuteIntervalLabel.setToolTipText("in minutes");
        
        minuteIntervalField = new javax.swing.JTextField();
        minuteIntervalField.setColumns(TEXTBOXCOLUMNWIDTH);
        minuteIntervalField.setText(Integer.toString(minuteInterval));
        minuteIntervalField.setName("minuteIntervalField"); // NOI18N
        minuteIntervalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minuteIntervalFieldActionPerformed(evt);
            }
        });
        
        simInOwnThread = new javax.swing.JCheckBox();
        simInOwnThread.setSelected(STFS);
        simInOwnThread.setText("Run Simulation in its own thread");
        simInOwnThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simInOwnThreadCheckboxActionPerformed(evt);
            }
        });
        
        presenInOwnThread = new javax.swing.JCheckBox();
        presenInOwnThread.setSelected(STFP);
        presenInOwnThread.setText("Run Presentation in its own thread");
        presenInOwnThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presenInOwnThreadCheckboxActionPerformed(evt);
            }
        });
        
        initiativeButtonGroup = new javax.swing.ButtonGroup();
        simHasInitRButton = new javax.swing.JRadioButton();
        simHasInitRButton.setText("Simulation");
        initiativeButtonGroup.add(simHasInitRButton);
        simHasInitRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simHasInitiativeButtonActionPerformed(evt);
            }
        });
        
        presenHasInitRButton = new javax.swing.JRadioButton();
        presenHasInitRButton.setText("Presentation");
        initiativeButtonGroup.add(presenHasInitRButton);
        presenHasInitRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presenHasInitiativeButtonActionPerformed(evt);
            }
        });
        
        guiHasInitRButton = new javax.swing.JRadioButton();
        guiHasInitRButton.setText("GUI has initiative");
        initiativeButtonGroup.add(guiHasInitRButton);
        guiHasInitRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guiHasInitiativeButtonActionPerformed(evt);
            }
        });
        
        switch(CM) {
			case SimulationEngine.MEDIATING:
				guiHasInitRButton.setSelected(true);
				break;
			case SimulationEngine.PULLING:
				presenHasInitRButton.setSelected(true);
				break;
			case SimulationEngine.PUSHING:
				simHasInitRButton.setSelected(true);
				break;
		}
        enableWidgets(true); 

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(presenInOwnThread)
                                    .addComponent(simInOwnThread))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                //.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(startButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pauseButton)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(displayRateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(displayRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bufferSizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bufferSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gridSpacingLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gridSpaceList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(minuteIntervalLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(minuteIntervalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27))))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(simHasInitRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(presenHasInitRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guiHasInitRButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(displayRateLabel)
                        .addComponent(displayRateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bufferSizeLabel)
                            .addComponent(bufferSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(gridSpacingLabel)
                                    .addComponent(gridSpaceList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(minuteIntervalLabel)
                                .addComponent(minuteIntervalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(simInOwnThread))
                    //.addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(presenInOwnThread)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(simHasInitRButton)
                    .addComponent(presenHasInitRButton)
                    .addComponent(guiHasInitRButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(pauseButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String start(){
    	String errMsg = "";
    	try {
    		
    		//If we're running tests, don't update from the user fields.
    		if(!this.doTerminate){
	    		//Get new values from user controls
	    		this.displayRate = Integer.parseInt(displayRateField.getText());
	    		if(this.displayRate < 1 ) {
	    			errMsg = "Display rate must be valid positive integer";
	                throw new IllegalArgumentException();
	            }
	    		this.gridSpacing = Integer.parseInt(gridSpaceList.getSelectedItem().toString() );
	    		
	    		this.bufferSize = Integer.parseInt(bufferSizeField.getText());
	    		if(this.bufferSize < 0 ) {
	    			errMsg = "Display rate must be valid positive integer";
	                throw new IllegalArgumentException();
	            }
	    		this.minuteInterval = Integer.parseInt(minuteIntervalField.getText() );
	    		if(this.minuteInterval < 0 ) {
	    			errMsg = "Display rate must be valid positive integer";
	                throw new IllegalArgumentException();
	            }
	    		this.STFP = presenInOwnThread.isSelected();
	    		this.STFS = simInOwnThread.isSelected();
    		}//!this.doTerminate
    		
    		//Start Simulation
    	    DataBuffer db = new DataBuffer(this.bufferSize);
			switch(CM) {
				case SimulationEngine.MEDIATING:
					mvp = new MediatingVisualizationPanel(gridSpacing, displayRate, minuteInterval, db);
					mvp.start(STFP);
					engine = new SimulationEngine(mvp, db, gridSpacing, minuteInterval, doTerminate);
					break;
				case SimulationEngine.PULLING:
				
					engine = new SimulationEngine(db, gridSpacing, displayRate, minuteInterval, doTerminate);
				
					puvp = engine.getCVP();
					puvp.start(STFP);
					break;
				case SimulationEngine.PUSHING:
					pvp = new PushingVisualizationPanel(gridSpacing, displayRate, minuteInterval, db);
					pvp.start(STFP);
					engine = new SimulationEngine(pvp, db, gridSpacing, minuteInterval, doTerminate);
					break;
			}			
			this.engine.start(STFS);
			
		} catch (GridSpacingException e) {
			e.printStackTrace();
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			if(errMsg.equals(""))
				errMsg = "Please enter integer values only!";
			JOptionPane.showMessageDialog(null, ""+errMsg);
		} catch(IllegalArgumentException iae) {
			iae.printStackTrace();
			JOptionPane.showMessageDialog(null, ""+errMsg);
		}
    	return errMsg;
    }
    
    protected void displayRateFieldActionPerformed(ActionEvent evt) {
        if (displayRateField!=null) {
            this.displayRate = Integer.parseInt(this.displayRateField.getText());
        }
    }

    /**
     * 
     * @param evt
     */
    private void bufferSizeFieldActionPerformed(ActionEvent evt) {
        if (bufferSizeField!=null) {
            this.bufferSize = Integer.parseInt(this.bufferSizeField.getText());
        }
    }
    
    protected void gridSpacingFieldActionPerformed(ActionEvent evt) {
        if (gridSpaceList!=null) {
            this.gridSpacing = Integer.parseInt(this.gridSpaceList.getSelectedItem().toString());
        }
    }

    /**
     * 
     * @param evt
     */
    private void minuteIntervalFieldActionPerformed(ActionEvent evt) {
        if (minuteIntervalField!=null) {
            this.minuteInterval = Integer.parseInt(this.minuteIntervalField.getText());
        }
    }
    
    /**
     * 
     * @param evt
     */
    private void simInOwnThreadCheckboxActionPerformed(ActionEvent evt) {
        if (simInOwnThread.isSelected()) {
            this.STFS = true; 
        } else {
            this.STFS = false; 
        }
    }
    
    /**
     * 
     * @param evt
     */
    private void presenInOwnThreadCheckboxActionPerformed(ActionEvent evt) { 
        if (simInOwnThread.isSelected()) {
            this.STFP = true; 
        } else {
            this.STFP = false; 
        }
    }
    
    /** 
     * @param evt
     */
    private void simHasInitiativeButtonActionPerformed(ActionEvent evt) {
        this.CM = SimulationEngine.PUSHING;
    }
    
    /**
     * 
     * @param evt
     */
    private void presenHasInitiativeButtonActionPerformed(ActionEvent evt) {
        this.CM = SimulationEngine.PULLING;        
    }  
    
    /**
     * 
     * @param evt
     */
    private void guiHasInitiativeButtonActionPerformed(ActionEvent evt) {
        this.CM = SimulationEngine.MEDIATING;        
    }
    
    /**
     * 
     * @param evt
     */
    private void pauseButtonActionPerformed(ActionEvent evt) {
    	Logging.turnOnLogging();
    	Logging.writeOut("Enter");
        if ("Pause".equals(pauseButton.getText())) {
            this.engine.pause(); 
        	Logging.writeOut("Changing Text to Unpause");
            pauseButton.setText("Unpause"); 
        } else {
            try {
                this.engine.unpause();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            Logging.writeOut("Changing Text to Pause");
            pauseButton.setText("Pause");
        }
        Logging.writeOut("Exit");
		Logging.turnOffLogging();
    }

    /**
     * @param evt
     * @throws GridSpacingException 
     */
    private void startButtonActionPerformed(ActionEvent evt) {
    	Logging.turnOnLogging();
    	Logging.writeOut("Enter");   	
        if ("Start".equals(startButton.getText()) && this.start().equals("")) {
            Logging.writeOut("Changing Text to Stop");
        	startButton.setText("Stop");
        	pauseButton.setEnabled(true);
        	enableWidgets(false);
           
        } else {
        	Logging.writeOut("Changing Text to Start");
            startButton.setText("Start");
            pauseButton.setEnabled(false);
            pauseButton.setText("Pause");
            enableWidgets(true);
            
            this.engine.stop();
            this.engine = null;
            // Dispose of viz panel
            switch(CM) {
            case SimulationEngine.MEDIATING:
                mvp.getFrame().dispose();
                break;
            case SimulationEngine.PULLING:            
                puvp.getFrame().dispose();
                break;
            case SimulationEngine.PUSHING:
                pvp.getFrame().dispose();
                break;
            }
        }
		Logging.writeOut("Exit");
		Logging.turnOffLogging();
    }
    
    /**
     * Enable or disable the user input widgets.
     */
    private void enableWidgets(boolean enable) {
        for(Enumeration<AbstractButton> e = initiativeButtonGroup.getElements(); e.hasMoreElements();) {
            e.nextElement().setEnabled(enable);
        }
        bufferSizeField.setEnabled(enable);
        bufferSizeLabel.setEnabled(enable);
        displayRateField.setEnabled(enable);
        displayRateLabel.setEnabled(enable);
        presenInOwnThread.setEnabled(enable);
        simInOwnThread.setEnabled(enable);
        minuteIntervalField.setEnabled(enable);
        gridSpaceList.setEnabled(enable);
        minuteIntervalLabel.setEnabled(enable);
        gridSpacingLabel.setEnabled(enable);
    }
    
    public void close(){
    	this.engine.stop();
        this.engine = null;
        // Dispose of viz panel
        switch(CM) {
        case SimulationEngine.MEDIATING:
            mvp.getFrame().dispose();
            mvp = null;
            break;
        case SimulationEngine.PULLING:            
            puvp.getFrame().dispose();
            puvp = null;
            break;
        case SimulationEngine.PUSHING:
            pvp.getFrame().dispose();
            pvp = null;
            break;
        }
        this.setVisible(false);
        this.dispose();
    }

}