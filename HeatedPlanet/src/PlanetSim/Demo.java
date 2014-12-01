package PlanetSim;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import presentation.Gui;
import app.conf.BootStrap;

public class Demo {
	static boolean simthread = true;
	static boolean presthread = false;
	static boolean simcontrol = false;
	static boolean prescontrol = false;
	static int buffer = 10;

	/**
	 * Force the presence of all arguments. If an argument is not provided an
	 * exception will be thrown.
	 * 
	 * If set to false, default values will be considered for the arguments that
	 * were not provided.
	 */
	private static final boolean FORCE_ALL_ARGUMENTS = false;

	/**
	 * Use default values for the included arguments in case of an error. If set
	 * to false, an invalid argument value will thrown an exception.
	 */
	private static final boolean USE_DEFAULT_ARGUMENTS = true;

	/**
	 * Default geographic precision.
	 */
	private static final int DEFAULT_GEOGRAPHIC_PRECISION = 100;

	/**
	 * Default temporal precision.
	 */
	private static final int DEFAULT_TEMPORAL_PRECISION = 100;

	/**
	 * Number of decimal digits for a normalized float.
	 */
	private static final int FLOAT_PRECISION = 7;

	/**
	 * Number of decimal digits for a normalized double.
	 */
	private static final int DOUBLE_PRECISION = 16;

	/**
	 * The precision of the data to be stored, in decimal digits after the
	 * decimal point. The default is to use the number of digits storable in a
	 * normalized float variable. The maximum is the number of digits storable
	 * in a normalized double variable. The minimum is zero. [-p]
	 */
	private static int precision = -1;

	/**
	 * The geographic precision (sampling rate) of the temperature data to be
	 * stored, as an integer percentage of the number of grid cells saved versus
	 * the number simulated. The default is 100%; that is, a value is stored for
	 * each grid cell. [-g]
	 */
	private static int geographicPrecision = -1;

	/**
	 * The temporal precision of the temperature data to be stored, as an
	 * integer percentage of the number of time periods saved versus the number
	 * computed. The default is 100%; that is, all computed values should be
	 * stored. [-t]
	 */
	private static int temporalPrecision = -1;

	public static void main(String[] args) {
		parseArgs(args);

		// bootstrap application
		BootStrap.init();
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		Gui.getInstance(simthread, presthread, simcontrol, prescontrol, buffer);
		// TODO register UI listener to close H2 web servers
	}

