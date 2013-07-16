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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class PreloadActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 ProfileInfo.deviceID = tm.getDeviceId();
		if (ServerAPI.isOnline()) {
			 
			Log.d("http", "isOnline");
			ServerAPI.offlineMod = false;
			if(auth())
				(new CheckTask()).execute(ProfileInfo.userID);
			else
			{
				Intent intent = new Intent(PreloadActivity.this, EnterActivity.class);
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
		ProfileInfo.userVKID = prefs.getString("userVKID", null);
		ProfileInfo.loginType = prefs.getString("loginType", "def");
		ProfileInfo.userVKToken = prefs.getString("userVKToken", null);
		ProfileInfo.userPass = prefs.getString("userPass", null);
		ProfileInfo.setScansCount(prefs.getInt("scansCount", 0));
		ProfileInfo.setMoneyCount(prefs.getInt("moneyCount", 0));
		ProfileInfo.setRescansCount(prefs.getInt("rescansCount", 0));
		
		Log.d("http", ProfileInfo.loginType);
		if(ProfileInfo.userID!=null||ProfileInfo.userVKID!=null)
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
			return ServerAPI.loadProfileInfo();
		}

		protected void onPostExecute(String objResult) {

			if(objResult.equals("true"))
			{
				Intent intent = new Intent(PreloadActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent(PreloadActivity.this, EnterActivity.class);
				finish();
				startActivity(intent);
			}
		}

	}

}
