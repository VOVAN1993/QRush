package ru.qrushtabs.app.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.*;

import ru.qrushtabs.app.PrizeActivity;
import ru.qrushtabs.app.ProfileInfo;
import ru.qrushtabs.app.QrushTabsApp;
import ru.qrushtabs.app.RatingField;
import ru.qrushtabs.app.RegistrationActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServerAPI {
	public static boolean offlineMod = true;

//	private static String executeHttpResponse(String query, List<NameValuePair> values) {
//		HttpClient client = new DefaultHttpClient();
//		HttpGet request = new HttpGet("http://188.120.235.179/" + query + "/");
//		HttpResponse response = null;
//		try {
//			response = client.execute(request);
//		} catch (ClientProtocolException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		BufferedReader rd = null;
//		StringBuilder sb = new StringBuilder("");
//		try {
//			rd = new BufferedReader(new InputStreamReader(response.getEntity()
//					.getContent()));
//
//			String line = "";
//			while ((line = rd.readLine()) != null) {
//				sb.append(line);
//
//			}
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return sb.toString();
//	}

	private static String executeHttpPostResponse(String query,
			List<NameValuePair> values) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost("http://188.120.235.179/" + query + "/");
		HttpResponse response = null;

		 
		values.add(new BasicNameValuePair("username", ProfileInfo.username));
		values.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(values, HTTP.ASCII));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("http", "Login form get: " + response.getStatusLine());

		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder("");
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				Log.d("http", line);
				sb.append(line);
			}
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		httpclient.getConnectionManager().shutdown();

		
		
		String resp = sb.toString();
		String report = "";
 		try {
			Log.d("http", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if(report.equals("success"))
			{
				if(jsonObj.has("atoken"))
				      ProfileInfo.userToken = (String) jsonObj.get("atoken");
			}
			else
			{
				
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	public static String signUp() {

		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		nvps.add(new BasicNameValuePair("what", "user"));
		nvps.add(new BasicNameValuePair("email", ProfileInfo.mail));
		nvps.add(new BasicNameValuePair("sex", ProfileInfo.sex));
		nvps.add(new BasicNameValuePair("deviceid", ProfileInfo.deviceID));
		if(ProfileInfo.signInType.equals("vk"))
		{
			nvps.add(new BasicNameValuePair("userid", ProfileInfo.userVKID));
			nvps.add(new BasicNameValuePair("vktoken", ProfileInfo.userVKToken));
		}
		else
		{
			nvps.add(new BasicNameValuePair("password", ProfileInfo.userPass));
		}
		
		

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
 		try {
			Log.d("http", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http on regist", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}
	public static String signin() {

		String req = "signin";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (ProfileInfo.signInType.equals("vk")) {
			nvps.add(new BasicNameValuePair("userid", ProfileInfo.userVKID));
			nvps.add(new BasicNameValuePair("vktoken", ProfileInfo.userVKToken));

		} else {
			nvps.add(new BasicNameValuePair("password", ProfileInfo.userPass));

		}
		nvps.add(new BasicNameValuePair("signintype", ProfileInfo.signInType));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http", "signin");
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success.equals("success")) {
			loadProfileInfo();
			return "true";
		} else
			return "false";
	}

	public static String loadProfileInfo() {

		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "status"));
		String resp = executeHttpPostResponse(req, nvps);
		String report = "";
		String money = "";
		String scans = "";
		String rescans = "";

		try {
			Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
				money = (String) jsonObj.get("balance");
				scans = (String) jsonObj.get("count_scan");
				rescans = (String) jsonObj.get("count_rescan");
				ProfileInfo.setScansCount(Integer.valueOf(scans));
				ProfileInfo.setMoneyCount(Integer.valueOf(money));
				ProfileInfo.setRescansCount(Integer.valueOf(rescans));
				ServerAPI.saveProfileInfo();
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadInfo", report);
		if (report.equals("success")) {
			Log.d("http money", money);
			return "true";
		} else
			return "false";
	}

	public static String tryAddScan(String scan) {
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "scan"));
		nvps.add(new BasicNameValuePair("scan", scan));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http load resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadScan", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}

	public static String addMoney(int count) {

		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "money"));
		nvps.add(new BasicNameValuePair("count", "" + count));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http   addmoney resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http http addmony", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}

	public static String getCountries() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "cities"));
		String resp = executeHttpPostResponse(req,nvps);
		String report = "";
		try {
			Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {

				ServerAPI.saveProfileInfo();
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static TreeMap<String,String> getCities(String countryID) {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "cities"));
		nvps.add(new BasicNameValuePair("countryid", "" + countryID));
		
		String resp = executeHttpPostResponse(req,nvps);
		String report = "";
		try {
			//Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {

				String citiesJson = jsonObj.get("cities").toString();
				citiesJson = citiesJson.replace("{", ""); 
				citiesJson = citiesJson.replace("}", ""); 
				citiesJson = citiesJson.replace("\"", ""); 
				
				
				//Log.d("cities", citiesJson);
				TreeMap<String,String> citiesMap = new TreeMap<String,String>();
				
				String pairs[] = citiesJson.split(",");
				for(int i = 0;i<pairs.length;i++)
				{
					String pair[] = pairs[i].split(":");
					citiesMap.put(pair[0], pair[1]);
				}
				
				
				 
				ServerAPI.saveProfileInfo();
				
				return citiesMap;
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getTopUsers() {
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "topUsers"));
 		
		String resp = executeHttpPostResponse(req,nvps);
		String report = "";
		try {
			//Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
  				JSONArray array = jsonObj.optJSONArray("topUsers");
				
  				String[] ratings = new String[array.length()];
				for(int i = 0;i<array.length();i++)
				{
					ratings[i] = array.get(i).toString();
				}
				ServerAPI.saveProfileInfo();
				
				return ratings;
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String[] searchFriend(String friendName) 
	{
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "users"));
		nvps.add(new BasicNameValuePair("pattern", friendName));
		String resp = executeHttpPostResponse(req,nvps);
		
		String report = "";
		try {
			//Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
  				JSONArray array = jsonObj.optJSONArray("users");
				
  				String[] users = new String[array.length()];
				for(int i = 0;i<array.length();i++)
				{
					users[i] = array.get(i).toString();
				}
				ServerAPI.saveProfileInfo();
				
				return users;
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String addFriend(String friendName) 
	{
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friendshipRequest"));
		nvps.add(new BasicNameValuePair("tousername", friendName));
		String resp = executeHttpPostResponse(req,nvps);
		
		String report = "";
		try {
			JSONObject jsonObj = new JSONObject(resp);
			
			
			
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getReqFriends() 
	{
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friendshipRequests"));
		String resp = executeHttpPostResponse(req,nvps);
		
		String report = "";
		try {
			//Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			JSONArray array = jsonObj.optJSONArray("friendshipRequests");
			report = (String) jsonObj.get("report");
			 
				String[] users = new String[array.length()];
				for(int i = 0;i<array.length();i++)
				{
					users[i] = array.get(i).toString();
				}
				ServerAPI.saveProfileInfo();				
				return users;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String[] getMyFriends(String friendName,int firstPosition,int lastPosition) 
	{
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "friends"));
		nvps.add(new BasicNameValuePair("firstPosition", "0"));
		nvps.add(new BasicNameValuePair("lastPosition", "0"));
 		String resp = executeHttpPostResponse(req,nvps);
		
		String report = "";
		try {
			//Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) {
  				JSONArray array = jsonObj.optJSONArray("friends");
				
  				String[] friends = new String[array.length()];
				for(int i = 0;i<array.length();i++)
				{
					friends[i] = array.get(i).toString();
				}
				ServerAPI.saveProfileInfo();
				
				return friends;
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String friendshipRequestRefuse(String userName) 
	{
		String req = "friendshipRequestRefuse";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("refuseUser", userName));
		String resp = executeHttpPostResponse(req,nvps);
		
		String report = "";
		try {
			//Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
 			report = (String) jsonObj.get("report");
			 
				 if(report.equals("success"))			
				return "true";	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	public static String addInfo(List<NameValuePair> nvps)
	{
		String req = "add";
 		nvps.add(new BasicNameValuePair("what", "profileInfo"));
 		String resp = executeHttpPostResponse(req,nvps);
 		String report = "";
		try {
			Log.d("http add profileInfo", resp);
			JSONObject jsonObj = new JSONObject(resp);
			report = (String) jsonObj.get("report");
			if (report.equals("success")) 
			{
				ServerAPI.saveProfileInfo();
				return "true";
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
	public static String loadFriendsInfo() {
		return null;
	}

	public static String loadRatingsInfo() {
		return null;
	}

	public static byte[] downloadProfilePhoto(String who) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost("http://188.120.235.179/downloadProfilePhoto/");
		HttpResponse response = null;

		List<NameValuePair> values = new ArrayList<NameValuePair>();
		values.add(new BasicNameValuePair("who", who));
		//values.add(new BasicNameValuePair("username", ProfileInfo.username));
		//values.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(values, HTTP.ASCII));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("http", "Login form get: " + response.getStatusLine());

		BufferedReader rd = null;
		InputStream is = null;
		try {
			is = response.getEntity().getContent();
		} catch (IllegalStateException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        int contentSize = (int) response.getEntity().getContentLength();
       // System.out.println("Content size ["+contentSize+"]");
        BufferedInputStream bis = new BufferedInputStream(is, 512);
        byte[] data = null; 
        data = new byte[contentSize];
        int bytesRead = 0;
        int offset = 0;
         
        while (bytesRead != -1 && offset < contentSize) {
            try {
				bytesRead = bis.read(data, offset, contentSize - offset);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            offset += bytesRead;
        }
		

		httpclient.getConnectionManager().shutdown();

		
		
		return data;
	}
	public static void saveProfileInfo() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		Editor editor = prefs.edit();
		editor.putString("userPass", ProfileInfo.userPass);
		editor.putString("userID", ProfileInfo.username);
		editor.putString("userToken", ProfileInfo.userToken);
		editor.putString("userVKID", ProfileInfo.userVKID);
		editor.putString("userVKToken", ProfileInfo.userVKToken);
		editor.putString("loginType", ProfileInfo.signInType);
		
		editor.putInt("scansCount", ProfileInfo.getScansCount());
		editor.putInt("rescanCount", ProfileInfo.getRescansCount());
		editor.putInt("moneyCount", ProfileInfo.getMoneyCount());
		
		editor.putString("avatarPath", ProfileInfo.avatarPath);
		editor.putString("sex", ProfileInfo.sex);
		
		editor.putString("city", ProfileInfo.city);
		editor.commit();
	}

	public static boolean isOnline() {
		Context c = QrushTabsApp.getAppContext();
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	public static void flushProfile() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
		Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		// TODO Auto-generated method stub
		
	}


}
