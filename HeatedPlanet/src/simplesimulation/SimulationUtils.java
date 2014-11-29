package simplesimulation;


import presentation.earth.TemperatureGrid;


/**
 * Class that includes the Physics model to adapt eccentricity and orbit tilt
 * (obliquity).
 */

public class SimulationUtils {

	/**
	 * Earth's eccentricity = e.
	 * 
	 * 0 <= e < 1
	 */
	public static final double EARTH_ECCENTRICITY = 0.0167d;

	/**
	 * Obliquity (tilt) of the planet Earth.
	 * 
	 * -180 <= Epsilon (E) <= 180 (degrees)
	 */
	public static final double OBLIQUITY = 23.44d;

	/**
	 * Distance from the Earth to the Sun at perihelion
	 */
	public static final int PERIHELION = 147094000;

	/**
	 * Length of the semi-major axis for the Earth - a
	 */
	public static final int A = 149600000;

	/**
	 * Argument of periapsis for the Earth
	 * 
	 * omega - W (degrees)
	 */
	public static final double EARTH_PERIAPSIS = 114d;

	/**
	 * Rotational period of the Earth with respect to the sun (minutes)
	 */
	public static final int ROTATIONAL_PERIOD = 1440; // 60 * 24;

	/**
	 * Approximate orbital period of Earth (ignoring Leap Years)
	 * 
	 * T (minutes)
	 */
	public static final int ORBITAL_PERIOD = 525600; // 365 * 24 * 60;

	/**
	 * The mean anomaly approximates the angular progress, with respect to the
	 * center of the ellipse, made by the orbiting body. For a particular moment
	 * in time, tau, its value in radians is:
	 * 
	 * Mt = 2 * PI * tau / T
	 * 
	 * @param timeSinceLastPerihelion
	 *            The time interval since last perihelion in minutes, denoted by
	 *            tau, for which 0 <= tau < T, where T is the ORBITAL_PERIOD.
	 * @param orbitalPeriod
	 *            Earth's (or planet's) Orbital period in minutes.
	 * 
	 * @return mean anomaly in radians
	 */
	public static double meanAnomaly(int timeSinceLastPerihelion,
			int orbitalPeriod) {
		int tau = timeSinceLastPerihelion % orbitalPeriod;

		return 2 * Math.PI * tau / orbitalPeriod;
	}

	/**
	 * The eccentric anomaly is the actual angle, from the center of the
	 * ellipse, to the orbiting body. Needs to be computed using a root-finder
	 * such as Newton-Raphson.
	 * 
	 * Etau : Mtau = Etau - e * sin(Etau)
	 * 
	 * @param eccentricity
	 *            Eccentricity
	 * @param meanAnomaly
	 *            Mean Anomaly in radians
	 * @param decimalPrecision
	 *            Decimal Precision
	 * 
	 * @return eccentric anomaly in radians
	 */
	public static double eccentricAnomaly(double eccentricity,
			double meanAnomaly, int decimalPrecision) {
		final int maxIterations = 30;
		int i = 0;

		double delta = Math.pow(10, -decimalPrecision);
		double Etau, F;

		Etau = (eccentricity < 0.8) ? meanAnomaly : Math.PI;

		F = Etau - eccentricity * Math.sin(meanAnomaly) - meanAnomaly;

		while (Math.abs(F) > delta && i < maxIterations) {
			Etau = Etau - F / (1.0 - eccentricity * Math.cos(Etau));
			F = Etau - eccentricity * Math.sin(Etau) - meanAnomaly;
			i++;
		}

		return Etau;
	}

	/**
	 * The true anomaly is the actual angle between the major axis and the line
	 * from the ellipse's focus to the planet.
	 * 
	 * Vtau = acos((cos(Etau) - e)/(1-e*cos(Etau)))
	 * 
	 * @param eccentricity
	 *            Eccentricity
	 * @param eccentricAnomaly
	 *            Eccentric Anomaly in radians
	 * 
	 * @return true anomaly
	 */
	public static double trueAnomaly(double eccentricity,
			double eccentricAnomaly) {
		double cosEtau = Math.cos(eccentricAnomaly);

		return Math.acos((cosEtau - eccentricity)
				/ (1 - eccentricity * cosEtau));
	}

