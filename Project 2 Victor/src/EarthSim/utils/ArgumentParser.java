package EarthSim.utils;

public class ArgumentParser {

    public static Arguments parse(String[] args) {
	Arguments arguments = new Arguments();
	int len = args.length;
	String arg;

	for (int i = 0; i < len; i++) {
	    arg = args[i];

	    if (Arguments.B.equals(arg)) {
		if (i == len - 1) {
		    System.out.println("Assuming buffer value of 1.");

		    continue;
		}

		try {
		    arguments.bufferSize = Integer.parseInt(args[i + 1]);
		    i++;
		} catch (NumberFormatException nfe) {
		    System.out.println("Assuming buffer value of 1.");
		}
	    } else if (Arguments.S.equals(arg)) {
		arguments.runSimulationOwnThread = true;
	    } else if (Arguments.P.equals(arg)) {
		arguments.runPresentationOwnThread = true;
	    } else if (Arguments.R.equals(arg)) {
		arguments.initiative = Arguments.Initiative.R;
	    } else if (Arguments.T.equals(arg)) {
		arguments.initiative = Arguments.Initiative.T;
	    } else if (Arguments.GS.equals(arg)) {
		if (i == len - 1) {
		    System.out.println("Assuming grid spacing value of 15.");
		    
		    continue;
		}

		try {
		    arguments.gridSpacing = parseGridSpacing(Integer.parseInt(args[i + 1]));
		    i++;
		} catch (NumberFormatException nfe) {
		    System.out.println("Assuming grid spacing value of 15.");
		}
	    }else if (Arguments.TS.equals(arg)) {
		if (i == len - 1) {
		    System.out.println("Assuming time step value of 1.");
		    
		    continue;
		}

		try {
		    arguments.timestep = parseGridSpacing(Integer.parseInt(args[i + 1]));
		    i++;
		} catch (NumberFormatException nfe) {
		    System.out.println("Assuming grid spacing value of 1.");
		}
	    }
	}

	return arguments;
    }

    private static final int[] GRID_SPACING_VALUES = { 1, 2, 3, 4, 5, 6, 9, 10,
	    12, 15, 18, 20, 30, 36, 45, 60, 90, 180 };

    public static int parseGridSpacing(int providedValue) {
	int lowerValue = GRID_SPACING_VALUES[0];

	for (int validValue : GRID_SPACING_VALUES) {
	    if (providedValue > validValue) {
		lowerValue = validValue;
	    } else if (providedValue == validValue) {
		return providedValue;
	    } else {
		break;
	    }
	}

	return lowerValue;
    }

    public static int parseSimulationTimeStep(int providedValue) {
	if (providedValue >= 1 && providedValue <= 1440) {
	    return providedValue;
	}

	return 1;
    }
}
