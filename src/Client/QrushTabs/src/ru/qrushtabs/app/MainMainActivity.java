package ru.qrushtabs.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

		
		if (ServerAPI.isOnline()) {
			 
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
			return ServerAPI.loadProfileInfo(ProfileInfo.userID,ProfileInfo.userPass);
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