	/**
	 * The Heated Planet simulation program should be invoked as follows:
	 * 
	 * java PlanetSim.Demo [-p #] [-g #] [-t #]
	 * 
	 * PlanetSim.Demo should be invoked with the following Invocation Parameters
	 * to allow the designer to control the persisted results.
	 * 
	 * -p #: The precision of the data to be stored, in decimal digits after the
	 * decimal point. The default is to use the number of digits storable in a
	 * normalized float variable. The maximum is the number of digits storable
	 * in a normalized double variable. The minimum is zero.
	 * 
	 * -g #: The geographic precision (sampling rate) of the temperature data to
	 * be stored, as an integer percentage of the number of grid cells saved
	 * versus the number simulated. The default is 100%; that is, a value is
	 * stored for each grid cell.
	 * 
	 * -t #: The temporal precision of the temperature data to be stored, as an
	 * integer percentage of the number of time periods saved versus the number
	 * computed. The default is 100%; that is, all computed values should be
	 * stored.
	 * 
	 * @param args
	 */
	private static void parseArgs(String[] args) {
		int len = args.length;

		for (int i = 0; i < len; i++) {
			if (args[i].equalsIgnoreCase("-p")) {
				if (i == len - 1) {
					if (!USE_DEFAULT_ARGUMENTS) {
						throw new IllegalArgumentException(
								"Need to provide decimal precision.");
					}

					System.out
							.println("No decimal precision value provided. Defaulting to normalized float precision.");

					precision = FLOAT_PRECISION;

					continue;
				}

				try {
					int candidatePrecision = Integer.parseInt(args[i + 1]);

					if (candidatePrecision > DOUBLE_PRECISION) {
						precision = DOUBLE_PRECISION;
					} else if (candidatePrecision < 0) {
						precision = FLOAT_PRECISION;
					} else {
						precision = candidatePrecision;
					}

					i++;
				} catch (NumberFormatException nfe) {
					if (USE_DEFAULT_ARGUMENTS) {
						precision = FLOAT_PRECISION;
					} else {
						throw new IllegalArgumentException(
								"Need to provide a valid decimal precision.");
					}
				}
			} else if (args[i].equalsIgnoreCase("-g")) {
				if (i == len - 1) {
					if (!USE_DEFAULT_ARGUMENTS) {
						throw new IllegalArgumentException(
								"Need to provide geographic precision.");
					}

					System.out
							.println("No geographic precision value provided. Defaulting to "
									+ DEFAULT_GEOGRAPHIC_PRECISION + "%.");

					geographicPrecision = DEFAULT_GEOGRAPHIC_PRECISION;

					continue;
				}

				try {
					int candidateGeographicPrecision = Integer
							.parseInt(args[i + 1]);

					if (candidateGeographicPrecision > 100) {
						geographicPrecision = 100;
					} else if (candidateGeographicPrecision < 0) {
						geographicPrecision = 0;
					} else {
						geographicPrecision = candidateGeographicPrecision;
					}

					i++;
				} catch (NumberFormatException nfe) {
					if (USE_DEFAULT_ARGUMENTS) {
						geographicPrecision = DEFAULT_GEOGRAPHIC_PRECISION;
					} else {
						throw new IllegalArgumentException(
								"Need to provide a valid geographic precision.");
					}
				}
			} else if (args[i].equalsIgnoreCase("-t")) {
				if (i == len - 1) {
					if (!USE_DEFAULT_ARGUMENTS) {
						throw new IllegalArgumentException(
								"Need to provide temporal precision.");
					}

					System.out
							.println("No temporal precision value provided. Defaulting to "
									+ DEFAULT_TEMPORAL_PRECISION + "%.");

					temporalPrecision = DEFAULT_TEMPORAL_PRECISION;

					continue;
				}

				try {
					int candidateTemporalPrecision = Integer
							.parseInt(args[i + 1]);

					if (candidateTemporalPrecision > 100) {
						temporalPrecision = 100;
					} else if (candidateTemporalPrecision < 0) {
						temporalPrecision = 0;
					} else {
						temporalPrecision = candidateTemporalPrecision;
					}

					i++;
				} catch (NumberFormatException nfe) {
					if (USE_DEFAULT_ARGUMENTS) {
						temporalPrecision = DEFAULT_TEMPORAL_PRECISION;
					} else {
						throw new IllegalArgumentException(
								"Need to provide a valid temporal precision.");
					}
				}
			}
		}

		// If we have no arguments and we can use defaults then do so
		if (len == 0 && !FORCE_ALL_ARGUMENTS && USE_DEFAULT_ARGUMENTS) {
			precision = FLOAT_PRECISION;
			geographicPrecision = DEFAULT_GEOGRAPHIC_PRECISION;
			temporalPrecision = DEFAULT_TEMPORAL_PRECISION;
		}

		// Checking for all provided arguments
		if (precision == -1 || geographicPrecision == -1
				|| temporalPrecision == -1) {
			throw new IllegalArgumentException(
					"Need to provide all arguments! (-p - g - t).\njava PlanetSim.Demo [-p #] [-g #] [-t #]");
		}

		System.out.println("Executing: PlanetSim.Demo -p " + precision + " -g "
				+ geographicPrecision + " -t " + temporalPrecision);
		
		// Setting the values on the GUI class
		
		Gui.DEFAULT_PRECISION = precision;
		Gui.DEFAULT_GEO_ACCURACY = geographicPrecision;
		Gui.DEFAULT_TEMP_ACCURACY = temporalPrecision;
	}
}