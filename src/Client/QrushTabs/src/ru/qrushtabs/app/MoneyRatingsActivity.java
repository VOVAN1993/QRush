package ru.qrushtabs.app;

import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MoneyRatingsActivity extends Activity {

	ListView lv;
	private static boolean inited = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.money_rating);

		initTabs();

	}

	private void initTabs() {
		  
			
			RatingField[] values2 = ServerAPI.getTopUsers();
 			RatingField[] values1 = values2;
			
			RatingsArrayAdapter adapter1 = new RatingsArrayAdapter(this,
					values1);
			lv = (ListView) findViewById(R.id.money_ratings_list1);
			lv.setAdapter(adapter1);
			
			RatingsArrayAdapter adapter2 = new RatingsArrayAdapter(this,
					values2);
			lv = (ListView) findViewById(R.id.money_ratings_list2);
			lv.setAdapter(adapter2);
			inited = true;
		
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

		// tabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);

		// View tabview = createTabView(tabHost.getContext(), "");
		// TabSpec setContent = tabHost.newTabSpec("").setIndicator(tabview);
		//
		// mTabHost.addTab(setContent);

		// for (int i = 0; i < 2; i++)
		// tabHost.getTabWidget().getChildTabViewAt(i)
		// .setBackgroundDrawable(null);
	}

	// private void setupTab(final View view, final String tag) {
	//
	// View tabview = createTabView(mTabHost.getContext(), tag);
	// TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview);
	//
	// mTabHost.addTab(setContent);
	//
	// }

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
