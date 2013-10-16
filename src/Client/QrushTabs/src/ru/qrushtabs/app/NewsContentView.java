package ru.qrushtabs.app;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
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

public class NewsContentView extends LinearLayout {

	private View rowView;
	private Button rescanButton;
	private ImageView arrowsImg;
	private ImageView imgPlace;
	private TextView newsTextView;
	private ScanObject scanObject;
	
	private Context context;

	public NewsContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.news_place, this, false);

		// newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
		this.addView(rowView);

		rescanButton = (Button) rowView.findViewById(R.id.rescan_btn);
		arrowsImg = (ImageView) rowView.findViewById(R.id.rescan_arrows_icon);

		arrowsImg.setBackgroundResource(R.drawable.rescan_arrows);

		rescanButton.setVisibility(View.VISIBLE);
		// imgPlace = (ImageView)rowView.findViewById(R.id.news_content_iv);
	}

	public NewsContentView(Context context) {
		this(context, null);
	}

	public void setNewsContent(ScanObject newContent,NewsArrayAdapter nar) {
		this.scanObject = newContent;
		TextView textView = (TextView) rowView.findViewById(R.id.news_place_name_tv);
 	        
	        // Изменение иконки для Windows и iPhone
	        
	        this.scanObject = newContent;
	        textView.setText(newContent.username);
		
	        textView = (TextView) rowView.findViewById(R.id.news_place_city_tv);
	        textView.setText(newContent.city);
		if (ProfileInfo.haveScan(newContent.code)) {
			rowView.setVisibility(View.GONE);
			FrameLayout fl = (FrameLayout) findViewById(R.id.rescan_btn_layout);
			fl.setVisibility(View.GONE);
		 
		} else {
			rescanButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					rescanButton.setVisibility(View.INVISIBLE);
					arrowsImg
							.setBackgroundResource(R.drawable.rescan_arrows_anim);
					AnimationDrawable anim = (AnimationDrawable) arrowsImg
							.getBackground();
					anim.start();
					scanObject.scantype = ScanObject.RESCAN;
					ScanBox.addScan(scanObject, (MyVungleActivity)context);
					//(new AddScanTask()).execute();


				}

			});
		}

		UserPhotosMap.setToImageView(newContent.username,
				(ImageView) findViewById(R.id.news_user_icon));
		 

		

	}
	

}
