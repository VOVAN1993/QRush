package ru.qrushtabs.app.games;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class RouletteRenderer extends GameRenderer implements Runnable {
	private Thread renderThread = null;
	private SurfaceHolder holder;
	public boolean running = false;
	private Bitmap roulette;
	private Bitmap roulArrow;


	private static RouletteRenderer instance;
 	private int rouletteX;
	private int rouletteY;
	
	private int roulArrowX;
	private int roulArrowY;
	private float roulAngPreVel = 0.2f;
	private float vels[] = {0.2f,0.4f,0.6f,0.8f,1.0f};
	private float roulAngAcc = 0.01f;
	private float roulAngVel = 0.2f;
	private float midPath = 360.0f;
	private int rx;
	int firstPositionY = 0;
	
	private GestureDetector gestureScanner;
	
	private OnGameEndListener onGameEndListener;
  	private int mTouchSlop;
	int mScreenHeight;
	int mScreenWidth;
	static final int TOUCH_MODE_TAP = 1;
	static final int TOUCH_MODE_DOWN = 2;
	int rouletteWidth = 100;
	int rouletteHeight = 100;
	int vely = 100;
	int pvely = 100;
	int nvely = -100;
	Rect mImagePosition;
	Region mImageRegion;
	boolean canImageMove;
	long currentTime = 0;

	boolean touching = false;
	int spixs[];
 
	Matrix rotateMatrix;
	float currentRotation = 0.0f;
	boolean isTwisting = false;
	boolean isStoping = false;
	
	public RouletteRenderer(Context context) {
		super(context);
		
		rotateMatrix = new Matrix();
		holder = getHolder();
		instance = this;
		roulette = BitmapFactory.decodeResource(getResources(),
				R.drawable.roulette);
		roulArrow = BitmapFactory.decodeResource(getResources(),
				R.drawable.roulette_arrow);

	 
 		rouletteWidth = roulette.getWidth();
		rouletteHeight = roulette.getHeight();

		spixs = new int[5];
		WindowManager wm = (WindowManager) context
				.getSystemService(context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mScreenHeight = display.getHeight();
		mScreenWidth = display.getWidth();

		rouletteX = mScreenWidth / 2 - roulette.getWidth() / 2;
		rouletteY = mScreenHeight / 2 - roulette.getHeight();
		
		roulArrowX = rouletteX  + roulette.getWidth()/2 - roulArrow.getWidth()/2;
		roulArrowY = rouletteY + roulette.getHeight()/2 - roulArrow.getHeight()/2;
		
		rotateMatrix.setTranslate(rouletteX, rouletteY);

	}

	public void setOnGameEndListener(OnGameEndListener l) {
		this.onGameEndListener = l;
	}

	public boolean onTouchEvent(MotionEvent event) {
		 
		if(isStoping)
			return true;
		
		int positionX = (int) event.getRawX();
		int positionY = (int) event.getRawY();

 		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
		{
			
			
		 Log.d(VIEW_LOG_TAG, "onDown "+ positionY);
		 firstPositionY = positionY;
		 currentTime = System.currentTimeMillis();
		 touching = true;
		 if(isTwisting&&stopTouched(positionX,positionY))
		 {
			 isStoping = true;
			 int needed = 0;
			 int f = ((int)currentRotation+(int)midPath+18)/36;
			 int m = ((int)currentRotation+(int)midPath+18)%36;
			 
			 if(f%2==needed)
			    roulAngAcc = roulAngPreVel*roulAngPreVel/(2.0f*midPath);
			 else
			 {
				 int rp ;
				 if(m>18)
					 roulAngAcc = roulAngPreVel*roulAngPreVel/(2.0f*(midPath+22));
				 else
					 roulAngAcc = roulAngPreVel*roulAngPreVel/(2.0f*(midPath-22));
					 
			 }
			 Log.d(VIEW_LOG_TAG, "Stop  " + roulAngAcc );

		 }
		}
			break;

		case MotionEvent.ACTION_MOVE: {
			
			if(isTwisting)
				return true;
			if(touching)
			{
				long deltaTime = System.currentTimeMillis() - currentTime;
 				int deltaY = positionY - firstPositionY;
				
 				float veloc = (float)deltaY/(float)deltaTime;
 				Log.d(VIEW_LOG_TAG,"vel "+ veloc);
 				 
 				if(veloc > 0.4)
 				{
 					currentTime = System.currentTimeMillis();
 					isTwisting = true;
 					roulAngPreVel = Math.min(1.0f, veloc - 0.2f);
 					roulAngVel = roulAngPreVel;//roulAngPreVel;
 					Log.d(VIEW_LOG_TAG, "Twist  " );
 					//isStoping = true;
 				}
			}
			 
		}
			break;
		case MotionEvent.ACTION_UP:
			touching = false;
			break;
		}
		return true;
	}

	private boolean stopTouched(int tX,int tY)
	{
		return tX < roulArrowX + roulArrow.getWidth() && tX > roulArrowX && tY > roulArrowY && tY < roulArrowY + roulArrow.getHeight();
 	}
	public void resume() {

 
		running = true;
		renderThread = new Thread(this);
		renderThread.start();

	}

	@Override
	public void run() {

		while (running) {
			if (!holder.getSurface().isValid())
				continue;
			Canvas canvas = holder.lockCanvas();

			canvas.drawRGB(228, 228, 228);

			if(isTwisting)
			{
				long deltaTime = System.currentTimeMillis() - currentTime;
				currentTime = System.currentTimeMillis();
 				rotateMatrix.preTranslate(0, 0);
				rotateMatrix.setRotate(currentRotation,roulette.getWidth()/2,roulette.getHeight()/2);
				currentRotation+=roulAngVel*deltaTime;
				rotateMatrix.postTranslate(rouletteX, rouletteY);
				
				if(isStoping)
				{
 					roulAngVel-=(float)deltaTime*roulAngAcc;
					
					if(roulAngVel<0)
					{
						Log.d(VIEW_LOG_TAG, "color "+((int)(currentRotation+18)/36)%2);
						isStoping = false;
						isTwisting = false;
					}
				}
			}
			
 			canvas.drawBitmap(roulette, rotateMatrix, null);
			canvas.drawBitmap(roulArrow, roulArrowX, roulArrowY, null);
			holder.unlockCanvasAndPost(canvas);
			 

		}
		
		// TODO Auto-generated method stub

	}

	public void pause() {

		running = false;
		// boolean flag = true;
		// while (flag) {
		// try {
		// Log.d(VIEW_LOG_TAG, "before join");
		// renderThread.join();
		// Log.d(VIEW_LOG_TAG, "after join");
		// flag = false;
		// } catch (InterruptedException e) {
		// Log.d(VIEW_LOG_TAG, "thread not paused");
		// }
		// }
		Log.d(VIEW_LOG_TAG, "thread paused");
	}

	public static RouletteRenderer getInstance(Context context) {
		if (instance == null)
			instance = new RouletteRenderer(context);
		return instance;
	}



}
