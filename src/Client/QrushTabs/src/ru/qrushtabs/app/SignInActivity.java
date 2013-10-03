package ru.qrushtabs.app;

 
import ru.qrushtabs.app.profile.ProfileInfo;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SignInActivity extends MyVungleActivity 
{
	 
	OnClickListener onSignIn = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			EditText ed = (EditText)SignInActivity.this.findViewById(R.id.signinAcc);
			ProfileInfo.mail = ed.getText().toString();
			ed = (EditText)SignInActivity.this.findViewById(R.id.signinPass);
			ProfileInfo.userPass = ed.getText().toString();
			(new CheckTask()).execute();	
		}
//		
//		
	};
	private class CheckTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signin();
		}

		protected void onPostExecute(String objResult) 
		{

			if(objResult.equals("true"))
			{
			     Log.d("on signin", "true");
		        ServerAPI.saveProfileInfo();
		        
		        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
				finish();
				startActivity(intent);				 
			}
			else
			{
				 Log.d("on signin", "true");
				TextView tv = (TextView)SignInActivity.this.findViewById(R.id.signin_report_view);
			    tv.setText(objResult);
			}
		
		}

	}
//	OnClickListener onSignUp = new OnClickListener()
//	{
//
//		@Override
//		public void onClick(View arg0) {
//			EditText ed = (EditText)SignInActivity.this.findViewById(R.id.signupAcc);
//			ProfileInfo.mail = ed.getText().toString();
//			ed = (EditText)SignInActivity.this.findViewById(R.id.signupPass);
//			ProfileInfo.userPass = ed.getText().toString();
//			ed = (EditText)SignInActivity.this.findViewById(R.id.username_te);
//			ProfileInfo.userID = ed.getText().toString();
//			
//			RadioButton rb = (RadioButton)SignInActivity.this.findViewById(R.id.radioMale);
//			if(rb.isChecked())
//				ProfileInfo.sex = "M";
//			else
//				ProfileInfo.sex = "F";
//			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
// 			(new SignUpTask()).execute();
//			
//		}
//		
//	};
//	private class SignUpTask extends AsyncTask<String,String,String> {
//
//		protected String doInBackground(String... args) {
//			return ServerAPI.signUp();
//		}
//
//		protected void onPostExecute(String objResult) {
//
//			if(objResult.equals("true"))
//			{
//			    TextView tv = (TextView)SignInActivity.this.findViewById(R.id.reportView);
//			    tv.setText("Вы успешно зарегистрировались");
//		        ServerAPI.saveProfileInfo();
//		        
//		        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//				finish();
//				startActivity(intent);
//			}
//			else
//			{
//				TextView tv = (TextView)SignInActivity.this.findViewById(R.id.reportView);
//			    tv.setText("Не получилось зарегестрирваться");
//			}
//		}
//
//	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.signin);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.simple_title);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if(!ProfileInfo.signInType.equals("def"))
//		{
//			EditText ed = (EditText)SignInActivity.this.findViewById(R.id.signupPass);
//			ed.setVisibility(View.INVISIBLE);
//		}
		Button btn = (Button)findViewById(R.id.submitBtn);
		btn.setOnClickListener(onSignIn);
//		Button btn = (Button)findViewById(R.id.signupBtn);
//		btn.setOnClickListener(onSignUp);
	}

}
