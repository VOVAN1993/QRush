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

public class NewsArrayAdapter extends ArrayAdapter<ScanObject> 
{

	private final Context context;
    private final ArrayList<ScanObject> values;
    ImageView arrows;
    private View rowView;
    public NewsArrayAdapter(Context context, ArrayList<ScanObject> values) {
        super(context, R.layout.rating_field, values);
        this.context = context;
        this.values = values;
     }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	NewsContentView rowView = new NewsContentView(context);

        	rowView.setNewsContent(values.get(position),this);

        return rowView;
    }
}
