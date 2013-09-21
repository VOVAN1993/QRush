package ru.qrushtabs.app.games;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class Chest extends View {
	public int width;
	public int height;
	private Bitmap[] frames;
	private int currentFrame = 0;
	private int lastFrame = 0;
	private int period = 250;
	private int currentPeriod = 0;
	public int x;
	public int y;
	 
	 
	
	public boolean isOpening = false;
	public boolean isOpened = false;

	public Chest(Context context,Bitmap[] frames) {
		super(context);
		this.frames = frames;
		lastFrame = frames.length - 1;
		width = frames[0].getWidth();
		height = frames[0].getHeight();
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
		if (isOpening) 
		{
			if(currentFrame==0)
				currentFrame++;
			else
			{
				currentPeriod+=deltaTime;
				if(currentPeriod>=period)
				{
					if(currentFrame==lastFrame)
					{
						isOpening=false;
						isOpened =true;
					}
					else
					{
						currentFrame++;
					}
					currentPeriod=0;
				}
			}
		}
		canvas.drawBitmap(frames[currentFrame], x - (frames[currentFrame].getWidth() - frames[0].getWidth()) , y - (frames[currentFrame].getHeight() - frames[0].getHeight()), null);
	}



	public void refresh() {
		currentFrame = 0;
		isOpening = false;
		isOpened = false;
 		
	}
 
}
