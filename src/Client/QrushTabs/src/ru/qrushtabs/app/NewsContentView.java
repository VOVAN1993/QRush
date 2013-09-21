package ru.qrushtabs.app;

import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
import ru.qrushtabs.app.utils.UserPhotosMap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsContentView extends LinearLayout {

	private View rowView;
	private Button rescanButton;
	private ImageView arrowsImg;
	private ImageView imgPlace;
	private TextView newsTextView;
	private ScanObject newsContent;
	
	private Context context;

	public NewsContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.news_place, this, false);

		// newsTextView = (TextView)rowView.findViewById(R.id.news_tv);
		this.addView(rowView);

		rescanButton = (Button) rowView.findViewById(R.id.rescan_btn);
		arrowsImg = (ImageView) rowView.findViewById(R.id.rescan_arrows_icon);

		arrowsImg.setBackgroundResource(R.drawable.rescan_arrows);

		rescanButton.setVisibility(View.VISIBLE);
		// imgPlace = (ImageView)rowView.findViewById(R.id.news_content_iv);
	}

	public NewsContentView(Context context) {
		this(context, null);
	}

	public void setNewsContent(ScanObject newContent) {
		this.newsContent = newContent;
		TextView textView = (TextView) rowView.findViewById(R.id.news_place_name_tv);
 	        
	        // Изменение иконки для Windows и iPhone
	        
	        this.newsContent = newContent;
	        textView.setText(newContent.username);
		
	        textView = (TextView) rowView.findViewById(R.id.news_place_city_tv);
	        textView.setText(newContent.city);
		if (ProfileInfo.haveScan(newContent.code)) {
			// ImageView div = (ImageView)findViewById(R.id.button_divider);
			// div.setVisibility(View.GONE);

			FrameLayout fl = (FrameLayout) findViewById(R.id.rescan_btn_layout);
			fl.setVisibility(View.GONE);
			// rescanButton.setEnabled(false);

			// arrowsImg.setVisibility(View.GONE);
			// rescanButton.setVisibility(View.GONE);
		} else {
			rescanButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					rescanButton.setVisibility(View.INVISIBLE);
					arrowsImg
							.setBackgroundResource(R.drawable.rescan_arrows_anim);

					// Log.d(null, (String)
					// ((TextView)rowView.findViewById(R.id.news_tv)).getText());
					// Animation anim =
					// AnimationUtils.loadAnimation(rowView.getContext(),
					// R.anim.arrow_rotate_anim);

					AnimationDrawable anim = (AnimationDrawable) arrowsImg
							.getBackground();
					anim.start();
					(new AddScanTask()).execute();

					// RotateAnimation anim = new RotateAnimation(0, 360,
					// 0,(float)arrowsImg.getWidth()/2.0f, 0,
					// (float)arrowsImg.getHeight()/2.0f);
					// anim.setDuration(500);
					// anim.
					// anim.setAnimationListener(new AnimationListener()
					// {
					//
					// @Override
					// public void onAnimationEnd(Animation arg0) {
					// rescanButton.setEnabled(false);
					// arrowsImg.setVisibility(View.GONE);
					// //newsContent.setScannable(false);
					// ProfileInfo.addScan(NewsContentView.this.newsContent.code);
					// Intent intent = new Intent(context,GamesActivity.class);
					// context.startActivity(intent);
					//
					// }
					//
					// @Override
					// public void onAnimationRepeat(Animation arg0) {
					// // TODO Auto-generated method stub
					//
					// }
					//
					// @Override
					// public void onAnimationStart(Animation arg0) {
					// // TODO Auto-generated method stub
					//
					// }
					//
					// });
					// arrowsImg.startAnimation(anim);

				}

			});
		}
		// newsTextView.setText(newsContent.getContent());
		// TextView dateTV = (TextView)findViewById(R.id.news_date_tv);
		// dateTV.setText(newContent.date);
		// TextView wonTV = (TextView)findViewById(R.id.news_won_tv);
		// wonTV.setText(newContent.won);
		UserPhotosMap.setToImageView(newContent.username,
				(ImageView) findViewById(R.id.news_user_icon));
		// }

		

	}
	private class AddScanTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {
			return ServerAPI.tryAddScan(NewsContentView.this.newsContent.code,"rescan");
		}

		protected void onPostExecute(String objResult) {

			
			AnimationDrawable anim = (AnimationDrawable) NewsContentView.this.arrowsImg
					.getBackground();
			anim.stop();
			NewsContentView.this.arrowsImg.setVisibility(View.INVISIBLE);
			if (objResult.equals("true")) {
				
				
				ProfileInfo.addScan(NewsContentView.this.newsContent.code);
				Intent intent = new Intent(NewsContentView.this.context,
						GamesActivity.class);
				NewsContentView.this.context.startActivity(intent);
			} else {
//				Intent intent = new Intent(NewsContentView.this.context,
//						EnterActivity.class);
//				NewsContentView.this.context.startActivity(intent);
			}
		}
	}

}
