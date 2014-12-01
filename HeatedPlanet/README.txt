Heated Planet Program
====================

DESCRIPTION
-----------
  This program simulates temperature of a planet as it is heated by the Sun.

HOW TO RUN
----------

   Compile the code with the following commands:

    
    
   Run the program from the project folder as

      java -cp <path to the executable> PlanetSim.Demo <options> 

   For best results , compile project using Eclipse or the instructions above, then from the project3 directory try:

     java PlanetSim.Demo [-p #] [-g #] [-t #] 
    	-p #: The precision of the data to be stored, in decimal digits after the decimal point.
	-g #: The geographic precision (sampling rate) of the temperature data to be stored. Value should be between 1 and 100.
	-t #: The temporal precision of the temperature data to be stored. Value should be between 1 and 100.

Note: in windows it is necessary to change ./bin to .\bin

USING THE USER INTERFACE
=========================

   Running the program from command line using information from the above section will bring up the graphical user interface for Planel Simulation. This user       

interface will have two options. 
	1) Enter data in various fields and click on "Run" button to run a new Simulation. You can pause/continue or stop the simulation. 
	2) Launch query interface by clicking on "Launch Query Interface" button. This will launch another user interface with various options
	   to run queries on simulation.  
   Please refer to the "GUI Design" section and "Interaction diagram" section of the Design Description document on how to use the graphical user interface.



