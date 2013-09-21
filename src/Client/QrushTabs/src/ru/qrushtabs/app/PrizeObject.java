package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class PrizeObject {

	  
	 public String prize = "0";
	 public String maxPrize = "0";
	 public String isTwice = "false";
	 public Bitmap bitmap;
	 public static PrizeObject parse(JSONObject o) throws NumberFormatException, JSONException{
		 	PrizeObject rf = new PrizeObject();
 	        rf.maxPrize = o.optString("code");
  	        rf.isTwice = o.optString("time");
  	        rf.prize = o.optString("prize");
 	        return rf;
	    } 
	
}
