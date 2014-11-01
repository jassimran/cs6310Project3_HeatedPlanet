package EarthSim;

import edu.gatech.cs6310.project2.team13.gui.widget.input.UserControls;
import edu.gatech.cs6310.project2.team13.utils.Logging;

public class Demo {
		
	public static void main(String[] args) {
		// [-s] [-p] [-r|-t] [-b #]	
		
		//Turn Off Logging when running Demo
		Logging.turnOffLoggingAlways();
		
		boolean separateThreadForSimulation = false; 
		boolean separateThreadForPresentation = false;
		
		boolean controlMethodSpecified = false;
		boolean terminateForTesting = false;
		int controlMethod = SimulationEngine.MEDIATING;
		
		int displayRate = 5;
		int bufferLength = 1;
		
		String currentArg;
		int i=0;
		while (i<args.length && args[i].startsWith("-")) {
			currentArg = args[i++];
			if(currentArg.equals("-s")) separateThreadForSimulation = true;
			if(currentArg.equals("-p")) separateThreadForPresentation = true;
			if(currentArg.equals("-r")) {
				if(controlMethodSpecified) {
					System.out.println("Error: more than one control method was specified. Use only [-r] or [-t]");
					System.exit(0);
				} else {
					controlMethodSpecified = true;
					controlMethod = SimulationEngine.PULLING;
				}
			}
			if(currentArg.equals("-t")) {
				if(controlMethodSpecified) {
					System.out.println("Error: more than one control method was specified. Use only [-r] or [-t]");
					System.exit(0);
				} else {
					controlMethodSpecified = true;
					controlMethod = SimulationEngine.PUSHING;
				}
			}
			if(currentArg.equals("-b")) {
				try {
					bufferLength = Integer.parseInt(args[i++]);
				} catch(NumberFormatException nfe) {
					System.out.println("Error: an invalid number was specified for the buffer length: " + nfe.getMessage());
					System.exit(0);
				}				
			}			
		}
		
		UserControls uic = new UserControls(displayRate,bufferLength,
        		separateThreadForSimulation,separateThreadForPresentation, controlMethod, terminateForTesting);

		// TODO: The next bit of code just gives us a graceful exit before terminating everything...
		// Why TODO? What else needs doing?
		try {
		    Thread.sleep(10000);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block. Re: TODO, what else needs doing?
		    e.printStackTrace();
		}		
		
	}
}