	/**
	 * The true anomaly is the actual angle between the major axis and the line
	 * from the ellipse's focus to the planet.
	 * 
	 * @param eccentricity
	 *            Eccentricity
	 * @param eccentricAnomaly
	 *            Eccentric Anomaly in radians
	 * @param decimalPrecision
	 *            Decimal Precision
	 * 
	 * @return true anomaly in radians
	 */
	public static double trueAnomaly(double eccentricity,
			double eccentricAnomaly, int decimalPrecision) {
		double sinEtau = Math.sin(eccentricAnomaly);
		double cosEtau = Math.cos(eccentricAnomaly);
		double sqrtEc = Math.sqrt(1.0 - eccentricity * eccentricity);
		double phi = Math.atan2(sqrtEc * sinEtau, cosEtau - eccentricity);

		return phi;
	}

	/**
	 * The true anomaly is the actual angle between the major axis and the line
	 * from the ellipse's focus to the planet.
	 * 
	 * @param timeSinceLastPerihelion
	 *            Time since last perihelion
	 * @param orbitalPeriod
	 *            Earth's (or planet's) Orbital Period in minutes
	 * @param eccentricity
	 *            Eccentricity
	 * @param decimalPrecision
	 *            Decimal Precision
	 * 
	 * @return true anomaly in radians
	 */
	public static double trueAnomaly(int timeSinceLastPerihelion,
			int orbitalPeriod, double eccentricity, int decimalPrecision) {
		return trueAnomaly(
				eccentricity,
				eccentricAnomaly(eccentricity,
						meanAnomaly(timeSinceLastPerihelion, orbitalPeriod),
						decimalPrecision), decimalPrecision);
	}

	/**
	 * The true anomaly is the actual angle between the major axis and the line
	 * from the ellipse's focus to the planet. It computes the true anomaly
	 * based on the time since last perihelion for the Earth constants.
	 * 
	 * @param timeSinceLastPerihelion
	 *            Time since last perihelion
	 * @param orbitalPeriod
	 *            Earth's (or planet's) Orbital Period in minutes
	 * @param eccentricity
	 *            Eccentricity
	 * @param decimalPrecision
	 *            Decimal Precision
	 * 
	 * @return true anomaly in radians
	 */
	public static double trueAnomalyForEarthConstants(
			int timeSinceLastPerihelion) {
		return trueAnomaly(timeSinceLastPerihelion, ORBITAL_PERIOD,
				EARTH_ECCENTRICITY, 5);
	}

	/**
	 * The distance (radius) between the focus and the planet at time t (tau).
	 * 
	 * rTau = a * ((1-e^2)/(1+e*cos(Vtau)))
	 * 
	 * @param semiMajorAxis
	 *            Semi-major axis of the orbit
	 * @param eccentricity
	 *            Eccentricity
	 * @param trueAnomaly
	 *            True Anomaly
	 * @return
	 */
	public static double radiusTau(double semiMajorAxis, double eccentricity,
			double trueAnomaly) {
		return semiMajorAxis
				* ((1 - eccentricity * eccentricity) / (1 + eccentricity
						* Math.cos(trueAnomaly)));
	}

	/**
	 * Cartesian coordinates of the planet at any point in time.
	 * 
	 * (xtau, ytau) = (c + a + cosEtau,b * sinEtau)
	 * 
	 * @param semiMajorAxis
	 *            Semi Major Axis of the orbit
	 * @param eccentricity
	 *            Eccentricity
	 * @param eccentricAnomaly
	 *            Eccentric anomaly
	 * @return An array of size 2 containing on its first position the X
	 *         coordinate and on its second position the Y coordinate [x,y].
	 */
	public static double[] position(double semiMajorAxis, double eccentricity,
			double eccentricAnomaly) {
		double[] coordinates = new double[2];

		double cosEtau = Math.cos(eccentricAnomaly);
		double sinEtau = Math.sin(eccentricAnomaly);

		// Setting x coordinate
		coordinates[0] = semiMajorAxis * (cosEtau - eccentricity);

		// Setting y coordinate
		coordinates[1] = semiMajorAxis
				* Math.sqrt(1.0 - eccentricity * eccentricity) * sinEtau;

		return coordinates;
	}

	/**
	 * Fixes number to the desired decimal precision.
	 * 
	 * @param number
	 *            Number
	 * @param decimalPrecision
	 *            Decimal Precision
	 * 
	 * @return the number rounded to x number of decimal places.
	 */
	public static double toFixed(double number, int decimalPrecision) {
		double dp = Math.pow(10, decimalPrecision);

		return Math.round(number * dp) / dp;
	}

