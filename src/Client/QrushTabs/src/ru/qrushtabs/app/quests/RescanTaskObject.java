package ru.qrushtabs.app.quests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RescanTaskObject {
	public int rescansCount = 0;
	public int rescannedCount = 0;
 

	public static RescanTaskObject parse(JSONObject rescansJSONObject)
			throws NumberFormatException, JSONException {
		RescanTaskObject rf = new RescanTaskObject();
		rf.rescannedCount = rescansJSONObject.optInt("rescannedCount");
		rf.rescansCount = rescansJSONObject.optInt("rescansCount");
		
		return rf;
	}
	
	public static JSONObject unparse(RescanTaskObject task)
			throws NumberFormatException, JSONException {
		JSONObject json = new JSONObject();
		json.put("rescansCount", task.rescansCount);
		json.put("rescannedCount", task.rescannedCount);
		return json;
	}

	public void putProgress(ArrayList<ProgressItem> progress) {
		ProgressItem pi = new ProgressItem();
		pi.name = "Ресканы";
		pi.full = rescansCount;
		pi.part = rescannedCount;
		progress.add(pi);
		
	}
	
 

}
