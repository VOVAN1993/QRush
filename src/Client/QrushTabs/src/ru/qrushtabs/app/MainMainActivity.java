package ru.qrushtabs.app;

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
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

public class MainMainActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		DefaultHttpClient httpclient = new DefaultHttpClient();
//
////        HttpGet httpget = new HttpGet("https://portal.sun.com/portal/dt");

        HttpResponse response = null;
//		try {
//			response = httpclient.execute(httpget);
//		} catch (ClientProtocolException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        HttpEntity entity = response.getEntity();
//
//        System.out.println("Login form get: " + response.getStatusLine());
//        if (entity != null) {
//            try {
//				entity.consumeContent();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        System.out.println("Initial set of cookies:");
//        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
//        if (cookies.isEmpty()) {
//            System.out.println("None");
//        } else {
//            for (int i = 0; i < cookies.size(); i++) {
//                System.out.println("- " + cookies.get(i).toString());
//            }
//        }

//        HttpPost httpost = new HttpPost("https://portal.sun.com/amserver/UI/Login?" +
//                "org=self_registered_users&" +
//                "goto=/portal/dt&" +
//                "gotoOnFail=/portal/dt?error=true");
        
//        HttpPost httpost = new HttpPost("http://188.120.235.179/add/");
//
//        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//        nvps.add(new BasicNameValuePair("what", "user"));
//        nvps.add(new BasicNameValuePair("username", "user1"));
//        nvps.add(new BasicNameValuePair("password", "password"));
//        nvps.add(new BasicNameValuePair("deviceid", "device_id"));
//
//        try {
//			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.ASCII));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        try {
//			response = httpclient.execute(httpost);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        HttpEntity entity = response.getEntity();
//        Log.d("http", "Login form get: " + response.getStatusLine());
//        
//        BufferedReader rd = null;
//		try {
//			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//			 String line = "";
//			while ((line = rd.readLine()) != null) {
//				  Log.d("http", line);
//			}
//		} catch (IllegalStateException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//       
//      
//
//      
//
//        // When HttpClient instance is no longer needed, 
//        // shut down the connection manager to ensure
//        // immediate deallocation of all system resources
//        httpclient.getConnectionManager().shutdown();        
		
		if (ServerAPI.isOnline()) {
			 
			Log.d("http", "isOnline");
			ServerAPI.offlineMod = false;
			if(auth())
				(new CheckTask()).execute(ProfileInfo.userID);
			else
			{
				Intent intent = new Intent(MainMainActivity.this, EnterActivity.class);
				finish();
				startActivity(intent);
			}
			 

		} else {
			Log.d("http", "isNotOnline");
			ServerAPI.offlineMod = true;
			auth();
			Intent intent = new Intent(this, MainActivity.class);
			finish();
			startActivity(intent);
		}

	}

	private boolean auth() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ProfileInfo.userID = prefs.getString("userID", null);
		ProfileInfo.userToken = prefs.getString("userToken", null);
		ProfileInfo.userPass = prefs.getString("userPass", null);
		ProfileInfo.setScansCount(prefs.getInt("scansCount", 0));
		ProfileInfo.setMoneyCount(prefs.getInt("moneyCount", 0));
		ProfileInfo.setRescansCount(prefs.getInt("rescansCount", 0));
		
		if(ProfileInfo.userID!=null)
		{
			Log.d("http", "onAuth yes");
			return true;
		}
		else
		{
			Log.d("http", "onAuth no");
			return false;
		}
 
	}

 
	private class CheckTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.loadProfileInfo(ProfileInfo.userID);
		}

		protected void onPostExecute(String objResult) {

			if(objResult.equals("true"))
			{
				Intent intent = new Intent(MainMainActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent(MainMainActivity.this, EnterActivity.class);
				finish();
				startActivity(intent);
			}
		}

	}

}
