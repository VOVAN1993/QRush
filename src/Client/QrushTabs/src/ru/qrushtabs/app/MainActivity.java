package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

import com.vungle.sdk.VunglePub;
import ru.qrushtabs.app.badges.Badges;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.friends.FriendField;
import ru.qrushtabs.app.profile.ProfileActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
import ru.qrushtabs.app.quests.QuestsActivity;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends TabActivity {
	private static MainActivity instance;

	TabHost tabHost;
	/** Called when the activit y is first created. */
	Handler mHandler = new Handler();

	OnClickListener onSettingsButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Intent intent = new Intent(MainActivity.this,
					SettingsActivity.class);
			startActivity(intent);

		}

	};

	@Override
	public void onResume() {
		super.onResume();
		 VunglePub.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		 VunglePub.onPause();
	}

	OnClickListener onScanBoxButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Intent intent = new Intent(MainActivity.this, ScanBoxActivity.class);
			startActivity(intent);

		}

	};
	private OnTabChangeListener l = new OnTabChangeListener() {

		private OnClickListener onGetMoneyButtonClickListener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
								 
					Intent intent = new Intent(MainActivity.this,
							GetMoneyActivity.class);
					startActivity(intent);				 
			}

		};
		OnClickListener onScanBoxButtonClickListener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(MainActivity.this,
						ScanBoxActivity.class);
				startActivity(intent);

			}

		};

		@Override
		public void onTabChanged(String arg0) {
			int i = tabHost.getCurrentTab();
			Button b = (Button) findViewById(R.id.title_right_button);
			b.setVisibility(View.VISIBLE);

			if (i == 0) {
				b.setOnClickListener(onGetMoneyButtonClickListener);
				b.setBackgroundDrawable(MainActivity.this.getResources()
						.getDrawable(R.drawable.getmoneybtn));
			}
			if (i == 1) {
				b.setVisibility(View.GONE);
			}
			if (i == 2) {
				b.setOnClickListener(onScanBoxButtonClickListener);
				if (ScanBox.getScansInfo().size() == 0)
					b.setBackgroundDrawable(MainActivity.this.getResources()
							.getDrawable(R.drawable.scanboxbtn));
				else
					b.setBackgroundDrawable(MainActivity.this.getResources()
							.getDrawable(R.drawable.scanboxbtnactive));

			}
			if (i == 3) {
				b.setVisibility(View.GONE);
			}
			if (i == 4) {
				b.setVisibility(View.GONE);
			}

		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		instance = this;
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.main_tabs);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Button b = (Button) findViewById(R.id.title_right_button);
		//
		b.setOnClickListener(onScanBoxButtonClickListener);
		//
		b = (Button) findViewById(R.id.title_left_button);

		b.setOnClickListener(onSettingsButtonClickListener);
		// l.onTabChanged("h");
		ScanBox.loadScans(this);
		if (ServerAPI.isOnline()) {
			Badges.initBadges(this);
			QuestObject.setAllQuests(ServerAPI.getQuests());
			QuestObject.addQuest(ServerAPI.getDailyQuest());
		}

		ServerAPI.loadProgress();
		QuestObject.checkTasks();
		b = (Button) findViewById(R.id.title_right_button);
		if (ScanBox.getScansInfo().size() > 0)
			b.setBackgroundDrawable(MainActivity.this.getResources()
					.getDrawable(R.drawable.scanboxbtnactive));
		initTabs();

		if (ServerAPI.isOnline()) {
			String users[] = ServerAPI.getMyFriends(ProfileInfo.username, "",
					0, 0);

			FriendField friends[] = new FriendField[users.length];
			ProfileInfo.friendsList = friends;
			for (int i = 0; i < users.length; i++) {
				try {
					friends[i] = FriendField.parse(new JSONObject(users[i]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ScanBox.saveScans();

		ServerAPI.saveProgress();
	}

	private void initTabs() {
		tabHost = getTabHost();
		tabHost.setup();

		
		TabHost.TabSpec tabSpec;
		Log.d("tabs", tabHost.getTabWidget().getMeasuredHeight()+"");
		
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
		for (int i = 0; i < 5; i++) {
			View v = tabHost.getTabWidget().getChildTabViewAt(i);
			v.setBackgroundDrawable(null);
			ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v
					.getLayoutParams();
			params.setMargins(-4, -4, -4, -4);
		}

		tabHost.setCurrentTabByTag("tag1");

		tabHost.setOnTabChangedListener(l);

	}

	public static MainActivity getInstance() {
		if (instance != null)
			return instance;
		else {
			return new MainActivity();
		}
	}
}