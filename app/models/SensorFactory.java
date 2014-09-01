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
			
			for (int i=0; i < controllersArray.size(); i++) {
				controllerJson = (JSONObject) controllersArray.get(i);
				
				if (controllerJson != null) {
					
					boolean value = (Boolean) controllerJson.get("enabled");
			
					if (value == true) {
						
						str = (String) controllerJson.get("Interface");
						
						if (str == "SPI") {
							//we have an SPI interface and need to build the controller
							
							int ch = (Integer) controllerJson.get("SPI-Channel");
							
							hwenv.controller = new SPIController(ch);
						
						}		
						
						jsonArray = (JSONArray) controllerJson.get("Sensors");
						
						if (jsonArray != null) {
							JSONObject jsonSensor = null; 
							String name = null;
							byte ch = -1;
							int enabled = -1;
							String method = null;					
							
							for (i=0; i < jsonArray.size(); i++) {
								
								jsonSensor = (JSONObject) jsonArray.get(i);
								
								name = (String) jsonSensor.get("name");
								ch = (Byte) jsonSensor.get("channel");
								enabled = (Integer) jsonSensor.get("enabled");
								method = (String) jsonSensor.get("Type");
								AnalogSensor analogSensor = new AnalogSensor(name, ch, enabled, hwenv.controller.MinReading, hwenv.controller.MaxReading, method);
								hwenv.analogsensors[hwenv.analogsensors.length] = analogSensor;
								
							}	
						}						
					
					}
				}
			}
		}
		return hwenv;		
	}
}
