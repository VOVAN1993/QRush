package ru.qrushtabs.app.profile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import ru.qrushtabs.app.badges.Badges;
import ru.qrushtabs.app.friends.FriendField;
import ru.qrushtabs.app.utils.OnInfoLoadListener;

public class ProfileInfo {

	public static final int maxPhotoWidth = 200;
	public static final int maxPhotoHeight = 200;
	
	private static int scansCount = 0;
	private static int rescansCount = 0;
	private static int moneyCount = 0 ; 
	
	private static boolean loaded = true;
	public static String sex = "M";
	public static String mail = "mail@mail.com";
	public static String signInType = "def";
	public static String username = "0";
	public static String userPass = "0";
	public static String userToken = "0";
	public static String userVKID = "0";
 	public static String userVKToken = "0";
 	public static String deviceID = "0";
 	public static String birthday = "0";
 	public static Bitmap avatarBitmap = null;
 	public static String avatarPath = "0";
 	public static File avatarFile = null;
 	
 	public static List<String> scansList = new ArrayList<String>();
 	public static FriendField[] friendsList;;
 	public static boolean friendsChanged = true;

 	public static List<String> myFriendsReqList = new ArrayList<String>();
 	
 	public static String city = "�������������";
 	public static String cityId = "0";
 	
 	public static boolean haveScan(String scan)
 	{
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
	public static void addScan(String scanInfo)
	{
		ProfileInfo.scansCount++;
		Badges.checkBadges();
		//���������� �� ������ � � ������
	}
	public static void setScansCount(int scansCount)
	{
		ProfileInfo.scansCount = scansCount;
		//���������� �� ������ � � ������
	}
	public static void setRescansCount(int rescansCount)
	{
		ProfileInfo.rescansCount = rescansCount;
		//���������� �� ������ � � ������
	}
	public static void setMoneyCount(int moneyCount)
	{
		ProfileInfo.moneyCount = moneyCount;
		//���������� �� ������ � � ������
	}
	public static void addMoneyCount(int moneyCount)
	{
		ProfileInfo.moneyCount += moneyCount;
		Badges.checkBadges();
		//���������� �� ������ � � ������
	}
	
	public static void saveInfo(OnInfoLoadListener l)
	{
		
	}
	public static void loadInfo(OnInfoLoadListener l)
	{
		
	}
}