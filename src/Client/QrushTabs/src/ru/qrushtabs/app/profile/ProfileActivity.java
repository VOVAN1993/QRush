package ru.qrushtabs.app.profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import ru.qrushtabs.app.ChangeProfileActivity;
import ru.qrushtabs.app.MyNewsContentView;
import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.NewsActivity;
import ru.qrushtabs.app.NewsArrayAdapter;
import ru.qrushtabs.app.NewsContentView;
import ru.qrushtabs.app.QrushTabsApp;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.ScanBox;
import ru.qrushtabs.app.ScanObject;
import ru.qrushtabs.app.SettingsActivity;
import ru.qrushtabs.app.R.drawable;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.badges.Badge;
import ru.qrushtabs.app.badges.Badges;
import ru.qrushtabs.app.badges.BadgesActivity;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.friends.FriendField;
import ru.qrushtabs.app.friends.FriendsActivity;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.OnInfoLoadListener;
import ru.qrushtabs.app.utils.QRLoading;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProfileActivity extends MyVungleActivity {
	ArrayList<ScanObject> newsInfo;
	ArrayList<ScanObject> questsInfo;
	ImageView newsLoadIV;
	OnInfoLoadListener onInfoFromLoad = new OnInfoLoadListener() {
		@Override
		public void onSuccess() {

		}

		@Override
		public void onFail() {

		}
	};

	ImageView ivPeakOver;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.profile_tab);

		newsLoadIV = (ImageView) ProfileActivity.this
				.findViewById(R.id.news_load_iv);
		if (!ServerAPI.offlineMod && ServerAPI.isOnline()) {
			String users[] = ServerAPI.getMyFriends(ProfileInfo.username, "",
					0, 0);
			FriendField friends[] = new FriendField[users.length];
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
			String req_users[] = ServerAPI.getReqFriends();
			ImageView flag = (ImageView) findViewById(R.id.requests_flag);
			if (req_users.length > 0) {
				flag.setVisibility(View.VISIBLE);
			} else {
				flag.setVisibility(View.GONE);
			}
			if (friends.length > 0)
				UserPhotosMap.setToImageView(friends[0].username,
						(ImageView) findViewById(R.id.fr_iv1));
			if (friends.length > 1)
				UserPhotosMap.setToImageView(friends[1].username,
						(ImageView) findViewById(R.id.fr_iv2));
			if (friends.length > 2)
				UserPhotosMap.setToImageView(friends[2].username,
						(ImageView) findViewById(R.id.fr_iv3));
		}
		ArrayList<Badge> badges = Badges.getAchievedBadges();

		if (badges.size() > 0)
			((ImageView) findViewById(R.id.bg_iv1)).setImageDrawable(badges
					.get(0).smallBadgeIcon);
		if (badges.size() > 1)
			((ImageView) findViewById(R.id.bg_iv2)).setImageDrawable(badges
					.get(1).smallBadgeIcon);
		if (badges.size() > 2)
			((ImageView) findViewById(R.id.bg_iv3)).setImageDrawable(badges
					.get(2).smallBadgeIcon);

		LinearLayout friendsBtn = (LinearLayout) findViewById(R.id.friendsbtn);

		friendsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!ServerAPI.offlineMod && ServerAPI.isOnline()) {
					Intent intent = new Intent(ProfileActivity.this,
							FriendsActivity.class);
					startActivity(intent);
				} else {
					BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();

					newFragment
							.setLabelText("� ������� ������ ���������� ������ � ����������!");
					newFragment.show(
							ProfileActivity.this.getSupportFragmentManager(),
							"missiles");
					newFragment.setDrawableBackground(ProfileActivity.this
							.getResources().getDrawable(
									R.drawable.black_alert_error));

				}

			}

		});

		LinearLayout badgesBtn = (LinearLayout) findViewById(R.id.badgessbtn);

		badgesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!ServerAPI.offlineMod && ServerAPI.isOnline()) {
					Intent intent = new Intent(ProfileActivity.this,
							BadgesActivity.class);
					intent.putExtra("username", ProfileInfo.username);
					startActivity(intent);
				} else {
					BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();

					newFragment
							.setLabelText("� ������� ������ ���������� ������ � ����������!");
					newFragment.show(
							ProfileActivity.this.getSupportFragmentManager(),
							"missiles");
					newFragment.setDrawableBackground(ProfileActivity.this
							.getResources().getDrawable(
									R.drawable.black_alert_error));

				}
			}

		});

	}

	private class LoadNewsTask extends AsyncTask<Void, Void, Integer> {

		protected Integer doInBackground(Void... voids) {
			newsInfo = ServerAPI.getScans(ProfileInfo.username);
			ProfileInfo.syncScans(newsInfo);
			questsInfo = ServerAPI
					.getCompletedQuestsForNews(ProfileInfo.username);
			newsInfo.addAll(0, questsInfo);

			return 0;

		}

		protected void onPostExecute(Integer objResult) {

			QRLoading.stopLoading(newsLoadIV);
			newsLoadIV.setVisibility(View.GONE);
			Comparator<ScanObject> cmp = new Comparator<ScanObject>() {
				SimpleDateFormat ft1 =new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				SimpleDateFormat ft2 =new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				@Override
				public int compare(ScanObject lhs, ScanObject rhs) {
 					 
					Date date1 = null;
					Date date2 = null;
					try {
						if(lhs.date.length()<19)
							date1 = ft1.parse(lhs.date);
						else
							date1 = ft2.parse(lhs.date);
						
						if(rhs.date.length()<19)
							date2 = ft1.parse(rhs.date);
						else
							date2 = ft2.parse(rhs.date);
 						if (date2.before(date1))
							return -1;
						else
							return 1;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return 0;
				}
			};
			Collections.sort(newsInfo, cmp);
			LinearLayout lv = (LinearLayout) findViewById(R.id.my_news_ll);
			lv.removeAllViews();
			lv.addView(newsLoadIV);
			int all = 0;
			for (int i = 0; all < 20 && i < newsInfo.size(); i++) {
				if (Integer.valueOf(newsInfo.get(i).prize) > 0) {
					MyNewsContentView ncv = new MyNewsContentView(
							ProfileActivity.this);
					ncv.setNewsContent(newsInfo.get(i));
					all++;
					lv.addView(ncv);
				}

			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ProfileInfo.newsChanged = true;
		ProfileInfo.profileChanged = true;
	}

	private static final String SCREEN_LABEL = "Profile Screen";

	@Override
	public void onPause()
	{
		super.onPause();
		Log.d("ProfileActivity", "onPause");
	}
	@Override
	public void onStart() {
		super.onStart();
		Log.d("ProfileActivit", "onStart");
		//EasyTracker.getInstance(this).activityStart(this);
		QrushTabsApp.getGaTracker().set(Fields.SCREEN_NAME, SCREEN_LABEL);
		QrushTabsApp.getGaTracker().send(MapBuilder.createAppView().build());
 		//QrushTabsApp.getGaTracker().send(MapBuilder.createAppView().build());

	}

	@Override
	public void onStop() {
		Log.d("ProfileActivit", "onStop");
		super.onStop();
 		

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("ProfileActivit", "onResume");
		//QrushTabsApp.getGaTracker().
		//HashMap<String, String> hitParameters = new HashMap<String, String>();
		//hitParameters.put(Fields.HIT_TYPE, "view");
		//hitParameters.put(Fields.SCREEN_NAME, "Profile Screen");

		//QrushTabsApp.getGaTracker().send(hitParameters);
		
		//QrushTabsApp.getGaTracker().set(Fields.SCREEN_NAME, SCREEN_LABEL);
		if (ProfileInfo.profileChanged) {
			TextView tv = (TextView) findViewById(R.id.name_tv);
			tv.setText(ProfileInfo.username);

			tv = (TextView) findViewById(R.id.city_tv);
			tv.setText(ProfileInfo.city);

			tv = (TextView) findViewById(R.id.my_scans_count_tv);
			tv.setText(String.valueOf(ProfileInfo.getScansCount()));

			tv = (TextView) findViewById(R.id.my_rescans_count_tv);
			tv.setText(String.valueOf(ProfileInfo.getRescansCount()));

			tv = (TextView) findViewById(R.id.profile_money_tv);
			tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));

			ivPeakOver = (ImageView) findViewById(R.id.avatar_iv);

			final float scale = getBaseContext().getResources()
					.getDisplayMetrics().density;

			int dpw = 86;
			int dph = 86;
			Bitmap bmp = null;

			// Log.d("avatar path", ProfileInfo.avatarPath);
			if (ProfileInfo.avatarPath != null
					&& !ProfileInfo.avatarPath.equals("0")
					&& ProfileInfo.avatarPath.length() > 0) {
				if (ProfileInfo.avatarBitmap == null)
					bmp = BitmapFactory.decodeFile(ProfileInfo.avatarPath);
				else
					bmp = ProfileInfo.avatarBitmap;

				if (bmp == null)
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.qrcat);
				ivPeakOver.setImageBitmap(BitmapCropper.crop(scale, bmp, dpw,
						dph));

			} else {

				if (ServerAPI.isOnline()) {
					byte[] bytes = ServerAPI
							.downloadProfilePhoto(ProfileInfo.username);
					if (bytes != null)
						bmp = BitmapFactory.decodeByteArray(bytes, 0,
								bytes.length);
					else
						bmp = BitmapFactory.decodeResource(getResources(),
								R.drawable.qrcat);
					UserPhotosMap.saveAvatar(bmp);
					ivPeakOver.setImageBitmap(BitmapCropper.crop(scale, bmp,
							dpw, dph));
				}

			}

			if (!ServerAPI.isOnline()) {
				return;
			}
			String req_users[] = ServerAPI.getReqFriends();
			ImageView flag = (ImageView) findViewById(R.id.requests_flag);
			if (req_users.length > 0) {
				flag.setVisibility(View.VISIBLE);
			} else {
				flag.setVisibility(View.GONE);
			}
			if (ProfileInfo.newsChanged) {
				newsLoadIV.setVisibility(View.VISIBLE);
				QRLoading.setLoading(newsLoadIV);

				(new LoadNewsTask()).execute();
				ProfileInfo.newsChanged = false;
			}

			if (ProfileInfo.friendsChanged) {
				String users[] = ServerAPI.getMyFriends(ProfileInfo.username,
						"", 0, 0);
				FriendField friends[] = new FriendField[users.length];
				for (int i = 0; i < users.length; i++) {
					try {
						friends[i] = FriendField
								.parse(new JSONObject(users[i]));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				((ImageView) findViewById(R.id.fr_iv1)).setImageBitmap(null);
				((ImageView) findViewById(R.id.fr_iv2)).setImageBitmap(null);
				((ImageView) findViewById(R.id.fr_iv3)).setImageBitmap(null);
				if (friends.length > 0)
					UserPhotosMap.setToImageView(friends[0].username,
							(ImageView) findViewById(R.id.fr_iv1));
				if (friends.length > 1)
					UserPhotosMap.setToImageView(friends[1].username,
							(ImageView) findViewById(R.id.fr_iv2));
				if (friends.length > 2)
					UserPhotosMap.setToImageView(friends[2].username,
							(ImageView) findViewById(R.id.fr_iv3));
			}
		}
	}

}