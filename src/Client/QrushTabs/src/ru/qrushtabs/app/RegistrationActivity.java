package ru.qrushtabs.app;

 
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends Activity 
{
	 
	String userID;
	String userPass;
	OnClickListener onSignIn = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			EditText ed = (EditText)RegistrationActivity.this.findViewById(R.id.signinAcc);
			userID = ed.getText().toString();
			ed = (EditText)RegistrationActivity.this.findViewById(R.id.signinPass);
			userPass = ed.getText().toString();
			ProfileInfo.userID = userID;
			ProfileInfo.userPass = userPass;
			(new CheckTask()).execute();
			 
			
		}
		
		
	};
	private class CheckTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.loadProfileInfo();
		}

		protected void onPostExecute(String objResult) 
		{

			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText("¬ы успешно вошли");
			    
				ProfileInfo.userID = userID;
		        ProfileInfo.userPass = userPass;
		        

		        ServerAPI.saveProfileInfo();
		        
		        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
			   // ServerAPI.loadProfileInfo(ProfileInfo.userID,ProfileInfo.userPass);
				 
			}
			else
			{
				TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText("Не получилось войти");
			}
		
		}

	}
	OnClickListener onSignUp = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			 TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
 			(new SignUpTask()).execute(userID,userPass,tm.getDeviceId());
			
		}
		
	};
	private class SignUpTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signUp();
		}

		protected void onPostExecute(String objResult) {

			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText("Вы успешно зарегистрировались");
			    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
			    ProfileInfo.userID = userID;
		        ProfileInfo.userPass = userPass;
		        ServerAPI.saveProfileInfo();
		        
		        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
				finish();
				startActivity(intent);
			}
			else
			{
				TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText("Не получилось зарегестрирваться");
			}
		}

	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Button btn = (Button)findViewById(R.id.signinBtn);
		btn.setOnClickListener(onSignIn);
		btn = (Button)findViewById(R.id.signupBtn);
		btn.setOnClickListener(onSignUp);
	}

}
