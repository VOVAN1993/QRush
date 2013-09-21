package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UsersArrayAdapter extends ArrayAdapter<UserField> {
	private final Context context;
    private final UserField[] values;

    public UsersArrayAdapter(Context context, UserField[] values) {
        super(context, R.layout.user_field, values);
        this.context = context;
        this.values = values;
    }
    
    UserField rf = null;
    TextView textView;
    UserFieldView rowView;
    Button btn;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {    	
    	rowView = new UserFieldView(context);   	
    	rowView.setUserInfo(values[position]);
        return rowView;
    }
}
