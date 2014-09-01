package globals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import models.HWEnvironment;
import models.SensorFactory;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class  Global extends GlobalSettings {
	
	HWEnvironment hwenv = null;
		
	@Override
	public void onStart (Application app){
		
		//Get config parameters
		
		JSONObject jsonDBParms = null;
		JSONArray jsonADCParms = null;
		JSONObject jsonDummyParms = null;
		
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(new FileReader("./bbqcontroller.conf"));
				
				System.out.println("Found configuration file");
				JSONObject jsonObject = (JSONObject) obj;
				
				jsonDBParms = (JSONObject) jsonObject.get("DB");
				jsonADCParms = (JSONArray) jsonObject.get("ADC");
				jsonDummyParms = (JSONObject) jsonObject.get("DUMMY");				
			} catch (ParseException e) {
				System.out.println("Unable to parse JSON");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				System.out.println("Unable to find config file");
				e.printStackTrace();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Unknown IOException");
				e.printStackTrace();
			};
			
		
		//Setup for readings
		
		//Create & Init Sensors
			
		SensorFactory sensfactory = new SensorFactory();
		System.out.println("Creating Hardware Environment...");
		hwenv = sensfactory.CreateHWEnv(jsonADCParms);
		System.out.println("Hardware Environment created");
		//Init db
		
		
		
	}
	
	@Override
	public void onStop (Application app){
		

		
	}

}