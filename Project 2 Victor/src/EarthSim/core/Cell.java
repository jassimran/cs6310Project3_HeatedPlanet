package EarthSim.core;

public class Cell {
    /**
     * Cell row index in Grid
     */
    public int row;

    /**
     * Cell column index in Grid
     */
    public int col;

    /**
     * Temperature value in K
     */
    public double T0;
    
    /**
     * Current temperature in K
     */
    public double T;
    
    /**
     * Temperature at time t
     */
    public double Tt;

    /**
     * Latitude of the origin of cells in row i
     * 
     * d = i - (rows / 2 )) x gs
     */
    public double d;

    /**
     * Longitude of the origin of cells in column j
     * 
     * Q = (j < ((cols)/2)) -> -d ; 360 - d
     * 
     * where d = (j + 1) x gs
     */
    public double Q;

    /**
     * Length of each of the vertical sides of the cell in meters
     * 
     * Lv = C x p
     */
    public double Lv;

    /**
     * Length of the base of the cell in meters.
     * 
     * Lb = cos(d) x Lv
     */
    public double Lb;

    /**
     * Length of the top of the cell in meters
     * 
     * Lt = cos(d + gs) x Lv
     */
    public double Lt;

    /**
     * Height or altitude of the cell in meters
     * 
     * h = sqrt(Lv^2 - 1/4 * (Lv - Lt)^2)
     */
    public double h;

    /**
     * Perimeter of the cell in meters
     * 
     * pm = Lt + Lb + 2 * Lv
     */
    public double pm;

    /**
     * Area of the cell in meters
     * 
     * a = 1/2 * (Lt + Lb) * h
     */
    public double a;

    /**
     * Proportion of the Earth's surface taken by the cell
     * 
     * ra = a / A;
     */
    public double ra;

    /**
     * X-coordinate of the origin of the cells in Grid column j as the number of
     * meters West of the Primary Meridian.
     * 
     * x = (mod(j + (cols / 2), cols) - ((cols / 2) - 1)) x C / cols
     */
    public double x;

    /**
     * Y-coordinate of the origin of the cells in Grid row i as the number of
     * meters North of the Equator.
     * 
     * y = (i - (rows / 2)) x C / rows
     */
    public double y;
    
    /**
     * Proportion of a cell's border shared with its East neighbor
     * 
     * PE = PW = Lv / pm
     */
    public double PE;
    
    /**
     * Proportion of a cell's border shared with its West neighbor
     * 
     * PE = PW = Lv / pm
     */
    public double PW;
    
    /**
     * Proportion of a cell's border shared with its North neighbor
     * 
     * PN = Lt / pm
     */
    public double PN;
    
    /**
     * Proportion of a cell's border shared with its South neighbor
     * 
     * PS = Lb / pm
     */
    public double PS;

    public Cell(int row, int col, double temperature) {
	this.row = row;
	this.col = col;
	this.T0 = temperature;
	T = T0;
    }
}
