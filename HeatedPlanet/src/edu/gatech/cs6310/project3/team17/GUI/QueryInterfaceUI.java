/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PlanetSim;

/**
 *
 * @author Subham
 */
public class QueryInterfaceUI extends javax.swing.JFrame {

    /**
     * Creates new form QueryInterfaceUI
     */
    public QueryInterfaceUI() {
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
        simulationNameLabel = new javax.swing.JLabel();
        simulationNameField = new javax.swing.JTextField();
        axisTiltLabel = new javax.swing.JLabel();
        orbitalEccentricityLabel = new javax.swing.JLabel();
        axisTiltSlider = new javax.swing.JSlider();
        orbitalEccentricityField = new javax.swing.JTextField();
        latitudeLabel = new javax.swing.JLabel();
        longitudeLabel = new javax.swing.JLabel();
        simulationPeriodLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        simulationStartField = new javax.swing.JTextField();
        simulationEndField = new javax.swing.JTextField();
        latitudeFromField = new javax.swing.JTextField();
        latitudeToField = new javax.swing.JTextField();
        longitudeFromField = new javax.swing.JTextField();
        longitudeToField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tableOptionsLabel = new javax.swing.JLabel();
        minTempLabel = new javax.swing.JLabel();
        maxTempLabel = new javax.swing.JLabel();
        regionMeanTempLabel = new javax.swing.JLabel();
        timeMeanTempLabel = new javax.swing.JLabel();
        tempTimeRegionLabel = new javax.swing.JLabel();
        minTempCheckbox = new javax.swing.JCheckBox();
        meanTempRegionCheckbox = new javax.swing.JCheckBox();
        maxTempRegionCheckbox = new javax.swing.JCheckBox();
        meanTempTimeCheckbox = new javax.swing.JCheckBox();
        tempsTimeRegionCheckbox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QueryInterface");
        setName("QInterfaceFrame"); // NOI18N

        QueryInterfaceLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        QueryInterfaceLabel.setText("Query Interface");

        simulationNameLabel.setLabelFor(simulationNameField);
        simulationNameLabel.setText("Name of the simulation");
        simulationNameLabel.setName("simulationNameLabel"); // NOI18N

        simulationNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulationNameFieldActionPerformed(evt);
            }
        });

        axisTiltLabel.setText("Axial tilt");

        orbitalEccentricityLabel.setText("Orbital eccentricity");

        axisTiltSlider.setMajorTickSpacing(60);
        axisTiltSlider.setMaximum(180);
        axisTiltSlider.setMinimum(-180);
        axisTiltSlider.setMinorTickSpacing(30);
        axisTiltSlider.setPaintLabels(true);
        axisTiltSlider.setPaintTicks(true);

        orbitalEccentricityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orbitalEccentricityFieldActionPerformed(evt);
            }
        });

        latitudeLabel.setText("Latitude");

        longitudeLabel.setText("Longitude");

        simulationPeriodLabel.setText("Simulation period");

        jLabel9.setText("to");

        simulationEndField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulationEndFieldActionPerformed(evt);
            }
        });

        latitudeFromField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                latitudeFromFieldActionPerformed(evt);
            }
        });

        latitudeToField.addActionListener(new java.awt.event.ActionListener() {
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

        jLabel10.setText("to");

        jLabel11.setText("to");

        tableOptionsLabel.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        tableOptionsLabel.setText("Include following data in the result set");

        minTempLabel.setText("Minumum temperature in the region selected");

        maxTempLabel.setText("Maximum temperature in the region selected");

        regionMeanTempLabel.setText("Mean temperature for the requested region");

        timeMeanTempLabel.setText("Mean temperature for the requested time period");

        tempTimeRegionLabel.setText("Temperatures for the requested region over the time period selected");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(minTempCheckbox)
                .addGap(162, 162, 162))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(QueryInterfaceLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(maxTempLabel)
                            .addComponent(regionMeanTempLabel)
                            .addComponent(timeMeanTempLabel)
                            .addComponent(tempTimeRegionLabel))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(meanTempRegionCheckbox)
                            .addComponent(meanTempTimeCheckbox)
                            .addComponent(maxTempRegionCheckbox)
                            .addComponent(tempsTimeRegionCheckbox)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jLabel9)
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(longitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(simulationEndField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(simulationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(162, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tableOptionsLabel)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(latitudeLabel)
                                .addComponent(longitudeLabel)
                                .addComponent(simulationPeriodLabel)
                                .addComponent(simulationNameLabel)
                                .addComponent(orbitalEccentricityLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(latitudeFromField, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                        .addComponent(longitudeFromField)
                                        .addComponent(simulationStartField))
                                    .addGap(35, 35, 35)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel11))
                                    .addGap(35, 35, 35)
                                    .addComponent(latitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(orbitalEccentricityField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(minTempLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(axisTiltLabel)
                                .addGap(65, 65, 65)
                                .addComponent(axisTiltSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(80, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(QueryInterfaceLabel)
                .addGap(5, 5, 5)
                .addComponent(simulationNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addComponent(longitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(simulationEndField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addComponent(minTempCheckbox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(regionMeanTempLabel)
                        .addGap(17, 17, 17)
                        .addComponent(maxTempLabel)
                        .addGap(18, 18, 18)
                        .addComponent(timeMeanTempLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(meanTempRegionCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(maxTempRegionCheckbox)
                        .addGap(18, 18, 18)
                        .addComponent(meanTempTimeCheckbox)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tempsTimeRegionCheckbox)
                    .addComponent(tempTimeRegionLabel))
                .addGap(31, 31, 31))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(44, 44, 44)
                    .addComponent(simulationNameLabel)
                    .addGap(24, 24, 24)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(axisTiltSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(orbitalEccentricityLabel)
                                .addComponent(orbitalEccentricityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(latitudeFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(latitudeToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(latitudeLabel))))
                        .addComponent(axisTiltLabel))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(longitudeLabel)
                                .addComponent(longitudeFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(simulationPeriodLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(simulationStartField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(tableOptionsLabel)))
                    .addGap(18, 18, 18)
                    .addComponent(minTempLabel)
                    .addContainerGap(184, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simulationNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulationNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simulationNameFieldActionPerformed

    private void orbitalEccentricityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orbitalEccentricityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orbitalEccentricityFieldActionPerformed

    private void simulationEndFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulationEndFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simulationEndFieldActionPerformed

    private void latitudeToFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_latitudeToFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_latitudeToFieldActionPerformed

    private void longitudeFromFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_longitudeFromFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_longitudeFromFieldActionPerformed

    private void longitudeToFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_longitudeToFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_longitudeToFieldActionPerformed

    private void latitudeFromFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_latitudeFromFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_latitudeFromFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel QueryInterfaceLabel;
    private javax.swing.JLabel axisTiltLabel;
    private javax.swing.JSlider axisTiltSlider;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
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
