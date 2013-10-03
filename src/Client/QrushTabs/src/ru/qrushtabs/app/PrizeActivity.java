package ru.qrushtabs.app;

import com.vungle.sdk.VunglePub;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PrizeActivity extends MyVungleActivity {
	
	public static int currentPrize = 0;//лучше передачу в активити сделать
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		if (VunglePub.isVideoAvailable(true))
//			//VunglePub.displayAdvert();
//		else {
//			Log.d("Vingle", "not available");
//		}
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.prize);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.simple_title);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        Button backButton = (Button)this.findViewById(R.id.continue_btn);
		backButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
		    finish();
		  }
		});
		
		TextView tv = (TextView)findViewById(R.id.prizeCountTV);
		
		tv.setText(""+currentPrize);
	}
}
