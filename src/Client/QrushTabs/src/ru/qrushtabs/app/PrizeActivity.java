package ru.qrushtabs.app;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;
import com.vungle.sdk.VunglePub;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PrizeActivity extends MyVungleActivity implements BannerAdListener/*InterstitialAdListener*/ {
	
	//private MoPubView moPubView;
	//MoPubInterstitial mInterstitial;
	public static int currentPrize = 0;//лучше передачу в активити сделать
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		if (VunglePub.isVideoAvailable(true))
//			//VunglePub.displayAdvert();
//		else {
//			Log.d("Vingle", "not available");
//		}
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.prize);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.simple_title);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        Button backButton = (Button)this.findViewById(R.id.continue_btn);
		backButton.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {
		    finish();
		  }
		});
		
//		 mInterstitial = new MoPubInterstitial(this, "015d5988dc664389aafde24e16185f09");
//		    mInterstitial.setInterstitialAdListener(this);
//		    mInterstitial.load();
//		 	moPubView = (MoPubView) findViewById(R.id.adview_prize);
//		    moPubView.setAdUnitId("028e5e162f714c5d9c9224e2ccb3ebea");
//		    moPubView.loadAd();
//		    
//		    moPubView.setBannerAdListener(this);
		TextView tv = (TextView)findViewById(R.id.prizeCountTV);
		
		tv.setText(""+currentPrize);
	}
	@Override
	public void onDestroy()
	{
		 //moPubView.destroy();
		//mInterstitial.destroy();
	    super.onDestroy();
	}
//	@Override
//	public void onInterstitialClicked(MoPubInterstitial arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onInterstitialDismissed(MoPubInterstitial arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onInterstitialFailed(MoPubInterstitial arg0, MoPubErrorCode arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onInterstitialLoaded(MoPubInterstitial arg0) {
//		 if (arg0.isReady()) {
//			 arg0.show();
//		    } else {
//		        // Other code
//		    }
//		
//	}
//	@Override
//	public void onInterstitialShown(MoPubInterstitial arg0) {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	public void onBannerClicked(MoPubView arg0) {
		Log.d("MoPub", "click banner");
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBannerCollapsed(MoPubView arg0) {
		Log.d("MoPub", "collapsed banner");
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBannerExpanded(MoPubView arg0) {
		Log.d("MoPub", "expanded banner");
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBannerFailed(MoPubView arg0, MoPubErrorCode arg1) {
		Log.d("MoPub", "error banner: "+arg1.name());
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBannerLoaded(MoPubView arg0) {
	//	moPubView.bringToFront();
		// TODO Auto-generated method stub
		
	}
}
