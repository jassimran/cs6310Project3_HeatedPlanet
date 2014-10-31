package EarthSim.core;

import GUI.TemperatureGrid;

public class Grid implements TemperatureGrid {
    public static enum Direction {
	NORTH, SOUTH, WEST, EAST
    };

    /**
     * Number of cells in the grid
     */
    public int N;

    /**
     * Average grid cell size
     * 
     * n = A / N
     */
    public double n;

    /**
     * Grid cells
     */
    public Cell[][] cells;

    /**
     * Number of columns
     * 
     * cols = 360 / gs
     */
    private int cols;
    
    /**
     * Number of rows
     * 
     * rows = 180 / gs
     */
    private int rows;
    
    /**
     * Grid spacing
     */
    private int gs;
    
    /**
     * Proportion of the Equator used by one unit of grid spacing
     */
    private double p;

    public Grid(int gridSpacing) {
	this(gridSpacing, 0);
    }

    public Grid(int gridSpacing, double initialTemperature) {
	gs = gridSpacing;
	p = gs / 360d;

	rows = 180 / gs;
	cols = 360 / gs;

	N = rows * cols;
	
	n = Earth.A / N;

	cells = new Cell[rows][cols];

	Cell tmpCell;
	int dQ;

	for (int row = 0; row < rows; row++) {
	    for (int col = 0; col < cols; col++) {
		tmpCell = new Cell(row, col, initialTemperature);
		// Latitude
		tmpCell.d = (row - (rows / 2)) * gs;
		// Longitude
		dQ = (col + 1) * gs;
		tmpCell.Q = (col < cols / 2) ? -dQ : 360 - dQ;
		tmpCell.Lv = Earth.C * p;
		// Length of base
		tmpCell.Lb = Math.cos(Math.toRadians(tmpCell.d)) + tmpCell.Lv;
		// Length of top
		tmpCell.Lt = Math.cos(Math.toRadians(tmpCell.d + gs))
			* tmpCell.Lv;
		// Height
		tmpCell.h = Math.sqrt(Math.pow(tmpCell.Lv, 2) - 0.25
			* (Math.pow(tmpCell.Lb - tmpCell.Lt, 2)));
		// Perimeter
		tmpCell.pm = tmpCell.Lt + tmpCell.Lb + 2 * tmpCell.Lv;
		// Area
		tmpCell.a = 0.5 * (tmpCell.Lt + tmpCell.Lb) * tmpCell.h;
		// Proportion of Earth's surface
		tmpCell.ra = tmpCell.a / Earth.A;
		// X-coordinate
		tmpCell.x = ((col + 0.5 * cols) % cols - (0.5 * cols - 1))
			* (Earth.C / cols);
		// Y-coordinate
		tmpCell.y = (row - 0.5 * rows) * (Earth.C / rows);
		// East border proportion
		tmpCell.PE = tmpCell.Lv / tmpCell.pm;
		// West border proportion
		tmpCell.PW = tmpCell.PE;
		// North border proportion
		tmpCell.PN = tmpCell.Lt / tmpCell.pm;
		// South border proportion
		tmpCell.PS = tmpCell.Lb / tmpCell.pm;

		cells[row][col] = tmpCell;
	    }
	}
    }

    public int cols() {
	return cols;
    }

    public int rows() {
	return rows;
    }

    public Cell getCell(int row, int col) {
	if (row < 0 || row >= rows) {
	    return null;
	}

	if (col < 0 || col >= cols) {
	    return null;
	}

	return cells[row][col];
    }

    public double TNeighbors(int row, int col) {
	return TNeighbors(getCell(row, col));
    }

    public double TNeighbors(Cell cell) {
	Cell north = getNeighbor(Direction.NORTH, cell);
	Cell south = getNeighbor(Direction.SOUTH, cell);
	Cell east = getNeighbor(Direction.EAST, cell);
	Cell west = getNeighbor(Direction.WEST, cell);

	return (cell.PN * north.T) + (cell.PS * south.T) + (cell.PE * east.T)
		+ (cell.PW * west.T);
    }

    public double TCool(int row, int col) {
	return TCool(getCell(row, col));
    }

    public double TCool(Cell cell) {
	double B = cell.a / n;
	double tAvg = TAvg();
	double tSun = Earth.TSun;
	double y = cell.T0 / tAvg;

	return -B * y * tSun;
    }

    public Cell getNeighbor(Direction direction, Cell cell) {
	return getNeighbor(direction, cell.row, cell.col);
    }

    public Cell getNeighbor(Direction direction, int row, int col) {
	Cell neighbor = null;

	switch (direction) {
	case NORTH:
	    neighbor = (row + 1 <= rows - 1) ? cells[row + 1][col]
		    : cells[row][(col + (int) cols / 2) % cols];

	    break;
	case SOUTH:
	    neighbor = (row - 1 >= 0) ? cells[row - 1][col]
		    : cells[row][(col + (int) cols / 2) % cols];

	    break;
	case WEST:
	    neighbor = cells[row][(col + 1) % cols];

	    break;
	case EAST:
	    neighbor = cells[row][(col - 1 + cols) % cols];

	    break;
	}

	return neighbor;
    }

    /**
     * Average temperature of all cells in the grid
     * 
     * @return Average temperature
     */
    public double TAvg() {
	double totalTemp = 0;

	for (Cell[] row : cells) {
	    for (Cell cell : row) {
		totalTemp += cell.T;
	    }
	}

	return totalTemp / N;
    }

    @Override
    public String toString() {
	StringBuffer sb = new StringBuffer();

	for (Cell[] row : cells) {
	    for (Cell cell : row) {
		sb.append(String.format("%.2f", cell.T)).append("\t");
	    }

	    sb.append("\n");
	}

	return sb.toString();
    }

    @Override
    public double getTemperature(int x, int y) {
	Cell cell = getCell(x,y);
	
	//return getCell(x, y).T;
	return cell != null ? cell.T : 0;
    }

    @Override
    public float getCellHeight(int x, int y) {
    	
    	//System.out.println("x,y : [" + x + "," + y + "]");
	Cell cell = getCell(x,y);
	
	//return (float) getCell(x, y).h;
	return cell != null ? (float) cell.h : 0f;
    }
    
    
    // Temp
    
    /**
     * The grid row i corresponding to latitude
     * 
     * @param latitude
     * @return Grid row index
     */
    public int rid(int latitude) {
//	if (latitude >= -90 && latitude <= 90) {
	    return (int) latitude / gs + rows / 2;
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
	    return longitude < 0 ? (-t - 1) : cols - (t + 1);
//	}
//
//	throw new IllegalArgumentException(
//		"Longitude needs to comply with: -180 <= longitude <= 180.");
    }
}
