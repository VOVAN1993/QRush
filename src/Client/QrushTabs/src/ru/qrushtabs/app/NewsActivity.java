package ru.qrushtabs.app;

import java.util.ArrayList;

import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;

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
 		 
// 		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.news_tab);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		 
		//ServerAPI.getFriendsScans();
		ListView lv = (ListView) findViewById(R.id.news_list);
//		newsInfo = new ArrayList<NewsContent>();
//		for(int i = 0;i < 15;i++)
//		{
//			NewsContent nc = new NewsContent();
//			nc.setContent("bla bla "+i);
//			if(i%1==0)
//			{
//				Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.banner);
//				nc.setBitmap(bmp);
//			}
//			if(i%4==0)
//			{
//				nc.setScannable(false);
//			}
//			newsInfo.add(nc);
//			
//		}
		ArrayList<ScanObject> friendsScans = ServerAPI.getFriendsScans();
		if(friendsScans==null || friendsScans.size()==0)
		{
			SadSmile.setSadSmile(this, "Љ сожалению, на данный момент, у вас нет доступных ресканов.");
		}
		else
		{
			newsInfoAdapter = new NewsArrayAdapter(this,friendsScans);
		
			lv.setAdapter(newsInfoAdapter);
		}

	}
}
