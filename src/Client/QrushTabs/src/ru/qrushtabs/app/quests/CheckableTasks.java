package ru.qrushtabs.app.quests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CheckableTasks {
	private RescanTaskObject rescanTask;
	private ScanTaskObject scanTask;

	public static CheckableTasks parse(JSONObject o)
			throws NumberFormatException, JSONException {
		CheckableTasks rf = new CheckableTasks();
		
		JSONObject rescans = o.getJSONObject("rescans");
		rf.rescanTask = RescanTaskObject.parse(rescans);
		
		JSONObject scans = o.getJSONObject("scans");
		rf.scanTask = ScanTaskObject.parse(scans);
	 
		return rf;
	}
	
	public static JSONObject unparse(CheckableTasks ct)
			throws NumberFormatException, JSONException {
		 JSONObject json = new JSONObject();
		 if(ct.scanTask!=null)
		 json.put("scans", ScanTaskObject.unparse(ct.scanTask));
		 
		 if(ct.rescanTask!=null)
			 json.put("rescans", RescanTaskObject.unparse(ct.rescanTask));
 
		return json;
	}

	public  int getScansCount() 
	{
		// TODO Auto-generated method stub
		return 1;
	}

	public void putProgress(ArrayList<ProgressItem> progress) {
	   if(rescanTask!=null)
		   rescanTask.putProgress(progress);
	   
	   if(scanTask!=null)
		   scanTask.putProgress(progress);
		
	}

	public boolean checkScan(String code) {
 		if(scanTask!=null)
 			return scanTask.checkScan(code);
 		
 		return false;
			
		
	}

	public boolean checkRescan() {
		if(rescanTask!=null && rescanTask.rescansCount < rescanTask.rescannedCount )
		{
			 rescanTask.rescansCount++;
			 return true;
		}
 			 
		return false;
	}
}
