package analyzer;
import java.io.*;
import java.util.*;

import org.joda.time.DateTime;

public class analyzer {
	
	int iterationCount =0;
	 String fileIdentifier ="";
     
    
     long startTime, stopTime, elapsedTime;
     long[] executionTimeResults;
     long[] memoryResults;
     long memoryUsed;
	
	public analyzer(int iterations, String fileContains)
	{
		this.iterationCount = iterations;
		executionTimeResults = new long[iterationCount];
		memoryResults = new long[iterationCount];
		this.fileIdentifier = fileContains;
	}
	
	public void setStartTime()
	{
		startTime = System.currentTimeMillis();
	}
	public void setEndTime()
	{
		stopTime = System.currentTimeMillis();
	}
	
	public void analyze()
	{
		File mem_file = new File("C:\\cs6310P3Test\\" + fileIdentifier +"_mem_usage.csv");
	     File time_file = new File("C:\\cs6310P3Test\\"+ fileIdentifier +"_execution_time.csv");
		 System.out.println("memory "+ mem_file.toString());
		 System.out.println("exec time "+ time_file.toString());
		if (!mem_file.exists()) {
        	try {
        		mem_file.createNewFile();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }

        if (!time_file.exists()) {
        	try {
        		time_file.createNewFile();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
        BufferedWriter mem_bw = null;
        BufferedWriter time_bw = null;
        try {
        	FileWriter mem_fw = new FileWriter(mem_file.getAbsoluteFile());
        	mem_bw = new BufferedWriter(mem_fw);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        try {
        	FileWriter time_fw = new FileWriter(time_file.getAbsoluteFile());
        	time_bw = new BufferedWriter(time_fw);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
       
        for (int i = 0; i < iterationCount; i++) {
			
  		
    		
    		executionTimeResults[i] = stopTime - startTime;
    		Runtime rt = Runtime.getRuntime();
    		memoryResults[i] = rt.totalMemory() - rt.freeMemory();
    		rt.gc();
    		
     

    		try {
    			time_bw.write(executionTimeResults[i] + "\n");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		try {
    			mem_bw.write(memoryResults[i]/1024L  + "\n");
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		//System.out.println(Arrays.toString(executionTimeResults));
    		//System.out.println(Arrays.toString(memoryResults));
    	}
    	try {
    		time_bw.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	try {
    		mem_bw.close();
    	} catch (IOException e) {
    	e.printStackTrace();
    	}
    }
}


