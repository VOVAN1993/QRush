package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.profile.OtherProfileActivity;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendRequestView extends LinearLayout {

	private View rowView;
	FriendField rf;
	private Context context;
	private OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			Intent intent = new Intent(FriendRequestView.this.context, OtherProfileActivity.class);
			FriendRequestView.this.context.startActivity(intent);
		}

	};

	public FriendRequestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.friend_request_field, this, false);

		// newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
		this.addView(rowView);
		this.setOnClickListener(l);
		// rescanButton = (Button)rowView.findViewById(R.id.rescan_btn);
		// arrowsImg = (ImageView)rowView.findViewById(R.id.rescan_arrows_icon);
		// imgPlace = (ImageView)rowView.findViewById(R.id.news_content_iv);
	}

	public FriendRequestView(Context context) {
		this(context, null);
	}

	TextView textView;

	public void setFriendInfo(FriendField rf) {
		textView = (TextView) rowView.findViewById(R.id.friend_req_username_tv);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.friend_req_icon);
		Button btnA = (Button) rowView.findViewById(R.id.accept_friend_btn);
		Button btnD = (Button) rowView.findViewById(R.id.decline_friend_btn);
		// Изменение иконки для Windows и iPhone

		this.rf = rf;
		textView.setText(rf.username);
		UserPhotosMap.setToImageView(rf.username, imageView);
		btnA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Button btnA = (Button) rowView
						.findViewById(R.id.accept_friend_btn);
				Button btnD = (Button) rowView
						.findViewById(R.id.decline_friend_btn);
				btnA.setVisibility(View.GONE);
				btnD.setVisibility(View.GONE);
				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();

				if (ServerAPI.addFriend(FriendRequestView.this.rf.username)
						.equals("true")) {
					newFragment
							.setLabelText("Друг добавлен");
					newFragment.show(
							((FriendsActivity) FriendRequestView.this.context)
									.getSupportFragmentManager(), "missiles");
					ProfileInfo.friendsChanged = true;
					newFragment
							.setDrawableBackground(((FriendsActivity) FriendRequestView.this.context)
									.getResources().getDrawable(
											R.drawable.black_alert_ok));
				} else {
					newFragment.setLabelText("Не удалось");
					newFragment.show(
							((FriendsActivity) FriendRequestView.this.context)
									.getSupportFragmentManager(), "missiles");
					newFragment
							.setDrawableBackground(((FriendsActivity) FriendRequestView.this.context)
									.getResources().getDrawable(
											R.drawable.black_alert_error));
				}

			}

		});
		btnD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();
				if (ServerAPI.friendshipRequestRefuse(
						FriendRequestView.this.rf.username).equals("true")) {
					newFragment.setLabelText("Запрос на дружбу отклонен");
					newFragment.show(
							((FriendsActivity) FriendRequestView.this.context)
									.getSupportFragmentManager(), "missiles");
					ProfileInfo.friendsChanged = true;
					newFragment
							.setDrawableBackground(((FriendsActivity) FriendRequestView.this.context)
									.getResources().getDrawable(
											R.drawable.black_alert_ok));
				} else {
					newFragment.setLabelText("Не удалось");
					newFragment.show(
							((FriendsActivity) FriendRequestView.this.context)
									.getSupportFragmentManager(), "missiles");
					newFragment
							.setDrawableBackground(((FriendsActivity) FriendRequestView.this.context)
									.getResources().getDrawable(
											R.drawable.black_alert_error));
				}

			}

		});
	}

}
