package EarthSim;

import EarthSim.core.Cell;
import EarthSim.core.Earth;
import EarthSim.core.Grid;
import EarthSim.utils.ArgumentParser;
import EarthSim.utils.Arguments;

public class Demo {

	public static void main(String[] args) {

		Arguments arguments = ArgumentParser.parse(args);

		Earth earth = new Earth(arguments);
		Grid grid = earth.getGrid();

		for (Cell[] row : grid.cells) {
			for (Cell cell : row) {
				System.out.println("[" + cell.row + "," + cell.col + "]: "
						+ " " + cell.d + " " + cell.Q+ " " + cell.x + " " + cell.y);
			}
		}
		
		int tmpC = 0;
		for (int j = -180; j <= 165 ; j = j + 15) {
		    tmpC++;
		    
		}
		
		System.out.println("Y: " + tmpC);
		
		 tmpC = 0;
		 
		 for (int i = 75 ; i >= -90; i = i - 15) {
		    tmpC++;
		    
		}
		
		System.out.println("X: " + tmpC);
		
		System.out.println(earth.cjQ(0));
		
		int tt = 20;
		
		for (int i = 75 ; i >= -90 && tt >= 0; i = i - 15) {
		    for (int j = -180; j <= 165 ; j = j + 15) {
			System.out.println("Testing: [" + i + "," + j + "] = "+ earth.rid(i) + " - " + earth.cjQ(j));
			
			
		    }
		}
		

		System.out.println(arguments);
		
		
		System.out.println(earth.getGrid());

		if (true) {
			return;
		}

		Controller controller = new Controller(arguments);

		controller.init();

		// Thread simulationThread = null;
		// Thread presentationThread = null;
		// Thread masterThread = null;
		//
		// Simulation simulation;
		// Presentation presentation;
		//
		// // Creating the shared memory object based on the initiative
		// Buffer<Grid> buffer;
		//
		// switch (arguments.initiative) {
		// case T:
		// buffer = new TBuffer<Grid>(arguments.bufferSize);
		// break;
		// case R:
		// buffer = new RBuffer<Grid>(arguments.bufferSize);
		// break;
		// case NONE:
		// default:
		// buffer = new Buffer<Grid>(arguments.bufferSize);
		// break;
		// }
		//
		// // Creating the simulation object
		// simulation = new Simulation(arguments, buffer);
		//
		// // Creating simulation thread
		// if (arguments.runSimulationOwnThread) {
		// simulationThread = new Thread(simulation);
		// }
		//
		// // Creating the presentation object
		// presentation = new Presentation(arguments, buffer);
		//
		// // Creating presentation thread
		// if (arguments.runPresentationOwnThread) {
		// presentationThread = new Thread(presentation);
		// }
		//
		// // Creating master thread
		// masterThread = new Thread(new Master(arguments, simulation,
		// presentation));
		//
		// // Creating GUI
		// HeatedEarthGUI gui = new HeatedEarthGUI(arguments);
		//
		// // Providing reference to UI
		// presentation.gui = gui;
		//
		// // Starting threads
		//
		// if (simulationThread != null) {
		// System.out.println("Running simulation on its own thread");
		// simulationThread.start();
		// }
		//
		// if (presentationThread != null) {
		// System.out.println("Running presentation on its own thread");
		// presentationThread.start();
		// }
		//
		// if (masterThread != null) {
		// System.out.println("Running master");
		// masterThread.start();
		// }
	}
}
