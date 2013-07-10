package ru.qrushtabs.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

abstract public class QuestContentView extends LinearLayout {

	public QuestContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	protected View rowView; 
	protected Context context;
 
	abstract public void setQuestContent(QuestContent newContent);
}
