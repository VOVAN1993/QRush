package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;


public class RatingField {

	 public String username = "";
	 public String city = "";
	 public String balance = "";
	 public String scansCount = "";
	 public static RatingField parse(JSONObject o) throws NumberFormatException, JSONException{
		 	RatingField rf = new RatingField();
	        rf.username = o.optString("username");
	        rf.balance = o.optString("totalSumBalance");
	        rf.scansCount = o.optString("count_scan");
	        rf.city = o.optString("city");
	        return rf;
	    }
}
