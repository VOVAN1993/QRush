package ru.qrushtabs.app;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.vungle.sdk.VunglePub;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class PreloadActivity extends MyVungleActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.preloader);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		ProfileInfo.deviceID = tm.getDeviceId();
		if (ServerAPI.isOnline()) {
			
			VunglePub.init(getApplication(), "ru.qrushtabs.app");
			// Log.d("http", "isOnline");
			ServerAPI.offlineMod = false;

			Intent intent = new Intent(this, InfoActivity.class);
			startActivityForResult(intent, 1);

			// Animation rotation = AnimationUtils.loadAnimation(this,
			// R.anim.arrow_rotate_anim);
			// ImageView myView = (ImageView)findViewById(R.id.preload_arrows);
			// myView.startAnimation(rotation);

			// if(auth())
			// {
			// (new CheckTask()).execute();
			// }
			// else
			// {
			// Intent intent = new Intent(PreloadActivity.this,
			// SignInActivity.class);
			// finish();
			// startActivity(intent);
			// }

		} else {
			Log.d("http", "isNotOnline");
			ServerAPI.offlineMod = true;
			Intent intent = new Intent(this, InfoActivity.class);
			startActivityForResult(intent, 1);
			// if(auth())
			// {
			// Intent intent = new Intent(this, MainActivity.class);
			// finish();
			// startActivity(intent);
			// }
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		Animation rotation = AnimationUtils.loadAnimation(this,
				R.anim.arrow_rotate_anim);
		ImageView myView = (ImageView) findViewById(R.id.preload_arrows);
		myView.startAnimation(rotation);
		
		if (ServerAPI.isOnline()) {
			if (auth()) {
				(new CheckTask()).execute();
			} else {
				Intent intent = new Intent(PreloadActivity.this,
						EnterActivity.class);
				finish();
				startActivity(intent);
			}

		} else {
			Log.d("http", "isNotOnline");
			ServerAPI.offlineMod = true;
			if (auth()) {
				Intent intent = new Intent(this, MainActivity.class);
				finish();
				startActivity(intent);
			} else {
				Intent intent = new Intent(PreloadActivity.this,
						EnterActivity.class);
				finish();
				startActivity(intent);
			}
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

		if (ProfileInfo.username != null) {
			Log.d("http", "onAuth yes");
			return true;
		} else {
			Log.d("http", "onAuth no");
			return false;
		}
	}

	private class CheckTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signin();
		}

		protected void onPostExecute(String objResult) {

			if (objResult.equals("true")) {
				Intent intent = new Intent(PreloadActivity.this,
						MainActivity.class);
				finish();
				startActivity(intent);
			} else {
				ImageView myView = (ImageView) findViewById(R.id.preload_arrows);
				myView.clearAnimation();
				Intent intent = new Intent(PreloadActivity.this,
						EnterActivity.class);
				finish();
				startActivity(intent);
			}
		}

	}

}
