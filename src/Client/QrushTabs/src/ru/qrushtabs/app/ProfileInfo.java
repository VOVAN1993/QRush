package ru.qrushtabs.app;

import ru.qrushtabs.app.utils.OnInfoLoadListener;

public class ProfileInfo {

	private static int scansCount = 4;
	private static int rescansCount = 4;
	private static float moneyCount = 34.50f ; 
	private static boolean loaded = true;
	public static String userID = "0";
	public static String userPass = "0";
	public static int getScansCount()
	{
		if(loaded)
		    return scansCount;
		else
			return 0;
	}
	public static float getMoneyCount()
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
	
	public static void saveInfo(OnInfoLoadListener l)
	{
		
	}
	public static void loadInfo(OnInfoLoadListener l)
	{
		
	}
}