	/**
	 * Equinoxes occur when the true anomaly of the planetâs position
	 * equals its Argument of Periapsis. That is when w = Vt (omega = Vtau).
	 * Solving this for T (tau) gives the time of the equinox.
	 * 
	 * tAn (TauAn) in minutes.
	 *
	 * @param eccentricity
	 *            Eccentricity
	 * @param angleOfPeriapsis
	 *            Earth's (or planet's) angle of periapsis in degrees
	 * @param orbitalPeriod
	 *            Earth's (or planet's) orbital period in minutes
	 * 
	 * @return Time of the equinox in minutes
	 */
	public static double timeOfEquinox(double eccentricity,
			double angleOfPeriapsis, int orbitalPeriod) {
		double Vtau = trueAnomaly(eccentricity,
				Math.toRadians(angleOfPeriapsis));

		double tauEquinox = (orbitalPeriod / (2 * Math.PI))
				* (Vtau - eccentricity * Math.sin(Vtau));

		return tauEquinox;
	}

	/**
	 * Using tAn as a starting point, the rotational angle as a function of time
	 * t (tau) in radians. Qt (Phi Tau)
	 * 
	 * @param timeTau
	 *            Time Tau in minutes
	 * @param eccentricity
	 *            Eccentricity
	 * @param angleOfPeriapsis
	 *            Earth's (or planet's) Angle of Periapsis in degrees
	 * @param orbitalPeriod
	 *            Earth's (or planet's) Orbital period in minutes
	 * 
	 * @return Rotational angle in radians
	 */
	public static double rotationalAngle(double timeTau, double eccentricity,
			double angleOfPeriapsis, int orbitalPeriod) {
		double tauAn = timeOfEquinox(eccentricity, angleOfPeriapsis,
				orbitalPeriod);
		double phiTau = (2 * Math.PI * ((timeTau - tauAn) % orbitalPeriod))
				/ orbitalPeriod;

		return phiTau;
	}

	/**
	 * Putting Qt (phiTau) and E (epsilon - obliquity) together, the latitude of
	 * the noon Sun as a function of the time period PsiTau (Yt) is given by:
	 * 
	 * Yt = E * sin(Qt)
	 * 
	 * @param timeTau
	 *            Time Tau in minutes
	 * @param eccentricity
	 *            Eccentricity
	 * @param angleOfPeriapsis
	 *            Earth's (or planet's) Angle of Periapsis.
	 * @param orbitalPeriod
	 *            Earth's (or planet's) Orbital Period in minutes
	 * @param obliquity
	 *            Earth's (or planet's) Obliquity or tilt.
	 * 
	 * @return Latitude Noon Sun in radians
	 */
	public static double latitudeNoonSun(double timeTau, double eccentricity,
			double angleOfPeriapsis, int orbitalPeriod, double obliquity) {
		double phiTau = rotationalAngle(timeTau, eccentricity,
				angleOfPeriapsis, orbitalPeriod);

		double psiTau = obliquity * Math.sin(phiTau);

		return psiTau;
	}

	/**
	 * Gets the attenuation coefficient based on the distance from the focus.
	 * 
	 * @param radiusAtPerihelion
	 *            Distance at perihelion
	 * @param radiusTau
	 *            Current distance
	 * 
	 * @return A double number between 0 and 1 (inclusive). 1 is the closest, 0
	 *         the farthest.
	 */
	public static double getEccentricityAttenuation(double radiusAtPerihelion,
			double radiusTau) {
		return radiusAtPerihelion / radiusTau;
	}

	public static SimpleTemperatureGridImpl dissipateExcessHeat(TemperatureGrid inputGrid,
			SimpleTemperatureGridImpl temperatureGrid, 
			int precision) {
		// calculate variables
		int rows = inputGrid.getRows();
		int cols = inputGrid.getCols();
		
		double totalOriginalTemp = 0.0;
		
		for(int y=0; y<rows; y++) {
			for(int x=0; x<cols; x++) {
				
				double originalTemp = inputGrid.getTemperature(x, y);
				
				totalOriginalTemp += originalTemp;

			}
		}
		
		double totalHeatToDissipate = (totalOriginalTemp / (rows*cols)) - 288.0;
		double cellHeatToDissipate = totalHeatToDissipate / (rows*cols);
		
		if(cellHeatToDissipate != 0)
			for(int y=0; y<rows; y++) {
				for(int x=0; x<cols; x++) {
					
					double originalTemp = temperatureGrid.getTemperature(x, y);
					double tempToUse = originalTemp - cellHeatToDissipate;
					
					SimpleCell simpleCell = new SimpleCell();
					simpleCell.t = round(tempToUse, precision);
					temperatureGrid.setTemperature(x, y, simpleCell);
				}
			}
		return temperatureGrid;
	}
	
	/**
	 * Rounds a double to the specified number of digits of precision.
	 * @param originalTemp
	 * @param precision
	 * @return The rounded value.
	 */
	protected static double round(double originalTemp, int precision) {
		return new Double(String.format("%." + precision +"f", originalTemp));
	}	
}
