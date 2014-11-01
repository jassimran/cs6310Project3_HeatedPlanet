package edu.gatech.cs6310.project2.team13.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logging {
	
	private static boolean logging = false;
	private static boolean trueValue = true;
	private static boolean falseValue = false;
	private static File logFile = new File("logFile.txt");
	private static FileWriter fw;
	private static BufferedWriter bw;
	
	private static boolean isLogging() {
		return logging;
	}
	private static void setLogging(boolean logging) {
		Logging.logging = logging;
	}
	public static void turnOnLogging() {
		setLogging(trueValue);
	}
	public static void turnOffLogging() {
		setLogging(falseValue);
	}
	
	public static void turnOffLoggingAlways(){
		falseValue = false;
		trueValue = false;
		turnOffLogging();
	}
	
	public static void turnOnLoggingAlways(){
		falseValue = true;
		trueValue = true;
		turnOnLogging();
	}
	
	public static void writeOut(String msg){
		if(isLogging()){
			System.out.println(messagePrefix()+" "+msg);
		}
	}
	public static void writeErr(String msg){
		if(isLogging()){
			System.err.println(messagePrefix()+" "+msg);
		}
	}
	/*public static void writeOutToFile(boolean showPrefix, String msg){
		if(isLogging()){
			try {
				if(showPrefix){
					bw.write(messagePrefix()+" "+msg);
				}else{
					bw.write(msg);
				}
				bw.flush();
			} catch (IOException e) {
				writeErr(e.getMessage());
			}
		}
	}*/
	
	public static void writeOut(boolean showPrefix, String msg){
		if(isLogging()){
			if(showPrefix){
				writeOut(msg);
			}else{
				System.out.println(msg);
			}
		}
	}
	public static void writeErr(boolean showPrefix, String msg){
		if(isLogging()){
			if(showPrefix){
				writeErr(msg);
			}else{
				System.err.println(msg);
			}
		}
	}
	
	private static String messagePrefix(){
		return "["+Thread.currentThread().getStackTrace()[3].toString()+
					"(Stack Depth="+(Thread.currentThread().getStackTrace().length-3)+")]";
	}
}
