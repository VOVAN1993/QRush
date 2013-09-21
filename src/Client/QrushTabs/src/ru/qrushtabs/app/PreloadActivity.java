package ru.qrushtabs.app;



import ru.qrushtabs.app.profile.ProfileInfo;
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

public class PreloadActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.preloader);
		 TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		 ProfileInfo.deviceID = tm.getDeviceId();
		if (ServerAPI.isOnline())
		{
			 
			Log.d("http", "isOnline");
			ServerAPI.offlineMod = false;
			if(auth())
				(new CheckTask()).execute();
			else
			{
				Intent intent = new Intent(PreloadActivity.this, SignInActivity.class);
				finish();
				startActivity(intent);
			}
			 

		} else {
			Log.d("http", "isNotOnline");
			ServerAPI.offlineMod = true;
			auth();
			Intent intent = new Intent(this, GamesActivity.class);
			finish();
			startActivity(intent);
		}
	}

	private boolean auth() {
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ProfileInfo.username = prefs.getString("userID", "Анонимус");
		ProfileInfo.userToken = prefs.getString("userToken", null);
		ProfileInfo.userVKID = prefs.getString("userVKID", null);
		ProfileInfo.signInType = prefs.getString("loginType", "def");
		ProfileInfo.userVKToken = prefs.getString("userVKToken", null);
		ProfileInfo.userPass = prefs.getString("userPass", null);
		
		ProfileInfo.setScansCount(prefs.getInt("scansCount", 0));
		ProfileInfo.setMoneyCount(prefs.getInt("moneyCount", 0));
		ProfileInfo.setRescansCount(prefs.getInt("rescansCount", 0));
		
		ProfileInfo.avatarPath = prefs.getString("avatarPath", "0");
		ProfileInfo.sex = prefs.getString("sex", "М");
		ProfileInfo.mail = prefs.getString("email", "mail@mail.ru");
		
		ProfileInfo.city = prefs.getString("city", "Репрежевальск");
		
		
 		if(ProfileInfo.username!=null||ProfileInfo.userVKID!=null)
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
			return ServerAPI.signin();
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
