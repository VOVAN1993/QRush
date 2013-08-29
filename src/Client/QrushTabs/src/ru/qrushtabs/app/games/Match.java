package ru.qrushtabs.app.games;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class Match extends View {
	public int width;
	public int height;
	private Bitmap bitmap;
	private int prevY;
	private int destY;
	public int x;
	public int y;
	public int placeY;
	public int vely = 400;

	private int pullDist = 40;
	private boolean isDragging = false;

	public Match(Context context, Bitmap bitmap) {
		super(context);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		this.bitmap = bitmap;
	}

	public void beginDrag(int prevY) {
		isDragging = true;
		this.prevY = prevY;
		this.destY = prevY;
	}

	public void stopDrag() {
		isDragging = false;
	}
	public void setX(int x)
	{
		this.x = x;
		
	}
	public void setY(int y)
	{
		this.y = y;
		placeY = y;
		
	}
	public void continueDrag(int destY) {
		this.destY = destY;
	}

	public boolean isTouches(int tX, int tY) {
		return tX < x + width && tX > x && tY > y && tY < y + height;
	}

	public void draw(Canvas canvas, long deltaTime) {
		if (isDragging) {
			int deltaY = destY - prevY;
			int tDeltaY = (int) (deltaTime * ((float) vely / 1000.0));
			if (deltaY > 0) 
			{
				
				y += Math.min(deltaY, tDeltaY);
				prevY += Math.min(deltaY, tDeltaY);
				if(y<placeY)
				{
					prevY = prevY - (y - placeY);
					y = placeY;
				}
				
			} else 
			{
//				y += Math.max(deltaY, -tDeltaY);
//				prevY += Math.max(deltaY, -tDeltaY);
			}
		}
		canvas.drawBitmap(bitmap, x, y, null);
	}
	public boolean isPulled()
	{
		if(y > placeY + pullDist)
			return true;
		else
		    return false;
	}
}
