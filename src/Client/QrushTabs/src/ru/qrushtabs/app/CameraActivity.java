package ru.qrushtabs.app;

import ru.qrushtabs.app.CameraPreview;
import ru.qrushtabs.app.dialogs.LoseDialog;
import ru.qrushtabs.app.dialogs.MyDialog;
import ru.qrushtabs.app.dialogs.OnDialogClickListener;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import ru.qrushtabs.app.games.RouletteRenderer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;

/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

public class CameraActivity extends FragmentActivity {
	private Camera mCamera;
	private CameraPreview mPreview;
	private Handler autoFocusHandler;

	ImageScanner scanner;
	ImageView aimImage;
	LinearLayout mainLayout;
	private boolean camIsNotBusy = true;
	private boolean previewing = true;
	private final String TAG = "Qrush";
	private FrameLayout preview;
	final private int DIALOG_EXIT = 1;
	static {
		System.loadLibrary("iconv");
	}

	// Mimic continuous auto-focusing
	AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			autoFocusHandler.postDelayed(doAutoFocus, 1000);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_tab);

		/* Instance barcode scanner */
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);

		autoFocusHandler = new Handler();

		preview = (FrameLayout) findViewById(R.id.cameraPreview);
		aimImage = (ImageView)findViewById(R.id.aim);
		startScan();

	}

	public void onPause() {
		Log.d(TAG, "MainActivity: onPause()");
		super.onPause();
		stopScan();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "MainActivity: onStart()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();

		startScan();
		Log.d(TAG, "MainActivity: onRestart()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "MainActivity: onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "MainActivity: onDestroy()");
	}

	public void onResume() {
		super.onResume();
		startScan();
		Log.d(TAG, "MainActivity: onResume()");

	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
		}
		return c;
	}

	public void startScan() {
		if (camIsNotBusy) {
			
			mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
			preview.addView(mPreview);
			aimImage.bringToFront();
			Log.d(TAG, "InitCamera");
			camIsNotBusy = false;
			mCamera = getCameraInstance();
			mCamera.setPreviewCallback(previewCb);
			mPreview.setCamera(mCamera);
			mCamera.startPreview();
			previewing = true;

		}
	}

	public void stopScan() {
		if (mCamera != null) {

			previewing = false;
			camIsNotBusy = true;
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			preview.removeView(mPreview);
			mPreview = null;
			mCamera.release();
			mCamera = null;
		}

	}

	private Runnable doAutoFocus = new Runnable() {
		public void run() {
			if (previewing)
				mCamera.autoFocus(autoFocusCB);
		}
	};

	PreviewCallback previewCb = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			Camera.Parameters parameters = camera.getParameters();
			Size size = parameters.getPreviewSize();

			Image barcode = new Image(size.width, size.height, "Y800");
			barcode.setData(data);

			int result = scanner.scanImage(barcode);

			if (result != 0) {

				Log.d(TAG, "On Scanned");
				 SymbolSet syms = scanner.getResults();
				 String str = "";
				 for (Symbol sym : syms) 
				 {
					 Log.d(TAG, "barcode "+ sym.getData());
				     str =  sym.getData();
				
				 }
				 int scanStatus = ScanBox.addScan(str);
				  camIsNotBusy = true;
				    stopScan();
				 if(scanStatus==2)
				 {
				  
				    //showDialog(DIALOG_EXIT);
				    Intent intent = new Intent(CameraActivity.this,GamesActivity.class);
				    startActivity(intent);
				 }
				 else
				 {
					 LoseDialog newFragment;
 					 newFragment = new LoseDialog();
 					 if(scanStatus==1)
 						 newFragment.setLabelText("—охранен в сундук");
 					 else
 					 {
 						newFragment.setLabelText("“акой скан уже был");
 					 }
 					 newFragment.show(getSupportFragmentManager(), "missiles");
 					 newFragment.setOnDialogClickListener(onDialogClick);
				 }
				
			}
		}
	};
	OnDialogClickListener onDialogClick = new OnDialogClickListener()
	{

		@Override
		public void onOkClick() 
		{
			 
			
		}

		@Override
		public void onCancelClick() {
			startScan();
		}
		
	};
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_EXIT) {
			AlertDialog.Builder adb = new AlertDialog.Builder(this);
			// заголовок
			adb.setTitle(R.string.scanned);
			// сообщение
			adb.setMessage(R.string.got_money);
			// иконка
			adb.setIcon(android.R.drawable.ic_dialog_info);
			// кнопка положительного ответа
			adb.setPositiveButton(R.string.contin, myClickListener);
			// кнопка отрицательного ответа
			//adb.setNegativeButton(R.string.no, myClickListener);
			// кнопка нейтрального ответа
			//adb.setNeutralButton(R.string.cancel, myClickListener);
			// создаем диалог
			return adb.create();
		}
		return super.onCreateDialog(id);
	}

	DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			// положительная кнопка
			case Dialog.BUTTON_POSITIVE:
				//saveData();
				startScan();
				//finish();
				break;
			// негаитвная кнопка
			case Dialog.BUTTON_NEGATIVE:
				//finish();
				break;
			// нейтральная кнопка
			case Dialog.BUTTON_NEUTRAL:
				break;
			}
		}

	};

	void saveData() {
		// Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
	}

}
