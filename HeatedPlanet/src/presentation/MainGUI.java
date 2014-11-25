package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

	
	
	public class MainGUI extends javax.swing.JFrame implements ActionListener, ChangeListener{

		
		private static final long serialVersionUID = -15968456987513L;
		static final String ACTION_SIM = "Live Simulation";
		static final String ACTION_QRY = "Query Existing Simulations";
		

	    /**
	     * Creates new form MainGUI
	     */
	    public MainGUI() {
	        initComponents();
	    }

	    /**
	     * This method is called from within the constructor to initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is always
	     * regenerated by the Form Editor.
	     */
	   
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	    private void initComponents() {
	    	JFrame guiframe = new JFrame();
	        mainPanel = new JPanel();
	        mainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
					Color.blue));
			mainPanel.setLayout(new java.awt.BorderLayout());
			
	        panelLabel = new JLabel();
	        panelLabel.setText("Heated Planet Simulation");
	        
	        liveSimulationButton = new JButton();
	        liveSimulationButton.addActionListener(this);
	        liveSimulationButton.setEnabled(true); 
	        
	        liveSimulationButton.setText("Live Simulation");
	        liveSimulationButton.addActionListener(this);
	        liveSimulationButton.setEnabled(true); 
	        liveSimulationButton.setActionCommand(ACTION_SIM);
	        
	        queryButton = new JButton();
	        queryButton.addActionListener(this);
	        queryButton.setEnabled(true); 
       
	       
	        
	        queryButton.setText("Query Existing Simulations");
	        queryButton.addActionListener(this);
	        queryButton.setEnabled(true); 
	        queryButton.setActionCommand(ACTION_QRY);

	       
	      
	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        queryButton.addActionListener(this);

	        liveSimulationButton.addActionListener(this);
	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(mainPanel);
	        mainPanel.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGap(48, 48, 48)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(panelLabel)
	                    .addComponent(liveSimulationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(queryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
	                .addContainerGap(223, Short.MAX_VALUE))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGap(19, 19, 19)
	                .addComponent(panelLabel)
	                .addGap(18, 18, 18)
	                .addComponent(liveSimulationButton)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
	                .addComponent(queryButton)
	                .addContainerGap())
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(19, 19, 19)
	                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(147, Short.MAX_VALUE))
	        );

	        pack();
	    }// </editor-fold>                        

	    @Override
		public void actionPerformed(ActionEvent evt) {
			// TODO Auto-generated method stub
	    	String command = evt.getActionCommand();
			System.out.println("Command: "+ command);

			if (command.equals(ACTION_SIM)) 
			{
				//Invoke Simulation Interface
				Gui simGUI = Gui.getInstance(false, true, false, false, 0);
				
				queryButton.setEnabled(false);
			}
			else if(command.equals(ACTION_QRY))
			{
				//Invoke Query interface
				QueryInterfaceUI qryUI = QueryInterfaceUI.getInstance();
				liveSimulationButton.setEnabled(false);
			}
		}
		public void stateChanged(ChangeEvent e) {

		}
	    /**
	     * @param args the command line arguments
	     */
	    public static void main(String args[]) {
	        /* Set the Nimbus look and feel */
	        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
	         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	         */
	        /*try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }*/
	        //</editor-fold>

	        /* Create and display the form */
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                new MainGUI().setVisible(true);
	            }
	        });
	    }

	    // Variables declaration - do not modify                     
	    private javax.swing.JButton liveSimulationButton;
	    private javax.swing.JButton queryButton;
	    private javax.swing.JLabel panelLabel;
	    private javax.swing.JPanel mainPanel;
	    // End of variables declaration                   


		
		
}



