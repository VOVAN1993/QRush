package ru.qrushtabs.app.games;

import ru.qrushtabs.app.MainActivity;
import ru.qrushtabs.app.R;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class ChestsRenderer extends GameRenderer implements Runnable {
	private Thread renderThread = null;
	private SurfaceHolder holder;
	public boolean running = false;
	private Bitmap[] frames;
 	private Chest chests[];
 	private int chestX;
 	private int chestY;
 
	private int currentMatchIndex = 0;
	private int curChestsCount = 3;
	private static ChestsRenderer instance;
    private Bitmap gameTitle;
    private int gameTitleX;
    private int gameTitleY;
    
    private Paint textPaint;
	private OnGameEndListener onGameEndListener;
  	private int mTouchSlop;
	int mScreenHeight;
	int mScreenWidth;
	static final int TOUCH_MODE_TAP = 1;
	static final int TOUCH_MODE_DOWN = 2;
 
	Rect mImagePosition;
	Region mImageRegion;
 
	long currentTime = 0;

	 
	private boolean isEnd = false;
	private boolean isOpening = false;
	
	public ChestsRenderer(Context context) {
		super(context);
		holder = getHolder();
		instance = this;
		chests = new Chest[curChestsCount];
		frames = new Bitmap[4];
		frames[0] =  BitmapFactory.decodeResource(getResources(),
				R.drawable.chest_1);
		frames[1] =  BitmapFactory.decodeResource(getResources(),
				R.drawable.chest_2);
		frames[2] =  BitmapFactory.decodeResource(getResources(),
				R.drawable.chest_3);
		frames[3] =  BitmapFactory.decodeResource(getResources(),
				R.drawable.chest_4);
		
		gameTitle = BitmapFactory.decodeResource(getResources(),
				R.drawable.gametitle);
		
		WindowManager wm = (WindowManager) context
				.getSystemService(context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mScreenHeight = display.getHeight();
		mScreenWidth = display.getWidth();

		int r = 10;
		chestX = mScreenWidth / 2 - frames[0].getWidth() / 2 - frames[0].getWidth() - r ;
		chestY = mScreenHeight / 2 - frames[0].getHeight();
		gameTitleX = mScreenWidth / 2 - gameTitle.getWidth()/2;
		gameTitleY = 20;
		 for(int i = 0;i < curChestsCount;i++)
		 {
			 chests[i] = new Chest(this.getContext(),frames);
			 chests[i].setX(chestX + r + frames[0].getWidth()*i);
			 chests[i].setY(chestY);
		 }

		
		
		 textPaint = new Paint();
		 textPaint.setTextAlign(Align.CENTER);
		// Typeface tf = Typeface.createFromAsset(MainActivity.getInstance().getAssets(), "fonts/lobster.ttf");  
		 //textPaint.setTypeface(tf);
		 textPaint.setTextSize(28);
		 textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
 		 textPaint.setColor(0xffffffff);
 		 
 
		

	}

	public void setOnGameEndListener(OnGameEndListener l) {
		this.onGameEndListener = l;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (isOpening)
			return true;
		int positionX = (int) event.getRawX();
		int positionY = (int) event.getRawY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			for (int i = 0; i < curChestsCount; i++)
				if (chests[i].isTouches(positionX, positionY)) {
					chests[i].isOpening = true;
					isOpening = true; 
					break;
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

	public void resume() {

		isOpening = false;
 		isEnd = false;
 		 for(int i = 0;i < curChestsCount;i++)
		 {
			 chests[i].refresh();
			 
		 }
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

			
			long deltaTime = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();
			for (int i = 0; i < curChestsCount; i++) {
				chests[i].draw(canvas, deltaTime);
				if (chests[i].isOpened) 
				{
						isEnd = true;
				}
			}
			canvas.drawBitmap(gameTitle, gameTitleX, gameTitleY, null);
			canvas.drawText("15456",gameTitleX+gameTitle.getWidth()/2 , gameTitleY+gameTitle.getHeight()/2, textPaint);
			holder.unlockCanvasAndPost(canvas);
			if(isEnd)
			{
				if(isWin)
					this.onGameEndListener.onGameEnd(true);
				else
					this.onGameEndListener.onGameEnd(true);
			}

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
 	}

	public static ChestsRenderer getInstance(Context context) {
		if (instance == null)
			instance = new ChestsRenderer(context);
		return instance;
	}

}
