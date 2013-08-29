package ru.qrushtabs.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class QuestsActivity extends Activity 
{
	ArrayList<QuestContent> questsInfo;
	QuestsArrayAdapter questsInfoAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.quests_tab);
		 

		 
		 
		ListView lv = (ListView) findViewById(R.id.quests_list);
		questsInfo = new ArrayList<QuestContent>();
		for(int i = 0;i < 15;i++)
		{
			QuestContent nc = new QuestContent();
			nc.setContent("bla bla "+i);
			if(i<2)
				nc.setIsActive(true);
			
			questsInfo.add(nc);
		}
		
		questsInfoAdapter = new QuestsArrayAdapter(this,questsInfo);
 		
		lv.setAdapter(questsInfoAdapter);
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			 

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(QuestsActivity.this,QuestActivity.class);
				startActivity(intent);
				
			}
			
		});

	}
}
