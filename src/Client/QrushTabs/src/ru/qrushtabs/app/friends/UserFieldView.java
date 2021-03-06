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

public class UserFieldView extends LinearLayout {

	 
	private View rowView; 
	UserField rf;
	private Context context;
	private OnClickListener l = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			Intent intent = new Intent(UserFieldView.this.context,OtherProfileActivity.class);
			intent.putExtra("username", rf.username);
			intent.putExtra("city", rf.city);
			UserFieldView.this.context.startActivity(intent);
			
		}
		
	};
 	public UserFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.user_field, this, false);
	   
	   //newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
	   this.addView(rowView);
	   
	   this.setOnClickListener(l );
	   
//	   rescanButton = (Button)rowView.findViewById(R.id.rescan_btn);
//	   arrowsImg = (ImageView)rowView.findViewById(R.id.rescan_arrows_icon);
	   //imgPlace = (ImageView)rowView.findViewById(R.id.news_content_iv);
	}
	public UserFieldView(Context context) {
		this(context, null);
	}
	
	public void setUserInfo(UserField rf) 
	{
		TextView textView = (TextView) rowView.findViewById(R.id.user_username_tv);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.user_icon);
	        
	        // ��������� ������ ��� Windows � iPhone
	        
	        this.rf = rf;
	        textView.setText(rf.username);
	        UserPhotosMap.setToImageView(rf.username, imageView);
	        Button btn = (Button)rowView.findViewById(R.id.add_friend_btn);

	        btn.setOnClickListener(new OnClickListener()
	        {

				@Override
				public void onClick(View arg0) {
					Button btn = (Button)rowView.findViewById(R.id.add_friend_btn);
					btn.setVisibility(View.GONE);
	 				
	 				
	 				BlackAlertDialog newFragment;
					newFragment = new BlackAlertDialog();
 					 
	 
 						if(ServerAPI.addFriend(UserFieldView.this.rf.username).equals("true"))
 						{
 							newFragment.setLabelText("������ �� ������ ������� ���������");
 							newFragment.show(((FriendsSearchActivity)UserFieldView.this.context).getSupportFragmentManager(),
 									"missiles");
 							ProfileInfo.friendsChanged = true;
 							newFragment.setDrawableBackground(((FriendsSearchActivity)UserFieldView.this.context).getResources().getDrawable(R.drawable.black_alert_ok));	 
 						}
 						else
 						{
 							newFragment.setLabelText("�� �������");
 							newFragment.show(((FriendsSearchActivity)UserFieldView.this.context).getSupportFragmentManager(),
 									"missiles");
 							newFragment.setDrawableBackground(((FriendsSearchActivity)UserFieldView.this.context).getResources().getDrawable(R.drawable.black_alert_error));	
 						}
						 
				 
					 	
					
				}
	        	
	        });
  	}
 
 
 
 

}
