package ru.qrushtabs.app;

import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FriendsActivity extends Activity 
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
 		
        String users[] = ServerAPI.getMyFriends("",0,0);
		
		
        FriendsArrayAdapter adapter1 = new FriendsArrayAdapter(FriendsActivity.this,
				users);
		ListView lv = (ListView) findViewById(R.id.friends_list);
		lv.setAdapter(adapter1);
		
        String req_users[] = ServerAPI.getReqFriends();
		
		
        FriendsRequestsArrayAdapter adapter2 = new FriendsRequestsArrayAdapter(FriendsActivity.this,
        		req_users);
		lv = (ListView) findViewById(R.id.friends_requests_list);
		lv.setAdapter(adapter2);
	}

}
