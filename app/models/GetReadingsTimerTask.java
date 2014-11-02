package models;

import java.util.TimerTask;
import java.util.ArrayList;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class GetReadingsTimerTask extends TimerTask {

private static JSONArray curReadings = null;
private static JSONArray notsentReadings = null;

private static AnalogSensor[] listAnalogSensors = null;

private static SPIController cont = null;

private static long sessionId = 0;
private static long observationId = 0; 

		public void setSessionId (long session) {
			sessionId = session;
		}


		public void initReadings (JSONArray cur, JSONArray notsent) {
		
			curReadings = cur;
			notsentReadings = notsent;
		
		}
		
		public void initController (SPIController controller) {
			
			cont = controller;
			
		}
		
		


		@Override 
		public void run() {
			
			synchronized (notsentReadings) {
				synchronized (curReadings) {
					curReadings.clear();
					
					for (AnalogSensor as: listAnalogSensors){			
						//get the reading from the sensor and populate the sensor object with it
						as.setReading(cont.readAnalogSensor(as.getCH()));	
						
						JSONObject json = new JSONObject();
						json.put("SESSION", sessionId);
						json.put("OBSERVATION", observationId);
						json.put("TYPE", "Temperature");
						json.put("NAME", as.getName());
						json.put("RAW_VALUE", as.getValue());
						json.put("CONVERTED_READING", as.getReading());
						json.put("UOM", "Celcius");
						json.put("TIME", as.getTimeAsString());
						notsentReadings.add(json);
						curReadings.add(json);
					}	
					observationId++;
				}
			}//sychronized
			
		}  //run
			
		
	} //GetReadingsTimerTask

