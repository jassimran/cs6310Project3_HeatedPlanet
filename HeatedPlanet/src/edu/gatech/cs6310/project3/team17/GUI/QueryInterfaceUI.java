/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.cs6310.project3.team17.GUI;

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
import java.util.*;

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
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import services.PersistenceService;

public class QueryInterfaceUI extends javax.swing.JFrame implements ActionListener, ChangeListener {

   
	
	
	static final int WIDTH = 800;
	static final int HEIGHT = 220;

	static final String ACTION_RUN = "runQuery";
	

	static final double DEFAULT_AXIAL_TILT = 23.44;
	static final double DEFAULT_ORBITAL_ECCENTRICITY = .0167;
	private Date simStart, simEnd;
	private static final long serialVersionUID = -15968456987503L; 
	private double tilt;
	// persistence service
	private PersistenceService persistenceService;
	int WIDTH_LABELS = WIDTH * 4 / 7 * 1
			/ 4;
	int WIDTH_EDITS = WIDTH * 4 / 7 * 3
			/ 4;
	int LABEL_HEIGHT = 26;


	
	 /**
     * Creates new form QueryInterfaceUI
     */
    public QueryInterfaceUI() {
        
    	persistenceService = PersistenceService.getInstance();
    	initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        QueryInterfaceLabel = new javax.swing.JLabel();
        tableOptionsLabel = new javax.swing.JLabel();
        runQuery = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        simulationNameLabel = new javax.swing.JLabel();
        simulationNameField = new javax.swing.JTextField(20);
        axisTiltSlider = new javax.swing.JSlider();
        axisTiltLabel = new javax.swing.JLabel();
        orbitalEccentricityLabel = new javax.swing.JLabel();
        orbitalEccentricityField = new javax.swing.JTextField();
        latitudeLabel = new javax.swing.JLabel();
        latitudeFromField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        latitudeToField = new javax.swing.JTextField();
        longitudeLabel = new javax.swing.JLabel();
        longitudeFromField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        longitudeToField = new javax.swing.JTextField();
        simulationPeriodLabel = new javax.swing.JLabel();
        
       
        simulationStartField = new javax.swing.JFormattedTextField();
        simulationStartField.setActionCommand(getName());
        
        jLabel9 = new javax.swing.JLabel();
        simulationEndField = new javax.swing.JFormattedTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QueryInterface");
        setName("QInterfaceFrame"); // NOI18N

        QueryInterfaceLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        QueryInterfaceLabel.setText("Query Interface");

        tableOptionsLabel.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        tableOptionsLabel.setText("Include following data in the result set");

        runQuery.setText("Run Query");
        runQuery.addActionListener(this);
		runQuery.setEnabled(true); 
        runQuery.setActionCommand(ACTION_RUN);

        simulationNameLabel.setLabelFor(simulationNameField);
        simulationNameLabel.setText("Name of the simulation");
        simulationNameLabel.setName("simulationNameLabel"); // NOI18N

        

        axisTiltSlider.setMajorTickSpacing(60);
        axisTiltSlider.setMaximum(180);
        axisTiltSlider.setMinimum(-180);
        axisTiltSlider.setMinorTickSpacing(30);
        axisTiltSlider.setPaintLabels(true);
        axisTiltSlider.setPaintTicks(true);

        axisTiltLabel.setText("Axial tilt");
        axisTiltSlider.setPreferredSize(new Dimension(15, 75));
        
        orbitalEccentricityLabel.setText("Orbital eccentricity");
        latitudeLabel.setText("Latitude");
        jLabel10.setText("to");
        longitudeLabel.setText("Longitude");
        jLabel11.setText("to");
        simulationPeriodLabel.setText("Simulation period");
        jLabel9.setText("to");
        simulationStartField.setAction(simulationStartField.getAction());
        simulationEndField.setAction(simulationEndField.getAction());
        /*latitudeToField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                latitudeToFieldActionPerformed(evt);
            }
        });
        longitudeFromField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                longitudeFromFieldActionPerformed(evt);
            }
        });
        longitudeToField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                longitudeToFieldActionPerformed(evt);
            }
        });
        simulationNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulationNameFieldActionPerformed(evt);
            }
        });

        orbitalEccentricityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orbitalEccentricityFieldActionPerformed(evt);
            }
        });

        
        latitudeFromField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                latitudeFromFieldActionPerformed(evt);
            }
        });*/

        
        simulationEndField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //simulationEndFieldActionPerformed(evt);
            	String cmd = evt.getActionCommand();
            	if(cmd.equals(simulationEndField.getAction()))
            	setSimulationPeriod(simulationStartField.getText(),simulationEndField.getText());
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(axisTiltLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(simulationNameLabel)
                        .addGap(49, 49, 49)
                        .addComponent(simulationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(50, Short.MAX_VALUE)
                        .addComponent(axisTiltSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(simulationPeriodLabel)
                            .addComponent(orbitalEccentricityLabel)
                            .addComponent(latitudeLabel)
                            .addComponent(longitudeLabel))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(longitudeFromField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(orbitalEccentricityField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(latitudeFromField, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                    .addComponent(simulationStartField))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel9))
                                        .addGap(48, 48, 48)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(longitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(simulationEndField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(48, 48, 48)
                                        .addComponent(latitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simulationNameLabel)
                    .addComponent(simulationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(axisTiltSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(axisTiltLabel))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orbitalEccentricityLabel)
                    .addComponent(orbitalEccentricityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(latitudeLabel)
                        .addComponent(latitudeFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(latitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(longitudeLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(longitudeFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(longitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simulationPeriodLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(simulationEndField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(simulationStartField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        minTempLabel.setText("Minumum temperature in the region selected");

        regionMeanTempLabel.setText("Mean temperature for the region selected");

        maxTempLabel.setText("Maximum temperature in the region selected");

        timeMeanTempLabel.setText("Mean temperature for the requested time period");

        tempTimeRegionLabel.setText("Temperatures for the requested region over the time period selected");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(minTempLabel)
                            .addComponent(maxTempLabel)
                            .addComponent(regionMeanTempLabel)
                            .addComponent(timeMeanTempLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(minTempCheckbox)
                            .addComponent(meanTempRegionCheckbox)
                            .addComponent(maxTempRegionCheckbox)
                            .addComponent(meanTempTimeCheckbox))
                        .addGap(48, 48, 48))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tempTimeRegionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tempsTimeRegionCheckbox)
                        .addContainerGap(48, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(minTempCheckbox)
                            .addComponent(minTempLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(maxTempLabel))
                    .addComponent(meanTempRegionCheckbox))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(regionMeanTempLabel)
                    .addComponent(maxTempRegionCheckbox))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(timeMeanTempLabel)
                    .addComponent(meanTempTimeCheckbox))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tempTimeRegionLabel)
                    .addComponent(tempsTimeRegionCheckbox))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(runQuery))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(QueryInterfaceLabel)
                            .addComponent(tableOptionsLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(QueryInterfaceLabel)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(tableOptionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(runQuery)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

                                      

    /**
     * @param args the command line arguments
     */
    public void launchQueryInterface() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QueryInterfaceUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    	 SimpleDateFormat dtf 
         = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss.SSS");
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
		//get the command
		String command = e.getActionCommand();
		

		if (command.equals(ACTION_RUN)) {
			
			
			
			
			//disable all controls
			//this.setEnableAllUserOptions(false);
			//disable the run button
			//runQuery.setEnabled(false);
			
			//call persisitence controller here
			runQuery();
		}
    }
    
   
  //enable/disable all controls
  	private void setEnableAllUserOptions(boolean bEnable) {
  	
  		
  		
         runQuery.setEnabled(bEnable);
         simulationNameField.setEnabled(bEnable);
         axisTiltSlider.setEnabled(bEnable);
         orbitalEccentricityField.setEnabled(bEnable);
         latitudeFromField.setEnabled(bEnable);
         latitudeToField.setEnabled(bEnable);
         longitudeFromField.setEnabled(bEnable);
         longitudeToField.setEnabled(bEnable);
         simulationStartField.setEnabled(bEnable);
         simulationEndField.setEnabled(bEnable);
         minTempCheckbox.setEnabled(bEnable);
         meanTempRegionCheckbox.setEnabled(bEnable);
         maxTempRegionCheckbox.setEnabled(bEnable);
         meanTempTimeCheckbox.setEnabled(bEnable);
         tempsTimeRegionCheckbox.setEnabled(bEnable);

  	}

    //call persistence controller and execute the query. Create output ui 
  	//to display results
    public void runQuery()
    {
    	//for now it just prints all the values to console.
    	
    	System.out.println("Name: "+ simulationNameField.getText());
    	System.out.println("Axial tilt: "+ axisTiltSlider.getValue());
    	System.out.println("ecc: "+ orbitalEccentricityField.getText());
    	System.out.println("latitude: "+ latitudeFromField.getText() + " to " + latitudeToField.getText());
    	System.out.println("longitude: "+ longitudeFromField.getText() + " to " + longitudeToField.getText());
    	System.out.println("simulation: "+ simulationStartField.getText() + " to " + simulationEndField.getText());
    	System.out.println("Min Temp "+ minTempCheckbox.isSelected());
    	System.out.println("Max Temp"+ maxTempRegionCheckbox.isSelected());
    	System.out.println("Regional avg temp "+ meanTempRegionCheckbox.isSelected());
    	System.out.println("Avg temp for the period "+ meanTempTimeCheckbox.isSelected() );
    	
    }
    
    public void stateChanged(ChangeEvent e) {

	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QueryInterfaceLabel;
    private javax.swing.JLabel axisTiltLabel;
    private javax.swing.JSlider axisTiltSlider;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField latitudeFromField;
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
    private javax.swing.JTextField simulationEndField;
    private javax.swing.JTextField simulationNameField;
    private javax.swing.JLabel simulationNameLabel;
    private javax.swing.JLabel simulationPeriodLabel;
    private javax.swing.JTextField simulationStartField;
    private javax.swing.JLabel tableOptionsLabel;
    private javax.swing.JLabel tempTimeRegionLabel;
    private javax.swing.JCheckBox tempsTimeRegionCheckbox;
    private javax.swing.JLabel timeMeanTempLabel;
    // End of variables declaration//GEN-END:variables
}

