package ru.qrushtabs.app;

 import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.OnInfoLoadListener;
import ru.qrushtabs.app.utils.ServerAPI;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProfileActivity extends Activity
{
	OnInfoLoadListener onInfoFromLoad = new OnInfoLoadListener()
	{
		@Override
		public void onSuccess() {
 			
		}

		@Override
		public void onFail() {
 			
		}
	};
 
	ImageView ivPeakOver;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.profile_tab);
 		ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar);
 		pb.setProgress(50);
 		
 		TextView tv = (TextView)findViewById(R.id.name_tv);
 		tv.setText(ProfileInfo.username);
 		
 		tv = (TextView)findViewById(R.id.city_tv);
 		tv.setText(ProfileInfo.city);
 		ivPeakOver=(ImageView) findViewById(R.id.avatar_iv);

 		final float scale = getBaseContext().getResources().getDisplayMetrics().density;
 		
 		int dpw = 86;
 		int dph = 86;
 		Bitmap bmp = null;
 		
// 		byte[] bmpBytes = ServerAPI.downloadProfilePhoto("Mihan");
//
// 		if(bmpBytes!=null)
//    	     bmp = BitmapFactory.decodeByteArray(bmpBytes, 0, bmpBytes.length);
// 		else
// 		{
 		Log.d("avatar path", ProfileInfo.avatarPath);
 		if(!ProfileInfo.avatarPath.equals("0"))
 		{
 			if(ProfileInfo.avatarBitmap==null)
 				bmp=BitmapFactory.decodeFile(ProfileInfo.avatarPath);
 			else
 				bmp = ProfileInfo.avatarBitmap;
 		}
 //		}
 		
 		if(bmp==null)
 		    bmp=BitmapFactory.decodeResource(getResources(), R.drawable.qrcat);
 		
 		ivPeakOver.setImageBitmap(BitmapCropper.crop(scale, bmp, dpw, dph));
 		
 		ivPeakOver.setOnClickListener(new OnClickListener()
 		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
 			
 		});
  		Button getMoneyButton = (Button)findViewById(R.id.get_money_btn);
 		
 		getMoneyButton.setOnClickListener(new OnClickListener()
 		{

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ProfileActivity.this,GetMoneyActivity.class);
				startActivity(intent);	
			}
 			
 		});
 		
 		Button searchFriendsBtn = (Button)findViewById(R.id.search_friend_btn);
 		
 		searchFriendsBtn.setOnClickListener(new OnClickListener()
 		{

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ProfileActivity.this,FriendsSearchActivity.class);
				startActivity(intent);	
			}
 			
 		});
 		
        Button friendsBtn = (Button)findViewById(R.id.friendsbtn);
 		
        friendsBtn.setOnClickListener(new OnClickListener()
 		{

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ProfileActivity.this,FriendsActivity.class);
				startActivity(intent);	
			}
 			
 		});

 		tv = (TextView)findViewById(R.id.moneyTV);
 		tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));
 		
	}
	@Override
	public void onResume()
	{
		super.onResume();

		TextView tv = (TextView)findViewById(R.id.moneyTV);
 		tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));
 		
 
	}
	
}