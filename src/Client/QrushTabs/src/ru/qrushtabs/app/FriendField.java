package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;


public class FriendField {

	 public String username = "";
	 public String city = "";
	 public static FriendField parse(JSONObject o) throws NumberFormatException, JSONException{
		 	FriendField rf = new FriendField();
	        rf.username = o.optString("username");
	        //rf.city = o.optString("city");
	        return rf;
	    }
}
