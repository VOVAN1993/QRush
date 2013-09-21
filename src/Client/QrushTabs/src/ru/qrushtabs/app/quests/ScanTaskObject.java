package ru.qrushtabs.app.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScanTaskObject {
 	public TreeMap<String, String[]> concreteCodes;
	public TreeMap<String, Boolean> usedConcreteCodes;
	public int usedConcreteCodesCount=0;
	
	public String commonCodes[][];
	public Boolean usedCommonCodes[];
	public String commonCodesName;
	public int commonCodesCount=0;
	public int usedCommonCodesCount=0;
	
	public int someCodesCount=0;
	public int someUsedCodesCount=0;
 
	public static ScanTaskObject parse(JSONObject scansJSONObject)
			throws NumberFormatException, JSONException {
		ScanTaskObject rf = new ScanTaskObject();
		if (scansJSONObject.has("someCodesCount"))
			rf.someCodesCount = scansJSONObject.getInt("someCodesCount");
		
		if (scansJSONObject.has("someUsedCodesCount"))
			rf.someUsedCodesCount = scansJSONObject.getInt("someUsedCodesCount");
		
		if (scansJSONObject.has("concreteCodes")) {
			rf.concreteCodes = new TreeMap<String, String[]>();
			rf.usedConcreteCodes = new TreeMap<String, Boolean>();
			rf.usedConcreteCodesCount = scansJSONObject.optInt("usedConcreteCodesCount");
			JSONObject concreteCodesJSON = scansJSONObject.getJSONObject("concreteCodes");
			Iterator<String> keys = concreteCodesJSON.keys();
			while (keys.hasNext()) {
				String key = keys.next();

				try {
					JSONArray codesJSONArray = concreteCodesJSON.getJSONArray(key);
					String codes[] = new String[codesJSONArray.length()];
							
					for(int i = 0;i<codes.length;i++)
						codes[i] = codesJSONArray.get(i).toString();
					rf.concreteCodes.put(key,codes);
					rf.usedConcreteCodes.put(key,false);
//					for (int i = 0; i < codesJSONArray.length(); i++) {
//						codes[i] = codesJSONArray.get(i).toString();
//					}

				} catch (Exception e) {

				}

			}

		}
		
		if (scansJSONObject.has("usedConcreteCodes")) {
			
			JSONObject usedConcreteCodesJSON = scansJSONObject.getJSONObject("usedConcreteCodes");
			Iterator<String> keys = usedConcreteCodesJSON.keys();
			while (keys.hasNext()) {
				String key = keys.next();

				try {
					 
					rf.usedConcreteCodes.put(key,usedConcreteCodesJSON.optBoolean(key));
				 
				} catch (Exception e) {

				}

			}

		}
		else
		{
			
		}
		if (scansJSONObject.has("commonCodes")) { 
			JSONArray commonCodesJSONArray = (JSONArray)scansJSONObject.getJSONArray("commonCodes");
			rf.commonCodesCount = scansJSONObject.optInt("commonCodesCount");
			rf.commonCodesName = scansJSONObject.optString("commonCodesName");
			rf.usedCommonCodesCount = scansJSONObject.optInt("usedCommonCodesCount");
			rf.usedCommonCodes = new Boolean[commonCodesJSONArray.length()];
			 
				for (int i = 0; i < rf.usedCommonCodes.length; i++) {	 
	 				rf.usedCommonCodes[i] = false;	 
				}
			 
			 
			rf.commonCodes = new String[commonCodesJSONArray.length()][];
			for (int i = 0; i < commonCodesJSONArray.length(); i++) {
				 
				JSONArray subCommonCodesJSONArray = (JSONArray)commonCodesJSONArray.get(i);
				rf.commonCodes[i] = new String[subCommonCodesJSONArray.length()];
				for(int j = 0;j < subCommonCodesJSONArray.length();j++)
					rf.commonCodes[i][j] = subCommonCodesJSONArray.get(j).toString();
			}
		}
		
		if (scansJSONObject.has("usedCommonCodes")) { 
			JSONArray usedCommonCodes = (JSONArray)scansJSONObject.getJSONArray("usedCommonCodes");			
			
			for (int i = 0; i < usedCommonCodes.length(); i++) {	 
 				rf.usedCommonCodes[i] = (Boolean)usedCommonCodes.get(i);	 
			}
		}
	 
		return rf;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONObject unparse(ScanTaskObject task)
			throws NumberFormatException, JSONException {
		JSONObject json = new JSONObject();
		if(task.concreteCodes!=null)
		{
			
			
			JSONObject cc = new JSONObject();
			    //json.put("concreteCodes", task.concreteCodes);
			    Iterator it = task.concreteCodes.entrySet().iterator();
			    while (it.hasNext()) {
 			       Map.Entry pairs = (Map.Entry)it.next();
 			       String[] codes = (String[])pairs.getValue();
 			       JSONArray codesArray = new JSONArray();
 			       
 			       for(int i = 0;i < codes.length;i++)
 			       {
 			    	   codesArray.put(codes[i]);
 			       }
 			       cc.put((String)pairs.getKey(), codesArray);
			        
 			    }	
			    json.put("concreteCodes", cc);
			    //json.put("usedConcreteCodes", task.usedConcreteCodes);
			    JSONObject cc1 = new JSONObject();
			    it = task.usedConcreteCodes.entrySet().iterator();
			    while (it.hasNext()) {
 			        Map.Entry pairs = (Map.Entry)it.next();
 			        cc1.put((String) pairs.getKey(), ((Boolean)pairs.getValue()).booleanValue());
			        
 			    }	
			    json.put("usedConcreteCodes", cc1);
				json.put("usedConcreteCodesCount", task.usedConcreteCodesCount);
				
			 
			
		}
		
		if(task.commonCodes!=null)
		{
			 
			
			JSONArray cc = new JSONArray();
			for(int i = 0;i<task.commonCodes.length;i++)
			{
				JSONArray subcc = new JSONArray();
				for(int j = 0;j < task.commonCodes[i].length;j++)
				{
					subcc.put(task.commonCodes[i][j]);
				}
				cc.put(subcc);
			}
			json.put("commonCodes", cc);
			json.put("commonCodesCount", task.commonCodesCount);
			JSONArray usedcc = new JSONArray();
			for(int i = 0;i<task.usedCommonCodes.length;i++)
			{
				usedcc.put(task.usedCommonCodes[i].booleanValue());
			}
			json.put("usedCommonCodes", usedcc);
 			json.put("usedCommonCodesCount", task.usedCommonCodesCount);
			json.put("commonCodesName", task.commonCodesName);
		}
		if(task.someCodesCount>0)
		{
			json.put("someCodesCount", task.someCodesCount);
 			json.put("someUsedCodesCount", task.someUsedCodesCount);
		}
		return json;
	}

	public void putProgress(ArrayList<ProgressItem> progress) {
	 
		if(commonCodes.length>0)
		{
			int usedCodes = 0;
			for(int i = 0;i < usedCommonCodes.length;i++)
				if(usedCommonCodes[i])
					usedCodes++;
					
			ProgressItem pi = new ProgressItem(commonCodesName,commonCodesCount,usedCodes);
			progress.add(pi);
		}
		
		
		if(concreteCodes.size()>0)
		{
			int usedCodes = 0;
			 Iterator it = usedConcreteCodes.entrySet().iterator();
			    while (it.hasNext()) {
			    	ProgressItem pi;
			        Map.Entry pairs = (Map.Entry)it.next();
			        if(usedConcreteCodes.get(pairs.getKey()))
			        	pi = new ProgressItem((String)pairs.getKey(),1,1);
			        else
			        	pi = new ProgressItem((String)pairs.getKey(),1,0);
			        progress.add(pi);
 			    }			
		}
		
		if(someCodesCount>0)
		{
			ProgressItem pi = new ProgressItem("Сканы",someCodesCount,someUsedCodesCount);
			progress.add(pi);
		}
		
	}

	public boolean checkScan(String code) 
	{
		boolean flag = true;
		if(concreteCodes.size()>0)
		{
			Iterator it = concreteCodes.entrySet().iterator();
		    while (it.hasNext()) 
		    {
		    	
 		        Map.Entry pairs = (Map.Entry)it.next();
		        String[] cc = (String[])pairs.getValue();
		        for(int i = 0; i < cc.length ; i++)
		        {
		        	if(cc[i].equals(code) && !usedConcreteCodes.get(pairs.getKey()))
		        	{
		        		usedConcreteCodes.put((String) pairs.getKey(), true);
		        		flag = true;
		        	} 
		        }
		         
			}			
			 
		}
		if(commonCodes.length>0)
		{
			for(int i = 0;i < commonCodes.length; i++)
			{
				for(int j = 0;j < commonCodes[i].length;j++)
				{
					if(commonCodes[i][j].equals(code) && !usedCommonCodes[i] && usedCommonCodesCount < commonCodesCount)
					{
						usedCommonCodes[i] = true;
						usedCommonCodesCount++;
						flag = true;
					}
				}
			}
		}
		
		
		if(someCodesCount>0 && someCodesCount > someUsedCodesCount )
		{
			someUsedCodesCount++;
			flag = true;
		}
		return flag;
		
	}
	
 

}
