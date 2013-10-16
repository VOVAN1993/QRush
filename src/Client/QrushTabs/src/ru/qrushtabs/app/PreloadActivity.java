package ru.qrushtabs.app;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.analytics.tracking.android.GoogleAnalytics;
import com.mopub.mobileads.MoPubView;
import com.vungle.sdk.VunglePub;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.SharedPrefsAPI;
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

	//private MoPubView moPubView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.preloader);
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		ProfileInfo.deviceID = tm.getDeviceId();
//		moPubView = (MoPubView) findViewById(R.id.adview_prize);
//	    moPubView.setAdUnitId("028e5e162f714c5d9c9224e2ccb3ebea");
//	    moPubView.loadAd();
		
		
		//ServerAPI.flushProfile();
		if (ServerAPI.isOnline()) {
			
			//VunglePub.init(getApplication(), "ru.qrushtabs.app");
			
			// Log.d("http", "isOnline");
			ServerAPI.offlineMod = false;

			Intent intent = new Intent(this, InfoActivity.class);
			startActivityForResult(intent, 1);

//			 Animation rotation = AnimationUtils.loadAnimation(this,
//			 R.anim.arrow_rotate_anim);
//			 ImageView myView = (ImageView)findViewById(R.id.preload_arrows);
//			 myView.startAnimation(rotation);

//			 if(auth())
//			 {
//			 (new CheckTask()).execute();
//			 }
//			 else
//			 {
//			 Intent intent1 = new Intent(PreloadActivity.this,
//			 SignInActivity.class);
//			 finish();
//			 startActivity(intent1);
//			 }

		} else {
//			Log.d("http", "isNotOnline");
			ServerAPI.offlineMod = true;
			Intent intent = new Intent(this, InfoActivity.class);
			startActivityForResult(intent, 1);
//			 if(auth())
//			 {
//			 Intent intent1 = new Intent(this, MainActivity.class);
//			 finish();
//			 startActivity(intent1);
//			 }
		}
	}

	@Override
	public void onDestroy()
	{
		// moPubView.destroy();
		//mInterstitial.destroy();
	    super.onDestroy();
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
			if (SharedPrefsAPI.auth()) {
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
			if (SharedPrefsAPI.auth()) {
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
