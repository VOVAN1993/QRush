package ru.qrushtabs.app.utils;

import ru.qrushtabs.app.R;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

public class QRLoading {

	private static ImageView iv;
	public static void setLoading(Activity a)
	{
		
		a.setContentView(R.layout.qr_loading);
		iv = (ImageView)a.findViewById(R.id.qr_loading_iv);
		
		iv.setBackgroundResource(R.drawable.qr_loading_anim);
		iv.post(new Runnable() {
            public void run() {     
            	AnimationDrawable anim = (AnimationDrawable) iv
    					.getBackground();
	                anim.start();
            }
          });	
		 
 
	}
	
	public static void setLoading(ImageView ive)
	{
		
		 QRLoading.iv = ive;
		 iv.setImageDrawable(null);
		iv.setBackgroundResource(R.drawable.qr_loading_anim);
		iv.post(new Runnable() {
            public void run() {     
            	AnimationDrawable anim = (AnimationDrawable) iv
    					.getBackground();
            	if(anim!=null)
	                anim.start();
            }
          });	
		 
 
	}
	
	public static void stopLoading(ImageView ive)
	{
		

		AnimationDrawable anim = (AnimationDrawable) ive
				.getBackground();
		if(anim!=null)
            anim.stop();	
		 
 
	}
	public static void stopLoading(Activity a) {
		iv = (ImageView)a.findViewById(R.id.qr_loading_iv);
		
		AnimationDrawable anim = (AnimationDrawable) iv
				.getBackground();
            anim.stop();	
	}
}
