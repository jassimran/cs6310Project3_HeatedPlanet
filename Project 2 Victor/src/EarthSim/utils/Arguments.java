package EarthSim.utils;

public class Arguments {
    public static enum Initiative {
	T, R, NONE
    };

    public static final String S = "-s";
    public static final String P = "-p";
    public static final String T = "-t";
    public static final String R = "-r";
    public static final String B = "-b";

    public static final String GS = "-gs";
    public static final String TS = "-ts";

    public boolean runSimulationOwnThread; // -s
    public boolean runPresentationOwnThread; // -p
    public Initiative initiative = Initiative.NONE; // [-t | -r]
    public int bufferSize = 1; // -b #,

    public int gridSpacing = 15;
    public int timestep = 1;

    public int masterSleepRate = 1000;
    public int simulationSleepRate = 1000;
    public int presentationSleepRate = 1000;
    
    public int pauseSleepRate = 1000;
    
    public int presentationRate = 1000;
    
    public int initialTemperature = 288;
    
    public int awaitTimeout = 5000 * 60; // 5 minutes

    @Override
    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append("EarthSim.Demo");

	if (runSimulationOwnThread) {
	    sb.append(" ").append(S);
	}

	if (runPresentationOwnThread) {
	    sb.append(" ").append(P);
	}

	switch (initiative) {
	case T:
	    sb.append(" ").append(Arguments.T);
	    break;
	case R:
	    sb.append(" ").append(Arguments.R);
	    break;
	case NONE:
	    break;
	}

	sb.append(" ").append(B).append(" ").append(bufferSize);

	sb.append(" ").append(GS).append(" ").append(gridSpacing);

	sb.append(" ").append(TS).append(" ").append(timestep);

	return sb.toString();
    }
}
