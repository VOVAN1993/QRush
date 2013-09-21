package ru.qrushtabs.app.mycamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.BitmapCropper;
import ru.qrushtabs.app.utils.SampleFileUpload;
import ru.qrushtabs.app.utils.ServerAPI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
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

        
        View lframe = (View)findViewById(R.id.left_frame);
        View rframe = (View)findViewById(R.id.right_frame);
        
        WindowManager wm = (WindowManager) this.getSystemService(Activity.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 		int mScreenWidth = display.getWidth();
        int w = lframe.getLayoutParams().width;
        LayoutParams lpars = lframe.getLayoutParams();
        LayoutParams rpars = rframe.getLayoutParams();
        
        lpars.height = mScreenWidth-2*w;
		lframe.setLayoutParams(lpars);
		
 		rpars.height = mScreenWidth-2*w;
		rframe.setLayoutParams(rpars);
        
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
        Button recapture = (Button) findViewById(R.id.avatar_recapture_btn);
        recapture.setVisibility(View.INVISIBLE);        
        recapture.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                    	Button btn = (Button)AvatarCameraActivity.this.findViewById(R.id.avatar_ok_btn);
            			btn.setVisibility(View.INVISIBLE); 
            			
            			Button captBtn = (Button)AvatarCameraActivity.this.findViewById(R.id.button_capture);
            			captBtn.setVisibility(View.VISIBLE);
            			
            			Button rebtn = (Button)AvatarCameraActivity.this.findViewById(R.id.avatar_recapture_btn);
            			rebtn.setVisibility(View.INVISIBLE);
                        mCamera.startPreview();
                    }
                }
            );
    
        
        Button okButton = (Button) findViewById(R.id.avatar_ok_btn);
        okButton.setVisibility(View.INVISIBLE);
        okButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
 
                	Intent i = new Intent();
                	i.putExtra("photo", bmpBytes);
                 	mCamera.stopPreview();
                	setResult(1,i);
                	releaseCamera();   
                    finish();
                	
                }
                
            }
        );
        
        Button backButton = (Button) findViewById(R.id.avatar_back_btn);
         backButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
 
                	Intent i = new Intent();
                  	mCamera.stopPreview();
                	setResult(0,i);
                	releaseCamera();   
                    finish();
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
    
    private PictureCallback mPicture = new PictureCallback() {

       

		@Override
        public void onPictureTaken(byte[] data, Camera camera) {

			bmpBytes = data;
			Button btn = (Button)AvatarCameraActivity.this.findViewById(R.id.avatar_ok_btn);
			btn.setVisibility(View.VISIBLE);
			Button captBtn = (Button)AvatarCameraActivity.this.findViewById(R.id.button_capture);
			captBtn.setVisibility(View.INVISIBLE);
			
			Button recapture = (Button) findViewById(R.id.avatar_recapture_btn);
	        recapture.setVisibility(View.VISIBLE);
          }
    };
    
   
  
  
  @Override
  protected void onPause() {
	  Log.d("camera", "pause");
      super.onPause();
      releaseCamera();              // release the camera immediately on pause event
  }

   

  private void releaseCamera(){
	  Log.d("camera","release");
      if (mCamera != null){
    	  mCamera.stopPreview();
    	  
          mCamera.release();        // release the camera for other applications
          mCamera = null;
      }
  }
}