/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import presentation.*;
import presentation.query.*;
import app.conf.BootStrap;
import controllers.AbstractControl;
import controllers.AbstractControlFactory;
import controllers.QueryControl;
import persistence.*;
import services.PersistenceService;
import simplesimulation.SimpleSimulationEngineImpl;
import simulation.SimulationSettings;
import simulation.SimulationSettingsFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import org.joda.time.DateTime;

import com.sun.org.apache.xml.internal.utils.UnImplNode;

import events.EventType;
import events.Listener;

public class QueryInterfaceUI extends javax.swing.JFrame implements 

ActionListener, ChangeListener, Listener {

   
	
	
	static final int WIDTH = 750;
	static final int HEIGHT = 220;

	static final String ACTION_RUN = "runQuery";
	

	static final double DEFAULT_AXIAL_TILT = 23.44;
	static final double DEFAULT_ORBITAL_ECCENTRICITY = .0167;
	private Date simStart, simEnd;
	private static final long serialVersionUID = -15968456987503L; 
	private double tilt = DEFAULT_AXIAL_TILT;
	// persistence service
	private PersistenceService ps;
	private static QueryInterfaceUI instance;
	int WIDTH_LABELS = WIDTH * 4 / 7 * 1
			/ 4;
	int WIDTH_EDITS = WIDTH * 4 / 7 * 3
			/ 4;
	int LABEL_HEIGHT = 26;
	private String simulationName = null;
	private double lat_start, lat_end, long_start, long_end, lat, longt;
	private boolean maxTemp, minTemp, meanTempRegion, meanTempTime, tempsTimeRegion;
	
	private double eccentricity = DEFAULT_ORBITAL_ECCENTRICITY;
	private boolean entireEarth;
	private QueryResult res;
	private QueryCell q1, q2;
	private AbstractControl control;
	private SimulationQuery simulationQuery;
	
	
	
	


	
	 /**
     * Creates new form QueryInterfaceUI  
     */
   private QueryInterfaceUI() {
    	createGui();
    	createControl();
    	this.setVisible(true);
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Query Interface");
        setName("QInterfaceFrame"); 
        //lat = 33.7784626;
    	//longt = -84.3988806;
		
		
		
		
        
    }
    
  //Singleton, similar to Gui.java
  	public static QueryInterfaceUI getInstance() {
  		if (instance == null) {
  			instance = new QueryInterfaceUI();
  		}
  		return instance;
  	}

  	public static void destroy() {
		if (instance != null) {
			instance = null;
		}
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

	private void createControl() {
		control = AbstractControlFactory.getInstance().createControl(AbstractControlFactory.Q);
				
		// register UI as listener for control events
		control.addListener(this);
	}

    private void createGui() {
    	
    	
    	this.getContentPane().setPreferredSize(
				new Dimension(800, 620));
    	int EDIT_BOX_WIDTH = 4;
		int LABEL_HEIGHT = 26;
    	
		firstPanel = new JPanel();
		firstPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.blue));
		firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.PAGE_AXIS));
		//firstPanel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT));
		QueryInterfaceLabel = new javax.swing.JLabel();
        tableOptionsLabel = new javax.swing.JLabel();
        qrybuttonGroup1 = new ButtonGroup();
        regionbuttonGroup = new ButtonGroup();
        runQuery = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        filter = new javax.swing.JButton();
        animate = new javax.swing.JButton();
        byNameButton = new JRadioButton();
        byPFButton = new JRadioButton();
        earthButton = new JRadioButton();
        parametersButton = new JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        simulationNameLabel = new javax.swing.JLabel();
        
        
       
        axisTiltLabel = new javax.swing.JLabel();
        axisTiltField = new javax.swing.JTextField(EDIT_BOX_WIDTH);
        orbitalEccentricityLabel = new javax.swing.JLabel();
        orbitalEccentricityField = new javax.swing.JTextField(EDIT_BOX_WIDTH);
        latitudeLabel = new javax.swing.JLabel();
        latitudeFromField = new javax.swing.JTextField(EDIT_BOX_WIDTH);
        jLabel10 = new javax.swing.JLabel();
        latitudeToField = new javax.swing.JTextField(EDIT_BOX_WIDTH);
        longitudeLabel = new javax.swing.JLabel();
        longitudeFromField = new javax.swing.JTextField(EDIT_BOX_WIDTH);
        jLabel11 = new javax.swing.JLabel();
        longitudeToField = new javax.swing.JTextField(EDIT_BOX_WIDTH);
        simulationPeriodLabel = new javax.swing.JLabel();
        simulationStartField = new javax.swing.JFormattedTextField(new SimpleDateFormat("mm/dd/yy"));
        simulationStartField.setColumns(5);
        simulationStartField.setActionCommand(getName());
        simulationStartField.setEnabled(false);
        
        latitudeFromField.setEnabled(false);
        latitudeToField.setEnabled(false);
        longitudeFromField.setEnabled(false);
        longitudeToField.setEnabled(false);
        
        jLabel9 = new javax.swing.JLabel();
        simulationEndField = new javax.swing.JFormattedTextField(new SimpleDateFormat("mm/dd/yy"));
        simulationEndField.setColumns(5);
        simulationEndField.setEnabled(false);
        
        simulationEndField.setActionCommand(getName());
        jPanel2 = new javax.swing.JPanel();
        minTempLabel = new javax.swing.JLabel();
        minTempCheckbox = new javax.swing.JCheckBox();
        regionMeanTempLabel = new javax.swing.JLabel();
        meanTempRegionCheckbox = new javax.swing.JCheckBox();
        maxTempLabel = new javax.swing.JLabel();
        maxTempRegionCheckbox = new javax.swing.JCheckBox();
        timeMeanTempLabel = new javax.swing.JLabel();
        meanTempTimeCheckbox = new javax.swing.JCheckBox();
        tempTimeRegionLabel = new javax.swing.JLabel();
        tempsTimeRegionCheckbox = new javax.swing.JCheckBox();
        
        QueryInterfaceLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        QueryInterfaceLabel.setText("Query Interface");
        
        

		
		//Top Panel
        JPanel topPanel = new JPanel();
        //topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		topPanel.setLayout(new BorderLayout()); 
		topPanel.setPreferredSize(new Dimension(200, HEIGHT-100));
		
    	qrybuttonGroup1.add(byNameButton); 
        byNameButton.setText("Name");
        byNameButton.setActionCommand("byName");
        byNameButton.addActionListener(this);
        qrybuttonGroup1.add(byPFButton);
        byPFButton.setText("Physical Factors");
        byPFButton.setActionCommand("byPF");
        byPFButton.addActionListener(this);
       

        simulationNameLabel.setText("Simulation name: ");
        
        nameSpinner = new JComboBox();
               
       
        nameSpinner.setName("Name");
        nameSpinner.setSize(EDIT_BOX_WIDTH, LABEL_HEIGHT);
        nameSpinner.addActionListener(this);
        nameSpinner.setActionCommand(getName());
        
        JPanel namePFPanel = new JPanel();
        namePFPanel.setLayout(new BoxLayout(namePFPanel, BoxLayout.PAGE_AXIS)); 
        namePFPanel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT-100));
        
        
        
        
        JPanel namePFRadioPanel = new JPanel();
        namePFRadioPanel.setLayout(new BoxLayout(namePFRadioPanel, BoxLayout.LINE_AXIS)); 
       
        Border nameBorder = BorderFactory.createTitledBorder("Search by ");
        namePFRadioPanel.add(byNameButton);
        namePFRadioPanel.add(byPFButton);
        namePFRadioPanel.setBorder(nameBorder);
        
        
        namePFPanel.add(namePFRadioPanel);
        namePFvaluePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePFvaluePanel.setPreferredSize(new Dimension(300, HEIGHT-100));
        
       
        Border valueBorder = BorderFactory.createTitledBorder("Matching Simulations ");
        namePFvaluePanel.setBorder(valueBorder);
        
        //nameSpinner.setMaximumSize(new Dimension(EDIT_BOX_WIDTH, 2));
        nameSpinner.setPreferredSize(new Dimension(250,25));
        nameSpinner.setLightWeightPopupEnabled(true);
		//nameSpinner.setPopupVisible(true);
		//nameSpinner.showPopup();
        

        namePFvaluePanel.add(simulationNameLabel);
        namePFvaluePanel.add(nameSpinner);
       

        //nameSpinner.setMaximumSize(new Dimension(10, 1));
        System.out.println("Combobox dimension: "+ nameSpinner.getSize());
      
       
     
        //namePFvaluePanel.setBorder(valueBorder);
        namePFPanel.add(namePFvaluePanel );
        
        
       
        topPanel.add(namePFPanel, BorderLayout.WEST);
        
        
        JPanel options2Panel = new JPanel();
        options2Panel.setLayout(new BoxLayout(options2Panel, BoxLayout.PAGE_AXIS));
        options2Panel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT-100));
        JPanel PFPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        Border pfBorder = BorderFactory.createTitledBorder("Physical factors ");
        PFPanel.setBorder(pfBorder);
        
        axisTiltLabel.setText("Axial tilt");
        orbitalEccentricityLabel.setText("Orbital eccentricity");
        axisTiltField.setEnabled(false);
        orbitalEccentricityField.setEnabled(false);
        PFPanel.add(axisTiltLabel);
        PFPanel.add(axisTiltField);
      
        PFPanel.add(orbitalEccentricityLabel);
        PFPanel.add(orbitalEccentricityField);
        
        options2Panel.add(PFPanel, BorderLayout.NORTH);
        
        
        
        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Border periodBorder = BorderFactory.createTitledBorder("Simulation Time Period ");
       
        periodPanel.setBorder(periodBorder);
        simulationStartField.setEnabled(false);
        simulationEndField.setEnabled(false);
        
        periodPanel.add(new JLabel("Start time:"));
        periodPanel.add(simulationStartField);
        periodPanel.add(new JLabel("End time:"));
        periodPanel.add(simulationEndField);
        periodPanel.add(new JLabel("                               "));
        
        simulationStartField.setAction(simulationStartField.getAction());
        simulationEndField.setAction(simulationEndField.getAction());
       
        options2Panel.add(periodPanel, BorderLayout.SOUTH);        
        topPanel.add(options2Panel, BorderLayout.EAST);
        
        
        firstPanel.add(topPanel, BorderLayout.NORTH);
        
        
        //Middle panel
        //physical bounds entire earth or use lat/long provided
        JPanel regionPanel = new JPanel();
        regionPanel.setLayout(new BoxLayout(regionPanel, BoxLayout.PAGE_AXIS));
        Border regionoptionBorder = BorderFactory.createTitledBorder("Physical bounds ");
        regionPanel.setBorder(regionoptionBorder);
        regionPanel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT-50));
        JPanel regionoptionPanel = new JPanel();
        regionoptionPanel.setLayout(new BoxLayout(regionoptionPanel, BoxLayout.LINE_AXIS));
        
        
        regionbuttonGroup.add(earthButton); 
        earthButton.setText("Whole Earth");
        earthButton.setActionCommand("earth");
        earthButton.addActionListener(this);
        regionbuttonGroup.add(parametersButton);
        parametersButton.setText("Use parameters provided");
        parametersButton.setActionCommand("parameters");
        parametersButton.addActionListener(this);
       
        
        regionoptionPanel.add(earthButton);       
        regionoptionPanel.add(parametersButton);        
        regionPanel.add(regionoptionPanel);
        
        
        //get latitude/longitude info
        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
                
        latitudeFromField.setEnabled(false);
        latitudeToField.setEnabled(false);
        longitudeFromField.setEnabled(false);
        longitudeToField.setEnabled(false);
        latitudeLabel.setText("Latitude");
        locationPanel.add(latitudeLabel);
        locationPanel.add(new JLabel("Start"));
        locationPanel.add(latitudeFromField);
        locationPanel.add(new JLabel("End"));
        locationPanel.add(latitudeToField);
        
        longitudeLabel.setText("Longitude");
        locationPanel.add(longitudeLabel);
        locationPanel.add(new JLabel("Start"));
        locationPanel.add(longitudeFromField);
        locationPanel.add(new JLabel("End"));
        locationPanel.add(longitudeToField);
        
       
        filter.setText("Filter");
        filter.addActionListener(this);
		filter.setEnabled(true); 
        filter.setActionCommand("filter");
       
        
        
        
        regionPanel.add(new JLabel("   "));
        regionPanel.add(locationPanel);
        regionPanel.add(filter);
        
        firstPanel.add(regionPanel, BorderLayout.CENTER);
        
        //Options panel
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT+50));
        Border optionsBorder = BorderFactory.createTitledBorder("Include results for");
        optionsPanel.setBorder(optionsBorder);
        
        minTempLabel.setText("Minumum temperature");
        regionMeanTempLabel.setText("Mean temperature over region");
        maxTempLabel.setText("Maximum temperature");
        timeMeanTempLabel.setText("Mean temperature over time");
        tempTimeRegionLabel.setText("Grid cell temperatures");
        
     
        
        
        JPanel optionlabelsPanel = new JPanel();
        optionlabelsPanel.setLayout(new BoxLayout(optionlabelsPanel, BoxLayout.PAGE_AXIS));
        //optionlabelsPanel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT));
        JPanel optionchkboxPanel = new JPanel();
        
        optionchkboxPanel.setLayout(new BoxLayout(optionchkboxPanel, BoxLayout.PAGE_AXIS));
        //optionchkboxPanel.setPreferredSize(new Dimension(WIDTH_EDITS, HEIGHT));
        optionlabelsPanel.add(minTempLabel);
        optionlabelsPanel.add(new JLabel("     "));
        optionlabelsPanel.add(regionMeanTempLabel);
        optionlabelsPanel.add(new JLabel("     "));
        optionlabelsPanel.add(maxTempLabel);
        optionlabelsPanel.add(new JLabel("     "));
        optionlabelsPanel.add(timeMeanTempLabel);
        optionlabelsPanel.add(new JLabel("     "));
        optionlabelsPanel.add(tempTimeRegionLabel);
        
        
        optionchkboxPanel.add(minTempCheckbox);
        optionchkboxPanel.add(new JLabel("     "));
        optionchkboxPanel.add(meanTempRegionCheckbox);
        optionchkboxPanel.add(new JLabel("     "));
        optionchkboxPanel.add(maxTempRegionCheckbox);
        optionchkboxPanel.add(new JLabel("     "));
        optionchkboxPanel.add(meanTempTimeCheckbox);
        optionchkboxPanel.add(new JLabel("     "));
        optionchkboxPanel.add(tempsTimeRegionCheckbox);

        JPanel runresetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        runresetPanel.setPreferredSize(new Dimension(WIDTH_EDITS, 250));
        runQuery.setText("Run Query");
        runQuery.addActionListener(this);
		runQuery.setEnabled(true); 
        runQuery.setActionCommand(ACTION_RUN);
        runresetPanel.add(new JLabel("                  "));
        runresetPanel.add(runQuery);
        reset.setText("Reset");
        reset.addActionListener(this);
        reset.setEnabled(true); 
        reset.setActionCommand("reset");
        animate.setText("Animate");
        animate.addActionListener(this);
		animate.setEnabled(true); 
        animate.setActionCommand("animate");
        
        
        
        runresetPanel.add(reset);
        runresetPanel.add(animate);
        
        optionsPanel.add(optionlabelsPanel);
        optionsPanel.add(optionchkboxPanel);
        optionsPanel.add(runresetPanel);
       
        firstPanel.add(optionsPanel, BorderLayout.SOUTH);

        
        simulationEndField.addActionListener(this);
        
         
        mainPanel.add(firstPanel, BorderLayout.WEST);
        this.getContentPane().add(firstPanel);
        pack();
    }

         
    
    public JPanel createOutputGui()
    {
    	
    	this.getContentPane().setPreferredSize( new Dimension(1320, 620));
    	int EDIT_BOX_WIDTH = 4;
		int LABEL_HEIGHT = 26;
		
    	JPanel Panel = new JPanel();
        
    	//To make content scroll, these dimension should be set to (520, 100020)
		Panel.setLayout(new BoxLayout(Panel, BoxLayout.PAGE_AXIS));
		Panel.setPreferredSize(new Dimension(520, 100020));
		
		
		
		 if(minTempCheckbox.isSelected())
		 {
			 minTempLabel = new javax.swing.JLabel("1.Minimum temperature for region: " );
		
			 //get data for the above labels
		
			 q1 = res.getMinTempCell();
		
	
		
		
			 Panel.add(minTempLabel);
			 Panel.add(new JLabel("Min Temperature: "+q1.getTemperature()+""));
			 Panel.add(new JLabel("Reading Time: "+q1.getSimulatedDate()+""));
			 Panel.add(new JLabel("Location(latitude/longitude): "+q1.getLatitude()+"/"+q1.getLongitude()));
			 Panel.add(new JLabel("****************"));
		
		 }
		 if(maxTempRegionCheckbox.isSelected())
		 {
		 
			 q2 = res.getMaxTempCell();
			 maxTempLabel = new javax.swing.JLabel("2. Maximum Temperature for region: ");
		
		
			 Panel.add(maxTempLabel);
			 Panel.add(new JLabel("Max Temperature: "+ q2.getTemperature()+""));
			 Panel.add(new JLabel("Reading Time:" + q2.getSimulatedDate()+""));
			 Panel.add(new javax.swing.JLabel("Location(latitude/longitude): "+ q2.getLatitude()+"/"+q2.getLongitude()));
			 Panel.add(new JLabel("****************"));
		 }
		 if(meanTempRegionCheckbox.isSelected())
		 {
			 
		 	 regionMeanTempLabel = new javax.swing.JLabel("Mean Temperature over region: ");
			 regionMeanTempLabel2 = new JLabel("Date, Temperature");
		
			 Panel.add(regionMeanTempLabel);
			 Panel.add(regionMeanTempLabel2);
		
			 List<QueryCell> meanQcell1 = res.getMeanTempOverRegion();
			 System.out.println("Mean temp over region query cell is empty?"+ meanQcell1.isEmpty());
			 for(QueryCell qc1 : meanQcell1)
			 {
				 Panel.add(new JLabel(qc1.getSimulatedDate()+"         " +qc1.getTemperature()));
			
			 }
			 Panel.add(new JLabel("****************"));
		}
		if(meanTempTimeCheckbox.isSelected())
		{
			
			timeMeanTempLabel = new javax.swing.JLabel("Mean Temperature during over time:");
			timeMeanTempLabel2 = new JLabel("Latitude/Longitude, Temperature");
		
			Panel.add(timeMeanTempLabel);
			Panel.add(timeMeanTempLabel2);
		
			List<QueryCell> meanQcell2 = res.getMeanTempOverTime();
			for(QueryCell qc2 : meanQcell2)
			{
				Panel.add(new JLabel(qc2.getLatitude()+"/"+qc2.getLongitude() + "    " + qc2.getTemperature()));
				Panel.add(new JLabel("                                            "));
			}
			Panel.add(new JLabel("****************"));
			
		}
		if(tempsTimeRegionCheckbox.isSelected())
		{
				
			JPanel gridPanel = new JPanel(new BorderLayout());
			gridPanel.setPreferredSize(new Dimension(WIDTH_EDITS, 400));
		
		
			tempTimeRegionLabel = new javax.swing.JLabel("Grid Temperatures:");
			Panel.add(tempTimeRegionLabel);
			Panel.add(new JLabel("                                                                         "));
			//get data from each Grid
		
			List<QueryGrid> qryGrids = res.getQueryGrids();
			int gridcount =0;
			for(QueryGrid qg : qryGrids)
			{
				gridcount++;
				tempTimeRegionLabel1 = new javax.swing.JLabel("== Grid "+ gridcount+ " =="+ qg.getSimulatedDate());
		
				Panel.add(tempTimeRegionLabel1);
				Panel.add(new JLabel("                                         "));
				Panel.add(new JLabel("Lat/Long,               Temperature"));
				List<QueryCell> qcells = qg.getQueryCells();
				for (QueryCell qcs : qcells)
				{
					Panel.add(new JLabel(qcs.getLatitude() + "/" + qcs.getLongitude() + "         " +qcs.getTemperature()+ " "));
				
				}
			}
		
		}
		
		System.out.println("Inside createOutputGui method");	
       
	   
	     pack();
	   
	     return Panel;
    	
    }

    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
    public void launchNewQueryInterface() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code  (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the 

default look and feel.
         * For details see 

http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : 

javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName

()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName

()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName

()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName

()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QueryInterfaceUI().setVisible(true);
               
                
            }
        });
    }

    
    //set simulation start and end time.
    
    public void setSimulationPeriod(String start, String end)
    {
    	 SimpleDateFormat dtf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss.SSS");
    	 try{
    		 
    		 System.out.println("Sim start: " + simulationStartField.getText());
        	 System.out.println("Sim end: "+ simulationEndField.getText());
    	 simStart = dtf.parse(simulationStartField.getText());
    	 simEnd = dtf.parse(simulationEndField.getText());
    	 System.out.println("Sim start: " + simStart);
    	 System.out.println("Sim end: "+ simEnd);
    	 }
    	 catch(Exception e)
    	 {
    		 System.out.println("Error parsing date time");
    	 }
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		String sname = (String)nameSpinner.getSelectedItem();
		nameSpinner.setToolTipText(sname);
		
		
		if (command.equals("byName"))
		{
			
			this.setEnableAllUserOptions(false);
			List<String> lst = new ArrayList<String>();
			lst = ((QueryControl)control).getSimulationList();
			System.out.println(lst.isEmpty());
			
			for(String s : lst)
			{
				System.out.println("from database: "+s);
				nameSpinner.addItem(s);
				
			}
			
     	}
		else if(command.equals("byPF"))
		{
			
	        this.setEnableAllUserOptions(true);
	        
		}
		else if(command.equals("earth"))
		{
			entireEarth = true;
			latitudeFromField.setEnabled(false);
	         latitudeToField.setEnabled(false);
	         longitudeFromField.setEnabled(false);
	         longitudeToField.setEnabled(false);
			
		}
		else if(command.equals("parameters"))
		{
			latitudeFromField.setEnabled(true);
	        latitudeToField.setEnabled(true);
	        longitudeFromField.setEnabled(true);
	        longitudeToField.setEnabled(true);
	       
	        
		}
		else if (command.equals(ACTION_RUN)) {
			
			
			
			simulationName=(String)nameSpinner.getSelectedItem();
			System.out.println("Selected Simulation "+ simulationName);
			//disable all controls
			this.setEnableAllUserOptions(false);
			//disable the run button
			runQuery.setEnabled(false);
			

			
			//call query controller here
			//use case 1
			if(byNameButton.isSelected())
			{
				if(!simulationName.isEmpty())
					res = ((QueryControl)control).getQueryResultBySimulationName(simulationName);
				
				displayQueryResults();
			    

				
			}
			//use case 2
			if(byPFButton.isSelected())
			{
				simulationQuery = new SimulationQuery(simulationName, 
						tilt, eccentricity, simStart, 
						simEnd, lat_start, lat_end,
						long_start,long_end);
				res = ((QueryControl)control).computeQueryResults(simulationQuery);
				
				if(res == null){
					SimulationSettings settings = SimulationSettingsFactory.createSimulationSettingsWithDefaults();
					settings.setName(simulationName);
					settings.setAxialTilt(tilt);
					settings.setEccentricity(eccentricity);
					settings.setSimulationLength(control.getSimulationMonths(simEnd));
					AbstractControl.setSimulationEngine(new SimpleSimulationEngineImpl(settings));
					control.runSimulation(settings);

				}
				else{
					displayQueryResults();
				}
					
			}
				

			
			

			
	
	    
		}
		else if(command.equals("reset"))
		{
		
			
			//remove items from comboBox, clear Radio Buttons.
			nameSpinner.removeAllItems();
			if(byNameButton.isSelected())
			{
				byNameButton.setSelected(false);
			}
			if(byPFButton.isSelected())
			{
				byPFButton.setSelected(false);
			}
			if(earthButton.isSelected())
			{
				earthButton.setSelected(false);
			}
			if(parametersButton.isSelected())
			{
				parametersButton.setSelected(false);
			}
			setEnableAllUserOptions(false);
			minTempCheckbox.setSelected(false);
	        meanTempRegionCheckbox.setSelected(false);
	        maxTempRegionCheckbox.setSelected(false);
	        meanTempTimeCheckbox.setSelected(false);
	        tempsTimeRegionCheckbox.setSelected(false);
		}
		else if(command.equals("filter"))
		{
			//if name radio button was selected before and nameSpinner was populated with values, this 
			//line will clear that list and add simulation names that match filter criteria
			
			if(byPFButton.isSelected())
			{
				
			nameSpinner.removeAllItems();
			
			try
			{
				lat_start = Double.parseDouble(latitudeFromField.getText());
	         	lat_end = Double.parseDouble(latitudeToField.getText());
	         	long_start = Double.parseDouble(longitudeFromField.getText());
	         	long_end = Double.parseDouble(longitudeToField.getText());
				
			}
			catch(Exception pe)
			{
				System.out.println("Error parsing lat/long");
				System.out.println(pe);
				
			}
			try
			{
				tilt = Double.parseDouble(axisTiltField.getText());
    	        eccentricity = Double.parseDouble(orbitalEccentricityField.getText());
			}
			catch(Exception pe)
			{
				System.out.println("Error parsing axis and tilt");
				System.out.println(pe);
				
			}
			
	        
	        //get data from fields
	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS"); 
            try
            {
            	System.out.println(simulationEndField.getText());
//    	        simStart = df.parse(simulationStartField.getText());
//    	        simEnd = df.parse(simulationEndField.getText());
    	        simStart = new DateTime(2014, 01, 04, 12, 00).toDate();
    	        simEnd = new DateTime(2014, 01, 15, 12, 00).toDate();
    	        
            }
            catch(Exception pe)
            {
            	System.out.println("Incorrect date format");
            	System.out.println(pe.toString());
            	
            }
			
            
            //filter simulations in the db based on above values.
			List<String> lst = new ArrayList<String>();
			try
			{
				
				System.out.println("Axis, tilt and end date "+ tilt +" " +  eccentricity + " "+ simEnd);
				lst = ((QueryControl)control).getSimulationListByUserInputs(tilt, eccentricity,simEnd);
				for(String s : lst)
				{
					nameSpinner.addItem(s);
					
				}
			}
			catch(Exception pe)
			{
				System.out.println("Empty parameter value");
			}
			}
			
			Border valueBorder = BorderFactory.createTitledBorder("Filtered Results ");
			namePFvaluePanel.setBorder(valueBorder);
			
			
		}
		else if(command.equals(simulationEndField.getAction()))
		{
		 
			setSimulationPeriod(simulationStartField.getText(),simulationEndField.getText());
		}
		else if(command.equals("animate"))
		{
			
		}
		else if(command.equals(nameSpinner.getAction()))
		{
			
			
		}
    }

	private void displayQueryResults() {
		JScrollPane outputScroller = new JScrollPane();
		JPanel newoutput = new JPanel(new BorderLayout());
		newoutput = createOutputGui();
		
		JViewport vw = outputScroller.getViewport();
		outputScroller.getViewport().add(newoutput);
		this.getContentPane().setPreferredSize(new Dimension(1320, 620));
		this.getContentPane().add(outputScroller, BorderLayout.EAST);
		this.pack();
		this.setVisible(true);
		System.out.println(outputScroller.getViewport().getExtentSize());
	}
    
    public void stateChanged(ChangeEvent e) {
        SpinnerListModel sModel = (SpinnerListModel) nameSpinner.getModel();
        if (sModel instanceof SpinnerListModel) {
            
        }
    }

    
   
  //enable/disable all controls
  	private void setEnableAllUserOptions(boolean bEnable) {
  	
  		
  		
  		 axisTiltField.setEnabled(bEnable);
         orbitalEccentricityField.setEnabled(bEnable);
         latitudeFromField.setEnabled(bEnable);
         latitudeToField.setEnabled(bEnable);
         longitudeFromField.setEnabled(bEnable);
         longitudeToField.setEnabled(bEnable);
         simulationStartField.setEnabled(bEnable);
         simulationEndField.setEnabled(bEnable);
         earthButton.setEnabled(bEnable);
         parametersButton.setEnabled(bEnable);
         filter.setEnabled(bEnable);

  	}

  	
  	public void clear()
  	{
  		//simulationNameField.repaint();
  		axisTiltField.setText("");
        orbitalEccentricityField.setText("");
        latitudeFromField.setText("");
        latitudeToField.setText("");
        longitudeFromField.setText("");
        longitudeToField.setText("");
        simulationStartField.setText("");
        simulationEndField.setText("");
        minTempCheckbox.setSelected(false);
        meanTempRegionCheckbox.setSelected(false);
        maxTempRegionCheckbox.setSelected(false);
        meanTempTimeCheckbox.setSelected(false);
        tempsTimeRegionCheckbox.setSelected(false);
        
  	}
   
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QueryInterfaceLabel;
    private javax.swing.JLabel axisTiltLabel;

    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1, jPanel2;
    private javax.swing.JPanel firstPanel;
    private javax.swing.JTextField latitudeFromField;
    private javax.swing.JTextField axisTiltField;
    private javax.swing.JLabel latitudeLabel;
    private javax.swing.JTextField latitudeToField;
    private javax.swing.JTextField longitudeFromField;
    private javax.swing.JLabel longitudeLabel;
    private javax.swing.JTextField longitudeToField;
    private javax.swing.JLabel maxTempLabel;
    private javax.swing.JCheckBox maxTempRegionCheckbox;
    private javax.swing.JCheckBox meanTempRegionCheckbox;
    private javax.swing.JCheckBox meanTempTimeCheckbox;
    private javax.swing.JCheckBox minTempCheckbox;
    private javax.swing.JLabel minTempLabel;
    private javax.swing.JTextField orbitalEccentricityField;
    private javax.swing.JLabel orbitalEccentricityLabel;
    private javax.swing.JLabel regionMeanTempLabel;
    private javax.swing.JButton runQuery;
    private javax.swing.JButton filter;
    private javax.swing.JButton reset;
    private javax.swing.JButton animate;
    private javax.swing.JFormattedTextField simulationEndField;
    private javax.swing.JTextField simulationNameField;
    private javax.swing.JLabel simulationNameLabel;
    private javax.swing.JLabel simulationPeriodLabel;
    private javax.swing.JFormattedTextField simulationStartField;
    private javax.swing.JLabel tableOptionsLabel;
    private javax.swing.JLabel tempTimeRegionLabel;
    private javax.swing.JCheckBox tempsTimeRegionCheckbox;
    private javax.swing.JLabel timeMeanTempLabel;
    private ButtonGroup qrybuttonGroup1;
    private JRadioButton byNameButton;
    private JRadioButton byPFButton;
    private ButtonGroup regionbuttonGroup;
    private JRadioButton earthButton;
    private JRadioButton parametersButton;
    private JComboBox nameSpinner;
    private JTable outputTable;
    private JLabel spacer, latStart, latEnd, longStart, longEnd;
    private JLabel minTempLabel1,readingTimeLabel,locationLabel,maxTempLabel1,timeMeanTempLabel2,regionMeanTempLabel2,tempTimeRegionLabel1;
    private JPanel mainPanel = new JPanel();
    private JPanel namePFvaluePanel;
    private JLabel comboxLabel;







	@Override
	public void notify(EventType e) {
		// TODO: Implement 
		if(e == EventType.SimulationFinishedEvent){
			SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							res = ((QueryControl)control).computeQueryResults(simulationQuery);
							if(res == null)
								throw new RuntimeException("The query result should not be null at this point.");

							displayQueryResults();
						}
					});
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
    
    // End of variables declaration//GEN-END:variables
    
           
}


