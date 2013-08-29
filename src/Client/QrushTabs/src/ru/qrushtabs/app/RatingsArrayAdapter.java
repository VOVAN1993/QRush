package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RatingsArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
    private final String[] values;

    public RatingsArrayAdapter(Context context, String[] values) {
        super(context, R.layout. rating_field, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rating_field, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        
        // Изменение иконки для Windows и iPhone
        
        String s = values[position];
        JSONObject jsonObj = null;
        try {
			jsonObj = new JSONObject(s);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        RatingField rf = null;
        try {
			rf = RatingField.parse(jsonObj);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        textView.setText(rf.username);
        imageView.setImageResource(R.drawable.profil);
         

        return rowView;
    }
}
