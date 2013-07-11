package ru.qrushtabs.app;

import java.util.ArrayList;

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

public class QuestsArrayAdapter extends ArrayAdapter<QuestContent> 
{

	private final Context context;
    private final ArrayList<QuestContent> values;
    ImageView arrows;
    private View rowView;
    public QuestsArrayAdapter(Context context, ArrayList<QuestContent> values) {
        super(context, R.layout.rating_field, values);
        this.context = context;
        this.values = values;
     }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	QuestContent qc = values.get(position);
    	QuestContentView rowView;
    	if(qc.getIsActive())
    	     rowView = new ActiveQuestContentView(context);
    	else
    		rowView = new NotActiveQuestContentView(context);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        rowView = inflater.inflate(R.layout.news_place, parent, false);
//        TextView textView = (TextView) rowView.findViewById(R.id.news_tv);
//       // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        	rowView.setQuestContent(values.get(position));
//        textView.setText(values.get(position).getContent());
//        // ��������� ������ ��� Windows � iPhone
//        if(values.get(position).getBitmap()!=null)
//        {
//        	Log.d(null, "Added bitmap");
//        	ImageView iv  = (ImageView)rowView.findViewById(R.id.news_content_iv);
//        	iv.setImageBitmap(values.get(position).getBitmap());
//        	
//        }
//        Button rescanButton = (Button)rowView.findViewById(R.id.rescan_btn);
//        arrows = (ImageView)rowView.findViewById(R.id.rescan_arrows_icon);
//        rescanButton.setOnClickListener(new OnClickListener()
//        {
//
//			@Override
//			public void onClick(View arg0) 
//			{
//				Log.d(null, (String) ((TextView)rowView.findViewById(R.id.news_tv)).getText());
//				//Animation anim = AnimationUtils.loadAnimation(rowView.getContext(), R.anim.arrow_rotate_anim);
//				RotateAnimation anim = new RotateAnimation(0, 360, 0,arrows.getWidth()/2, 0, arrows.getHeight()/2);
//				anim.setDuration(500);
// 				arrows.startAnimation(anim);
//				
//			}
//        	
//        });
 

        return rowView;
    }
}