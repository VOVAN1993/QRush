package ru.qrushtabs.app.utils;

import ru.qrushtabs.app.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCropper 
{
	public static Bitmap crop(float scale, Bitmap bmp, int maxWidthDP,int maxHeightDP)
	{
		
		int pixelsw = (int) (maxWidthDP * scale + 0.5f);
 		int pixelsh = (int) (maxHeightDP * scale + 0.5f);
 		
 		int minsize = Math.min(bmp.getWidth(), bmp.getHeight());
 		float scaling = (float)minsize/(float)pixelsw;
 		float neededw =  (float)bmp.getWidth() / scaling;
 		float neededh =  (float)bmp.getHeight() / scaling;
 		Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, (int)neededw, (int)neededh,true);
 		int neededx = (int)neededw/2 - pixelsw/2;
 		int neededy = (int)neededh/2 - pixelsh/2;
 		 
 		Bitmap croppedBitmap = Bitmap.createBitmap(bmp2, neededx, neededy, pixelsw, pixelsh);
   		return croppedBitmap;
	}
	public static Bitmap pxcrop(float scale, Bitmap bmp, int maxWidth,int maxHeight)
	{
		
		int pixelsw = maxWidth;
 		int pixelsh = maxHeight;
 		
 		int minsize = Math.min(bmp.getWidth(), bmp.getHeight());
 		float scaling = (float)minsize/(float)pixelsw;
 		float neededw =  Math.round((float)bmp.getWidth() / scaling);
 		float neededh =  Math.round((float)bmp.getHeight() / scaling);
 		Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, (int)neededw, (int)neededh,true);
 		int neededx = (int)neededw/2 - pixelsw/2;
 		int neededy = (int)neededh/2 - pixelsh/2;
 		 
 		Bitmap croppedBitmap = Bitmap.createBitmap(bmp2, neededx, neededy, pixelsw, pixelsh);
   		return croppedBitmap;
	}
}
