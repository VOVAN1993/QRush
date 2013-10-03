package ru.qrushtabs.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.profile.OtherProfileActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;

 
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EnterActivity extends MyVungleActivity {
	
	private static EnterActivity instance;
	OnClickListener l = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			if(!ServerAPI.isOnline()) 
			{
				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();
				 
					newFragment.setLabelText("Ошибка подключения");
					newFragment.show(EnterActivity.this
							.getSupportFragmentManager(), "missiles");
				 
					newFragment
							.setDrawableBackground(EnterActivity.this
									.getResources().getDrawable(
											R.drawable.black_alert_error));
					return;
			}
			ProfileInfo.signInType = "def";
			Intent intent = new Intent(EnterActivity.this, RegistrationActivity.class);
			//finish();
			
			startActivity(intent);
			
		}
		
		
	};
	OnClickListener vl = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			 
			if(!ServerAPI.isOnline()) 
			{
				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();
				 
					newFragment.setLabelText("Ошибка подключения");
					newFragment.show(EnterActivity.this
							.getSupportFragmentManager(), "missiles");
				 
					newFragment
							.setDrawableBackground(EnterActivity.this
									.getResources().getDrawable(
											R.drawable.black_alert_error));
					return;
			}
			ProfileInfo.signInType = "vk";
			Intent intent = new Intent(EnterActivity.this, VkLoginActivity.class);
			//finish();
			startActivity(intent);
			
		}
		
		
	};
	OnClickListener ll = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			 
			if(!ServerAPI.isOnline()) 
			{
				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();
				 
					newFragment.setLabelText("Ошибка подключения");
					newFragment.show(EnterActivity.this
							.getSupportFragmentManager(), "missiles");
				 
					newFragment
							.setDrawableBackground(EnterActivity.this
									.getResources().getDrawable(
											R.drawable.black_alert_error));
					return;
			}
			ProfileInfo.signInType = "def";
			Intent intent = new Intent(EnterActivity.this, SignInActivity.class);
			//finish();
			startActivity(intent);
			
		}
		
		
	};
	  public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          
          instance = this;
          //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
          this.setContentView(R.layout.enter);
//          getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_empty);
          //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
          
          
          Button bt = (Button)findViewById(R.id.regbtn);
          bt.setOnClickListener(l);
          bt = (Button)findViewById(R.id.vkloginbtn);
          bt.setOnClickListener(vl);
        
          bt = (Button)findViewById(R.id.signinBtn);
          bt.setOnClickListener(ll);
  }
	  @Override
	  public void onDestroy()
	  {
		  super.onDestroy();
		  instance = null;
	  }
	  public static EnterActivity getInstance()
	  {
		  return instance;
	  }

}
