package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.utils.ActivitiesStack;
import ru.qrushtabs.app.utils.SadSmile;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OtherFriendsActivity extends Activity 
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
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_back);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		
        
        ActivitiesStack.addActivity(this);
        
        Button b = (Button)findViewById(R.id.header_back_btn);
       
         
        b.setOnClickListener(new OnClickListener() {
  		  @Override
  		  public void onClick(View v) {
  			  ActivitiesStack.finishAll();
  			  
  		  }
  		});
        
//        b = (Button)findViewById(R.id.title_right_button);
//        
//        b.setBackgroundDrawable(getResources().getDrawable(R.drawable.add_friendbtn));

        
        String users[] = ServerAPI.getMyFriends(getIntent().getStringExtra("username"),"",0,0);
		
        FriendField friends[]  = new FriendField[users.length];
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
	        FriendsArrayAdapter adapter1 = new FriendsArrayAdapter(OtherFriendsActivity.this,
					friends);
			lv.setAdapter(adapter1);
        }
		
 
		lv = (ListView) findViewById(R.id.friends_requests_list);

 
    	TextView reqTV = (TextView)findViewById(R.id.req_tv);
    	reqTV.setVisibility(View.GONE);
    	lv.setVisibility(View.GONE);
     
    	 if(users.length==0)
         {
         	SadSmile.setSadSmile(this, "К сожалению, на данный момент, у данного пользователя нет друзей.");
         }
 		
		
		//ActivitiesStack.addActivity(this);

//		Button backButton = (Button)findViewById(R.id.header_back_btn);
//		//backButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.backbtn));
//		backButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				  ActivitiesStack.finishAll();
//			}
//		});

		 
		
//		Button searchButton = (Button)findViewById(R.id.title_right_button);
//		searchButton.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.title_addfriendbtn));
//		searchButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(OtherFriendsActivity.this,FriendsSearchActivity.class);
//				startActivity(intent);
// 			}
//		});
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
