package ru.qrushtabs.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import ru.qrushtabs.app.RegistrationActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class ServerAPI {
	public static boolean offlineMod = true;

	private static String executeHttpResponse(String query,
			List<NameValuePair> values) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://188.120.235.179/" + query + "/");
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

		BufferedReader rd = null;
		StringBuilder sb = new StringBuilder("");
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			String line = "";
			while ((line = rd.readLine()) != null) {
				sb.append(line);

			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

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

		return sb.toString();
	}

	public static String signUp() {

		
		// String req =
		// "add&what=user&username="+userID+"&password="+userPass+"&deviceid="+deviceID;
		// String resp = executeHttpResponse(req);
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
//		nvps.add(new BasicNameValuePair("username", userID));
		
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
		Log.d("http on regist", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}
	public static String signin() {

		// String req =
		// "get&what=atoken&username="+userID+"&password="+userPass;
		// String resp = executeHttpResponse(req);
		String req = "signin";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		//nvps.add(new BasicNameValuePair("what", "atoken"));
		// nvps.add(new BasicNameValuePair("username", ProfileInfo.userID));
		if (ProfileInfo.signInType.equals("vk")) {
			nvps.add(new BasicNameValuePair("userid", ProfileInfo.userVKID));
			nvps.add(new BasicNameValuePair("vktoken", ProfileInfo.userVKToken));

		} else {
			nvps.add(new BasicNameValuePair("password", ProfileInfo.userPass));

		}
		nvps.add(new BasicNameValuePair("signintype", ProfileInfo.signInType));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		String token = "";
		try {
			Log.d("http", "signin");
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			token = (String) jsonObj.get("atoken");
			ProfileInfo.userToken = token;
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http on reg", success);
		if (success.equals("success")) {
			loadProfileInfo();
			return "true";
		} else
			return "false";
	}
	public static String getToken() {

		// String req =
		// "get&what=atoken&username="+userID+"&password="+userPass;
		// String resp = executeHttpResponse(req);
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "atoken"));
		// nvps.add(new BasicNameValuePair("username", ProfileInfo.userID));
		if (ProfileInfo.signInType.equals("vk")) {
			nvps.add(new BasicNameValuePair("atokenvk", ProfileInfo.userVKToken));

		} else {
			nvps.add(new BasicNameValuePair("password", ProfileInfo.userPass));

		}

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		String token = "";
		try {
			Log.d("http", "getToken");
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			token = (String) jsonObj.get("atoken");
			ProfileInfo.userToken = token;
			loadProfileInfo();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http on reg", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}

	public static String loadProfileInfo() {
		// String req =
		// "get&what=status&username="+userID+"&atoken="+ProfileInfo.userToken;
		// Log.d("http load req", req);
		// String resp = executeHttpResponse(req);
		String req = "get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "status"));
		// nvps.add(new BasicNameValuePair("username", ProfileInfo.userID));
		// nvps.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		String money = "";
		String scans = "";
		String rescans = "";
		String explanation = "";
		String token = "";
		try {
			Log.d("http load profile", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			explanation = (String) jsonObj.get("explanation");
			if (success.equals("success")) {
				money = (String) jsonObj.get("money");
				scans = (String) jsonObj.get("count_scan");
				rescans = (String) jsonObj.get("count_rescan");
				if (jsonObj.has("atoken")) {
					ProfileInfo.userToken = (String) jsonObj.get("atoken");
				}
				ProfileInfo.setScansCount(Integer.valueOf(scans));
				ProfileInfo.setMoneyCount(Integer.valueOf(money));
				ProfileInfo.setRescansCount(Integer.valueOf(rescans));
				ServerAPI.saveProfileInfo();
			} else {
				if (explanation.equals("Invalid token")) {
					return getToken();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http loadIngo", success);
		if (success.equals("success")) {
			Log.d("http money", money);
			return "true";
		} else
			return "false";
	}

	public static String tryAddScan(String scan) {
		// String req =
		// "add&what=scan&username="+userID+"&atoken="+ProfileInfo.userToken+"&code="+scan;
		// Log.d("http load req", req);
		// String resp = executeHttpResponse(req);
		String req = "add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "scan"));
		// nvps.add(new BasicNameValuePair("username", ProfileInfo.userID));
		// nvps.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));
		nvps.add(new BasicNameValuePair("code", scan));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http load resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			if (jsonObj.has("atoken")) {
				ProfileInfo.userToken = (String) jsonObj.get("atoken");
			}

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
		// String req =
		// "update&what=money&username="+userID+"&atoken="+ProfileInfo.userToken+"&count="+count;
		// Log.d("http addmony req", req);
		// String resp = executeHttpResponse(req);
		String req = "update";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("what", "money"));
		// nvps.add(new BasicNameValuePair("username", ProfileInfo.userID));
		// nvps.add(new BasicNameValuePair("atoken", ProfileInfo.userToken));
		nvps.add(new BasicNameValuePair("count", "" + count));

		String resp = executeHttpPostResponse(req, nvps);
		String success = "";
		try {
			Log.d("http   addmony resp", resp);
			JSONObject jsonObj = new JSONObject(resp);
			success = (String) jsonObj.get("report");
			if (jsonObj.has("atoken")) {
				ProfileInfo.userToken = (String) jsonObj.get("atoken");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("http http addmony", success);
		if (success.equals("success")) {
			return "true";
		} else
			return "false";
	}

	public static String loadFriendsInfo() {
		return null;
	}

	public static String loadRatingsInfo() {
		return null;
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
		editor.commit();
	}

	public static boolean isOnline() {
		Context c = QrushTabsApp.getAppContext();
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
