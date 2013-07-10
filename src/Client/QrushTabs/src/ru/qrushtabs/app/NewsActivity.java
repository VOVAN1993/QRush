package ru.qrushtabs.app;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class NewsActivity extends Activity 
{
	ArrayList<NewsContent> newsInfo;
	NewsArrayAdapter newsInfoAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.news_tab);
		 

		 
		 
		ListView lv = (ListView) findViewById(R.id.news_list);
		newsInfo = new ArrayList<NewsContent>();
		for(int i = 0;i < 15;i++)
		{
			NewsContent nc = new NewsContent();
			nc.setContent("bla bla "+i);
			if(i%1==0)
			{
				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.banner);
				nc.setBitmap(bmp);
			}
			if(i%4==0)
			{
				nc.setScannable(false);
			}
			newsInfo.add(nc);
			
		}
		
		newsInfoAdapter = new NewsArrayAdapter(this,newsInfo);
		
		lv.setAdapter(newsInfoAdapter);

	}
}
