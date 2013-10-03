package ru.qrushtabs.app.quests;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TasksObject 
{
	CheckableTasks ct;
	String uncheckableTasks[];
 	
	public static TasksObject parse(JSONObject o)
			throws NumberFormatException, JSONException {
		TasksObject rf = new TasksObject();
		if(o.has("checkable"))
		rf.ct = CheckableTasks.parse(o.getJSONObject("checkable"));
 		return rf;
	}
	public int getScansCount()
	{
		return ct.getScansCount();
	}
	
	public boolean checkScan(String code)
	{
		if(ct==null)
			return false;
		return ct.checkScan(code);
	}
	public boolean checkRescan()
	{
		if(ct==null)
			return false;
		return ct.checkRescan();
	}
	public ArrayList<ProgressItem> getProgress()
	{
		ArrayList<ProgressItem> progress = new ArrayList<ProgressItem>();
		if(ct!=null)
			ct.putProgress(progress);
		
		return progress;
	}
	public static JSONObject unparse(TasksObject to)
	{
		JSONObject json = new JSONObject();
		
		
		
			try {
 				if(to.ct!=null)
				json.put("checkable", CheckableTasks.unparse(to.ct));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return json;
	}
	public boolean canComplete() {
		if(ct!=null)
			return ct.canComplete(); 
		else
			return false;
		 
	}
	
	  
	
}
