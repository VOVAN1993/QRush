package ru.qrushtabs.app;

import android.graphics.Bitmap;
import android.util.Log;

public class NewsContent 
{
	private String content;
	private Bitmap bitmap;
	private boolean scannable = true;
	
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public void setScannable(boolean scannable)
	{
		this.scannable = scannable;
	}
	public boolean getScannable()
	{
		return scannable;
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
