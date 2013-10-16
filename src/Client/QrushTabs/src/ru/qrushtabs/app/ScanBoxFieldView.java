package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.quests.QuestObject;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
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

public class ScanBoxFieldView extends LinearLayout {

	 
	private View rowView; 
	ScanObject so;
	private Context context;
	private OnClickListener l = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			if(ServerAPI.isOnline())
			{
				ScanBox.addScan(so,(MyVungleActivity)ScanBoxFieldView.this.context);

				
			}
			else
			{
				BlackAlertDialog newFragment;
				newFragment = new BlackAlertDialog();
				 
				newFragment.setLabelText("В оффлайн режиме нельзя отправлять сканы.");
				newFragment.show(((FragmentActivity) ScanBoxFieldView.this.context).getSupportFragmentManager(),
						"missiles");
				newFragment.setDrawableBackground(ScanBoxFieldView.this.context.getResources().getDrawable(R.drawable.black_alert_error));

				 
			}
			
		}
		
	};
 	public ScanBoxFieldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       rowView = inflater.inflate(R.layout.scan_box_field, this, false);
	   
	   //newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
	   this.addView(rowView);
	   
	   Button scanBtn = (Button)findViewById(R.id.scan_box_rescan_btn);
	   scanBtn.setOnClickListener(l );
	 //  scanBtn.setOnClickListener(l)
 
	}
	public ScanBoxFieldView(Context context) {
		this(context, null);
	}
	
	public void setScanInfo(ScanObject so) 
	{
		this.so = so;
  	}
 
 
 
 

}
