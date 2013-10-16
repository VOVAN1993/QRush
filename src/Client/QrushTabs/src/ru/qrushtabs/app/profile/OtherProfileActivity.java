package ru.qrushtabs.app.profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.MyNewsContentView;
import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.NewsContentView;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.drawable;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.ScanObject;
import ru.qrushtabs.app.badges.Badge;
import ru.qrushtabs.app.badges.Badges;
import ru.qrushtabs.app.badges.BadgesActivity;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.friends.FriendField;
import ru.qrushtabs.app.friends.OtherFriendsActivity;
import ru.qrushtabs.app.utils.ActivitiesStack;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.OnInfoLoadListener;
import ru.qrushtabs.app.utils.QRLoading;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class OtherProfileActivity extends MyVungleActivity {
	OnInfoLoadListener onInfoFromLoad = new OnInfoLoadListener() {
		@Override
		public void onSuccess() {

		}

		@Override
		public void onFail() {

		}
	};
	ArrayList<ScanObject> newsInfo;
	ArrayList<ScanObject> questsInfo;
	ImageView newsLoadIV;
	private String username;
	private String city;
	ImageView ivPeakOver;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.profile_tab);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		newsLoadIV = (ImageView)findViewById(R.id.news_load_iv);

		Intent intent = getIntent();
		TextView tv = (TextView) findViewById(R.id.name_tv);
		username = intent.getStringExtra("username");
		tv.setText(username);

		city = intent.getStringExtra("city");
		tv = (TextView) findViewById(R.id.city_tv);
		tv.setText(city);
		OtherProfileInfo opi = ServerAPI.loadOtherProfileInfo(username);
		if (opi != null) {
			tv = (TextView) findViewById(R.id.my_scans_count_tv);
			tv.setText(String.valueOf(opi.getScansCount()));

			tv = (TextView) findViewById(R.id.my_rescans_count_tv);
			tv.setText(String.valueOf(opi.getRescansCount()));

			tv = (TextView) findViewById(R.id.profile_money_tv);
			tv.setText(String.valueOf(opi.getMoneyCount()));
		}
		ActivitiesStack.addActivity(this);

		Button b = (Button) findViewById(R.id.title_left_button);

		b.setBackgroundDrawable(getResources().getDrawable(R.drawable.backbtn));

		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivitiesStack.finishAll();

			}
		});

		b = (Button) findViewById(R.id.title_right_button);

		b.setVisibility(View.VISIBLE);

		
		LinearLayout badgesBtn = (LinearLayout) findViewById(R.id.badgessbtn);

		badgesBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OtherProfileActivity.this,
						BadgesActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
			}

		});

		if (ProfileInfo.username.equals(username))
			b.setVisibility(View.GONE);
		else

		if (ProfileInfo.checkMyFriend(username)) {
			b.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.add_friendbtn));
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();
					if (ServerAPI.addFriend(username).equals("true")) {
						newFragment
								.setLabelText("Запрос на дружбу успешно отправлен");
						newFragment.show(OtherProfileActivity.this
								.getSupportFragmentManager(), "missiles");
						ProfileInfo.friendsChanged = true;
						newFragment
								.setDrawableBackground(OtherProfileActivity.this
										.getResources().getDrawable(
												R.drawable.black_alert_ok));
						
					} else {
						newFragment.setLabelText("Запрос уже был отправлен");
						newFragment.show(OtherProfileActivity.this
								.getSupportFragmentManager(), "missiles");
						newFragment
								.setDrawableBackground(OtherProfileActivity.this
										.getResources().getDrawable(
												R.drawable.black_alert_error));
					}
					Button b = (Button) findViewById(R.id.title_right_button);
					b.setVisibility(View.INVISIBLE);
				}
			});
		} else {
			b.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.unfollowbtn));
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();
					if (ServerAPI.removeFriend(username).equals("true")) {
						newFragment.setLabelText("Друг успешно удален");
						newFragment.show(OtherProfileActivity.this
								.getSupportFragmentManager(), "missiles");
						ProfileInfo.friendsChanged = true;
						newFragment
								.setDrawableBackground(OtherProfileActivity.this
										.getResources().getDrawable(
												R.drawable.black_alert_ok));
					} else {
						newFragment.setLabelText("Не удалось");
						newFragment.show(OtherProfileActivity.this
								.getSupportFragmentManager(), "missiles");
						newFragment
								.setDrawableBackground(OtherProfileActivity.this
										.getResources().getDrawable(
												R.drawable.black_alert_error));
					}
					Button b = (Button) findViewById(R.id.title_right_button);
					b.setVisibility(View.INVISIBLE);

				}
			});
		}

		ivPeakOver = (ImageView) findViewById(R.id.avatar_iv);

		UserPhotosMap.setToImageView(intent.getStringExtra("username"),
				ivPeakOver);

		LinearLayout friendsBtn = (LinearLayout) findViewById(R.id.friendsbtn);

		friendsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(OtherProfileActivity.this,
						OtherFriendsActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
			}

		});
		//
		if (!ServerAPI.isOnline())
			return;

		newsLoadIV.setVisibility(View.VISIBLE);
		QRLoading.setLoading(newsLoadIV);
		
		(new LoadNewsTask()).execute();
		String users[] = ServerAPI.getMyFriends(username, "", 0, 0);
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

		String badgesStr[] = ServerAPI.getAchievments(username);

		if (badgesStr.length > 0)
			((ImageView) findViewById(R.id.bg_iv1)).setImageDrawable(Badges
					.getDrawable(badgesStr[0]));
		if (badgesStr.length > 1)
			((ImageView) findViewById(R.id.bg_iv2)).setImageDrawable(Badges
					.getDrawable(badgesStr[1]));
		if (badgesStr.length > 2)
			((ImageView) findViewById(R.id.bg_iv3)).setImageDrawable(Badges
					.getDrawable(badgesStr[2]));
	}

	private class LoadNewsTask extends AsyncTask<Void, Void, Integer> {

		protected Integer doInBackground(Void...voids) {
			newsInfo = ServerAPI
					.getScans(OtherProfileActivity.this.username);
			questsInfo = ServerAPI
					.getCompletedQuestsForNews(OtherProfileActivity.this.username);
			newsInfo.addAll(0, questsInfo);

			
			return 0;
			 
		}

		protected void onPostExecute(Integer objResult) {
			 
			QRLoading.stopLoading(newsLoadIV);
			newsLoadIV.setVisibility(View.GONE);
			Comparator<ScanObject> cmp = new Comparator<ScanObject>() {

				@Override
				public int compare(ScanObject lhs, ScanObject rhs) {
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-mm-dd HH:mm");
					Date date1 = null;
					Date date2 = null;
					try {
						date1 = ft.parse(lhs.date);
						date2 = ft.parse(rhs.date);
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
							OtherProfileActivity.this);
					ncv.setNewsContent(newsInfo.get(i));
					all++;
					lv.addView(ncv);
				}

			}
		}
	}
	@Override
	public void onResume() {
		super.onResume();

		 

	}

}