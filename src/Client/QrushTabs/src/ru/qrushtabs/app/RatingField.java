package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;


public class RatingField {

	 public String username = "";
	 public String balance = "";
	 public String scansCount = "";
	 public static RatingField parse(JSONObject o) throws NumberFormatException, JSONException{
		 	RatingField rf = new RatingField();
	        rf.username = o.optString("username");
	        rf.balance = o.optString("balance");
	        rf.scansCount = o.optString("count_scan");
	        return rf;
	    }
}
