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

public class FriendsSearchActivity extends Activity 
{
	private OnClickListener onSearchClick = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			TextView tv = (TextView)findViewById(R.id.name_for_search_tv);
			String users[] = ServerAPI.searchFriend(tv.getText().toString());
			
			
			UsersArrayAdapter adapter1 = new UsersArrayAdapter(FriendsSearchActivity.this,
					users);
			ListView lv = (ListView) findViewById(R.id.friends_search_list);
			lv.setAdapter(adapter1);
			
		}
		
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.friends_search);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		
 		Button btn =(Button)findViewById(R.id.friends_search_btn);
 		btn.setOnClickListener(onSearchClick);
	}

}
