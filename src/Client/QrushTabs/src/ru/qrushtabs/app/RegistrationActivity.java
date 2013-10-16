package ru.qrushtabs.app;

 
import ru.qrushtabs.app.mycamera.AvatarCameraActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.SharedPrefsAPI;
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

public class RegistrationActivity extends MyVungleActivity 
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
	private boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
	private void alert(String message)
	{
		TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
		tv.setText(message);
	}
	OnClickListener onSignUp = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			EditText ed = (EditText)RegistrationActivity.this.findViewById(R.id.signupAcc);
			if(ed.getText().toString().length()==0)
			{
				alert("Все поля должны быть заплнены!");
				return;
			}
			if(!isValidEmail(ed.getText().toString()))
			{
				alert("Некорректно введена почта");
				return;
			}	
			
			ProfileInfo.mail = ed.getText().toString();
			ed = (EditText)RegistrationActivity.this.findViewById(R.id.signupPass);
			if(ed.getText().toString().length()==0 && ProfileInfo.signInType.equals("def"))
			{
				alert("Все поля должны быть заплнены!");
				return;
			}
			if(ed.getText().toString().length()<6 && ProfileInfo.signInType.equals("def"))
			{
				alert("Должно быть более пяти символов в пароле");
				return;
			}	
			ProfileInfo.userPass = ed.getText().toString();
			ed = (EditText)RegistrationActivity.this.findViewById(R.id.username_te);
			if(ed.getText().toString().length()==0)
			{
				alert("Все поля должны быть заплнены!");
				return;
			}
			if(ed.getText().toString().length()<3)
			{
				alert("Должно быть более двух символов в нике");
				return;
			}	
			ProfileInfo.username = ed.getText().toString();
			
			if(!ServerAPI.checkUser(ProfileInfo.username).equals("false"))
			{
				alert("Такой пользователь уже существует");
				return;
			}
			
			if(!ServerAPI.checkMail(ProfileInfo.mail).equals("false"))
			{
				alert("Пользователь с такой почтой уже существует");
				return;
			}
  			//(new CheckTask()).execute(ProfileInfo.username,ProfileInfo.mail);
			Intent intent = new Intent(RegistrationActivity.this, SecondFormActivity.class);
			finish();
			startActivity(intent);
			
		}
		
	};
	
	private class CheckTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.signUp();
		}

		protected void onPostExecute(String objResult) {

 			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    //tv.setText("Вы успешно зарегистрировались");
			    SharedPrefsAPI.saveProfileInfo();
		        
		        Intent intent = new Intent(RegistrationActivity.this, SecondFormActivity.class);
				finish();
				startActivity(intent);
			}
			else
			{
				TextView tv = (TextView)RegistrationActivity.this.findViewById(R.id.reportView);
			    tv.setText(objResult);
			}
		}

	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.registration);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_empty);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		EditText etValue = (EditText)this.findViewById(R.id.username_te);
        etValue.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		if(!ProfileInfo.signInType.equals("def"))
		{
			EditText ed = (EditText)RegistrationActivity.this.findViewById(R.id.signupPass);
			ed.setVisibility(View.GONE);
			ImageView iv = (ImageView)RegistrationActivity.this.findViewById(R.id.pass_iv);
			iv.setVisibility(View.GONE);
			
			EditText ede = (EditText)RegistrationActivity.this.findViewById(R.id.signupRepass);
			ede.setVisibility(View.GONE);
			ImageView ive = (ImageView)RegistrationActivity.this.findViewById(R.id.repass_img);
			ive.setVisibility(View.GONE);
		}
//		Button btn = (Button)findViewById(R.id.signinBtn);
//		btn.setOnClickListener(onSignIn);
		
		Button btn = (Button)findViewById(R.id.signupBtn);
		btn.setOnClickListener(onSignUp);
	}

}
