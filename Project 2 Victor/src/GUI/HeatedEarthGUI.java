package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import EarthSim.Controller;
import EarthSim.core.Grid;
import EarthSim.utils.ArgumentParser;
import EarthSim.utils.Arguments;

public class HeatedEarthGUI extends JFrame {
	private final Arguments arguments;
	private final Controller controller;

	private JFrame frame = new JFrame();
	private JPanel infoPanel, controlPanel;
	private EarthPanel earthImagePanel;
	private JLabel earthTimeLabel, earthPositionLabel, simulationSliderLabel,	sSliderValueLabel;
	private JLabel presentationLabel, pValueLabel, gridSpaceLabel, gValueLabel;
	private JLabel simulationLengthLabel, slValueLabel, bufferLength;
	private JButton startButton, stopButton, pauseButton, resumeButton;
	public String earthTime = "Not started", earthPosition = "Not started";
	private SunDisplay SD;
	public int simulationRateValue = 1, presentationRateValue = 1,	gridSpaceValue = 15, simulationLengthValue = 1;
	private JTextField simulationRateInput, presentationRateInput,	gridSpaceInput, simulationLengthInput;
	private EarthGridDisplay earthGridDP;

	public HeatedEarthGUI(final Controller controller) {
		this.controller = controller;
		this.arguments = controller.arguments;

		{
			infoPanel = new JPanel();
			infoPanel.setLayout(null);

			infoPanel.setPreferredSize(new Dimension(900, 50));
			{
				earthTimeLabel = new JLabel();
				infoPanel.add(earthTimeLabel);
				earthTimeLabel.setText("Time in simulation: " + earthTime);
				earthTimeLabel.setBounds(50, 20, 221, 14);
			}
			{
				earthPositionLabel = new JLabel();
				infoPanel.add(earthPositionLabel);
				earthPositionLabel.setText("Earth Position: " + earthPosition);
				earthPositionLabel.setBounds(350, 20, 221, 14);
			}
			{
				earthImagePanel = new EarthPanel(getMinimumSize(),
						getMaximumSize(), getPreferredSize());
				// earthImagePanel.setLayout(null);
				earthImagePanel.setBounds(50, 50, 850, 425);
			}
		}
		{
			controlPanel = new JPanel();
			controlPanel.setLayout(null);
			controlPanel.setPreferredSize(new Dimension(900, 300));

			{
				// Simulation Rate
				/*
				 * simulationRateValue changes with slider sSliderValue will
				 * display the value of the slider
				 */
				simulationSliderLabel = new JLabel("Simulation Rate");
				simulationSliderLabel.setBounds(50, 10, 150, 15);
				controlPanel.add(simulationSliderLabel);

				sSliderValueLabel = new JLabel("minutes");
				sSliderValueLabel.setBounds(380, 10, 150, 15);
				controlPanel.add(sSliderValueLabel);
				

				simulationRateInput = new JTextField();
				simulationRateInput.setText("60");
				simulationRateInput.setBounds(185, 10, 175, 25);
				controlPanel.add(simulationRateInput);

			}

			{
				// Presentation Rate
				presentationLabel = new JLabel("Presentation Rate");
				presentationLabel.setBounds(50, 40, 150, 15);
				controlPanel.add(presentationLabel);

				pValueLabel = new JLabel("seconds");
				pValueLabel.setBounds(380, 40, 150, 15);
				controlPanel.add(pValueLabel);

				presentationRateInput = new JTextField();
				presentationRateInput.setText("1");
				presentationRateInput.setBounds(185, 40, 175, 25);
				controlPanel.add(presentationRateInput);

			}

			{
				// Grid Spacing
				gridSpaceLabel = new JLabel("Grid Spacing");
				gridSpaceLabel.setBounds(50, 70, 150, 15);
				controlPanel.add(gridSpaceLabel);

				gValueLabel = new JLabel("degrees");
				gValueLabel.setBounds(380, 70, 150, 15);
				controlPanel.add(gValueLabel);

				gridSpaceInput = new JTextField();
				gridSpaceInput.setText("15");
				gridSpaceInput.setBounds(185, 70, 175, 25);
				controlPanel.add(gridSpaceInput);

			}

			{
				// Simulation Length
				simulationLengthLabel = new JLabel("Simulation Length");
				simulationLengthLabel.setBounds(50, 100, 150, 15);
				controlPanel.add(simulationLengthLabel);

				slValueLabel = new JLabel("months");
				slValueLabel.setBounds(380, 100, 150, 15);
				controlPanel.add(slValueLabel);

				simulationLengthInput = new JTextField();
				simulationLengthInput.setText("1");
				simulationLengthInput.setBounds(185, 100, 175, 25);
				controlPanel.add(simulationLengthInput);

			}
			{
				//Buffer length
				String bufferlength = "Buffer length entered is " +arguments.bufferSize;
				bufferLength = new JLabel (bufferlength );
				bufferLength.setBounds(50, 130, 200, 20);
				controlPanel.add(bufferLength);
				
			}
			{
				// Buttons
				{
					// startButton

					startButton = new JButton("start");
					startButton.setBounds(500, 10, 90, 20);
					controlPanel.add(startButton);
					startButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								simulationRateValue = Integer.parseInt(simulationRateInput.getText());
								gridSpaceValue = Integer.parseInt(gridSpaceInput.getText());
								presentationRateValue = Integer.parseInt(presentationRateInput.getText());
								simulationLengthValue = Integer.parseInt(simulationLengthInput.getText());

								if (simulationRateValue < 1	|| simulationRateValue > 1440) {

									JOptionPane.showMessageDialog(frame,"simuluation rate should range between 1 and 1440 minutes only");
								} else if (gridSpaceValue < 1|| gridSpaceValue > 180) {

									JOptionPane.showMessageDialog(frame,"GridSpace value should range between 15 and 180 degrees only");
								} else {

									/*
									 * Code here to call the function which
									 * starts the simulation
									 */
									// Updating arguments

									// updateMap();
									earthImagePanel.drawGrid(gridSpaceValue);
									simulationRateInput.setEditable(false);
									gridSpaceInput.setEditable(false);
									presentationRateInput.setEditable(false);
									simulationLengthInput.setEditable(false);
									stopButton.setEnabled(true);
									pauseButton.setEnabled(true);
									startButton.setEnabled(false);
									arguments.gridSpacing = gridSpaceValue;
									//arguments.presentationRate = presentationRateValue;
									//arguments.simulationRate = simulationRateValue;
									

									// Calling start on the controller
									controller.start();
								}
							} catch (NullPointerException exception) {
								JOptionPane
								.showMessageDialog(frame,
										"Please enter the missing parameter values");
							} catch (Exception exception) {
								JOptionPane
								.showMessageDialog(frame,
										"Please enter integer values only for parameters");
							}

						}

					});
				}

				{
					// stopButton
					stopButton = new JButton("stop");
					stopButton.setBounds(500, 38, 90, 20);
					stopButton.setEnabled(false);
					controlPanel.add(stopButton);
					stopButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							/*
							 * Code here to call the function which stops the
							 * simulation
							 */
							// Stopping the controller
							controller.stop();

							startButton.setEnabled(true);
							pauseButton.setEnabled(false);
							resumeButton.setEnabled(false);
							stopButton.setEnabled(false);
							simulationRateInput.setEditable(true);
							gridSpaceInput.setEditable(true);
							presentationRateInput.setEditable(true);
							simulationLengthInput.setEditable(true);
						}

					});
				}

				{
					// pauseButton
					pauseButton = new JButton("pause");
					pauseButton.setBounds(500, 66, 90, 20);
					pauseButton.setEnabled(false);
					controlPanel.add(pauseButton);
					pauseButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							/*
							 * Code here to call the function which pauses the
							 * simulation
							 */
							// Pausing the controller
							controller.pause();

							resumeButton.setEnabled(true);
							pauseButton.setEnabled(false);
						}

					});
				}

				{
					// resumeButton
					resumeButton = new JButton("resume");
					resumeButton.setBounds(500, 94, 90, 20);
					resumeButton.setEnabled(false);
					controlPanel.add(resumeButton);
					resumeButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							/*
							 * Code here to call the function which resumes the
							 * simulation
							 */
							pauseButton.setEnabled(true);
							resumeButton.setEnabled(false);

							// Resuming the controller
							controller.resume();
						}

					});
				}

			}

		}

		infoPanel.setVisible(true);
		frame.add(earthImagePanel);
		frame.add(infoPanel);
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.setSize(1000, 800);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		pack();
	}

	// Getters and setters for variables to be displayed

	public String getEarthTime() {
		return earthTime;
	}

	public void setEarthTime(String earthTime) {
		this.earthTime = earthTime;
	}

	public String getEarthPosition() {
		return earthPosition;
	}

	public void setEarthPosition(String earthPosition) {
		this.earthPosition = earthPosition;
	}

	public void setGridSpaceValue(int gridSpaceValue) {
		this.gridSpaceValue = gridSpaceValue;
	}

	public void setSimulationRateValue(int simulationRateValue) {
		this.simulationRateValue = simulationRateValue;
	}

	public void setPresentationRateValue(int presentationRateValue) {
		this.presentationRateValue = presentationRateValue;
	}

	public void setsimulationLengthValue(int simulationLengthValue) {
		this.simulationLengthValue = simulationLengthValue;
	}

	// Use these to get values from the UI
	public int getPresentationRateValue() {
		return presentationRateValue;
	}

	public int getSimulationRateValue() {
		return simulationRateValue;
	}

	public int getGridSpaceValue() {
		return gridSpaceValue;
	}

	public int getsimulationLengthValue() {
		return simulationLengthValue;
	}

	public void updateMap(Grid grid) {
		System.out.println("Updating map with grid");

		if (earthImagePanel != null) {
			System.out.println("Updating with grid: " + (grid != null));
			earthImagePanel.moveSunPosition (360 * simulationRateValue   / 1440f  );
			earthImagePanel.updateGrid((TemperatureGrid) grid);
			
			//earthPositionLabel.setText(""+ SD.getDegreePosition());
			System.out.println("Updated Grid");
			
			//
			//


		} else {
			System.out.println("Can't update since the earthImagePanel is null...");
		}
	}

	//    public static void main(String[] args) {

	//	final Arguments arguments =  ArgumentParser.parse(args);

	//	EventQueue.invokeLater(new Runnable() {
	//	    @Override
	//	    public void run() {
	//		HeatedEarthGUI gui = new HeatedEarthGUI(arguments);
	//	    }
	//	});
	//    }

}
