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

abstract public class GameRenderer extends SurfaceView{
 
	 
	 
	private static GameRenderer instance;
	protected int displayOffset;
	public boolean isWin = false;
	protected OnGameEndListener onGameEndListener;
	public GameRenderer(Context context)
	{
		super(context);
	}
	
	public void setOnGameEndListener(OnGameEndListener l) {
		this.onGameEndListener = l;
 	}

	 
	abstract public void resume();
	abstract public void pause();
	

	 
	 

}
