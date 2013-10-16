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

public class ChangePassActivity extends MyVungleActivity 
{
	 

	 
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
				alert("Старый пароль не верен!");
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
			
			
  			(new ChangePassTask()).execute(oldPass,newPass);
			
		}
		
	};
	
	private class ChangePassTask extends AsyncTask<String,String,String> {

		protected String doInBackground(String... args) {
			return ServerAPI.changePass(args[0],args[1]);
		}

		protected void onPostExecute(String objResult) {

 			if(objResult.equals("true"))
			{
			    TextView tv = (TextView)ChangePassActivity.this.findViewById(R.id.reportView);
			    tv.setText("Пароль поменялся");
			    SharedPrefsAPI.saveProfileInfo();
		        
		         
			}
			else
			{
				TextView tv = (TextView)ChangePassActivity.this.findViewById(R.id.reportView);
				tv.setText("Пароль не поменялся");
			    tv.setText(objResult);
			}
		}

	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.change_pass);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_back);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 
	 
        Button backButton = (Button) this.findViewById(R.id.header_back_btn);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

 
		
		Button btn = (Button)findViewById(R.id.change_pass_btn);
		btn.setOnClickListener(onSignUp);
	}

}
