package ru.qrushtabs.app;

 
import ru.qrushtabs.app.mycamera.AvatarCameraActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ChangePassActivity extends Activity 
{
	 
//	OnClickListener onSignIn = new OnClickListener()
//	{
//
//		@Override
//		public void onClick(View arg0) {
//			
//			EditText ed = (EditText)RegistrationActivity.this.findViewById(R.id.signinAcc);
//			ProfileInfo.userID = ed.getText().toString();
//			ed = (EditText)RegistrationActivity.this.findViewById(R.id.signinPass);
//			ProfileInfo.userPass = ed.getText().toString();
//			(new CheckTask()).execute();
//			 
//			
//		}
//		
//		
//	};
//	private class CheckTask extends AsyncTask<String,String,String> {
//
//		protected String doInBackground(String... args) {
//			return ServerAPI.loadProfileInfo();
//		}
//
//		protected void onPostExecute(String objResult) 
//		{
//
//			if(objResult.equals("true"))
//			{
//			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
//			    tv.setText("Вы успешно вошли");
//		        ServerAPI.saveProfileInfo();
//		        
//		        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//				finish();
//				startActivity(intent);				 
//			}
//			else
//			{
//				TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
//			    tv.setText("Не получилось войти");
//			}
//		
//		}
//
//	}
	 
	private void alert(String message)
	{
		TextView tv = (TextView)ChangePassActivity.this.findViewById(R.id.reportView);
		tv.setText(message);
	}
	OnClickListener onSignUp = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			String oldPass;
			String newPass;
			String newRepass;
			EditText ed = (EditText)ChangePassActivity.this.findViewById(R.id.old_pass_te);
			oldPass=ed.getText().toString();
			if(!ProfileInfo.userPass.equals(oldPass))
			{
				alert("Пароль не верен!");
				return;
			}
 			ed = (EditText)ChangePassActivity.this.findViewById(R.id.new_pass_te);
 			newPass = ed.getText().toString();
			if(ed.getText().toString().length()==0)
			{
				alert("Введите новый пароль!");
				return;
			}
			if(ed.getText().toString().length()<4)
			{
				alert("Должно быть более трех символов в пароле");
				return;
			}	
 			ed = (EditText)ChangePassActivity.this.findViewById(R.id.new_repass_te);
 			newRepass = ed.getText().toString();
			if(!newRepass.equals(newPass))
			{
				alert("Пароли не совпадают!");
				return;
			}
			 
			//ProfileInfo.username = ed.getText().toString();
			
			
  			//(new SignUpTask()).execute();
			
		}
		
	};
	
	private class SignUpTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signUp();
		}

		protected void onPostExecute(String objResult) {

 			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)ChangePassActivity.this.findViewById(R.id.reportView);
			    //tv.setText("Вы успешно зарегистрировались");
		        ServerAPI.saveProfileInfo();
		        
		        Intent intent = new Intent(ChangePassActivity.this, SecondFormActivity.class);
				finish();
				startActivity(intent);
			}
			else
			{
				TextView tv = (TextView)ChangePassActivity.this.findViewById(R.id.reportView);
			    tv.setText(objResult);
			}
		}

	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.change_pass);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_empty);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 
	 
 
		
		Button btn = (Button)findViewById(R.id.change_pass_btn);
		btn.setOnClickListener(onSignUp);
	}

}
