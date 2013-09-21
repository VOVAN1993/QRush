package ru.qrushtabs.app;

import ru.qrushtabs.app.quests.QuestContentView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActiveQuestContentView extends QuestContentView  {

	protected View rowView; 
	protected Context context;

	public ActiveQuestContentView(Context context) {
		super(context, null);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   rowView = inflater.inflate(R.layout.active_quest, this, false);
	   
	   this.addView(rowView);
	}

 
}
