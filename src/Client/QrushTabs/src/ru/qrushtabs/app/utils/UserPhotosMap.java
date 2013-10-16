package ru.qrushtabs.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeMap;

import ru.qrushtabs.app.MainActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.RatingField;
import ru.qrushtabs.app.profile.ProfileInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class UserPhotosMap {

	
	private static boolean loading = false;
	private static LinkedList<LoadPhotoTask> loadingQueue = new LinkedList<LoadPhotoTask>();
	public static class UserNameComparator implements Comparator<String> {
		public int compare(String c1, String c2) {
			return c1.compareTo(c2);
		}
	}

	public static TreeMap<String, Bitmap> photosMap = new TreeMap<String, Bitmap>(new UserNameComparator());

	public static Bitmap get(String userName) {
		
		if (photosMap.containsKey(userName))
			return photosMap.get(userName);
		else
			return null;
	}
	 private static int MEDIA_TYPE_IMAGE = 1;
	 
	 
	public static Bitmap saveAvatar(Bitmap bmp) {
		if(bmp==null)
		bmp = BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), R.drawable.qrcat);
    	int width = bmp.getWidth();
    	int height = bmp.getHeight();

    	File pictureFile;
    	// create a matrix for the manipulation
    	
    	
  		
 		 
 		
		ProfileInfo.avatarBitmap = BitmapCropper.pxcrop(bmp, ProfileInfo.maxPhotoWidth, ProfileInfo.maxPhotoHeight);
		
		
        pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE );
        if (pictureFile == null){
            Log.d("camera", "Error creating media file, check storage permissions: ");
            return null;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            ProfileInfo.avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            ProfileInfo.avatarFile = pictureFile;
            ProfileInfo.avatarPath = pictureFile.getAbsolutePath();
            Log.d("avatar path", ProfileInfo.avatarPath);
//            fos.write(bmpBytes);
//            fos.close();
          
            return bmp;
                   
            
           
            
        } catch (FileNotFoundException e) {
            Log.d("camera", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("camera", "Error accessing file: " + e.getMessage());
        }
        
        return null;
        
        
	}
	public static void setToImageView(String username,ImageView imageView)
	{
        Bitmap userPhoto = UserPhotosMap.get(username);
        if(userPhoto==null)
        {
	       // byte[] data = ServerAPI.downloadProfilePhoto(username);
        	LoadPhotoTask task = new LoadPhotoTask();
        	task.iv = imageView;
        	task.username = username;
        	QRLoading.setLoading(imageView);
        	if(!loading)
        	{
        		task.execute();
        		loading = true;
        	}
        	else
        	{
        		loadingQueue.add(task);
        	}
	        Log.d("photo loading", username);		  	
        }
        else
        {
        	Log.d("photo loaded before", username);
        	imageView.setImageBitmap(userPhoto);
        }
	}
	static private class LoadPhotoTask extends AsyncTask<Void, Void, byte[]> {

		String username;
		ImageView iv;
		protected byte[] doInBackground(Void... s) {
 			return ServerAPI.downloadProfilePhoto(username);
		}

		protected void onPostExecute(byte[] data) {

			QRLoading.stopLoading(iv);
			if(data!=null)
	        {
	        	Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
	        	UserPhotosMap.add(username, bmp);
	        	//imageView.setImageBitmap(BitmapCropper.pxcrop(bmp, imageView.getWidth(), imageView.getHeight()));
	        	iv.setImageBitmap(UserPhotosMap.get(username));
	        }
	        else
	        {
	        	UserPhotosMap.add(username, BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), R.drawable.qrcat));
	        	//imageView.setImageBitmap(BitmapCropper.pxcrop(UserPhotosMap.get(username),imageView.getWidth(), imageView.getHeight()));
	        	iv.setImageBitmap(UserPhotosMap.get(username));

	        }
			if(loadingQueue.size()>0)
			{
				loadingQueue.poll().execute();
			}
			else
			{
				loading = false;
			}
			
		}
	}
	 private static Uri getOutputMediaFileUri(int type){
	        return Uri.fromFile(getOutputMediaFile(type));
	  }

	  /** Create a File for saving an image or video */
	  private static File getOutputMediaFile(int type){
	      // To be safe, you should check that the SDCard is mounted
	      // using Environment.getExternalStorageState() before doing this.

	      File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	                Environment.DIRECTORY_PICTURES), "MyCameraApp");
	      // This location works best if you want the created images to be shared
	      // between applications and persist after your app has been uninstalled.

	      // Create the storage directory if it does not exist
	      if (! mediaStorageDir.exists()){
	          if (! mediaStorageDir.mkdirs()){
	              Log.d("MyCameraApp", "failed to create directory");
	              return null;
	          }
	      }

	      // Create a media file name
	      String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	      File mediaFile;
	      if (type == MEDIA_TYPE_IMAGE){
	          mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	          "IMG_"+ timeStamp + ".jpg");
	      } else {
	          return null;
	      }

	      return mediaFile;
	  }
	public static void add(String userName, Bitmap photo) {
		if (!photosMap.containsKey(userName))
			photosMap.put(userName, photo);
	}
}
