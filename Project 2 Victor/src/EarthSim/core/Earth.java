package EarthSim.core;

import java.util.Random;

import EarthSim.utils.Arguments;

public class Earth {

    /**
     * Earth's radius
     */
    public static final double R = 6.371e6;

    /**
     * Earth's circumference (2 * Pi * R)
     */
    public static final double C = 4.003014e7;

    /**
     * Earth's Surface in m^2 (4 * Pi * R^2)
     */
    public static final double A = 5.10072e14;

    /**
     * Earth's Surface visible to the Sun in m^2 (2 * Pi * R^2)
     */
    public static final double ADay = 2.55036e14;

    /**
     * Total power Earth receives from the Sun
     */
    public static final double P = 1.740e17;

    /**
     * Amount of power per unit of area reaching the Earth (a.k.a. Solar
     * constant)
     * 
     * S = P / A
     */
    public static final double S = 1.366e3;

    /**
     * Earth's aldebo (average fraction of energy reflected)
     */
    public static final double a = 0.3;

    /**
     * Earth's emissivity (average fraction of energy reradiated)
     */
    public static final double e = 0.612;

    // -- Could be in a different utility class
    /**
     * Stefan-Boltzmann constant SB = J * K^-4 * m^-2 * s-1
     */
    public static final double SB = 5.67e-8;

    /**
     * Average temperature of the Earth in K due to Solar radiation
     * 
     * Math.pow(((1 - a) * S) / (4 * e * SB), 0.25)
     */
    public static final double TE = 288.0;

    /**
     * Earth's rotational period
     */
    public static final int RP = 1440;

    /**
     * Latitude of the position on the Earth's surface where the sun is directly
     * overhead. On planet without tilt, Sun will always be over the Equator.
     */
    public static final double QSun = 0;

    /**
     * For an unattenuated cell, the power from the Sun.
     */
    public static final double TSun = 278;

    /**
     * Proportion of the Equator used by one unit of grid spacing (p = gs/360)
     */
    private double p;

    private int gs;

    private Grid grid;

    private Arguments arguments;

    /**
     * Number of grid cells per (24-hour) time zone (at the Equator)
     */
    private int gcptz;

    public Earth(Arguments arguments) {
	this.arguments = arguments;

	gs = arguments.gridSpacing;

	grid = new Grid(gs, arguments.initialTemperature);

	p = gs / 360d;
	gcptz = grid.cols() / 24;

	// System.out.println(gcptz);

	// for (Cell[] row : grid.cells) {
	// for (Cell cell : row) {
	// System.out.println("[" + cell.row + "," + cell.col + "]: "
	// + " " + cell.d + ":" + cell.Q);
	// //
	// System.out.println("["+cell.row+","+cell.col+"]: "+grid.TNeighbors(cell)+" "+grid.TCool(cell)+" "+TSun+" "+cell.d+":"+cell.Q+" "+cell.a+
	// // " "+cell.h+ " " +cell.Lb);
	// }
	// }

    }

    public Grid getGrid() {
	return grid;
    }

    public double p() {
	return p;
    }

    /**
     * Rotational angle in degrees as a function of time t in minutes since the
     * simulation's start.
     * 
     * Ot = mod(t, 1440) * 360 / 1440;
     * 
     * @param t
     *            time in minutes
     * @return Rotational angle in degrees
     */
    public double Ot(double t) {
	return t % RP * 360 / RP;
    }

    /**
     * Grid column under the sun at time t.
     * 
     * Jt = mod(cols x (Ot / 360) + (cols / 2), cols)
     * 
     * @param t
     *            time in minutes
     * @return Column index
     */
    public int Jt(double t) {
	int cols = grid.cols();

	return (int) (cols * (Ot(t) / 360) + (0.5 * cols)) % cols;
    }

    /**
     * The grid row i corresponding to latitude
     * 
     * @param latitude
     * @return Grid row index
     */
    public int rid(int latitude) {
//	if (latitude >= -90 && latitude <= 90) {
	    return (int) latitude / gs + grid.rows() / 2;
//	}
//
//	throw new IllegalArgumentException(
//		"Latitude needs to comply with: -90 <= latitude <= 90.");
    }

