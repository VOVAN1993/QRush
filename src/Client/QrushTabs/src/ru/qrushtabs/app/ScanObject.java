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
	
	 public final static String SCAN = "scan";
	 public final static String RESCAN = "rescan";
	 public final static String QUEST = "quest";
	 
	 public final static String SCAN_ACTION = "scan action";
 
	 public final static String FAILED_SCAN = "failed scan";
	
	 public boolean scanned = false;
	 public String username = "";
	 public String scantype = "scan";
	 public String city = "";
	 public String code = "";
	 public String prize = "50";
	 public String date = "13:27";
	 public String count = "12";
	 public String isPaid = "false";
	 public Bitmap bitmap;
	 public static ScanObject parse(JSONObject o) throws NumberFormatException, JSONException{
		 	ScanObject rf = new ScanObject();
 	        rf.code = o.optString("code");
  	        rf.date = o.optString("date");
  	        rf.prize = o.optString("prize");
  	        rf.scantype = o.optString("scantype");
  	        rf.isPaid = o.optString("isPaid");
  	        if(rf.isPaid.equals("false"))
  	        	rf.prize="0";
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


