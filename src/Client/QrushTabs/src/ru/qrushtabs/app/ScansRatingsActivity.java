package ru.qrushtabs.app;

 import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;

public class ScansRatingsActivity extends Activity {

	ListView lv;
	 public void onCreate(Bundle savedInstanceState) 
	 {
	        super.onCreate(savedInstanceState);
	        
  	        setContentView(R.layout.scans_rating);
 	        
 	       String[] values1 = new String[] { "Android", "iPhone", "WindowsMobile",
 	                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
 	                "Linux", "OS/2" };
 	      String[] values2 = new String[] { "iPhone", "WindowsMobile",
	                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	                "Linux", "OS/2" };
 	        RatingsArrayAdapter adapter1 = new RatingsArrayAdapter(this, values1);
	        lv = (ListView)findViewById(R.id.scan_ratings_list1);
	        lv.setAdapter(adapter1);
	        
	        RatingsArrayAdapter adapter2 = new RatingsArrayAdapter(this, values2);
	        lv = (ListView)findViewById(R.id.scan_ratings_list2);
	        lv.setAdapter(adapter2);
	         
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
			TabHost tabHost = (TabHost)findViewById(R.id.rating_type_tabhost);
			// инициализация
			tabHost.setup();

			TabHost.TabSpec tabSpec;

			tabSpec = tabHost.newTabSpec("tag1");
			tabSpec.setIndicator("Всего");
			 tabSpec.setContent(R.id.scan_ratings_list1);
			tabHost.addTab(tabSpec);

			tabSpec = tabHost.newTabSpec("tag2");
			tabSpec.setIndicator("Сегодня");
			 tabSpec.setContent(R.id.scan_ratings_list2);
			tabHost.addTab(tabSpec);
	 }
}
