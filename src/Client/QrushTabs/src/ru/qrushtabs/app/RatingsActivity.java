package ru.qrushtabs.app;

 import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

public class RatingsActivity extends TabActivity {

	ListView lv;
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        
  	        setContentView(R.layout.ratings_tab);
 	        
// 	       String[] values1 = new String[] { "Android", "iPhone", "WindowsMobile",
// 	                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
// 	                "Linux", "OS/2" };
// 	      String[] values2 = new String[] { "iPhone", "WindowsMobile",
//	                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//	                "Linux", "OS/2" };
// 	        RatingsArrayAdapter adapter1 = new RatingsArrayAdapter(this, values1);
//	        lv = (ListView)findViewById(R.id.ratings_list1);
//	        lv.setAdapter(adapter1);
//	        
//	        RatingsArrayAdapter adapter2 = new RatingsArrayAdapter(this, values2);
//	        lv = (ListView)findViewById(R.id.ratings_list2);
//	        lv.setAdapter(adapter2);
	         
	        initTabs();
//	        iv = (ImageView)findViewById(R.drawable.banner);
//	        
//	        iv.setOnClickListener(new OnClickListener() {
//	            public void onClick(View v) {
//	            	 
//	                 
//	                 Intent intent = new Intent(th, CameraTestActivity.class);
//	         		 startActivity(intent);
//	            }
//	        });
	        
	       
	 }
	 private void initTabs() {
			TabHost tabHost = getTabHost();
			// инициализация
			tabHost.setup();

			TabHost.TabSpec tabSpec;

			tabSpec = tabHost.newTabSpec("tag1");
			tabSpec.setIndicator("",
					getResources().getDrawable(R.drawable.earned_tab_selector));
			 tabSpec.setContent(new Intent(this,MoneyRatingsActivity.class));
			tabHost.addTab(tabSpec);

			tabSpec = tabHost.newTabSpec("tag2");
			tabSpec.setIndicator("",
					getResources().getDrawable(R.drawable.scanned_tab_selector));
			 tabSpec.setContent(new Intent(this,ScansRatingsActivity.class));
			tabHost.addTab(tabSpec);
			
			for (int i = 0; i < 2; i++)
				tabHost.getTabWidget().getChildTabViewAt(i)
						.setBackgroundDrawable(null);
	 }
}
