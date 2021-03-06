package ru.qrushtabs.app.games;

import ru.qrushtabs.app.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;

public class Coin extends View {
	public int width;
	public int height;
	private Bitmap coin;
	private int currentFrame = 0;
	private int lastFrame = 30;
	private int period = 30;
	private int currentPeriod = 0;
	public int x;
	public int y;
	 
	 
	
	 
	public boolean isFlyed = false;
	private MediaPlayer waudioPlayer;

	public Coin(Context context) {
		super(context);
		
		coin = BitmapFactory.decodeResource(getResources(),
				R.drawable.chest_coin);
		width = coin.getWidth();
		height = coin.getHeight();
		waudioPlayer  = MediaPlayer.create(context, R.raw.chest_win);
		 
	}
 

	 
	public void setX(int x)
	{
		this.x = x;
		
	}
	public void setY(int y)
	{
		this.y = y;
 		
	}
	 

	public boolean isTouches(int tX, int tY) {
		return tX < x + width && tX > x && tY > y && tY < y + height;
	}

	public void draw(Canvas canvas, long deltaTime) {
		 
			if(currentFrame==0)
			{
				currentFrame++;
				waudioPlayer.start();
			}
			else
			{
				currentPeriod+=deltaTime;
				if(currentPeriod>=period)
				{
					if(!waudioPlayer.isPlaying())
					{ 
						isFlyed =true;
 					}
					else
					{
						currentFrame++;
					}
					currentPeriod=0;
				}
			}
		
 		canvas.drawBitmap(coin, x , y - currentFrame*2, null);
	}



	public void refresh() {
		currentFrame = 0;
		isFlyed = false;
 		
	}
 
}
