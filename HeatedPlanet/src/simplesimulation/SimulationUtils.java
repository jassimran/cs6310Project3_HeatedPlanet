package simplesimulation;

public class SimulationUtils {

    /**
     * Earth's eccentricity = e.
     * 
     * 0 <= e < 1
     */
    public static final float EARTH_ECCENTRICITY = 0.0167f;

    /**
     * Obliquity (tilt) of the planet Earth.
     * 
     * -180 <= Epsilon (E) <= 180 (degrees)
     */
    public static final float OBLIQUITY = 23.44f;

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
    public static final int EARTH_PERIAPSIS = 114;

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
     * 
     * @return mean anomaly in radians
     */
    public static double meanAnomaly(int timeSinceLastPerihelion) {
	int tau = timeSinceLastPerihelion % ORBITAL_PERIOD;

	return 2 * Math.PI * tau / ORBITAL_PERIOD;
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
     * The distance (radius) between the focus and the planet at time t (tau).
     * 
     * rTau = a * ((1-e^2)/(1+e*cos(Vtau)))
     * 
     * @param semiMajorAxis
     *            Semi-major axis
     * @param eccentricity
     *            Eccentricity (Earth's)
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
     * Equinoxes occur when the true anomaly of the planetÕs position equals its
     * Argument of Periapsis. That is when w = Vt (omega = Vtau). Solving this
     * for T (tau) gives the time of the equinox.
     * 
     * tAn (TauAn) in minutes.
     *
     * @param eccentricity
     *            Earth's eccentricity
     * @param periapsis
     *            Earth's periapsis in degrees
     * @param orbitalPeriod
     *            Earth's orbital period
     * 
     * @return Time of the equinox in minutes
     */
    public static double timeOfEquinox(double eccentricity, double periapsis,
	    int orbitalPeriod) {
	double Vtau = trueAnomaly(eccentricity, Math.toRadians(periapsis));

	double tauEquinox = (orbitalPeriod / (2 * Math.PI))
		* (Vtau - eccentricity * Math.sin(Vtau));

	return tauEquinox;
	// double Vtau = trueAnomaly(EARTH_ECCENTRICITY, EARTH_PERIAPSIS);
	//
	// double tauEquinox = (ORBITAL_PERIOD / (2 * Math.PI)) * (Vtau -
	// EARTH_ECCENTRICITY * Math.sin(Vtau));
	//
	// return tauEquinox;
    }

    /**
     * Using tAn as a starting point, the rotational angle as a function of time
     * t (tau) in radians. Qt (Phi Tau)
     * 
     * @param timeTau
     *            Time Tau in minutes
     * 
     * @return Rotational angle in radians
     */
    public static double rotationalAngle(double timeTau) {
	double tauAn = timeOfEquinox(EARTH_ECCENTRICITY, EARTH_PERIAPSIS,
		ORBITAL_PERIOD);
	double phiTau = (2 * Math.PI * ((timeTau - tauAn) % ORBITAL_PERIOD))
		/ ORBITAL_PERIOD;

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
     * 
     * @return Latitude Noon Sun in radians
     */
    public static double latitudeNoonSun(double timeTau) {
	double phiTau = rotationalAngle(timeTau);

	double psiTau = OBLIQUITY * Math.sin(phiTau);

	return psiTau;
    }

    public static void demo(double eccentricity, int timeSinceLastPerihelion) {
	double e = eccentricity;
	double Mtau = SimulationUtils.meanAnomaly(timeSinceLastPerihelion);
	int dp = 5;

	System.out.println("Mean Anomaly: " + Mtau + " radians - "
		+ toFixed(Math.toDegrees(Mtau), 2) + " degrees.");

	double Etau = SimulationUtils.eccentricAnomaly(e, Mtau, dp);

	System.out.println("Eccentric Anomaly: " + Etau + " radians - "
		+ toFixed(Math.toDegrees(Etau), 2) + " deg.");

	double Vtau = SimulationUtils.trueAnomaly(e, Etau, dp);

	System.out.println("True Anomaly: " + Vtau + " radians - "
		+ toFixed(Math.toDegrees(Vtau), 2) + " deg.");

	double[] position = SimulationUtils.position(A, eccentricity, Etau);

	System.out
		.println("Position [" + position[0] + "," + position[1] + "]");

	double timeOfEquinox = timeOfEquinox(EARTH_ECCENTRICITY,
		EARTH_PERIAPSIS, ORBITAL_PERIOD);

	System.out.println("Time of Equinox: " + timeOfEquinox / 1440
		+ " days.");

	System.out.println("Latitude noon Sun:");

	for (int i = 0; i < ORBITAL_PERIOD; i = i + 100) {
	    System.out.println(latitudeNoonSun(i));
	}
    }
}
