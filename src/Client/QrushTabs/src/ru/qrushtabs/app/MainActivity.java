package ru.qrushtabs.app;

 import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
 

public class MainActivity extends TabActivity {
	/** Called when the activity is first created. */
	Handler mHandler = new Handler();
	OnClickListener onScanBoxButtonClickListener = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			Intent intent = new Intent(MainActivity.this,ScanBoxActivity.class);
			startActivity(intent);
			
		}
		
	};
	OnClickListener onSettingsButtonClickListener = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
			startActivity(intent);
			
		}
		
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main_tabs);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        Button b = (Button)findViewById(R.id.scan_box_button);
        
        b.setOnClickListener(onScanBoxButtonClickListener);
        
        b = (Button)findViewById(R.id.settings_btn);
        
        b.setOnClickListener(onSettingsButtonClickListener);
        
        ScanBox.loadScans(this);
		initTabs();

	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		ScanBox.saveScans();
	}
	private void initTabs() {
		TabHost tabHost = getTabHost();
 		tabHost.setup();

		TabHost.TabSpec tabSpec;

		tabSpec = tabHost.newTabSpec("tag1");
		tabSpec.setIndicator("",
				getResources().getDrawable(R.drawable.profile_tab_selector));
		 tabSpec.setContent(new Intent(this, ProfileActivity.class));
		tabHost.addTab(tabSpec);
 		tabSpec = tabHost.newTabSpec("tag2");
		tabSpec.setIndicator("",
				getResources().getDrawable(R.drawable.note_tab_selector));
		 tabSpec.setContent(new Intent(this, NewsActivity.class));
		tabHost.addTab(tabSpec);
 		tabSpec = tabHost.newTabSpec("tag3");
		tabSpec.setIndicator("",
				getResources().getDrawable(R.drawable.scan_tab_selector));
		 tabSpec.setContent(new Intent(this, CameraActivity.class));
		tabHost.addTab(tabSpec);
 		tabSpec = tabHost.newTabSpec("tag4");
		tabSpec.setIndicator("",
				getResources().getDrawable(R.drawable.raiting_tab_selector));
		 tabSpec.setContent(new Intent(this, MoneyRatingsActivity.class));
		tabHost.addTab(tabSpec);
 		tabSpec = tabHost.newTabSpec("tag5");
		tabSpec.setIndicator("",
				getResources().getDrawable(R.drawable.star_tab_selector));
		 tabSpec.setContent(new Intent(this, QuestsActivity.class));
		tabHost.addTab(tabSpec);
 		for (int i = 0; i < 5; i++)
 		{
 			 View v = tabHost.getTabWidget().getChildTabViewAt(i);
			v.setBackgroundDrawable(null);
			 ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
		        params.setMargins(-4, -4, -4, -4);
 		}

 		tabHost.setCurrentTabByTag("tag3");

 		 
	}
}