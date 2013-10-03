package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RatingsArrayAdapter extends ArrayAdapter<RatingField> {
	private final Context context;
    private final RatingField[] values;

    public RatingsArrayAdapter(Context context, RatingField[] values) {
        super(context, R.layout. rating_field, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	Log.d("ratings", position+"");
    	RatingFieldView rowView = new RatingFieldView(context);
    	
    	
    	rowView.setRatingInfo(values[position]);


        return rowView;
    }
}
