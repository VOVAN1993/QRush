package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

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

public class RatingFieldView extends LinearLayout {

	 
	private View rowView; 
	RatingField rf;
	private Context context;
	private OnClickListener l = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			Intent intent = new Intent(RatingFieldView.this.context,OtherProfileActivity.class);
			intent.putExtra("username", rf.username);
			intent.putExtra("city", "Москва");
			RatingFieldView.this.context.startActivity(intent);
			
		}
		
	};
 	public RatingFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.rating_field, this, false);
	   
	   this.addView(rowView);
	   this.setOnClickListener(l);
	}
	public RatingFieldView(Context context) {
		this(context, null);
	}
	ImageView imageView;
	public void setRatingInfo(RatingField rfe) 
	{
		if(rfe==null)
		{
			this.setVisibility(View.INVISIBLE);
			return;
		}
			this.rf = rfe;
		    TextView textView = (TextView) rowView.findViewById(R.id.rating_name_tv);
	        imageView = (ImageView) rowView.findViewById(R.id.rating_icon);
	        
	        TextView moneyTV = (TextView) rowView.findViewById(R.id.rating_money_tv);
	        TextView scansTV = (TextView) rowView.findViewById(R.id.rating_scans_tv);
	        
	        textView.setText(rf.username);
	        moneyTV.setText(rf.balance);
	        scansTV.setText(rf.scansCount);
	        
	        textView = (TextView) rowView.findViewById(R.id.rating_city_tv);
	        textView.setText(rf.city);
	        // Изменение иконки для Windows и iPhone
	        
	        
	         
					 UserPhotosMap.setToImageView(rf.username, imageView);
					
			 
	       

  	}
 
 
 
 

}
