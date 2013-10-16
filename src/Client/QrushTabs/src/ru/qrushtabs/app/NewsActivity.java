package ru.qrushtabs.app;

import java.util.ArrayList;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.QRLoading;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

public class NewsActivity extends MyVungleActivity 
{
	private ArrayList<ScanObject> newsContent;
    private NewsArrayAdapter newsInfoAdapter;
	ListView lv;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		 
        

		

	}
	private class LoadRatingsTask extends AsyncTask<Void, Void, ArrayList<ScanObject>> {

		protected ArrayList<ScanObject> doInBackground(Void...voids) {
			return  ServerAPI.getFriendsScans();
		}

		protected void onPostExecute(ArrayList<ScanObject> objResult) {

			if(objResult==null || objResult.size()==0)
			{
				SadSmile.setSadSmile(NewsActivity.this, "К сожалению, на данный момент, у вас нет доступных ресканов.");
			}
			else
			{
				newsContent = objResult;
				
				for(int i = 0;i<newsContent.size();i++)
				{
					if (ProfileInfo.haveScan(newsContent.get(i).code)) 
					{
						
						newsContent.get(i).scanned = true;
					}
					 
				}
				cleanScans();
				if(newsContent.size()>0)
				{
					
					setContentView(R.layout.news_tab);
					lv = (ListView) findViewById(R.id.news_list);
					newsInfoAdapter = new NewsArrayAdapter(NewsActivity.this,objResult);			
					lv.setAdapter(newsInfoAdapter);
					
				}
				else
				{
					SadSmile.setSadSmile(NewsActivity.this, "К сожалению, на данный момент, у вас нет доступных ресканов.");
				}
			}
			
		}
	}
	private void cleanScans()
	{
		Log.d("news", "cleanScans");
		int all = 0;
		for(int i = 0;i<newsContent.size();i++)
		{
			if (newsContent.get(i).scanned || all>10) 
			{
				newsContent.remove(i);
				i--;
			}
			else
			{
				all++;
			}
		}
	}
	@Override
	public void onResume()
	{
		super.onResume();
		if(ServerAPI.isOnline() && !ServerAPI.offlineMod)
		{
		 
				QrushTabsApp.getGaTracker().set(Fields.SCREEN_NAME, "Rescans Screen");
				QrushTabsApp.getGaTracker().send(MapBuilder.createAppView().build());
			 
			QRLoading.setLoading(this);
			(new LoadRatingsTask()).execute();
			
			
 
		}
		else
		{
			SadSmile.setSadSmile(this, "К сожалению, на данный момент, вы в оффлайн режиме.");
		}
	}
}
