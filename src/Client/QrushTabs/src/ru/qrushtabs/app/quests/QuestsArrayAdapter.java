package ru.qrushtabs.app.quests;

import java.util.ArrayList;

import ru.qrushtabs.app.ActiveQuestContentView;
import ru.qrushtabs.app.NotActiveQuestContentView;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestsArrayAdapter extends ArrayAdapter<QuestObject> 
{

	private final Context context;
    private final ArrayList<QuestObject> values;
    ImageView arrows;
    private View rowView;
    public QuestsArrayAdapter(Context context, ArrayList<QuestObject> values) {
        super(context, R.layout.rating_field, values);
        this.context = context;
        this.values = values;
     }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	QuestObject qc = values.get(position);
    	QuestContentView rowView;
    	rowView = new QuestContentView(context);

        	rowView.setQuestContent(values.get(position));

 

        return rowView;
    }
}
