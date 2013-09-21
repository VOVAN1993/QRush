package ru.qrushtabs.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class ScanObject 
{
	
	 public static String SCAN = "scan";
	 public static String RESCAN = "rescan";
	 public static String QUEST = "quest";
	
	 public String username = "";
	 public String scantype = "scan";
	 public String city = "";
	 public String code = "";
	 public String prize = "50";
	 public String date = "13:27";
	 public String count = "12";
	 public Bitmap bitmap;
	 public static ScanObject parse(JSONObject o) throws NumberFormatException, JSONException{
		 	ScanObject rf = new ScanObject();
 	        rf.code = o.optString("code");
  	        rf.date = o.optString("time");
  	        rf.prize = o.optString("prize");
  	        rf.scantype = o.optString("scantype");
	        return rf;
	    } 
	
			
	 public static ScanObject parseQuest(JSONObject o) throws NumberFormatException, JSONException{
		 	ScanObject rf = new ScanObject();
	        //rf.code = o.optString("code");
	        //rf.date = o.optString("time");
		 	rf.date = o.optString("completeDate");
		 	rf.prize = o.optString("prize");
	        rf.scantype = "quest";
	        return rf;
	    } 
}


