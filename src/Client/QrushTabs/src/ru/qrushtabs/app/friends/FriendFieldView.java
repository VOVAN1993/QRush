package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.profile.OtherProfileActivity;
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

public class FriendFieldView extends LinearLayout {

	 
	private View rowView; 
	FriendField rf;
	private Context context;
	private OnClickListener l = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			Intent intent = new Intent(FriendFieldView.this.context,OtherProfileActivity.class);
			intent.putExtra("username", rf.username);
			intent.putExtra("city", rf.city);
			FriendFieldView.this.context.startActivity(intent);
			
		}
		
	};
 	public FriendFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.friend_field, this, false);
	   
	   //newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
	   this.addView(rowView);
	   this.setOnClickListener(l);
//	   rescanButton = (Button)rowView.findViewById(R.id.rescan_btn);
//	   arrowsImg = (ImageView)rowView.findViewById(R.id.rescan_arrows_icon);
	   //imgPlace = (ImageView)rowView.findViewById(R.id.news_content_iv);
	}
	public FriendFieldView(Context context) {
		this(context, null);
	}
	
	public void setFriendInfo(FriendField rf) 
	{
		    TextView textView = (TextView) rowView.findViewById(R.id.friend_username_tv);
	        ImageView imageView = (ImageView) rowView.findViewById(R.id.friend_icon);
	        
	        // Изменение иконки для Windows и iPhone
	        
	        this.rf = rf;
	        textView.setText(rf.username);
	        UserPhotosMap.setToImageView(rf.username, imageView);
	        
	        textView = (TextView) rowView.findViewById(R.id.friend_city_tv);
	        textView.setText(rf.city);
//	        Button btnD = (Button)rowView.findViewById(R.id.unfollow_friend_btn);
//	        btnD.setOnClickListener(new OnClickListener()
//	        {
//				@Override
//				public void onClick(View arg0) 
//				{
//			        Button btnD = (Button)rowView.findViewById(R.id.unfollow_friend_btn);
//
//			        btnD.setVisibility(View.GONE);
//					TextView reportTV = (TextView)rowView.findViewById(R.id.friend_report_tv);
//					if(ServerAPI.removeFriend(FriendFieldView.this.rf.username).equals("true"))
//					{
//						reportTV.setTextColor(0x00ff00);
//						reportTV.setText("Друг удален");
//					}				
//				}
//	        	
//	        });
  	}
 
 
 
 

}
