package models;


import org.json.simple.JSONArray;


public class Readings {

	private static JSONArray currentReadings = null;
	private static JSONArray unsentReadings = null;
	
	public JSONArray getCurrentReadings() {
		return currentReadings;
	}
	
	public JSONArray getUnsentReadings() {
		return unsentReadings;
	}
	

}
