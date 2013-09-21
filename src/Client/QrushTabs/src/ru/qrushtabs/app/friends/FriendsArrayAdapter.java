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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsArrayAdapter extends ArrayAdapter<FriendField> {
	private final Context context;
    private final FriendField[] values;

    public FriendsArrayAdapter(Context context, FriendField[] values) {
        super(context, R.layout.user_field, values);
        this.context = context;
        this.values = values;
    }

    FriendFieldView rowView;
    FriendField rf;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	rowView = new FriendFieldView(context);
    	
    	rowView.setFriendInfo(values[position]);
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        rowView = inflater.inflate(R.layout.friend_field, parent, false);
//        TextView textView = (TextView) rowView.findViewById(R.id.friend_username_tv);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.friend_icon);
//        
//        // Изменение иконки для Windows и iPhone
//        
//        String s = values[position];
//        JSONObject jsonObj = null;
//        try {
//			jsonObj = new JSONObject(s);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        rf = null;
//        try {
//			rf = FriendField.parse(jsonObj);
//		} catch (NumberFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        textView.setText(rf.username);
//        UserPhotosMap.setToImageView(rf.username, imageView);
////        Bitmap userPhoto = UserPhotosMap.get(rf.username);
////        if(userPhoto==null)
////        {
////	        byte[] data = ServerAPI.downloadProfilePhoto(rf.username);
////	        Log.d("photo loading", rf.username);
////	        if(data!=null)
////	        {
////	        	Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
////	        	UserPhotosMap.add(rf.username, bmp);
////	        	imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
////	        }
////	        else
////	        {
////	        	UserPhotosMap.add(rf.username, BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), R.drawable.qrcat));
////	        	imageView.setImageBitmap(UserPhotosMap.get(rf.username));
////	        }
////	        	
////        }
////        else
////        {
////        	Log.d("photo loaded before", rf.username);
////        	imageView.setImageBitmap(userPhoto);
////        }
//         
//        Button btnD = (Button)rowView.findViewById(R.id.unfollow_friend_btn);
//        btnD.setOnClickListener(new OnClickListener()
//        {
//			@Override
//			public void onClick(View arg0) 
//			{
//		        Button btnD = (Button)rowView.findViewById(R.id.unfollow_friend_btn);
//
//		        btnD.setVisibility(View.GONE);
//				TextView reportTV = (TextView)rowView.findViewById(R.id.friend_report_tv);
//				if(ServerAPI.removeFriend(rf.username).equals("true"))
//				{
//					reportTV.setTextColor(0x00ff00);
//					reportTV.setText("Друг удален");
//				}				
//			}
//        	
//        });
        return rowView;
    }
}
