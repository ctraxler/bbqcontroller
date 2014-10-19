package models;

import java.util.Calendar;
import java.text.SimpleDateFormat;


public class AnalogSensor {
	
	
	private String name;
	private int lowReading;
	private int highReading;  
	private byte ch;
	private int currentReading;
	private Calendar currentreadingcal = Calendar.getInstance();
	private  SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS Z");
	private int  readings[] = new int[60];
	private int readingsIdx = -1;
	private int readingsCnt = 0;
	private String method = null;
	
	
	public String getName() {
		return name;
		
	}
	
	public byte getCH () {
		
		return ch; 
		
	}
	
	
	public int getValue(){
		
		return currentReading;
		
	}
	
	public  AnalogSensor (String name, byte ch, boolean enabled, int low, int high, String methodparm ){
		
		this.name = name;
		this.ch = ch;
		this.lowReading = low;
		this.highReading = high;
		this.currentReading = -1;
		this.method = methodparm;
	}
	
	public void setReading(int reading) {
		currentReading = reading; 
		
		readingsIdx++;
		
		if (readingsIdx == 60) { readingsIdx = 0;}
		readings[readingsIdx] = reading;
		
		if (readingsCnt < 60) { readingsCnt++; }
		
		
//		System.out.println("Setting reading for channel [" + this.ch + "]");
//		System.out.println("Reading is: " + reading);
//		System.out.println("readingsIdx: " + readingsIdx);
//		System.out.println("readingsCnt: " + readingsCnt);
//		System.out.println("Average Reading is: " + this.getAvgValue());

		//need to figure out how to just get the current date to set the time here.
		currentreadingcal = Calendar.getInstance();	
		
	}
	
	public float getAvgValue() {
		
		int i = 0;
		int total = 0;
		
		if (readingsCnt==0) {
			return 0;
		}
		
		
		for (i = 0; i < readingsCnt; i++) {
			total = total + readings[i];
		}
		
		
		return (total / readingsCnt);
	}
	
	public float getReading(){
		
		
		if (this.method == "Analog-Linear") {
			//compute the value of the sendor based upon the reading and the low and high reading possible
			//perhaps add information about the resolution of the controller
			
			float milliVolts = 0;
			float tempCelsius = 0;
			
			milliVolts = this.currentReading * ( 3300 / (this.highReading - this.lowReading));
			
			System.out.println("Millivolts read: " + String.format("%.2f", milliVolts));
			
			
			tempCelsius = ((milliVolts - 100) /10) - 40;
			
			System.out.println("Temp is: " + String.format("%.2f", tempCelsius));
			
			return  tempCelsius;
		}
		
	return -273;
	}
	
	public String getTimeAsString() {
		
		return simpledateformat.format(currentreadingcal.getTime());
	}
	
}
