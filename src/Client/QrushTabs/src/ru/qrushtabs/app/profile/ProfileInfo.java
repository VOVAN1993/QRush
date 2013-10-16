package ru.qrushtabs.app.profile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import ru.qrushtabs.app.ScanObject;
import ru.qrushtabs.app.badges.Badges;
import ru.qrushtabs.app.friends.FriendField;
import ru.qrushtabs.app.utils.OnInfoLoadListener;

public class ProfileInfo {

	public static final int maxPhotoWidth = 200;
	public static final int maxPhotoHeight = 200;
	public static boolean needToWatchAd = false;
	private static int scansCount = 0;
	private static int rescansCount = 0;
	private static int moneyCount = 0 ; 
	private static int allMoneyCount = 0 ; 
	
	private static boolean loaded = true;
	public static boolean newsChanged = true;
	public static String sex = "M";
	public static String mail = null;
	public static String signInType = null;
	public static String username = null;
	public static String userPass = null;
	public static String userToken = null;
	public static String userVKID = null;
 	public static String userVKToken = null;
 	public static String deviceID = null;
 	public static String birthday = null;
 	public static Bitmap avatarBitmap = null;
 	public static String avatarPath = null;
 	public static File avatarFile = null;
 	
 	public static List<String> scansList = new ArrayList<String>();
 	public static FriendField[] friendsList;;
 	public static boolean friendsChanged = true;
 	public static boolean profileChanged = true;

 	public static List<String> myFriendsReqList = new ArrayList<String>();
 	
 	public static String city = "ђепрежевальск";
 	public static String cityId = "0";
 	
 	public static boolean haveScan(String scan)
 	{
 		if(scansList==null)
 			return false;
 		for(int i = 0;i<scansList.size();i++)
 			if(scansList.get(i).equals(scan))
 				return true;
 		return false;
 	}
 	
 
 	public static boolean checkMyFriend(String name)
 	{
 		for(int i = 0;i<friendsList.length;i++)
 			if(friendsList[i].username.equals(name))
 				return false;
 		return true;
 	}
 	public static void syncScans(ArrayList<ScanObject> scans)
 	{
 		scansList.clear();
 		for(int i = 0;i<scans.size();i++)
 		{
 			scansList.add(scans.get(i).code);
 		}
 	}
 	public static void removeFriend(String name)
 	{
 		for(int i = 0;i<friendsList.length;i++)
 			if(friendsList[i].username.equals(name))
 				friendsList[i].username = "0";
 		
 		
 			 friendsChanged = true;
 	}
	public static int getScansCount()
	{
		if(loaded)
		    return scansCount;
		else
			return 0;
	}
	public static int getRescansCount()
	{
		if(loaded)
		    return rescansCount;
		else
			return 0;
	}
	public static int getMoneyCount()
	{
		if(loaded)
		    return moneyCount;
		else
			return 0;
	}
	public static void addScan(String scanInfo, String type)
	{
		newsChanged = true;
		if(type.equals(ScanObject.SCAN))
			ProfileInfo.scansCount++;
		else
			ProfileInfo.rescansCount++;
		scansList.add(scanInfo);
		Badges.checkBadges();
		//добавление на сервер и в склайт
	}
	public static void setScansCount(int scansCount)
	{
		ProfileInfo.scansCount = scansCount;
		//добавление на сервер и в склайт
	}
	public static void setRescansCount(int rescansCount)
	{
		ProfileInfo.rescansCount = rescansCount;
		//добавление на сервер и в склайт
	}
	public static void setMoneyCount(int moneyCount)
	{
		ProfileInfo.moneyCount = moneyCount;
		//добавление на сервер и в склайт
	}
	public static void addMoneyCount(int moneyCount)
	{
		ProfileInfo.moneyCount += moneyCount;
		ProfileInfo.allMoneyCount = Math.max(moneyCount, allMoneyCount);
		Badges.checkBadges();
		//добавление на сервер и в склайт
	}
	public static void setTotalSum(int total)
	{
		allMoneyCount = total;
	}
	public static void saveInfo(OnInfoLoadListener l)
	{
		
	}
	public static void loadInfo(OnInfoLoadListener l)
	{
		
	}
}
