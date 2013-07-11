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

 
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EnterActivity extends Activity {
	
	OnClickListener l = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			 
			Intent intent = new Intent(EnterActivity.this, RegistrationActivity.class);
			finish();
			startActivity(intent);
			
		}
		
		
	};
	  public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          
          this.setContentView(R.layout.enter);
          Button bt = (Button)findViewById(R.id.loginbtn);
          bt.setOnClickListener(l);
        
  }

}
