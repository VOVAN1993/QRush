package ru.qrushtabs.app.games;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.dialogs.ToTwiceDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MatchesRenderer extends GameRenderer implements Runnable {
	private Thread renderThread = null;
	private SurfaceHolder holder;
	public boolean running = false;
	private Bitmap spichBox;
	private Bitmap spichText;
	private Match matches[];
	// private MyImage shortMatch;
	// private MyImage longMatch;
	private int currentMatchIndex = 0;
	private int curSpichCount = 3;
	private static MatchesRenderer instance;
	private int curShortSpich = 0;
	private int boxX;
	private int boxY;
	private int rx;

	private OnGameEndListener onGameEndListener;
  	private int mTouchSlop;
	int mScreenHeight;
	int mScreenWidth;
	static final int TOUCH_MODE_TAP = 1;
	static final int TOUCH_MODE_DOWN = 2;
	int boxWidth = 100;
	int boxHeight = 100;
	int vely = 100;
	int pvely = 100;
	int nvely = -100;
	Rect mImagePosition;
	Region mImageRegion;
	boolean canImageMove;
	long currentTime = 0;

	int spixs[];

	private Paint alphaPaint;
	private int boxAlpha = 100;
	private boolean matchPulled = false;
	private boolean isEnd = false;

	public MatchesRenderer(Context context) {
		super(context);
		holder = getHolder();
		instance = this;
		spichBox = BitmapFactory.decodeResource(getResources(),
				R.drawable.spichbox);
		spichText = BitmapFactory.decodeResource(getResources(),
				R.drawable.spich_text);

		matches = new Match[5];

		for (int i = 1; i < 5; i++) {
			matches[i] = new Match(context, BitmapFactory.decodeResource(
					getResources(), R.drawable.longspich));

		}

		matches[0] = new Match(context, BitmapFactory.decodeResource(
				getResources(), R.drawable.shortspich));

		alphaPaint = new Paint();
		boxWidth = spichBox.getWidth();
		boxHeight = spichBox.getHeight();

		spixs = new int[5];
		WindowManager wm = (WindowManager) context
				.getSystemService(context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mScreenHeight = display.getHeight();
		mScreenWidth = display.getWidth();

		boxX = mScreenWidth / 2 - spichBox.getWidth() / 2;
		boxY = mScreenHeight / 2 - spichBox.getHeight();

	}

	public void setOnGameEndListener(OnGameEndListener l) {
		this.onGameEndListener = l;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (matchPulled)
			return true;
		int positionX = (int) event.getRawX();
		int positionY = (int) event.getRawY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			for (int i = 0; i < curSpichCount; i++)
				if (matches[i].isTouches(positionX, positionY)) {
					Log.d(VIEW_LOG_TAG, String.valueOf(positionY));
					currentTime = System.currentTimeMillis();
					currentMatchIndex = i;
					matches[currentMatchIndex].beginDrag(positionY);
					canImageMove = true;
					break;
				}
		}
			break;

		case MotionEvent.ACTION_MOVE: {
			if (canImageMove == true) {
				matches[currentMatchIndex].continueDrag(positionY);

			}
		}
			break;
		case MotionEvent.ACTION_UP:

			canImageMove = false;
			matches[currentMatchIndex].stopDrag();
			break;
		}
		return true;
	}

	public void resume() {

		matchPulled = false;
		isEnd = false;
		boxAlpha = 255;
		alphaPaint.setAlpha(boxAlpha);
		curShortSpich = (int) (Math.random() * curSpichCount);
		rx = spichBox.getWidth() / (curSpichCount + 1);

		for (int i = 0; i < curSpichCount; i++) {
			spixs[i] = mScreenWidth / 2 - spichBox.getWidth() / 2
					+ (int) (rx * ((float) i + 0.5));
		}
		 
			int si = (int) (Math.random() * curSpichCount);
			int xx = spixs[0];
			spixs[0] = spixs[curShortSpich];
			spixs[curShortSpich] = xx;
		 
		for (int i = 0; i < curSpichCount; i++) {
			matches[i].x = spixs[i];
			matches[i].setY(boxY + spichBox.getHeight() / 4);
		}

		matches[0].setY( boxY + spichBox.getHeight() / 4 + (matches[1].height - matches[0].height));
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
			for (int i = 0; i < curSpichCount; i++) {
				matches[i].draw(canvas, deltaTime);
			}
			if (matches[currentMatchIndex].isPulled()) {
				matchPulled = true;
			}
			
			if (matchPulled) {

				alphaPaint.setAlpha(boxAlpha);
				boxAlpha-=deltaTime/10;
				if(boxAlpha<0)
					isEnd = true;
			}
			
			canvas.drawBitmap(spichBox, boxX, boxY, alphaPaint);
			canvas.drawBitmap(spichText, mScreenWidth/2 - spichText.getWidth()/2, boxY + spichBox.getHeight()/2 - spichText.getHeight()/2, alphaPaint);
			holder.unlockCanvasAndPost(canvas);
			if(isEnd)
			{
				if(currentMatchIndex!=0)
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

	public static MatchesRenderer getInstance(Context context) {
		if (instance == null)
			instance = new MatchesRenderer(context);
		return instance;
	}

}
