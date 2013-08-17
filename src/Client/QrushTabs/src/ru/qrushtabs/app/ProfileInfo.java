package ru.qrushtabs.app;

import ru.qrushtabs.app.utils.OnInfoLoadListener;

public class ProfileInfo {

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
		//добавление на сервер и в склайт
	}
	
	public static void saveInfo(OnInfoLoadListener l)
	{
		
	}
	public static void loadInfo(OnInfoLoadListener l)
	{
		
	}
}
