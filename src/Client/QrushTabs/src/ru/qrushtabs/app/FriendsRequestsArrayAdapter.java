package ru.qrushtabs.app;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.utils.ServerAPI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsRequestsArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
    private final String[] values;

    public FriendsRequestsArrayAdapter(Context context, String[] values) {
        super(context, R.layout.user_field, values);
        this.context = context;
        this.values = values;
    }
    FriendField rf = null;
    TextView textView;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.friend_request_field, parent, false);
        textView = (TextView) rowView.findViewById(R.id.friend_req_username_tv);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.friend_req_icon);
        Button btnA = (Button)rowView.findViewById(R.id.accept_friend_btn);
        Button btnD = (Button)rowView.findViewById(R.id.decline_friend_btn);
        // Изменение иконки для Windows и iPhone
       
        String s = values[position];
        JSONObject jsonObj = null;
        try {
			jsonObj = new JSONObject(s);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			rf = FriendField.parse(jsonObj);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        textView.setText(rf.username);
        imageView.setImageResource(R.drawable.profil);
        btnA.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) 
			{
				if(ServerAPI.addFriend(rf.username).equals("true"))
				{
					textView.setText("Добавлен");
				}
				else
				{
					
				}				
			}
        	
        });
        btnD.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View arg0) {
				if(ServerAPI.friendshipRequestRefuse(rf.username).equals("true"))
				{
					textView.setText("Отклонен");
				}
				else
				{
					
				}
				
			}
        	
        });

        return rowView;
    }
}
