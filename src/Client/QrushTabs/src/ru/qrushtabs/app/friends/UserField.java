package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;


public class UserField {

	 public String username = "";
	 public String city = "";
	 public static UserField parse(JSONObject o) throws NumberFormatException, JSONException{
		 	UserField rf = new UserField();
	        rf.username = o.optString("username");
	        rf.city = o.optString("city");
	        return rf;
	    }
}
