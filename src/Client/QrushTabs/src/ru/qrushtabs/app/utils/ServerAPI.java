package ru.qrushtabs.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import ru.qrushtabs.app.PrizeActivity;
import ru.qrushtabs.app.ProfileInfo;
import ru.qrushtabs.app.QrushTabsApp;
import ru.qrushtabs.app.RegistrationActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServerAPI {
	public static boolean offlineMod = true;
//	public static String checkUser(String userName) {
//		String req = "check&what=username&username=" + userName;
//		String resp = executeHttpResponse(req);
//		String success = "";
//		try {
//			Log.d("http", resp);
//			JSONObject jsonObj = new JSONObject(resp);
//			success = (String) jsonObj.get("report");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Log.d("http", success);
//		if (success.equals("success"))
//			return "true";
//		else
//			return "false";
//	}

	private static String executeHttpResponse(String str) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://188.120.235.179/"+str);
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Get the response
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String line = "";
		StringBuilder sb = new StringBuilder("");
		try {
			while ((line = rd.readLine()) != null) {
				sb.append(line);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static String signUp(String userID,String userPass,String deviceID) {
		
		String req = "add&what=user&username="+userID+"&password="+userPass+"&deviceid="+deviceID;
		String resp = executeHttpResponse(req);
		String success = "";
		String token = "";
		try {
			Log.d("http", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			token = (String) jsonObj.get("atoken");
			ProfileInfo.userToken = token;

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http on reg", success);
		if (success.equals("success"))
		{

			return "true";
			
		}
		else
			return "false";
	}
public static String getToken(String userID,String userPass,String deviceID) {
		
		String req = "get&what=atoken&username="+userID+"&password="+userPass;
		String resp = executeHttpResponse(req);
		String success = "";
		String token = "";
		try {
			Log.d("http", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			token = (String) jsonObj.get("new_token");
			ProfileInfo.userToken = token;

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http on reg", success);
		if (success.equals("success"))
		{

			return "true";
			
		}
		else
			return "false";
	}
	public static String loadProfileInfo(String userID) {
		String req = "get&what=status&username="+userID+"&atoken="+ProfileInfo.userToken;
		Log.d("http load req", req);
		String resp = executeHttpResponse(req);
		String success = "";
		String money = "";
		String scans = "";
		String rescans = "";
		String token = "";
		try {
			Log.d("http load resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			money = (String) jsonObj.get("money");
			scans = (String) jsonObj.get("count_scan");
			rescans = (String) jsonObj.get("count_rescan");
			if(jsonObj.has("atoken"))
			{
				ProfileInfo.userToken = (String) jsonObj.get("atoken");
			}
			ProfileInfo.setScansCount(Integer.valueOf(scans));
			ProfileInfo.setMoneyCount( Integer.valueOf(money));
			ProfileInfo.setRescansCount(Integer.valueOf(rescans));
			ServerAPI.saveProfileInfo();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadIngo", success);
		if (success.equals("success"))
		{
			Log.d("http money", money );
			return "true";
		}
		else
			return "false";
 	}

	public static String tryAddScan(String userID,String userPass,String scan) {
		String req = "add&what=scan&username="+userID+"&atoken="+ProfileInfo.userToken+"&code="+scan;
		Log.d("http load req", req);
		String resp = executeHttpResponse(req);
		String success = "";
		try {
			Log.d("http load resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			if(jsonObj.has("atoken"))
			{
				ProfileInfo.userToken = (String) jsonObj.get("atoken");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadScan", success);
		if (success.equals("success"))
		{
 			return "true";
		}
		else
			return "false";
	}
	public static String addMoney(String userID,String userPass,int count) {
		String req = "update&what=money&username="+userID+"&atoken="+ProfileInfo.userToken+"&count="+count;
		Log.d("http addmony req", req);
		String resp = executeHttpResponse(req);
		String success = "";
		try {
			Log.d("http   addmony resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			if(jsonObj.has("atoken"))
			{
				ProfileInfo.userToken = (String) jsonObj.get("atoken");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http http addmony", success);
		if (success.equals("success"))
		{
			return "true";
		}
		else
			return "false";
	}
	public static String loadFriendsInfo() {
		return null;
	}

	public static String loadRatingsInfo() {
		return null;
	}

	public static void saveProfileInfo()
	{
		    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QrushTabsApp.getAppContext());
	        Editor editor=prefs.edit();
	        editor.putString("userPass", ProfileInfo.userPass);
	        editor.putString("userID", ProfileInfo.userID);
	        editor.putString("userID", ProfileInfo.userID);
	        editor.putString("userToken", ProfileInfo.userToken);
	        editor.putInt("scansCount", ProfileInfo.getScansCount());
	        editor.putInt("rescanCount", ProfileInfo.getRescansCount());
	        editor.putInt("moneyCount", ProfileInfo.getMoneyCount());
 	        editor.commit();
	}
	public static boolean isOnline() {
		Context c =  QrushTabsApp.getAppContext();
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
