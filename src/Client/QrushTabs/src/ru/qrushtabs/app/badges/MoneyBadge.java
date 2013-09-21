package ru.qrushtabs.app.badges;

import ru.qrushtabs.app.profile.ProfileInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MoneyBadge extends Badge {
	
	public int moneyCount;
	
	public MoneyBadge(String name,Drawable d,int count)
	{
		this.badgeIcon = d;
		moneyCount = count;
		this.name = name;
	}
	@Override
	public boolean isAchieved()
	{
		if(ProfileInfo.getMoneyCount() >= moneyCount)
		{
			if(!achieved)
				achieved = achieve();			
 		}
		else
			this.achieved = false;
			
		return achieved;
	}

}
