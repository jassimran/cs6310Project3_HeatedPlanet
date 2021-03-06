package simulation;

/**
 * Contains simulation settings.
 */
public class SimulationSettings {
	/**
	 * Positive integer angular degrees between 1° and 180°; default is 15° (one time zone). 
	 */
	private int gridSpacing = 15;
	
	/**
	 * Positive integer number of minutes between 1 and 1440 (1 day); default is 1 minute.
	 * Represents how much simulated time passes between temperature recalculations.
	 */
	private int simulationTimeStep = 15;
	
	/**
	 * Rate at which the presentation is displayed.
	 */
	private long presentationDisplayRate;
	
	/**
	 * The buffer size
	 */
	private int bufferSize;
	
	/**
	 * Indicates that the Simulation should run in its own thread
	 */
	private boolean sOption;
	
	/**
	 * Indicates that the Presentation should run in its own thread
	 */
	private boolean pOption;
	
	/**
	 * Indicates that the Simulation, after producing an updated grid, should instruct the Presentation to consume it.
	 */
	private boolean tOption;
	
	/**
	 * Indicates that the Presentation, after completing the display of a grid, should instruct the Simulation to produce another.
	 */
	private boolean rOption;
	
	/**
	 * Unique text string denoting the simulation.
	 */
	private String name;
	
	/**
	 * Non-negative integer (Solar) months between 1 and 1200; default 12 (one Solar year).
	 */
	private int simulationLength;
	
	/**
	 * Indicates the precision of the temperature that will be persisted.
	 */
	private int precision;
	
	/**
	 * Indicates the axial tilt, which is the number of degrees between +/-180 in the northern hemisphere at the summer solstice.  
	 */
	private double tilt;

	/**
	 * Indicates the Temporal Accuracy
	 */
	private int temporalAccuracy;
	
	/**
	 * Indicates the Geographical Precision
	 */
	private int geoAccuracy;
	
	/**
	 * Indicates the eccentricity of the planet
	 */
	private double eccentricity;
	
	// earth panel state
	private int numCellsX;
	private int numCellsY;
	private int degreeSeparation;

	public synchronized int getGridSpacing() {
		return this.gridSpacing;
	}
	public synchronized void setGridSpacing(int gridSpacing) {
		this.gridSpacing = gridSpacing;
	}

	public synchronized int getSimulationTimeStep() {
		return this.simulationTimeStep;
	}
	public synchronized void setSimulationTimeStep(int simulationTimeStep) {
		this.simulationTimeStep = simulationTimeStep;
	}

	public synchronized long getPresentationDisplayRate() {
		return this.presentationDisplayRate;
	}
	public synchronized void setPresentationDisplayRate(long presentationDisplayRate) {
		this.presentationDisplayRate = presentationDisplayRate;
	}

	public synchronized int getBufferSize() {
		return this.bufferSize;
	}
	public synchronized void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public synchronized boolean isSOption() {
		return this.sOption;
	}
	public synchronized void setSOption(boolean sOption) {
		this.sOption = sOption;
	}

	public synchronized boolean isPOption() {
		return this.pOption;
	}
	public synchronized void setPOption(boolean pOption) {
		this.pOption = pOption;
	}

	public synchronized boolean isTOption() {
		return this.tOption;
	}
	public synchronized void setTOption(boolean tOption) {
		this.tOption = tOption;
	}

	public synchronized boolean isROption() {
		return this.rOption;
	}
	public synchronized void setROption(boolean rOption) {
		this.rOption = rOption;
	}

	public synchronized int getPrecision() {
		return precision;
	}
	public synchronized void setPrecision(int precision) {
		this.precision = precision;
	}
	
	public synchronized double getAxialTilt() {
		return tilt;
	}
	public synchronized void setAxialTilt(double axialTilt) {
		this.tilt = axialTilt;
	}
	
	public synchronized int getTemporalAccuracy() {
		return temporalAccuracy;
	}
	public synchronized void setTemporalAccuracy(int temporalAccuracy) {
		this.temporalAccuracy = temporalAccuracy;
	}
	
	public synchronized int getGeoAccuracy() {
		return geoAccuracy;
	}
	public synchronized void setGeoAccuracy(int geoAccuracy) {
		this.geoAccuracy = geoAccuracy;
	}
	
	public synchronized double getEccentricity() {
		return eccentricity;
	}
	public synchronized void setEccentricity(double eccentricity) {
		this.eccentricity = eccentricity;
	}
	
	public synchronized String getName() {
		return name;
	}
	public synchronized void setName(String name) {
		this.name = name;
	}
	
	public synchronized int getSimulationLength() {
		return simulationLength;
	}
	public synchronized void setSimulationLength(int simulationLength) {
		this.simulationLength = simulationLength;
	}
	
	public synchronized int getNumCellsX() {
		return numCellsX;
	}
	public synchronized void setNumCellsX(int numCellsX) {
		this.numCellsX = numCellsX;
	}
	
	public synchronized int getNumCellsY() {
		return numCellsY;
	}
	public synchronized void setNumCellsY(int numCellsY) {
		this.numCellsY = numCellsY;
	}
	
	public synchronized int getDegreeSeparation() {
		return degreeSeparation;
	}
	public synchronized void setDegreeSeparation(int degreeSeparation) {
		this.degreeSeparation = degreeSeparation;
	}
}
