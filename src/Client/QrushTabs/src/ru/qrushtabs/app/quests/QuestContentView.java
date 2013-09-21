package ru.qrushtabs.app.quests;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.utils.ServerAPI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class QuestContentView extends LinearLayout {

 	public QuestObject qo;
	private OnClickListener onImageClick = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			
			Log.d("quest", "onQuestClick");
			QuestObject.currentQuestObject = qo;
			Intent intent;
			if(qo.isDaily)
				intent = new Intent(QuestContentView.this.context, DailyQuestActivity.class);
			else
				intent = new Intent(QuestContentView.this.context, QuestActivity.class);
			QuestContentView.this.context.startActivity(intent);
			
		}
		
	};
	public QuestContentView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		this.context = context;
	}

	protected View rowView; 
	protected Context context;
	private OnClickListener onIconsClick = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			
			 
			if(progressLL.getVisibility()==View.GONE)
			{
				progressLL.setVisibility(View.VISIBLE);
			}
			else
			{
				progressLL.setVisibility(View.GONE);
			}
		}
		
	};
 
 
	private LinearLayout progressLL;
	public void setQuestContent(QuestObject newContent)
	{
		 
		Log.d("quest", "setQuestContent "+newContent.name);
		if(qo!=null && qo.name.equals(newContent.name))
			return;
			
		
		this.qo = newContent;
//		if(qo.state==QuestObject.COMPLETED)
//		{
//			this.setVisibility(View.GONE);
//			return;
//		}
//		else
//			this.setVisibility(View.VISIBLE);
		qo.setQuestContentView(this);
 
		TextView questTV = (TextView)findViewById(R.id.quest_preview_name_tv);
		questTV.setText(qo.name);
		
		TextView prizeTV = (TextView)findViewById(R.id.quest_preview_prize_tv);
		prizeTV.setText("+"+qo.prize);
		LinearLayout ll = (LinearLayout)findViewById(R.id.quest_progress_ll);
		ll.setVisibility(View.GONE);
		ImageView icon = (ImageView)QuestContentView.this.findViewById(R.id.quest_preview_icon_iv);
		if(qo.state==QuestObject.ACTIVE)
		{
			icon.setImageDrawable(QuestContentView.this.getResources().getDrawable(R.drawable.quest_active_icon));
			ll.setVisibility(View.VISIBLE);
			
			ArrayList<ProgressItem> pis = newContent.tasksProgress.getProgress();
			int scansCount = 0;
			int rescansCount = 0;
			int usedScansCount = 0;
			int usedRescansCount = 0;
			for(int i = 0; i < pis.size();i++)
			{
				ProgressItem pi = pis.get(i);
				if(pi.name.equals("Ресканы"))
				{
					rescansCount+=pi.full;
					usedRescansCount+=pi.part;
				}
				else
				{
					scansCount+=pi.full;
					usedScansCount+=pi.part;
				}
			}
			TextView tv = (TextView)findViewById(R.id.rescans_progress_tv);
			if(rescansCount>0)
			{
				tv.setText(usedRescansCount+"/"+rescansCount);
			}
			else
			{
				tv.setVisibility(View.GONE);
				ImageView rescansIV = (ImageView)findViewById(R.id.rescan_icon_iv);
				rescansIV.setVisibility(View.GONE);
			}
			
			tv = (TextView)findViewById(R.id.scans_progress_tv);
			if(scansCount>0)
			{
				tv.setText(usedScansCount+"/"+scansCount);
			}
			else
			{
				tv.setVisibility(View.GONE);
				ImageView scansIV = (ImageView)findViewById(R.id.scan_icon_iv);
				scansIV.setVisibility(View.GONE);
				
			}
			///
			progressLL = (LinearLayout)findViewById(R.id.quest_progress_lv);
			for(int i = 0;i<pis.size();i++)
			{
				TextView pitv = new TextView(this.getContext());
				pitv.setText(pis.get(i).name+": "+pis.get(i).part+"/"+pis.get(i).full);
				progressLL.addView(pitv);
			}
			progressLL.setVisibility(View.GONE);
			
			RelativeLayout rl = (RelativeLayout)findViewById(R.id.quests_progress_icons_rl);
			rl.setOnClickListener(onIconsClick);
			
		}
		if(qo.state==QuestObject.MODERATING)
		{
			icon.setImageDrawable(QuestContentView.this.getResources().getDrawable(R.drawable.quest_moderate_icon));
			icon.setBackgroundColor(QuestContentView.this.getResources().getColor(R.color.extragray));
		}
		if(qo.state==QuestObject.NOT_ACTIVE)
		{
			icon.setImageDrawable(QuestContentView.this.getResources().getDrawable(R.drawable.quest_get_icon));
		}
		if(qo.isDaily)
		{
			icon.setImageDrawable(QuestContentView.this.getResources().getDrawable(R.drawable.quest_daily_icon));
		}
		
		ImageView questIV = (ImageView)findViewById(R.id.quest_preview_iv);
		questIV.setOnClickListener(onImageClick);
	}
	public void refreshProgress()
	{
		
//		if(qo.state==QuestObject.COMPLETED)
//		{
//			this.setVisibility(View.GONE);
//			return;
//		}
//		else
//			this.setVisibility(View.VISIBLE);
		qo.setQuestContentView(this);
		ArrayList<ProgressItem> pis = qo.tasksProgress.getProgress();
		int scansCount = 0;
		int rescansCount = 0;
		int usedScansCount = 0;
		int usedRescansCount = 0;
		for(int i = 0; i < pis.size();i++)
		{
			ProgressItem pi = pis.get(i);
			if(pi.name.equals("Ресканы"))
			{
				rescansCount+=pi.full;
				usedRescansCount+=pi.part;
			}
			else
			{
				scansCount+=pi.full;
				usedScansCount+=pi.part;
			}
		}
		TextView tv = (TextView)findViewById(R.id.rescans_progress_tv);
		if(rescansCount>0)
		{
			tv.setText(usedRescansCount+"/"+rescansCount);
		}
		else
		{
			tv.setVisibility(View.GONE);
			ImageView rescansIV = (ImageView)findViewById(R.id.rescan_icon_iv);
			rescansIV.setVisibility(View.GONE);
		}
		
		tv = (TextView)findViewById(R.id.scans_progress_tv);
		if(scansCount>0)
		{
			tv.setText(usedScansCount+"/"+scansCount);
		}
		else
		{
			tv.setVisibility(View.GONE);
			ImageView scansIV = (ImageView)findViewById(R.id.scan_icon_iv);
			scansIV.setVisibility(View.GONE);
			
		}
		///
		progressLL = (LinearLayout)findViewById(R.id.quest_progress_lv);
		for(int i = 0;i<pis.size();i++)
		{
			TextView pitv = new TextView(this.getContext());
			pitv.setText(pis.get(i).name+": "+pis.get(i).part+"/"+pis.get(i).full);
			progressLL.addView(pitv);
		}
		progressLL.setVisibility(View.GONE);
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.quests_progress_icons_rl);
		rl.setOnClickListener(onIconsClick);
		
		ImageView questIV = (ImageView)findViewById(R.id.quest_preview_iv);
		questIV.setOnClickListener(onImageClick);
	}
	public QuestContentView(Context context) {
		super(context, null);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   rowView = inflater.inflate(R.layout.quest_preview, this, false);
	   
	   this.addView(rowView);
	   
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Log.d("quest", "onDraw ");
	}
	
}
