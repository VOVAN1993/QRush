package ru.qrushtabs.app.games;

import ru.qrushtabs.app.GamesActivity;
import ru.qrushtabs.app.PrizeActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.dialogs.LoseDialog;
import ru.qrushtabs.app.dialogs.MyDialog;
import ru.qrushtabs.app.dialogs.OnDialogClickListener;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
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
import android.widget.LinearLayout;

public class RouletteRenderer extends GameRenderer implements Runnable {

	private final int OFF_STATE = 0;
	private final int TWISTING_STATE = 1;
	private final int STOPING_STATE = 2;
	private final int END_STATE = 3;
	private final int ORANGE_COLOR = 0;
	private final int GREEN_COLOR = 1;
	
	private boolean choosed = false;
	private int currentState = OFF_STATE;

	private int choosedColor = GREEN_COLOR;
	private int rouletteColor = GREEN_COLOR;
	private Thread renderThread = null;
	private SurfaceHolder holder;
	public boolean running = false;
	private Bitmap roulette;
	private Bitmap roulArrow;

	private Bitmap pushButtonOff;
	private Bitmap pushButtonOn;

	private Bitmap greenButtonPressed;
	private Bitmap greenButtonNormal;

	private Bitmap orangeButtonPressed;
	private Bitmap orangeButtonNormal;

	private Bitmap middleButtonOff;

	private int middleButtonX;
	private int middleButtonY;
	private int middleButtonArrowWidth;
	private int orangeButtonX;
	private int orangeButtonY;
	private int greenButtonX;
	private int greenButtonY;
	private int pushButtonX;
	private int pushButtonY;

	private static RouletteRenderer instance;
	private int rouletteX;
	private int rouletteY;
	private int roulArrowX;
	private int roulArrowY;
	private float roulAngPreVel = 0.3f;
	private float vels[] = { 0.2f, 0.4f, 0.6f, 0.8f, 1.0f };
	private float roulAngAcc = 0.01f;
	private float roulAngVel = 0.2f;
	private float midPath = 360.0f;
	private float cellAngle = 25.7142857f;
	private float halfCellAngle = 12.85714f;
	private float arrowCenterX;
	private float arrowCenterY;
	private int rx;

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
	// Region mImageRegion;
	boolean canImageMove;
	long currentTime = 0;

	boolean touching = false;
	int spixs[];

	Matrix rotateMatrix;
	Matrix arrowRotateMatrix;
	float currentRotation = 0.0f;
	float currentArrowRotation = 0.0f;
	float maxArrowRotation = 0.80f;
	float roulArrowAcc = 0.0f;
	float roulArrowVel = 0.0f;
	float currentPath = 0.0f;
	float lastPath = 0.0f;

	 

	private Paint textPaint;

	private MediaPlayer audioPlayer;
	Paint antiAliasPaint;
	Paint alphaPaint;

	public RouletteRenderer(Context context) {
		super(context);
		setWillNotDraw(false);
		rotateMatrix = new Matrix();
		arrowRotateMatrix = new Matrix();
		holder = getHolder();
		instance = this;
		roulette = BitmapFactory.decodeResource(getResources(),
				R.drawable.roulette);
		roulArrow = BitmapFactory.decodeResource(getResources(),
				R.drawable.roulette_arrow);

		pushButtonOff = BitmapFactory.decodeResource(getResources(),
				R.drawable.pushbtn_off);
		pushButtonOn = BitmapFactory.decodeResource(getResources(),
				R.drawable.pushbtn_on);

		greenButtonPressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.r_greenbtn_normal);
		greenButtonNormal = BitmapFactory.decodeResource(getResources(),
				R.drawable.r_greenbtn_normal);

		orangeButtonPressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.r_orangebtn_normal);
		orangeButtonNormal = BitmapFactory.decodeResource(getResources(),
				R.drawable.r_orangebtn_normal);

		middleButtonOff = BitmapFactory.decodeResource(getResources(),
				R.drawable.middlebtn_off);

		rouletteWidth = roulette.getWidth();
		rouletteHeight = roulette.getHeight();

		spixs = new int[5];
		WindowManager wm = (WindowManager) context
				.getSystemService(context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mScreenHeight = display.getHeight();
		mScreenWidth = display.getWidth();

		 

		rouletteX = mScreenWidth / 2 - roulette.getWidth() / 2;
		rouletteY = mScreenHeight / 6 + 50;

		roulArrowX = rouletteX + roulette.getWidth() / 2 - roulArrow.getWidth()
				/ 2;
		roulArrowY = rouletteY + -roulArrow.getHeight() / 2
				- roulArrow.getHeight() / 6;
		arrowCenterX = roulArrow.getWidth() / 2;
		arrowCenterY = (float) roulArrow.getHeight() * 16.0f / 42.0f;

		pushButtonX = rouletteX + roulette.getWidth() / 2
				- pushButtonOff.getWidth() / 2;
		pushButtonY = rouletteY + roulette.getHeight() / 2
				- pushButtonOff.getHeight() / 2;

		middleButtonX = mScreenWidth / 2 - middleButtonOff.getWidth() / 2;
		middleButtonY = mScreenHeight - middleButtonOff.getHeight()
				- middleButtonOff.getHeight() / 2;

		greenButtonX = middleButtonX + middleButtonOff.getWidth() / 2;
		greenButtonY = middleButtonY
				+ (middleButtonOff.getHeight() - greenButtonNormal.getHeight())
				/ 2;

		orangeButtonX = middleButtonX + middleButtonOff.getWidth() / 2
				- orangeButtonNormal.getWidth();
		orangeButtonY = middleButtonY
				+ (middleButtonOff.getHeight() - orangeButtonNormal.getHeight())
				/ 2;

		rotateMatrix.setTranslate(rouletteX, rouletteY);
		arrowRotateMatrix.setTranslate(roulArrowX, roulArrowY);
		antiAliasPaint = new Paint();
		antiAliasPaint.setFilterBitmap(true);
		alphaPaint = new Paint();
		alphaPaint.setAlpha(32);

		textPaint = new Paint();
		textPaint.setTextAlign(Align.LEFT);
	 
	}

	public void setOnGameEndListener(OnGameEndListener l) {
		this.onGameEndListener = l;
	}

	public boolean onTouchEvent(MotionEvent event) {

		if (currentState == STOPING_STATE)
			return true;

		int positionX = (int) event.getRawX();
		int positionY = (int) event.getRawY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			if (currentState == OFF_STATE) {
				if (bitmapTouched(positionX, positionY, greenButtonNormal,
						greenButtonX, greenButtonY)) {
					audioPlayer = MediaPlayer.create(
							RouletteRenderer.this.getContext(), R.raw.spinone);
					audioPlayer.setLooping(true);
					if (isWin)
						choosedColor = GREEN_COLOR;
					else
						choosedColor = ORANGE_COLOR;
					
					rouletteColor = GREEN_COLOR;
					currentState = TWISTING_STATE;
					choosed = true;
					currentTime = System.currentTimeMillis();
					roulAngVel = roulAngPreVel;
					Log.d(VIEW_LOG_TAG, "Twist");

					audioPlayer.start();
				}
				if (bitmapTouched(positionX, positionY, orangeButtonNormal,
						orangeButtonX, orangeButtonY)) {
					audioPlayer = MediaPlayer.create(
							RouletteRenderer.this.getContext(), R.raw.spinone);
					audioPlayer.setLooping(true);
					if (isWin)
						choosedColor = ORANGE_COLOR;
					else
						choosedColor = GREEN_COLOR;
					rouletteColor = ORANGE_COLOR;
					currentState = TWISTING_STATE;
					choosed = true;
					currentTime = System.currentTimeMillis();
					roulAngVel = roulAngPreVel;
					Log.d(VIEW_LOG_TAG, "Twist");

					audioPlayer.start();
				}
			}

			if (stopTouched(positionX, positionY)
					&& currentState == TWISTING_STATE) {

				audioPlayer.stop();
				audioPlayer = MediaPlayer.create(
						RouletteRenderer.this.getContext(), R.raw.spintwo);
				audioPlayer.setLooping(false);
				audioPlayer.start();
				currentState = STOPING_STATE;
				currentPath = currentRotation;
				float f = (currentRotation + midPath) / cellAngle;
				float m = (currentRotation + midPath) % cellAngle;

				if (((int) f) % 2 == choosedColor) {
					roulAngAcc = roulAngPreVel * roulAngPreVel
							/ (2.0f * (midPath));
					lastPath = midPath;
				} else {
					if (m > halfCellAngle) {
						roulAngAcc = roulAngPreVel * roulAngPreVel
								/ (2.0f * (midPath + 14));
						lastPath = midPath + 14;
					} else {
						roulAngAcc = roulAngPreVel * roulAngPreVel
								/ (2.0f * (midPath - 14));
						lastPath = midPath - 14;
					}

				}

				Log.d(VIEW_LOG_TAG, "Stop  " + roulAngAcc);

			}
		}
			break;

		case MotionEvent.ACTION_MOVE: {

		}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return true;
	}

	private boolean stopTouched(int tX, int tY) {
		return tX < rouletteX + roulette.getWidth() && tX > rouletteX
				&& tY - displayOffset > rouletteY && tY - displayOffset < rouletteY + roulette.getHeight();
	}

	private boolean bitmapTouched(int tX, int tY, Bitmap bitmap, int bX, int bY) {
		return tX < bX + bitmap.getWidth() && tX > bX && tY - displayOffset > bY
				&& tY - displayOffset < bY + bitmap.getHeight();
	}

	public void resume() {

		running = true;
		choosed = false;
		renderThread = new Thread(this);
		renderThread.start();

	}

	boolean ft  = false;
	@Override
	public void run() {

		while (running) {
			if (!holder.getSurface().isValid())
				continue;
			
			if (!ft) {
				LinearLayout a = ((LinearLayout) this.getParent());
				Log.d("games",
						"measuredHeight of gamesLayout "
								+ a.getMeasuredHeight());
				this.displayOffset = mScreenHeight - a.getMeasuredHeight();
				middleButtonY -=displayOffset;
				greenButtonY -=displayOffset;
				orangeButtonY -=displayOffset;
				roulArrowY -=displayOffset;
				rouletteY -=displayOffset;
				pushButtonY-=displayOffset;
				arrowRotateMatrix.setTranslate(roulArrowX, roulArrowY);
				rotateMatrix.setTranslate(rouletteX, rouletteY);
				ft = true;
			}
			Canvas canvas = holder.lockCanvas();
			canvas.drawRGB(228, 228, 228);
			if (currentState == TWISTING_STATE || currentState == STOPING_STATE) {

				float deltaTime = System.currentTimeMillis() - currentTime;
				currentTime = System.currentTimeMillis();
				rotateMatrix.preTranslate(0, 0);
				rotateMatrix.setRotate(currentRotation,
						roulette.getWidth() / 2, roulette.getHeight() / 2);
				currentRotation += roulAngVel * deltaTime;
				rotateMatrix.postTranslate(rouletteX, rouletteY);

				arrowRotateMatrix.preTranslate(0, 0);
				arrowRotateMatrix.setRotate(currentArrowRotation, arrowCenterX,
						arrowCenterY);
				arrowRotateMatrix.postTranslate(roulArrowX, roulArrowY);

				float offset = (int) ((currentRotation) % (halfCellAngle));
				float ov = 4;
				float ovb = 5;
				float ovk = 6;
				
				float maxoffset = (ovb+ov);
				roulArrowVel = 2f;
				 
				 
					currentArrowRotation += roulArrowVel;
					 

					if (offset < ovb || offset > halfCellAngle - ov) {
						if (offset > halfCellAngle - ov)
							offset -= halfCellAngle - ov;
						else
							offset += ov;
						currentArrowRotation = Math.min(-(offset) * ovk,
								currentArrowRotation);
					} else {
						currentArrowRotation = -(maxoffset) * ovk;
					}
				 

				if (currentArrowRotation >= 0)
					currentArrowRotation = 0;

				if (currentState == STOPING_STATE) {

					roulAngAcc = roulAngVel
							* roulAngVel
							/ (2.0f * (lastPath - (currentRotation - currentPath)));

					roulAngVel -= (float) deltaTime * roulAngAcc;

					if (roulAngVel < 0.0001) {
						roulAngVel = 0;
						audioPlayer.stop();
						// pause();
 						currentState = END_STATE;
 						
						//this.onGameEndListener.onGameEnd(r);
					}
				}
			}

			if(currentState == END_STATE)
			{
				currentArrowRotation += roulArrowVel;
				arrowRotateMatrix.preTranslate(0, 0);
				arrowRotateMatrix.setRotate(currentArrowRotation, arrowCenterX,
						arrowCenterY);
				arrowRotateMatrix.postTranslate(roulArrowX, roulArrowY);
				if (currentArrowRotation >= 0)
				{
					currentArrowRotation = 0;
					//boolean r = ((int) ((currentRotation) / cellAngle) % 2) == choosedColor;
					currentState = OFF_STATE;
					
					 if(isWin)
					 {
					 audioPlayer = MediaPlayer.create(getContext(),
					 R.raw.win_gmae2);
					 }
					 else
					 {
					 audioPlayer = MediaPlayer.create(getContext(),
					 R.raw.fail);
					 }
					 audioPlayer.start();
					
					// if(r)
					// {
					// audioPlayer = MediaPlayer.create(getContext(),
					// R.raw.win);
					// }
					// else
					// {
					// audioPlayer = MediaPlayer.create(getContext(),
					// R.raw.fail);
					// }
					// audioPlayer.start();
					this.onGameEndListener.onGameEnd(isWin);
				}
			}
			canvas.drawBitmap(roulette, rotateMatrix, antiAliasPaint);
			canvas.drawBitmap(roulArrow, arrowRotateMatrix, antiAliasPaint);

			if (currentState == TWISTING_STATE) {
				canvas.drawBitmap(pushButtonOn, pushButtonX, pushButtonY, null);
			} else {
				canvas.drawBitmap(pushButtonOff, pushButtonX, pushButtonY, null);
			}
			if (currentState == TWISTING_STATE || currentState == STOPING_STATE || currentState == END_STATE || choosed) {
				if (rouletteColor == ORANGE_COLOR) {
					
					
					canvas.drawBitmap(greenButtonPressed, greenButtonX,
							greenButtonY, alphaPaint);
					canvas.drawBitmap(orangeButtonNormal, orangeButtonX,
							orangeButtonY, null);
				} else {
					canvas.drawBitmap(greenButtonNormal, greenButtonX,
							greenButtonY, null);
					canvas.drawBitmap(orangeButtonPressed, orangeButtonX,
							orangeButtonY, alphaPaint);
				}
			} else {
				canvas.drawBitmap(greenButtonNormal, greenButtonX,
						greenButtonY, null);
				canvas.drawBitmap(orangeButtonNormal, orangeButtonX,
						orangeButtonY, null);
			}
			canvas.drawBitmap(middleButtonOff, middleButtonX, middleButtonY,
					null);
//			canvas.drawBitmap(gameTitle, gameTitleX, gameTitleY, null);
//
//			canvas.drawBitmap(coins, coinsX - 20, coinsY, null);
//			canvas.drawText(
//					prize,
//					gameTitleX + gameTitle.getWidth() / 2 - 20,
//					gameTitleY + gameTitle.getHeight() / 2
//							+ gameTitle.getHeight() / 4, textPaint);
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
		// Log.d(VIEW_LOG_TAG, "thread paused");
	}

	public static RouletteRenderer getInstance(Context context) {
		if (instance == null)
			instance = new RouletteRenderer(context);
		return instance;
	}

}
