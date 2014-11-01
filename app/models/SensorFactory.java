package models;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.AnalogSensor;

public class SensorFactory {
	
	
	public HWEnvironment CreateHWEnv(JSONArray controllersArray) {

		HWEnvironment hwenv = new HWEnvironment();
				
		if (controllersArray != null) {
			JSONObject controllerJson = null;
			JSONArray jsonArray = null;
			JSONObject json = null;
			String str = null;
			Boolean flagEnabled = false; 
			
			System.out.println("controllersArray  ==> " + controllersArray.toString());
			
			for (int i=0; i < controllersArray.size(); i++) {
				controllerJson = (JSONObject) controllersArray.get(i);
				
				if (controllerJson != null) {
					
					System.out.println("controllerJson ==> " + controllerJson);
					
					controllerJson = (JSONObject) controllerJson.get("Controller");
					
					System.out.println("controllerJson after eating Controller key ==> " + controllerJson);
					
					flagEnabled = (Boolean) controllerJson.get("enabled");
			
					if (flagEnabled == true) {
						
						System.out.println("Controller is enabled...");
						
						
						
						str = (String) controllerJson.get("Interface");
						
						System.out.println("Interface is: " + str);
						
						if (str.equals("SPI")) {
							//we have an SPI interface and need to build the controller
							
							int ch = (Integer) controllerJson.get("SPI-Channel");
							
							System.out.println("Creating SPI Controller on channel " + Integer.toString(ch));
							
							hwenv.controller = new SPIController(ch);
							System.out.println("Controller created"); 
						}		
						
						jsonArray = (JSONArray) controllerJson.get("Sensors");
						
						if (jsonArray != null) {
							JSONObject jsonSensor = null; 
							String name = null;
							long ch = -1;
				
							Boolean enabled = false;
							String method = null;
							
							ArrayList <AnalogSensor> al = new ArrayList<AnalogSensor>();
							
							for (i=0; i < jsonArray.size(); i++) {
								
								jsonSensor = (JSONObject) jsonArray.get(i);
								
								System.out.println("Sensor Config " + i + ": " + jsonSensor.toString());
								jsonSensor = (JSONObject) jsonSensor.get("sensor");
								System.out.println("Sensor Config after eating sensor key: " + jsonSensor.toString());
								name = (String) jsonSensor.get("name");
								
								ch = (Long) jsonSensor.get("channel");
								
								System.out.println("Enabled info as byte: " + ch);
								enabled = (Boolean) jsonSensor.get("enabled");
								method = (String) jsonSensor.get("Type");
								AnalogSensor analogSensor = new AnalogSensor(name, ch, enabled, SPIController.MinReading, SPIController.MaxReading, method);
								
								System.out.println("Analog Sensor Array Length: " + al.size());
								
							
								
								al.add(analogSensor);
									
								
							}	
							
							
							hwenv.analogsensors = al.toArray(new AnalogSensor[al.size()]);

							
							                                        							                                      
						}						
					
					}
				}
			}
		}
		return hwenv;		
	}
}
