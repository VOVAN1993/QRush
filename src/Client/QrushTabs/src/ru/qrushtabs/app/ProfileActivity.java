package ru.qrushtabs.app;

 import ru.qrushtabs.app.utils.OnInfoLoadListener;
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
 		
 		ivPeakOver=(ImageView) findViewById(R.id.avatar);

 		final float scale = getBaseContext().getResources().getDisplayMetrics().density;
 		
 		int dpw = 86;
 		int dph = 86;
 		
 		int pixelsw = (int) (dpw * scale + 0.5f);
 		int pixelsh = (int) (dph * scale + 0.5f);

 		
 		Bitmap bmp=BitmapFactory.decodeResource(getResources(), R.drawable.profil);
 		int minsize = Math.min(bmp.getWidth(), bmp.getHeight());
 		float scaling = (float)minsize/(float)pixelsw;
 		float neededw =  (float)bmp.getWidth() / scaling;
 		float neededh =  (float)bmp.getHeight() / scaling;
 		Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, (int)neededw, (int)neededh,true);
 		
 		 
 		Bitmap croppedBitmap = Bitmap.createBitmap(bmp2, 0, 0, Math.min(pixelsw,(int)neededw), Math.min(pixelsh,(int)neededh));
 		//Bitmap resizedbitmap=Bitmap.createBitmap(bmp,0,0, pixelsh, pixelsw);
 		ivPeakOver.setImageBitmap(croppedBitmap);
 		
  		Button getMoneyButton = (Button)findViewById(R.id.get_money_btn);
 		
 		getMoneyButton.setOnClickListener(new OnClickListener()
 		{

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ProfileActivity.this,GetMoneyActivity.class);
				startActivity(intent);	
			}
 			
 		});
 		TextView tv = (TextView)findViewById(R.id.scansTV);
 		tv.setText(String.valueOf(ProfileInfo.getScansCount()));
 		
 		tv = (TextView)findViewById(R.id.rescansTV);
 		tv.setText(String.valueOf(ProfileInfo.getRescansCount()));
 		
 		tv = (TextView)findViewById(R.id.moneyTV);
 		tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));
 		
	}
	@Override
	public void onResume()
	{
		super.onResume();
		TextView tv = (TextView)findViewById(R.id.scansTV);
 		tv.setText(String.valueOf(ProfileInfo.getScansCount()));
 		
 		tv = (TextView)findViewById(R.id.rescansTV);
 		tv.setText(String.valueOf(ProfileInfo.getRescansCount()));
 		
 		tv = (TextView)findViewById(R.id.moneyTV);
 		tv.setText(String.valueOf(ProfileInfo.getMoneyCount()));
 		
 
	}
	
}