package EarthSim;

import presentation.Gui;

public class Demo {
	static boolean simthread = true;
	static boolean presthread = false;
	static boolean simcontrol = false;
	static boolean prescontrol = false;
	static int buffer = 10;
	public static void main(String[] args) {
		
			parseArgs(args);

			if (prescontrol && simcontrol){
				System.out.println("Invalid command line arguments!");
				System.out.println("Program must be run as: java EarthSim.Demo [-s] [-p] [-r|-t] [-b #]");
			}
			else{
				Gui.getInstance(simthread, presthread, simcontrol, prescontrol, buffer );
			}
			
	}
	private static void parseArgs(String[] args) {
		for (int i = 0; i < args.length; i++){
			if(args[i].equalsIgnoreCase("-s")){
				simthread = true;
			}
			else if (args[i].equalsIgnoreCase("-p")){
				presthread = true;
			}
			else if (args[i].equalsIgnoreCase("-r")){
				prescontrol = true;
			}
			else if (args[i].equalsIgnoreCase("-t")){
				simcontrol = true;
			}
			else if (args[i].equalsIgnoreCase("-b")){
				buffer = Integer.valueOf(args[i+1]);
			}
			else
				continue;
		}
	}
}