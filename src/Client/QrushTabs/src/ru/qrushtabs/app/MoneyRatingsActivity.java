package ru.qrushtabs.app;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.QRLoading;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MoneyRatingsActivity extends MyVungleActivity {

	ListView lv;
	private RatingField[] users;
	private RatingField[] friends;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(!ServerAPI.isOnline() || ServerAPI.offlineMod)
		{
			SadSmile.setSadSmile(this, "В оффлайне рейтинги недоступны");
         	return;
		}
		
		QRLoading.setLoading(this);
		(new LoadRatingsTask()).execute();

	}

	private class LoadRatingsTask extends AsyncTask<Void, Void, RatingField[]> {

		protected RatingField[] doInBackground(Void...voids) {
			return ServerAPI.getTopUsers();
		}

		protected void onPostExecute(RatingField[] objResult) {

			users = objResult;
			(new LoadFriendRatingsTask()).execute();
			
		}
	}
	
	private class LoadFriendRatingsTask extends AsyncTask<Void, Void, RatingField[]> {

		protected RatingField[] doInBackground(Void...voids) {
			return ServerAPI.getTopFriends();
		}

		protected void onPostExecute(RatingField[] objResult) {

			friends = objResult;
			initTabs();
			
		}
	}
	private void initTabs() {
		  
		QRLoading.stopLoading(this);
		setContentView(R.layout.money_rating);
 		
		LinearLayout ll1 = (LinearLayout)findViewById(R.id.money_ratings_list1);
		
		for(int i = 0;i<5;i++)
		{
			RatingFieldView rfv = new RatingFieldView(this);
			
			if(i>=users.length)
			{
				rfv.setRatingInfo(null);
			}
			else
			{
				rfv.setRatingInfo(users[i]);
			}
			ll1.addView(rfv);
		}
		LinearLayout ll2 = (LinearLayout)findViewById(R.id.money_ratings_list2);
		
		for(int i = 0;i<5;i++)
		{
			RatingFieldView rfv = new RatingFieldView(this);
			
			if(i>=friends.length)
			{
				rfv.setRatingInfo(null);
			}
			else
			{
				rfv.setRatingInfo(friends[i]);
			}
			ll2.addView(rfv);
		}
//		RatingsArrayAdapter adapter1 = new RatingsArrayAdapter(this,
//				users);
//		lv = (ListView) findViewById(R.id.money_ratings_list1);
//		lv.setAdapter(adapter1);
//		
//		RatingsArrayAdapter adapter2 = new RatingsArrayAdapter(this,
//				friends);
//		lv = (ListView) findViewById(R.id.money_ratings_list2);
//		lv.setAdapter(adapter2);
 		
		TabHost tabHost = (TabHost) findViewById(R.id.rating_type_tabhost);
		// инициализация
		tabHost.setup();
		//
		TabHost.TabSpec tabSpec;

		tabSpec = tabHost.newTabSpec("tag1");
		tabSpec.setIndicator(createLeftTabView(this, "Общий"));
		tabSpec.setContent(R.id.money_ratings_list1);
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag2");
		tabSpec.setIndicator(createRightTabView(this, "Друзья"));
		tabSpec.setContent(R.id.money_ratings_list2);
		tabHost.addTab(tabSpec);

	}

	 

	@Override
	public void onResume()
	{
		super.onResume();
		QrushTabsApp.getGaTracker().set(Fields.SCREEN_NAME, "Ratings Screen");
		QrushTabsApp.getGaTracker().send(MapBuilder.createAppView().build());
	}
	private View createLeftTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.left_tab_bg,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;

	}

	private View createRightTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.right_tab_bg,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;

	}

}