    /**
     * The grid column j corresponding to the longitude
     * 
     * @param longitude
     * @return Grid column index
     */
    public int cjQ(int longitude) {
//	if (longitude >= -180 && longitude <= 180) {
	    int t = ((longitude + gs) / gs) - 1;
	    // return longitude / gs + grid.cols() / 2;
	    return longitude < 0 ? (-t - 1) : grid.cols() - (t + 1);
//	}
//
//	throw new IllegalArgumentException(
//		"Longitude needs to comply with: -180 <= longitude <= 180.");
    }

    /**
     * The longitude of the position of the Earth's surface where the Sun is
     * directly overhead at a given time.
     * 
     * @param t
     *            time in minutes
     * @return longitude of the position.
     */
    public double LSun(double t) {
	double ot = Ot(t);

	return (ot < 90) ? -ot : 180 - ot;
    }

    /**
     * Cell's heat attenuation on the Equator at time t
     * 
     * @param t
     *            time in minutes
     * @return Cell's heat attenuation
     */
    public double at(double t) {
	double ot = Ot(t);

	return (Math.abs(ot) < 90) ? Math.cos(Math.toRadians(ot)) : 0;
    }

    /**
     * Cell's combined attenuation
     * 
     * @param cell
     * @param t
     *            time in minutes
     * @return Cell's combined attenuation
     */
    public double ksi(Cell cell, double t) {
	return isDaylight(cell, t) ? cell.d * at(t) : 0;
    }

    /**
     * The Grid Columns in daylight at time t
     * 
     * @param t
     *            time in minutes
     * @return An array with column indices in daylight
     */
    public int[] tau(double t) {
	int[] colsInDaylight = new int[12];

	int jt = Jt(t);
	int j = 0;
	int c;

	for (int i = 5; i >= -6; i--, j++) {
	    c = jt + i * gcptz;

	    if (c < 0) {
		c = grid.cols() - c;
	    }

	    colsInDaylight[j] = c;
	}

	return colsInDaylight;
    }

    /**
     * Checks if a cell is in daylight at time t
     * 
     * @param cell
     * @param t
     *            time in minutes
     * @return true if in daylight, false otherwise
     */
    public boolean isDaylight(Cell cell, double t) {
	int[] values = tau(t);
	int col = cell.col;

	for (int value : values) {
	    if (value == col) {
		return true;
	    }
	}

	return false;
    }

    public void print() {
	StringBuffer sb = new StringBuffer();

	for (Cell[] row : grid.cells) {
	    for (Cell cell : row) {
		sb.append(cell.Tt).append(" ");
	    }

	    sb.append("\n");
	}

	System.out.println(sb.toString());
    }

    public void test(double t) {
	Cell[][] cells = grid.cells;
	double TNeighbors;

	for (Cell[] row : cells) {
	    for (Cell cell : row) {
		TNeighbors = grid.TNeighbors(cell);

		System.out.println("[" + cell.row + "," + cell.col + "]: "
			+ cell.T0 + " - " + TNeighbors + " - " + " - "
			+ ksi(cell, t)); // + (ksi(cell, t) * TSun));
	    }
	}
    }

    public void iterate(double t) {
	Cell[][] cells = grid.cells;
	double TNeighbors;

	for (int i = 0; i < t; i++) {
	    System.out.println("----------");
	    for (Cell[] row : cells) {
		for (Cell cell : row) {
		    TNeighbors = grid.TNeighbors(cell);

		    if (TNeighbors <= cell.T0) {
			TNeighbors *= -1;
		    }

		    cell.Tt = cell.T0 + (ksi(cell, i) * TSun)
			    - grid.TCool(cell) + TNeighbors;
		}
	    }

	    for (Cell[] row : cells) {
		for (Cell cell : row) {
		    cell.T0 = cell.Tt;
		    cell.T = cell.Tt;
		}
	    }

	    // System.out.println("Grid:\n" + grid);
	}
    }

    public Grid getTmpGrid() {
	Grid gridTmp = new Grid(arguments.gridSpacing,
		new Random().nextInt(arguments.initialTemperature));

//	if (true) {
//	    return gridTmp;
//	}
	
	Cell cellTmp;

	for (Cell[] row : gridTmp.cells) {
	    for (Cell cell : row) {
		cellTmp = grid.getCell(cell.row, cell.col);
		cell.T0 = cellTmp.T0;
		cell.T = cellTmp.T;
		cell.Tt = cellTmp.Tt;
	    }
	}

	return gridTmp;
    }
}
