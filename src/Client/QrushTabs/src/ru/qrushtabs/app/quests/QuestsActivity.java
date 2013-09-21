package ru.qrushtabs.app.quests;

import java.util.ArrayList;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;

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
	ArrayList<QuestObject> questsInfo;
	QuestsArrayAdapter questsInfoAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.quests_tab);
		 

		 
//		if(QuestObject.getAllQuests().size()==0)
//		{
//		 
//	         	SadSmile.setSadSmile(this, "К сожалению, квесты закончились.");
//	         	return;
//		}
//		ListView lv = (ListView) findViewById(R.id.quests_list);
//		
//
// 		questsInfoAdapter = new QuestsArrayAdapter(this,QuestObject.getAllQuests());
// 		
//		lv.setAdapter(questsInfoAdapter);


	}
//	
	@Override
	public void onResume()
	{
		super.onResume();
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
		}
		ListView lv = (ListView) findViewById(R.id.quests_list);
		

 		questsInfoAdapter = new QuestsArrayAdapter(this,quests);
 		
		lv.setAdapter(questsInfoAdapter);
	}
}
