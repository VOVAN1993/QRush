package ru.qrushtabs.app.badges;

import ru.qrushtabs.app.profile.ProfileInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ScansBadge extends Badge {
	
	public int scansCount;
 	public ScansBadge(String name,Drawable d,Drawable ds,int count)
	{
 		this.smallBadgeIcon = ds;
		this.badgeIcon = d;
		this.name =name;
		scansCount = count;
	}
 
	@Override
	public boolean isAchieved()
	{
		if(ProfileInfo.getScansCount() >= scansCount)
		{
			if(!achieved)
				achieved = achieve();			
 		}
		else
			this.achieved = false;
			
		return achieved;
	}

}
