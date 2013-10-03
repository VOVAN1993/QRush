package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.drawable;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ActivitiesStack;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FriendsActivity extends MyVungleActivity 
{
	
//	private OnClickListener onSearchClick = new OnClickListener()
//	{
//
//		@Override
//		public void onClick(View arg0) {
//			TextView tv = (TextView)findViewById(R.id.name_for_friend_tv);
//			String users[] = ServerAPI.searchFriend(tv.getText().toString());
//			
//			
//			UsersArrayAdapter adapter1 = new UsersArrayAdapter(FriendsActivity.this,
//					users);
//			ListView lv = (ListView) findViewById(R.id.friends_list);
//			lv.setAdapter(adapter1);
//			
//		}
//		
//	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.friends);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		
        
		 

		Button backButton = (Button)findViewById(R.id.title_left_button);
		backButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.backbtn));
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				  finish();
			}
		});

		 
		
		Button searchButton = (Button)findViewById(R.id.title_right_button);
		searchButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.title_addfriendbtn));
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendsActivity.this,FriendsSearchActivity.class);
				startActivity(intent);
 			}
		});
	}
	@Override
	public void onResume()
	{
		super.onResume();
		if(!ProfileInfo.friendsChanged)
			return;
		String req_users[] = ServerAPI.getReqFriends();	
		String users[] = ServerAPI.getMyFriends(ProfileInfo.username,"",0,0);
		if(users.length==0 && req_users.length==0)
		{
			SadSmile.setSadSmile(this,"К сожалению, у вас нет друзей.");
			return;
		}
		setContentView(R.layout.friends);
	    
		
        FriendField friends[]  = new FriendField[users.length];
        ProfileInfo.friendsList = friends;
        for(int i = 0;i<users.length;i++)
        {
        	try {
				friends[i] = FriendField.parse(new JSONObject(users[i]));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        ListView lv = (ListView) findViewById(R.id.friends_list);
        if(users.length==0)
        {
        	TextView frTV = (TextView)findViewById(R.id.friends_tv);
        	frTV.setVisibility(View.GONE);
        	lv.setVisibility(View.GONE);
        }
        else
        {
	        FriendsArrayAdapter adapter1 = new FriendsArrayAdapter(FriendsActivity.this,
					friends);
			lv.setAdapter(adapter1);
        }
		
        
        
        FriendField req_friends[]  = new FriendField[req_users.length];
        for(int i = 0;i<req_users.length;i++)
        {
        	try {
        		req_friends[i] = FriendField.parse(new JSONObject(req_users[i]));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		ListView lv1 = (ListView) findViewById(R.id.friends_requests_list);

        if(req_users.length==0)
        {
        	TextView reqTV = (TextView)findViewById(R.id.req_tv);
        	reqTV.setVisibility(View.GONE);
        	lv1.setVisibility(View.GONE);
        }
        else
        {
	        FriendsRequestsArrayAdapter adapter2 = new FriendsRequestsArrayAdapter(FriendsActivity.this,
	        		req_friends);
			lv1.setAdapter(adapter2);
        }
        if(req_users.length==0 && users.length==0)
        {
        	SadSmile.setSadSmile(this, "К сожалению, на данный момент, у вас нет друзей.");
        }
		
	}
	private class DownloadProfilePhotoTask extends AsyncTask<String,String,byte[]> {

		protected byte[] doInBackground(String... args) {
			return ServerAPI.downloadProfilePhoto(args[0]);
		}
		protected void onPostExecute(byte[] result ) {

//			if(objResult.equals("true"))
//			{
//				Intent intent = new Intent(PreloadActivity.this, MainActivity.class);
//				finish();
//				startActivity(intent);
//			}
//			else
//			{
//				Intent intent = new Intent(PreloadActivity.this, EnterActivity.class);
//				finish();
//				startActivity(intent);
//			}
		}

	}

}
