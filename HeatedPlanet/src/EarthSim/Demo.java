package EarthSim;

import java.sql.SQLException;

import org.h2.tools.Server;

import presentation.Gui;

public class Demo {
	static boolean simthread = true;
	static boolean presthread = false;
	static boolean simcontrol = false;
	static boolean prescontrol = false;
	static int buffer = 10;
	public static void main(String[] args) {
		
			parseArgs(args);
			

			// TODO remove before deliverable
			try {
				// start H2 web server
				Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
				Server webServer = Server.createWebServer("-web","-webAllowOthers", "-webPort", "8082").start();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (prescontrol && simcontrol){
				System.out.println("Invalid command line arguments!");
				System.out.println("Program must be run as: java EarthSim.Demo [-s] [-p] [-r|-t] [-b #]");
			}
			else{
				Gui.getInstance(simthread, presthread, simcontrol, prescontrol, buffer );
				// TODO register UI listener to close H2 web servers
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