package ru.qrushtabs.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsContentView extends LinearLayout {

	 
	private View rowView; 
	private Button rescanButton;
	private ImageView arrowsImg;
	private ImageView imgPlace;
	private TextView newsTextView;
	private NewsContent newsContent;
	private Context context;
 	public NewsContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   rowView = inflater.inflate(R.layout.news_place, this, false);
	   
	   newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
	   this.addView(rowView);
	   
	   rescanButton = (Button)rowView.findViewById(R.id.rescan_btn);
	   arrowsImg = (ImageView)rowView.findViewById(R.id.rescan_arrows_icon);
	   imgPlace = (ImageView)rowView.findViewById(R.id.news_content_iv);
	}
	public NewsContentView(Context context) {
		this(context, null);
	}
	
	public void setNewsContent(NewsContent newContent) 
	{
		this.newsContent = newContent;
		if(!newsContent.getScannable())
		{
			rescanButton.setEnabled(false);
			arrowsImg.setVisibility(View.GONE);
		}
		else
		{
	        rescanButton.setOnClickListener(new OnClickListener()
	        {
	
				@Override
				public void onClick(View arg0) 
				{
					Log.d(null, (String) ((TextView)rowView.findViewById(R.id.news_tv)).getText());
					//Animation anim = AnimationUtils.loadAnimation(rowView.getContext(), R.anim.arrow_rotate_anim);
					RotateAnimation anim = new RotateAnimation(0, 360, 0,arrowsImg.getWidth()/2, 0, arrowsImg.getHeight()/2);
					anim.setDuration(500);
					anim.setAnimationListener(new AnimationListener()
					{

						@Override
						public void onAnimationEnd(Animation arg0) {
							rescanButton.setEnabled(false);
							arrowsImg.setVisibility(View.GONE);
							newsContent.setScannable(false);
							
							Intent intent = new Intent(context,GamesActivity.class);
							context.startActivity(intent);
							
						}

						@Override
						public void onAnimationRepeat(Animation arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onAnimationStart(Animation arg0) {
							// TODO Auto-generated method stub
							
						}
						
					});
	 				arrowsImg.startAnimation(anim);
	 				
					
				}
	        	
	        });
		}
		newsTextView.setText(newsContent.getContent());
		
      if(newsContent.getBitmap()!=null)
      {
    	  
    	  //Bitmap bmp = newsContent.getBitmap();
    	  Bitmap bmp =  Bitmap.createBitmap(newsContent.getBitmap(), 0, 0, 100, 100);
          imgPlace.setImageBitmap(bmp);
      	
      }
  	}
 
 
 
 

}
