package ru.qrushtabs.app;

 
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegistrationActivity extends Activity 
{
	 
	String userID;
	String userPass;
	OnClickListener onSignIn = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			(new CheckTask()).execute(userID);
			
		}
		
		
	};
	private class CheckTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.checkUser(args[0]);
		}

		protected void onPostExecute(String objResult) 
		{

			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText("Вы успешно вошли");
			    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
		        Editor editor=prefs.edit();
		        editor.putString("pass", userPass);
		        editor.putString("user_id", userID);
		        ProfileInfo.userID = userID;
		        ProfileInfo.userPass = userPass;
		        editor.commit();
			}
		}

	}
	OnClickListener onSignUp = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			 TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
 			(new CheckTask()).execute(userID,userPass,tm.getDeviceId());
			
		}
		
	};
	private class SignUpTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signUp(args[0],args[1],args[2]);
		}

		protected void onPostExecute(String objResult) {

			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText("Вы успешно зарегистрировались");
			    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
		        Editor editor=prefs.edit();
		        editor.putString("pass", userPass);
		        editor.putString("user_id", userID);
		        ProfileInfo.userID = userID;
		        ProfileInfo.userPass = userPass;
		        editor.commit();
			}
		}

	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Button btn = (Button)findViewById(R.id.signinBtn);
		btn.setOnClickListener(onSignIn);
		btn = (Button)findViewById(R.id.signupAcc);
		btn.setOnClickListener(onSignUp);
	}

}
