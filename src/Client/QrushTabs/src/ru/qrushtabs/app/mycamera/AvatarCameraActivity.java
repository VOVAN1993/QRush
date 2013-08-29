package ru.qrushtabs.app.mycamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.qrushtabs.app.ProfileInfo;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.SampleFileUpload;
import ru.qrushtabs.app.utils.ServerAPI;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class AvatarCameraActivity extends Activity {

    private static final int MEDIA_TYPE_IMAGE = 1;
	private Camera mCamera;
    private AvatarCameraPreview mPreview;
    private byte[] bmpBytes;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycamera);

        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        );
        Button okButton = (Button) findViewById(R.id.button_ok);
        okButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
 
                	Bitmap bmp = BitmapFactory.decodeByteArray(bmpBytes, 0, bmpBytes.length);
                	int width = bmp.getWidth();
                	int height = bmp.getHeight();

                	// create a matrix for the manipulation
                	Matrix matrix = new Matrix();
                	// rotate the Bitmap 90 degrees (counterclockwise)
                	matrix.postRotate(90);

                	// recreate the new Bitmap, swap width and height and apply transform
                	Bitmap rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0,
                	                  width, height, matrix, true);
                	
                	final float scale = getBaseContext().getResources().getDisplayMetrics().density;
             		
             		 
             		
        			ProfileInfo.avatarBitmap = BitmapCropper.pxcrop(scale, rotatedBitmap, ProfileInfo.maxPhotoWidth, ProfileInfo.maxPhotoHeight);
        			
        			
                    pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE );
                    if (pictureFile == null){
                        Log.d("camera", "Error creating media file, check storage permissions: ");
                        return;
                    }

                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        ProfileInfo.avatarBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        ProfileInfo.avatarFile = pictureFile;
                        ProfileInfo.avatarPath = pictureFile.getAbsolutePath();
                        Log.d("avatar path", ProfileInfo.avatarPath);
//                        fos.write(bmpBytes);
//                        fos.close();
                      

                               
                        
                        setResult(1);
                        finish();
                        
                    } catch (FileNotFoundException e) {
                        Log.d("camera", "File not found: " + e.getMessage());
                    } catch (IOException e) {
                        Log.d("camera", "Error accessing file: " + e.getMessage());
                    }
                }
                
            }
        );
        // Create an instance of Camera
        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);
        // Create our Preview view and set it as the content of our activity.
        mPreview = new AvatarCameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }
    
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
    File pictureFile;
    private PictureCallback mPicture = new PictureCallback() {

        private int MEDIA_TYPE_IMAGE = 1;

		@Override
        public void onPictureTaken(byte[] data, Camera camera) {

			bmpBytes = data;
          }
    };
    
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
  
  
  @Override
  protected void onPause() {
      super.onPause();
      releaseCamera();              // release the camera immediately on pause event
  }

   

  private void releaseCamera(){
	  Log.d("camera","release");
      if (mCamera != null){
          mCamera.release();        // release the camera for other applications
          mCamera = null;
      }
  }
}