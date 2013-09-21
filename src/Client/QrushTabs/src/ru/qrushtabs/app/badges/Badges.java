package ru.qrushtabs.app.badges;

import java.util.ArrayList;

import ru.qrushtabs.app.R;
import ru.qrushtabs.app.profile.ProfileInfo;
import ru.qrushtabs.app.utils.ServerAPI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class Badges 
{
	private static ArrayList<Badge> badges = new ArrayList<Badge>();
	
	public static void initBadges(Context c)
	{
		Resources res = c.getResources();
		badges.clear();
		Badge scan50 = new ScansBadge("scan50",res.getDrawable(R.drawable.bg_scan50_small),50);
		Badge scan100 = new ScansBadge("scan100",res.getDrawable(R.drawable.bg_scan100_small),100);
		Badge scan500 = new ScansBadge("scan500",res.getDrawable(R.drawable.bg_scan500_small),500);
		Badge scan1000 = new ScansBadge("scan1000",res.getDrawable(R.drawable.bg_scan1000_small),1000);
		
		Badge money50 = new MoneyBadge("money50",res.getDrawable(R.drawable.bg_50monet_small),50);
		Badge money100 = new MoneyBadge("money100",res.getDrawable(R.drawable.bg_100monet_small),100);
		Badge money500 = new MoneyBadge("money500",res.getDrawable(R.drawable.bg_500monet_small),500);
		Badge money1000 = new MoneyBadge("money1000",res.getDrawable(R.drawable.bg_1000monet_small),1000);
		
		badges.add(scan50);
		badges.add(scan100);
		badges.add(scan500);
		badges.add(scan1000);
		
		badges.add(money50);
		badges.add(money100);
		badges.add(money500);
		badges.add(money1000);
		
		
		String str[] = ServerAPI.getAchievments(ProfileInfo.username);
		for(int i= 0;i<str.length;i++)
		{
			for(int j = 0;j<badges.size();j++)
			{
				if(badges.get(j).name.equals(str[i]))
				{
					badges.get(j).achieved = true;
				}
			}
		}
	}
	
	public static void checkBadges()
	{
		int l = badges.size();
		for(int i = 0; i < l;i++)
		{
			badges.get(i).isAchieved();
		}
	}

	public static ArrayList<Badge> getBadges() {
		// TODO Auto-generated method stub
		return badges;
	}
	public static Drawable getDrawable(String badgeName)
	{
		int l = badges.size();
		for(int i = 0; i < l;i++)
		{
			if(badges.get(i).name.equals(badgeName))
				return badges.get(i).badgeIcon;
		}
		return null;
	}
	public static ArrayList<Badge> getAchievedBadges() {
		ArrayList<Badge> achievedBadges = new ArrayList<Badge>(); 
		int l = badges.size();
		for(int i = 0; i < l;i++)
		{
			if(badges.get(i).achieved)
				achievedBadges.add(badges.get(i));
		}
		return achievedBadges;
	}
}
