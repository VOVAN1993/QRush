package ru.qrushtabs.app.friends;

import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.MyVungleActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.R.id;
import ru.qrushtabs.app.R.layout;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FriendsSearchActivity extends MyVungleActivity 
{
	private OnClickListener onSearchClick = new OnClickListener()
	{

		@Override
		public void onClick(View arg0) {
			TextView tv = (TextView)findViewById(R.id.name_for_search_tv);
			String users[] = ServerAPI.searchFriend(tv.getText().toString());
			if(users==null)
			{
				TextView report_tv = (TextView)findViewById(R.id.friends_search_report_tv);
				report_tv.setText("Не корректно введено имя");
				return;

			}
			
			   UserField friends[]  = new UserField[users.length];
		        for(int i = 0;i<users.length;i++)
		        {
		        	try {
						friends[i] = UserField.parse(new JSONObject(users[i]));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			UsersArrayAdapter adapter1 = new UsersArrayAdapter(FriendsSearchActivity.this,
					friends);
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
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title_back);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		
 		Button btn =(Button)findViewById(R.id.friends_search_btn);
 		btn.setOnClickListener(onSearchClick);
 		
 		Button backButton = (Button) this.findViewById(R.id.header_back_btn);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
