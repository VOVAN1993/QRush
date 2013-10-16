package ru.qrushtabs.app.quests;

import java.util.ArrayList;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

import ru.qrushtabs.app.GoogleConsts;
import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.QrushTabsApp;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.ScanBoxFieldView;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.dialogs.BlackAlertDialog;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class QuestsActivity extends MyVungleActivity 
{
	ArrayList<QuestObject> questsInfo;
	QuestsArrayAdapter questsInfoAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.quests_tab);
	}
 	
	@Override
	public void onResume()
	{
	 
			QrushTabsApp.getGaTracker().set(Fields.SCREEN_NAME, "Ratings Screen");
			QrushTabsApp.getGaTracker().send(MapBuilder.createAppView().build());
		 
		super.onResume();
		if(!ServerAPI.isOnline() || ServerAPI.offlineMod)
		{
			SadSmile.setSadSmile(this, "К сожалению, в оффлайн режиме не доступны квесты.");
         	return;
		}
		ArrayList<QuestObject> quests = QuestObject.getAllQuests();
		if(quests.size()==0)
		{
		 
	         	SadSmile.setSadSmile(this, "К сожалению, квесты закончились.");
	         	return;
		}
		for(int i = 0;i<quests.size();i++)
		{
			if(quests.get(i).state==QuestObject.COMPLETED)
			{
				quests.remove(i);
				i--;
			}
			else
			{
				if(quests.get(i).state==QuestObject.ACTIVE && quests.get(i).canComplete())
				{
					if (ServerAPI.changeQuestStatus(quests.get(i).name, quests.get(i).isDaily, "completed").equals("true")) {
						
						quests.get(i).state = QuestObject.COMPLETED;
						ProfileInfo.newsChanged = true;
						ProfileInfo.profileChanged = true;
						ProfileInfo.addMoneyCount(Integer.valueOf(quests.get(i).prize));
						
						QrushTabsApp.getGaTracker().send(
								MapBuilder.createEvent(GoogleConsts.QUEST_ACTION, // Event
										// category
										// (required)
										GoogleConsts.COMPLETE, // Event action (required)
										quests.get(i).name, // Event label
										Long.valueOf(quests.get(i).prize)) // Event value
										.build());
						
						BlackAlertDialog newFragment;
						newFragment = new BlackAlertDialog();
						 
						newFragment.setLabelText("Квест "+quests.get(i).name+" выполнен");
						newFragment.show(this.getSupportFragmentManager(),
								"missiles");
						newFragment.setDrawableBackground(this.getResources().getDrawable(R.drawable.black_alert_ok));
						
						quests.remove(i);
						i--;
 
					}
					
				}
			}
		}
		ListView lv = (ListView) findViewById(R.id.quests_list);
		

 		questsInfoAdapter = new QuestsArrayAdapter(this,quests);
 		
		lv.setAdapter(questsInfoAdapter);
	}
}
