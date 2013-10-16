package ru.qrushtabs.app;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyNewsContentView extends LinearLayout {

	private View rowView;
	private ScanObject newsContent;
 
	public MyNewsContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
 		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.my_news_place, this, false);
 		this.addView(rowView);
 
	}

	public MyNewsContentView(Context context) {
		this(context, null);
	}

	public void setNewsContent(ScanObject newContent) {
		this.newsContent = newContent;
		
		TextView tv = (TextView)findViewById(R.id.my_news_info_tv);
		ImageView iv = (ImageView)findViewById(R.id.my_news_icon);
		Log.d("onMyNews", newsContent.scantype);
		if(newsContent.scantype.equals(ScanObject.SCAN))
		{
			tv.setText("Получил за скан");
			iv.setImageDrawable(this.getResources().getDrawable(R.drawable.scan_icon));
		}
		else
		{
		   if(newsContent.scantype.equals(ScanObject.QUEST))
		     {
			   tv.setText("Получил за квест");
			   iv.setImageDrawable(this.getResources().getDrawable(R.drawable.quest_icon));
		   }
		   else
		   {
			   tv.setText("Получил за рескан");
			   iv.setImageDrawable(this.getResources().getDrawable(R.drawable.rescan_img));
		   }

		}
		
		TextView prizeTV = (TextView)findViewById(R.id.my_news_money_tv);
		prizeTV.setText("+"+newsContent.prize);
		
		TextView dateTV = (TextView)findViewById(R.id.my_news_date_tv);
		dateTV.setText(newsContent.date);
		// newsTextView.setText(newsContent.getContent());
		// TextView dateTV = (TextView)findViewById(R.id.news_date_tv);
		// dateTV.setText(newContent.date);
		// TextView wonTV = (TextView)findViewById(R.id.news_won_tv);
		// wonTV.setText(newContent.won);
//		UserPhotosMap.setToImageView(newContent.username,
//				(ImageView) findViewById(R.id.news_user_icon));
		// }

		

	}
	
}
