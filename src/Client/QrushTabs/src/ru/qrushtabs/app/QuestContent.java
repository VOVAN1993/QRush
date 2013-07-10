package ru.qrushtabs.app;

import android.graphics.Bitmap;
import android.util.Log;

public class QuestContent 
{
	private String content;
	private Bitmap bitmap;
	private boolean isActive = false;
	
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public void setIsActive(boolean isActive)
	{
		this.isActive = isActive;
	}
	public boolean getIsActive()
	{
		return isActive;
	}
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap)
	{
		Log.d(null, "Set bitmap");
		this.bitmap = bitmap;
	}
}
