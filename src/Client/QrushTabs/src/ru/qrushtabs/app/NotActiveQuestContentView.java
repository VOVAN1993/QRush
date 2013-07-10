package ru.qrushtabs.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotActiveQuestContentView extends QuestContentView {

	protected View rowView; 
	protected Context context;

	public NotActiveQuestContentView(Context context) {
		super(context, null);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   rowView = inflater.inflate(R.layout.not_active_quest, this, false);
	   
	   this.addView(rowView);
	   
	   
	}

	@Override
	public void setQuestContent(QuestContent newContent) {
		// TODO Auto-generated method stub
		
	}
}
