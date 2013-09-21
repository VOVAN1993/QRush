package ru.qrushtabs.app.badges;

import ru.qrushtabs.app.utils.ServerAPI;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
 
public class Badge {
	public Drawable badgeIcon;
	public boolean achieved = false;
	public String name;
	public Badge(Drawable bmp)
	{
		this.badgeIcon = bmp;
	}
	public Badge()
	{
		
	}
	public boolean isAchieved()
	{
		return false;
	}
	public void draw(ImageView iv)
	{
		 iv.setImageDrawable(badgeIcon);
		 if(achieved)
				iv.setAlpha(255);
			else
				iv.setAlpha(60);
	}
	public boolean achieve()
	{
		if(ServerAPI.addAchievment(name).equals("true"))
			return true;
		else		
			return false;
	}

}
