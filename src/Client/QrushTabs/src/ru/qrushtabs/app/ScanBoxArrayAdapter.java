package ru.qrushtabs.app;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanBoxArrayAdapter extends ArrayAdapter<ScanObject> {
	private final Context context;
    private final ArrayList<ScanObject> values;

    public ScanBoxArrayAdapter(Context context, ArrayList<ScanObject> values) {
        super(context, R.layout.scan_box_field, values);
        this.context = context;
        this.values = values;
     }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ScanBoxFieldView rowView = new ScanBoxFieldView(context);
    	
    	 
    	rowView.setScanInfo(values.get(position));
       // LayoutInflater inflater = (LayoutInflater) context
       //         .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View rowView = inflater.inflate(R.layout.scan_box_field, parent, false);
       // TextView textView = (TextView) rowView.findViewById(R.id.label);
       // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
      //  textView.setText(values.get(position));
        // Изменение иконки для Windows и iPhone
//        String s = values.get(position);
//        if (s.startsWith("Windows7") || s.startsWith("iPhone")
//                || s.startsWith("Solaris")) {
//            imageView.setImageResource(R.drawable.profil);
//        } else {
//            imageView.setImageResource(R.drawable.profil);
//        }

        return rowView;
    }
}
